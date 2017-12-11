/**
 * Created by Ronshmul on 10/12/2017.
 */
public class MetaData {
    private int df;
    private int frequencyInDoc;
    private Document document;

    public MetaData(int df, int frequencyInDoc, Document document) {
        this.df = df;
        this.frequencyInDoc = frequencyInDoc;
        this.document = document;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public int getFrequencyInDoc() {
        return frequencyInDoc;
    }

    public void setFrequencyInDoc(int frequencyInDoc) {
        this.frequencyInDoc = frequencyInDoc;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
