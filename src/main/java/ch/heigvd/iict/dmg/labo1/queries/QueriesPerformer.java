package ch.heigvd.iict.dmg.labo1.queries;

import ch.heigvd.iict.dmg.labo1.IndexPath;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Comparator;

public class QueriesPerformer {
	
	private Analyzer		analyzer		= null;
	private IndexReader 	indexReader 	= null;
	private IndexSearcher 	indexSearcher 	= null;

	public QueriesPerformer(Analyzer analyzer, Similarity similarity) {
		this.analyzer = analyzer;
		Path path = FileSystems.getDefault().getPath(IndexPath.path);
		Directory dir;
		try {
			dir = FSDirectory.open(path);
			this.indexReader = DirectoryReader.open(dir);
			this.indexSearcher = new IndexSearcher(indexReader);
			if(similarity != null)
				this.indexSearcher.setSimilarity(similarity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printTopRankingTerms(String field, int numTerms) {
		// TODO student
		// This methods print the top ranking term for a field.
		// See "Reading Index".
	    System.out.println("Top ranking terms for field ["  + field +"] are: ");
		TermStats[] topTerms = null;
	    try {
			topTerms = HighFreqTerms.getHighFreqTerms(indexReader, numTerms, field, new Comparator<TermStats>() {
				@Override
				public int compare(TermStats o1, TermStats o2) {
					if (o1.totalTermFreq == o2.totalTermFreq)
						return 0;
					else if (o1.totalTermFreq < o2.totalTermFreq)
						return -1;
					else
						return 0;
				}
			});
		}catch (Exception e){
			e.printStackTrace();
			return;
		}
		for (TermStats term : topTerms){
			System.out.println("Value : " + term.termtext.utf8ToString());
			System.out.println("Freq. : " + term.totalTermFreq + "\n");
		}
	}
	
	public void query(String q) {
		// TODO student
		// See "Searching" section
		try {
			// 2.1. create query parser
			QueryParser parser = new QueryParser("title", analyzer);
			Query query = parser.parse(q);

			TopDocs topdocs = indexSearcher.search(query, 10);
			ScoreDoc[] hits = topdocs.scoreDocs;
			System.out.println("Searching for [" + q +"]");
			// 3.4. retrieve results
			System.out.println("Results found: " + topdocs.totalHits);

			for (ScoreDoc hit : hits) {
				Document doc = indexSearcher.doc(hit.doc);
				System.out.println(doc.get("id") + ": " + doc.get("title") + " (" + hit.score + ")");
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	 
	public void close() {
		if(this.indexReader != null)
			try { this.indexReader.close(); } catch(IOException e) { /* BEST EFFORT */ }
	}
	
}
