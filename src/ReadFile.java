import com.sun.org.apache.xerces.internal.impl.dv.xs.StringDV;

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

    public String cutParts(Scanner scanner , String delimiterStart , String delimiterEnd){

        scanner.useDelimiter(Pattern.compile(delimiterStart));
        String line = scanner.next();
        //System.out.println(line);

        scanner.useDelimiter(Pattern.compile(delimiterEnd));
        line = scanner.next().replace(delimiterStart, "");
        System.out.println(line);

        return line;
    }

    public void  readDocs() throws IOException {  // reads documents from each file in the list of directories

//        for (int i = 0; i < listOfDirs.length; i++) {
//            //get to the wanted file
//            File temp = listOfDirs[i];
//            File[] currDir = temp.listFiles();
//            File currFile = currDir[0];
           File currFile = new File("C:\\Users\\Sivan\\IdeaProjects\\Information Retrieval-Part A\\src\\ahlaDoc");

            // scanning lines in currFile and create information about the currDoc
            Document currDoc = new Document();
            Scanner scanner = new Scanner(currFile);

            //insert doc number to the currDoc
            currDoc.setDocNo(cutParts(scanner , "<DOCNO>" , "</DOCNO>"));

            String content = cutParts(scanner , "<TEXT>" , "</TEXT>");



           /* BufferedReader bufferedReader = new BufferedReader( new FileReader(file));
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
            }*/

        }
//    }

   // }

    public void readFile(){
        //listOfFiles[0].getAbsolutePath()
    }





}
