package ch.heigvd.iict.dmg.labo1;

import ch.heigvd.iict.dmg.labo1.indexer.CACMIndexer;
import ch.heigvd.iict.dmg.labo1.parsers.CACMParser;
import ch.heigvd.iict.dmg.labo1.queries.QueriesPerformer;
import ch.heigvd.iict.dmg.labo1.similarities.MySimilarity;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {


		//Maurice : made a global path for the indexes repo
		IndexPath.path = "index/english";

		// 1.1. create an analyzer
		Analyzer analyser = getAnalyzer();

		// TODO student "Tuning the Lucene Score"
		//Similarity similarity = new ClassicSimilarity();
		Similarity similarity = new MySimilarity();

		CACMIndexer indexer = new CACMIndexer(analyser, similarity);

		//Begin Time measure
		long begin = System.currentTimeMillis();

		indexer.openIndex();
		CACMParser parser = new CACMParser("documents/cacm.txt", indexer);
		parser.startParsing();
		indexer.finalizeIndex();

		//End time measure
		long end = System.currentTimeMillis();

		System.out.println("Analyze took : " + (end - begin) + " ms");

		QueriesPerformer queriesPerformer = new QueriesPerformer(analyser, similarity);

		// Section "Reading Index"
		readingIndex(queriesPerformer);

		// Section "Searching"
		searching(queriesPerformer);

		queriesPerformer.close();
		
	}

	private static void readingIndex(QueriesPerformer queriesPerformer) {
		queriesPerformer.printTopRankingTerms("author", 10);
		queriesPerformer.printTopRankingTerms("title", 10);
	}

	private static void searching(QueriesPerformer queriesPerformer) {
		// Example
		// TODO student
        // queriesPerformer	.query(<containing the term Information Retrieval>);
        // queriesPerformer.query(<containing both Information and Retrieval>);
        // and so on for all the queries asked on the instructions...
        //
        // Reminder: it must print the total number of results and
        // the top 10 results.

		//3.4
        //queriesPerformer.query("Information Retrieval");
        //queriesPerformer.query("Information AND Retrieval");
        //queriesPerformer.query("Retrieval AND (Information NOT Database)");
        //queriesPerformer.query("Info*");
        //queriesPerformer.query("Information Retrieval~5");


		//3.5
        queriesPerformer.query("compiler program");
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
		EnglishAnalyzer analyzer = new EnglishAnalyzer();

		//Shingler of size 1 and 2
		//ShingleAnalyzerWrapper analyzer = new ShingleAnalyzerWrapper(2, 2);

		//Shingler of size 1 and 3
		//ShingleAnalyzerWrapper analyzer = new ShingleAnalyzerWrapper(3, 3);

		/*StopAnalyzer analyzer = null;
		try {
			analyzer = new StopAnalyzer(Paths.get("common_words.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		return analyzer;
	}

}
