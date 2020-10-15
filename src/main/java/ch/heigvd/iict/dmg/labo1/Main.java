package ch.heigvd.iict.dmg.labo1;

import ch.heigvd.iict.dmg.labo1.indexer.CACMIndexer;
import ch.heigvd.iict.dmg.labo1.parsers.CACMParser;
import ch.heigvd.iict.dmg.labo1.queries.QueriesPerformer;
import ch.heigvd.iict.dmg.labo1.similarities.MySimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.Similarity;

public class Main {

	public static void main(String[] args) {


		//Maurice : made a global path for the indexes repo
		IndexPath.path = "index/shingle_1_3";


		// 1.1. create an analyzer
		Analyzer analyser = getAnalyzer();

		// TODO student "Tuning the Lucene Score"
		Similarity similarity = new MySimilarity();
		
		CACMIndexer indexer = new CACMIndexer(analyser, similarity);
		indexer.openIndex();
		CACMParser parser = new CACMParser("documents/cacm.txt", indexer);
		parser.startParsing();
		indexer.finalizeIndex();



		QueriesPerformer queriesPerformer = new QueriesPerformer(analyser, similarity);

		// Section "Reading Index"
		readingIndex(queriesPerformer);

		// Section "Searching"
		searching(queriesPerformer);

		queriesPerformer.close();
		
	}

	private static void readingIndex(QueriesPerformer queriesPerformer) {
		queriesPerformer.printTopRankingTerms("authors", 10);
		queriesPerformer.printTopRankingTerms("title", 10);

	}

	private static void searching(QueriesPerformer queriesPerformer) {
		// Example
		queriesPerformer.query("compiler program");

		// TODO student
        // queriesPerformer.query(<containing the term Information Retrieval>);
		// queriesPerformer.query(<containing both Information and Retrieval>);
        // and so on for all the queries asked on the instructions...
        //
		// Reminder: it must print the total number of results and
		// the top 10 results.
	}

	private static Analyzer getAnalyzer() {
	    // TODO <OK ?> student... For the part "Indexing and Searching CACM collection
		// - Indexing" use, as indicated in the instructions,
		// the StandardAnalyzer class.
		//
		// For the next part "Using different Analyzers" modify this method
		// and return the appropriate Analyzers asked.

		//StandardAnalyzer analyzer = new StandardAnalyzer();
		//WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
		//EnglishAnalyzer analyzer = new EnglishAnalyzer();

		//Shingler of size 1 and 2
		//ShingleAnalyzerWrapper analyzer = new ShingleAnalyzerWrapper();

		//Shingler of size 1 and 3
		ShingleAnalyzerWrapper analyzer = new ShingleAnalyzerWrapper(3, 3);

		return analyzer;
	}

}
