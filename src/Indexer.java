import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ronshmul on 10/12/2017.
 */
public class Indexer {

    private HashMap<String , List<Integer>> dictionary;
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
            LinkedList<String> listToTempPosting = new LinkedList<String>();
            for( Map.Entry<String,MetaData> term : termsToIndex.entrySet()){
                if(dictionary.get(term.getKey())==null){
                    ArrayList<Integer> fields = new ArrayList<>();
                    dictionary.put(term.getKey(), fields);
                    fields.add(term.getValue().getDf());
                    fields.add(term.getValue().getFrequencyInCorpus());
                }
                else{
                    dictionary.get(term.getKey()).set(0, dictionary.get(term.getKey()).get(0) + term.getValue().getDf());
                    dictionary.get(term.getKey()).set(1, dictionary.get(term.getKey()).get(1) + term.getValue().getFrequencyInCorpus());
                }

                String toTemp = term.getKey()+":";
                for( Map.Entry<Document,Integer> tf : term.getValue().getFrequencyInDoc().entrySet()){
                    toTemp = toTemp.concat(tf.getKey().getDocNo() +":"+tf.getValue()+",");
                }
                listToTempPosting.add(toTemp);
            }

            listToTempPosting.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            int size = listToTempPosting.size();
            for (int i = 0; i < size; i++) {
                temporaryPostingFile.write(listToTempPosting.get(i));
                if(i<size-1){
                    temporaryPostingFile.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
