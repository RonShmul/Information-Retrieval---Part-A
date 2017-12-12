import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ronshmul on 10/12/2017.
 */
public class Indexer {


    private HashMap<String , List<String>> dictionary;
    private String corpusPath;
    private String filesPath;
    public Indexer(String corpusPath, String filesPath) {

        this.corpusPath = corpusPath;
        this.filesPath = filesPath;
        dictionary = new HashMap<>();

    }

    public void initialize(){
        //get to the main directory
        File headDir = new File(corpusPath);

        //get all the directories from the main directory:
        File[] listOfDirs = headDir.listFiles();

        //create new readFile
        ReadFile corpus = new ReadFile(corpusPath);

        //iterate on all the files in the corpus
        for (int i = 0; i < listOfDirs.length; i++) {

            //get to the wanted file
            File temp = listOfDirs[i];
            File[] currDir = temp.listFiles();
            File currFile = currDir[0];

            //get the name of the file
            String path = currFile.getName();

            //create a new parse
            Parse parse = new Parse();

            //get all the parsed terms of a specific file
            LinkedHashMap<String ,MetaData > termsToIndex = parse.parse(corpus.readFile(currFile));
            for( Map.Entry<String,MetaData> term : termsToIndex.entrySet()){
                System.out.println(term.getKey());
            }
            //send to a method that construct the indexing
            //constructPosting(termsToIndex, path);
        }
    }

    private void constructPosting(LinkedHashMap<String, MetaData> termsToIndex, String fileName) {
        try {
            BufferedWriter temporaryPostingFile = new BufferedWriter(new FileWriter(filesPath + "\\" + fileName + "PostingFile"));

            for( Map.Entry<String,MetaData> term : termsToIndex.entrySet()){
                if(dictionary.get(term.getKey()) != null) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
