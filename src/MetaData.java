import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ronshmul on 10/12/2017.
 */
public class MetaData {
    private int df;
    private long frequencyInCorpus;
    private HashMap<Document, Integer> frequencyInDoc;

    public MetaData(int df, long frequencyInCorpus, HashMap<Document, Integer> frequencyInDoc) {
        this.df = df;
        this.frequencyInCorpus = frequencyInCorpus;
        this.frequencyInDoc = frequencyInDoc;
    }

    public int getDf() {
        return df;
    }

    public long getFrequencyInCorpus() {
        return frequencyInCorpus;
    }

    public HashMap<Document, Integer> getFrequencyInDoc() {
        return frequencyInDoc;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public void setFrequencyInCorpus(long frequencyInCorpus) {
        this.frequencyInCorpus = frequencyInCorpus;
    }

    public void setFrequencyInDoc(HashMap<Document, Integer> frequencyInDoc) {
        this.frequencyInDoc = frequencyInDoc;
    }
}
