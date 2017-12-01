import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */


public class Main {
    public static void main(String [] args) {
        ReadFile readFile = new ReadFile();
        try {
            readFile.readDocs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

