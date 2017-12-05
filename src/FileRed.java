import java.io.*;
import java.nio.CharBuffer;
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

    public FileRed(String path){
        pathStr = path;
        headDir = new File(pathStr);
        listOfDirs = headDir.listFiles();

    }

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
            //StringBuilder builder = new StringBuilder();
           // while(bufferedReader.ready()) {
                    String fileString ="";
                    String temp;
                    //the garbage at the end make the "bufferedReader.ready()" return true and the DOCNO and content are null.. should be handled
                    while((temp=bufferedReader.readLine())!= null){
                        fileString=fileString.concat(temp);
                    }


                    Pattern patternDocno = Pattern.compile("(?<=<DOCNO>)(.*?)(?=</DOCNO>)");
                    Pattern patternText = Pattern.compile("(?<=<TEXT>)(.*?)(?=</TEXT>)");
                    Matcher matchDocno = patternDocno.matcher(fileString);
                    Matcher matchText = patternText.matcher(fileString);
                    Document document = new Document();
                    while(matchDocno.find() && matchText.find()) {
                        System.out.println(matchDocno.group());
                    }
                    //document.setDocNo(cutParts(fileString, "<DOCNO>", "</DOCNO>"));
                    //document.setPositionInFile(((Long)positionTracker).toString());
                    //System.out.println(document.getDocNo());
                    //String content = cutParts(fileString, "<TEXT>", "</TEXT>");
                    //System.out.println(content);
             //   }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        String cutParts(String fileString, String startDelimiter, String endDelimiter) {
//            StringBuilder builder = new StringBuilder();
            String s;
            String builder = "";
            String text = null;
            String regex = "(?<=" + startDelimiter + ")(.*?)(?=" + endDelimiter + ")";
            Pattern pattern = Pattern.compile(regex);
               // while ((s = bufferedReader.readLine()) != null) {
//                    positionTracker += s.getBytes().length;
                    Matcher matcher = pattern.matcher(builder);
                    if (matcher.find()) {
                        return matcher.group();
                    }
               // }

            return text;
        }

}
