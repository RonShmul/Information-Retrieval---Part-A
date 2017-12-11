import java.util.HashMap;
import java.util.List;

/**
 * Created by Ronshmul on 10/12/2017.
 */
public class Indexer {

    private HashMap<String ,MetaData > termsToIndex;
    private HashMap<String , List<String>> dictionary;
    private String primaryPath;

    public Indexer(String primaryPath) {
        this.primaryPath = primaryPath;
        this.termsToIndex = termsToIndex; //todo change it to parse output
        dictionary = new HashMap<>();
    }

    public void initialize(){

    }


}
