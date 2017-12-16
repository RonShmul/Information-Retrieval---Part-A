/**
 * Created by Sivan on 12/4/2017.
 */
public class Term {
    private String term;
    private int df;
    private int frequencyInCorup;
    private int pointerToPostings;
    private int getPointerToCache;

    //constructors


    public Term(String term) {
        this.term = term;
        this.df = 1;
        this.frequencyInCorup = 1;
    }

    public Term(String term, int df, int frequencyInCorup, int pointerToPostings, int getPointerToCache) {
        this.term = term;
        this.df = df;
        this.frequencyInCorup = frequencyInCorup;
        this.pointerToPostings = pointerToPostings;
        this.getPointerToCache = getPointerToCache;
    }

    //getters
    public String getTerm() {
        return term;
    }

    public int getDf() {
        return df;
    }

    public int getFrequencyInCorup() {
        return frequencyInCorup;
    }

    public int getPointerToPostings() {
        return pointerToPostings;
    }

    public int getGetPointerToCache() {
        return getPointerToCache;
    }

    //setters

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public void setFrequencyInCorup(int frequencyInCorup) {
        this.frequencyInCorup = frequencyInCorup;
    }

    public void setPointerToPostings(int pointerToPostings) {
        this.pointerToPostings = pointerToPostings;
    }

    public void setGetPointerToCache(int getPointerToCache) {
        this.getPointerToCache = getPointerToCache;
    }
}
