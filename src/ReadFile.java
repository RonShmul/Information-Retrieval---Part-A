import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;
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

    public void  readDocs(){  // reads documents from each file in the list of directories

        for(int i=0; i<listOfDirs.length; i++){
            //get to the wanted file
            File temp = listOfDirs[i];
            File []currDir = temp.listFiles();
            File currFile = currDir[0];

            //
            Document currDoc= new Document();
            try {
                Scanner scan = new Scanner(currFile);
                scan.useDelimiter(Pattern.compile("<DOCNO>"));
                String line = scan.next();
                System.out.println(line);

                scan.useDelimiter(Pattern.compile("</DOCNO>"));
                line = scan.next();
                currDoc.setDocNo(line);
                System.out.println(line);

               /* BufferedReader reader = new BufferedReader(new FileReader(currFile));
                String line; */

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void readFile(){
        //listOfFiles[0].getAbsolutePath()
    }





}
