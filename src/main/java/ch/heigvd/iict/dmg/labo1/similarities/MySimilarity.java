package ch.heigvd.iict.dmg.labo1.similarities;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class MySimilarity extends ClassicSimilarity {

	// TODO student
	// Implement the functions described in section "Tuning the Lucene Score"
    @Override
    public float tf(float freq){
        if(freq <= 0)
            return 0;
        return 1 + (float) Math.log(freq);
    }
    @Override
    public float idf(long docFreq, long numDocs){
        return (float) Math.log(numDocs / (docFreq + 1.0)) + 1;
    }
    @Override
    public float lengthNorm(int numTerms){
        return 1;
    }

}
