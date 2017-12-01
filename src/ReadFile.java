import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 30/11/2017.
 */


public class ReadFile {
    private static String pathStr;
    private String currentDoc;
    private File headDir;
    private File[] listOfDirs;

    public void setPathStr(String path){
        pathStr = path;
    }

    public void setListOfDirs(){

        headDir = new File(pathStr);
        listOfDirs = headDir.listFiles();
    }

    public void  readDocs() throws IOException {  // reads documents from each file in the list of directories

        //for(int i=0; i<listOfDirs.length; i++){
            //get to the wanted file
           /* File temp = listOfDirs[i];
            File []currDir = temp.listFiles();
            File currFile = currDir[0];*/
           File file = new File("C:\\Users\\Sivan\\IdeaProjects\\Information Retrieval-Part A\\src\\ahlaDoc");

            //
            //Document currDoc= new Document();
           /* try {
                Scanner scan = new Scanner(currFile);
                scan.useDelimiter(Pattern.compile("<DOCNO>"));
                String line = scan.next();
                System.out.println(line);

                scan.useDelimiter(Pattern.compile("</DOCNO>"));
                line = scan.next();
                //currDoc.setDocNo(line);
                System.out.println(line);

                BufferedReader reader = new BufferedReader(new FileReader(currFile));
                String line;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

            BufferedReader bufferedReader = new BufferedReader( new FileReader(file));
            String s;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null) {
                builder.append(s);
                if(builder.toString().contains("</DOCNO>"))
                    break;
            }

            Pattern docnoPattern = Pattern.compile("(?<=<DOCNO>)(.*?)(?=</DOCNO>)");
            Matcher docnoMatcher = docnoPattern.matcher(builder.toString());
            if(docnoMatcher.find()) {
                String docno = docnoMatcher.group().replaceAll("\\s+", "");
                System.out.println(docno);
            }

        }

   // }

    public void readFile(){
        //listOfFiles[0].getAbsolutePath()
    }





}
