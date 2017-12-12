import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */


public class Main {
    public static void main(String [] args) {



        //FileRed fileRed = new FileRed("C:\\Users\\Ronshmul\\Desktop\\corpus\\corpus");
        //ReadFile fileRed = new ReadFile("corpus");
        //File file = new File("D:\\Sivan\\studies\\corpus\\FB396001\\FB396001");
        //System.out.println(file.getName());
//        fileRed.readCorpus();
//        List<Document> documents = fileRed.getDocuments();
//        Document doc2 = documents.get(10);
//        try {
//
//            BufferedReader file = new BufferedReader(new FileReader(doc2.getPath()));
//            file.skip(doc2.getPositionInFile());
//            System.out.println(doc2.getDocNo());
//            System.out.println(file.readLine());
//            file.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Indexer indexer = new Indexer("corpus", "harta");
        indexer.initialize();
    }

}

