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
//        else
//            System.out.println("basa");


        FileRed fileRed = new FileRed();
        fileRed.setPathStr("D:\\Sivan\\studies\\corpus");
        fileRed.setListOfDirs();
        fileRed.readCorpus();
    }

}

