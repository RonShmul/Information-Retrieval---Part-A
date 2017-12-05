import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sivan on 12/5/2017.
 */
public class FileRed {
    private static String pathStr;
    private static long positionTracker;
    private File headDir;
    private File[] listOfDirs;

    public void setPathStr(String path){
        pathStr = path;
    }

    public void setListOfDirs(){

        headDir = new File(pathStr);
        listOfDirs = headDir.listFiles();
    }

    public void readCorpus() {
        for (int i = 0; i < listOfDirs.length; i++) {
            //get to the wanted file
            File temp = listOfDirs[i];
            File[] currDir = temp.listFiles();
            File currFile = currDir[0];
            readDoc(currFile);
        }
    }

    public void readDoc(File currentFile) {

            //File currFile = new File("C:\\Users\\Sivan\\IdeaProjects\\Information Retrieval-Part A\\src\\ahlaDoc");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile), 1000*8192);


        while(bufferedReader.ready()) {
                //the garbage at the end make the "bufferedReader.ready()" return true and the DOCNO and content are null.. should be handled
                Document document = new Document();
                document.setDocNo(cutParts(bufferedReader, "<DOCNO>", "</DOCNO>"));
                document.setPositionInFile(((Long)positionTracker).toString());
                System.out.println(document.getDocNo());
                String content = cutParts(bufferedReader, "<TEXT>", "</TEXT>");
                System.out.println(content);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        String cutParts(BufferedReader bufferedReader, String startDelimiter, String endDelimiter) {
            StringBuilder builder = new StringBuilder();
            String s;
            String text = null;
            try {
                while ((s = bufferedReader.readLine()) != null) {
                    positionTracker += s.getBytes().length;
                    builder.append(s);
                    if (builder.toString().contains(endDelimiter))
                        break;
                }

                String regex = "(?<=" + startDelimiter + ")(.*?)(?=" + endDelimiter + ")";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(builder.toString());
                if (matcher.find()) {
                    text = matcher.group();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
            return text;
        }

}
