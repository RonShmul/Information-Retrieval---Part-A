/**
 * Created by Ronshmul on 30/11/2017.
 */
public class Document { // A class that helps us to save data on a specific documents besides the content

    private String path;
    private String docNo;
    private long positionInFile; //the first bit of the document - where it starts


    public String getPath() {
        return path;
    }

    public String getDocNo() {
        return docNo;
    }

    public long getPositionInFile() {
        return positionInFile;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDocNo(String docNo) {
        if(docNo!= null)
            this.docNo = docNo.replaceAll("\\s+" , "");
    }

    public void setPositionInFile(long positionInFile) {
        this.positionInFile = positionInFile;
    }
}
