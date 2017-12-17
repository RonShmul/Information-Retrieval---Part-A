/**
 * Created by Sivan on 12/4/2017.
 */
public class Term implements Comparable<Term>{
    private String term;
    private int df;
    private int frequencyInCorup;
    private long pointerToPostings;
    private String postingFilePath;

    //constructors
    public Term(String term) {
        this.term = term;
        this.df = 1;
        this.frequencyInCorup = 1;
    }

    public Term(String term, int df, int frequencyInCorup, int pointerToPostings) {
        this.term = term;
        this.df = df;
        this.frequencyInCorup = frequencyInCorup;
        this.pointerToPostings = pointerToPostings;
    }

    //getters

    public String getPostingFilePath() {
        return postingFilePath;
    }

    public String getTerm() {
        return term;
    }

    public int getDf() {
        return df;
    }

    public int getFrequencyInCorup() {
        return frequencyInCorup;
    }

    public long getPointerToPostings() {
        return pointerToPostings;
    }


    //setters
    public void setPostingFilePath(String postingFilePath) {
        this.postingFilePath = postingFilePath;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public void setFrequencyInCorup(int frequencyInCorup) {
        this.frequencyInCorup = frequencyInCorup;
    }

    public void setPointerToPostings(long pointerToPostings) {
        this.pointerToPostings = pointerToPostings;
    }



    @Override
    public int hashCode() {
        return term.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Term otherTerm = (Term)obj;
        return this.term.equals(otherTerm.getTerm());
    }

    @Override
    public int compareTo(Term o) {
        if(this.frequencyInCorup < o.frequencyInCorup)
            return 1;
        else if(this.frequencyInCorup > o.frequencyInCorup)
            return -1;
        else return 0;
    }
}
