/**
 * Created by Ronshmul on 30/11/2017.
 */
public class Document { // A class that helps us to save data on a specific documents besides the content

    private String path;
    private String docNo;
    private String positionInFile; //the first bit of the document - where it starts


    public String getPath() {
        return path;
    }

    public String getDocNo() {
        return docNo;
    }

    public String getPositionInFile() {
        return positionInFile;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public void setPositionInFile(String positionInFile) {
        this.positionInFile = positionInFile;
    }
}
