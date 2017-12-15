import java.io.*;
import java.util.*;

/**
 * Created by Ronshmul on 10/12/2017.
 */
public class Indexer {

    private HashMap<String, List<Integer>> dictionary;
    private String corpusPath;
    private String filesPath;
    private int counterForFiles;

    public Indexer(String corpusPath, String filesPath) {

        this.corpusPath = corpusPath;
        this.filesPath = filesPath;
        dictionary = new HashMap<>();
        counterForFiles = 0;

    }

    public void initialize() {
        //get to the main directory
        File headDir = new File(corpusPath);

        //get all the directories from the main directory:
        File[] listOfDirs = headDir.listFiles();

        //create new readFile
        ReadFile corpus = new ReadFile(corpusPath);

        //iterate on all the files in the corpus
        for (int i = 0; i < listOfDirs.length; i++) {
            counterForFiles = i;
            //get to the wanted file
            File temp = listOfDirs[i];
            File[] currDir = temp.listFiles();
            File currFile = currDir[0];

            //get the name of the file
            String path = currFile.getName();

            //create a new parse
            Parse parse = new Parse();

            //get all the parsed terms of a specific file
            LinkedHashMap<String, MetaData> termsToIndex = parse.parse(corpus.readFile(currFile));


//            for( Map.Entry<String,MetaData> term : termsToIndex.entrySet()){
//                System.out.println(term.getKey());
//            }
            //send to a method that construct the indexing
            //constructPosting(termsToIndex, path);
        }
    }

    private void constructPosting(LinkedHashMap<String, MetaData> termsToIndex) {
        try {
            BufferedWriter temporaryPostingFile = new BufferedWriter(new FileWriter(filesPath + "\\" + counterForFiles));
            LinkedList<String> listToTempPosting = new LinkedList<>();
            for (Map.Entry<String, MetaData> term : termsToIndex.entrySet()) {
                if (dictionary.get(term.getKey()) == null) {
                    ArrayList<Integer> fields = new ArrayList<>();
                    dictionary.put(term.getKey(), fields);
                    fields.add(term.getValue().getDf());
                    fields.add(term.getValue().getFrequencyInCorpus());
                } else {
                    dictionary.get(term.getKey()).set(0, dictionary.get(term.getKey()).get(0) + term.getValue().getDf());
                    dictionary.get(term.getKey()).set(1, dictionary.get(term.getKey()).get(1) + term.getValue().getFrequencyInCorpus());
                }

                String toTemp = term.getKey() + ":";
                for (Map.Entry<Document, Integer> tf : term.getValue().getFrequencyInDoc().entrySet()) {
                    toTemp = toTemp.concat(tf.getKey().getDocNo() + ":" + tf.getValue() + ",");
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
                if (i < size - 1) {
                    temporaryPostingFile.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mergePosting(String fileName) {   // merging the temporary posting files - two in each iteration
        try {

            int count = 0;
            while (counterForFiles > 2) {  //we will merge the last two files in separate - to the final posting files
                if (counterForFiles % 2 != 0) {  //if the number of files is odd - we will merge the last two files for even number of files to merge
                    File fileF = new File(filesPath + "\\" + counterForFiles);
                    File fileS = new File(filesPath + "\\" + (counterForFiles - 1));
                    File temp = new File(filesPath + "\\" + "temporaryFile");
                    BufferedReader readFirst = new BufferedReader(new FileReader(fileF));
                    BufferedReader readSecond = new BufferedReader(new FileReader(fileS));
                    BufferedWriter writeToFile = new BufferedWriter(new FileWriter(temp));
                    helpMerge(readFirst, readSecond, writeToFile);
                    fileF.delete();
                    fileS.delete();
                    File newFile = new File(filesPath + "\\" + (counterForFiles - 1));
                    temp.renameTo(newFile);
                    counterForFiles--;
                }

                for (int i = 0; counterForFiles > 2 && i < counterForFiles; i = i + 2) {
                    File fileF = new File(filesPath + "\\" + i);
                    File fileS = new File(filesPath + "\\" + (i + 1));
                    File temp = new File(filesPath + "\\" + "temporaryFile");
                    BufferedReader readFirst = new BufferedReader(new FileReader(fileF));
                    BufferedReader readSecond = new BufferedReader(new FileReader(fileS));
                    BufferedWriter writeToFile = new BufferedWriter(new FileWriter(temp));
                    helpMerge(readFirst, readSecond, writeToFile);
                    fileF.delete();
                    fileS.delete();
                    File newFile = new File(filesPath + "\\" + count);  // name the merged file with counter
                    temp.renameTo(newFile);
                    count++;
                }
                counterForFiles = counterForFiles / 2;
                count =0;
            }

            //merging the two files that left and separate to final posting files
            finalPosting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finalPosting() {
        try {
            BufferedReader readFirst = new BufferedReader(new FileReader(new File(filesPath + "\\" + "0")));
            BufferedReader readSecond = new BufferedReader(new FileReader(filesPath + "\\" + "1"));
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(new File(filesPath + "\\" + "finalNumbers")));
            String fLine = readFirst.readLine();
            String sLine = readSecond.readLine();
            while (fLine != null && sLine != null) {
                while (!(Character.isLetter(fLine.charAt(0))) && !(Character.isLetter(sLine.charAt(0)))) {
                    String fTerm = fLine.substring(0, fLine.indexOf(":", 0));
                    String sTerm = fLine.substring(0, fLine.indexOf(":", 0));
                    if (fTerm.compareTo(sTerm) == 1) {  //if the second term is smaller than the first term - we will write the second to the file
                        writeToFile.write(sLine);
                        sLine = readSecond.readLine();
                    } else if (fTerm.compareTo(sTerm) == -1) {  // if the first term is smaller than the second
                        writeToFile.write(fLine);
                        fLine = readFirst.readLine();
                    } else {    //if its the same term - we need to merge between the information
                        sLine = sLine.substring(sLine.indexOf(":") + 1, sLine.length() - 1);
                        fLine = fLine.concat(sLine);
                        fLine = fLine.concat("\n");
                        writeToFile.write(fLine);
                        fLine = readFirst.readLine();
                        sLine = readSecond.readLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void helpMerge(BufferedReader first, BufferedReader second, BufferedWriter writeToFile) {    // a help function for merging 2 temp posting file for one
        try {
            //read the first lines from 2 files
            String fLine = first.readLine();
            String sLine = second.readLine();

            //running until end of one file
            while (fLine != null && sLine != null) {
                String fTerm = fLine.substring(0, fLine.indexOf(":", 0));
                String sTerm = fLine.substring(0, fLine.indexOf(":", 0));
                if (fTerm.compareTo(sTerm) == 1) {  //if the second term is smaller than the first term - we will write the second to the file
                    writeToFile.write(sLine);
                    sLine = second.readLine();
                } else if (fTerm.compareTo(sTerm) == -1) {  // if the first term is smaller than the second
                    writeToFile.write(fLine);
                    fLine = first.readLine();
                } else {    //if its the same term - we need to merge between the information
                    sLine = sLine.substring(sLine.indexOf(":") + 1, sLine.length() - 1);
                    fLine = fLine.concat(sLine);
                    fLine = fLine.concat("\n");
                    writeToFile.write(fLine);
                    fLine = first.readLine();
                    sLine = second.readLine();
                }

            }
            if (fLine == null) {
                while (sLine != null) {
                    writeToFile.write(sLine + "\n");
                    sLine = second.readLine();
                }
            } else {
                while (fLine != null) {
                    writeToFile.write(fLine + "\n");
                    fLine = first.readLine();
                }
            }
            first.close();
            second.close();
            writeToFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

