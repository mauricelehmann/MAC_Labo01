package ch.heigvd.iict.dmg.labo1.indexer;

import ch.heigvd.iict.dmg.labo1.IndexPath;
import ch.heigvd.iict.dmg.labo1.parsers.ParserListener;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CACMIndexer implements ParserListener {
	
	private Directory 	dir 			= null;
	private IndexWriter indexWriter 	= null;
	
	private Analyzer 	analyzer 		= null;
	private Similarity 	similarity 		= null;
	
	public CACMIndexer(Analyzer analyzer, Similarity similarity) {
		this.analyzer = analyzer;
		this.similarity = similarity;
	}
	
	public void openIndex() {
		// 1.2. create an index writer config
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE); // create and replace existing index
		iwc.setUseCompoundFile(false); // not pack newly written segments in a compound file: 
		//keep all segments of index separately on disk
		if(similarity != null)
			iwc.setSimilarity(similarity);
		// 1.3. create index writer
		Path path = FileSystems.getDefault().getPath(IndexPath.path);
		try {
			this.dir = FSDirectory.open(path);
			this.indexWriter = new IndexWriter(dir, iwc);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onNewDocument(Long id, String authors, String title, String summary) {
		Document doc = new Document();

		// TODO <OK ? > student: add to the document "doc" the fields given in
		// parameters. You job is to use the right Field and FieldType
		// for these parameters.


		//Maurice : Did that because the default summary value is set to null and not "" :/
		summary =  summary == null ? summary = "" : summary;

		//Maurice : Version with term vector enabled
		FieldType ft = new FieldType();
		ft.setIndexOptions(IndexOptions.DOCS);
		ft.setStoreTermVectors(true);
		//ft.setStoreTermVectorOffsets(true); //TODO : Maurice : enable ou pas?

		Field idField = new Field("id", id.toString(), ft);
		Field authorField = new Field("author", authors, ft);
		Field titleField = new Field("title", title, ft);
		Field summaryField = new Field("summary", summary, ft);

		doc.add(idField);
		doc.add(authorField);
		doc.add(titleField);
		doc.add(summaryField);


		//Maurice : Version without term vector enabled
		/*
		doc.add(new NumericDocValuesField("id", id));
		doc.add(new StringField("Authors", authors, Field.Store.NO));
		doc.add(new TextField("Title", title, Field.Store.NO));
		doc.add(new TextField("Summary", summary, Field.Store.YES));
		 */

		try {
			this.indexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void finalizeIndex() {
		if(this.indexWriter != null)
			try { this.indexWriter.close(); } catch(IOException e) { /* BEST EFFORT */ }
		if(this.dir != null)
			try { this.dir.close(); } catch(IOException e) { /* BEST EFFORT */ }
	}
	
}
