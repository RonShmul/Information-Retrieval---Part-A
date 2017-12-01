import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */


public class Main {
    public static void main(String [] args) {
        String x = "13,300";
        String y = "13.232";
        String z = "1,202.888";

//        String x1 = Parse.numbers(x);
        String y1 = Parse.numbers(y);
//        String z1 = Parse.numbers(z);
//        System.out.println(x1);
//        System.out.println(y1);
//        System.out.println(z1);
        try {
            File file = new File("C:\\Users\\Ronshmul\\IdeaProjects\\Information-Retrieval---Part-A\\src\\ahlaDoc");
            Scanner scan = new Scanner(file);

            scan.useDelimiter(Pattern.compile("<DOCNO>"));
            String line = scan.next();
            System.out.println(line);

            scan.useDelimiter(Pattern.compile("</DOCNO>"));
            line = scan.next();
            System.out.println(line);

               /* BufferedReader reader = new BufferedReader(new FileReader(currFile));
                String line; */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

