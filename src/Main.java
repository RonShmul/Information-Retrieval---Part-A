import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */


public class Main {
    public static void main(String [] args) {
        Pattern pattern = Pattern.compile("\\w+");

        Matcher matcher1 = pattern.matcher("dsfdsf,");
        Matcher matcher2 = pattern.matcher("34-23423-34-");

        if(matcher1.matches()) {
            System.out.println("tags are words");
        }
        if(matcher2.matches()) {
            System.out.println("numbers with dots and psix");
        }
        System.out.println("done");
//        ReadFile readFile = new ReadFile();
//        try {
//            readFile.readDocs();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//        Pattern dots = Pattern.compile("[A-Z]\\w*");
//
//        Matcher docnoMatcher = dots.matcher("Sivan");
//        if(docnoMatcher.find()) {
//            String docno = docnoMatcher.group();
//            System.out.println(docno);
//            String s = "(3fgh) 'erg: h[;rtr]' \"rt: rg\" {e;rtert}".replaceAll("[\\[\\](){}]|\"|:|;","");
//                    //.replaceAll("[\\[\\](){}]","").replaceAll("\"","");
//            System.out.println(s);
//        }
//        else4
//            System.out.println("basa");


        //FileRed fileRed = new FileRed("C:\\Users\\Ronshmul\\Desktop\\corpus\\corpus");
        //FileRed fileRed = new FileRed("corpus");

        //fileRed.readCorpus();
    }

}

