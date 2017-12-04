import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */
public class Parse {

    private Document document;
    private String content;
    private static Dictionary<String , String> months;
    private static HashMap terms;

    /**
     * constructor
     * @param document
     * @param content
     */
    public Parse(Document document, String content) {
        this.document = document;
        this.content = content;

        terms = new HashMap();
    }

    public void setMonthDictionary(){
        months.put("jan" , "1");
        months.put("january" , "1");
        months.put("feb" , "2");
        months.put("february" , "2");
        months.put("mar" , "3");
        months.put("march" , "3");
        months.put("apr" , "4");
        months.put("april" , "4");
        months.put("may" , "5");
        months.put("jun" , "6");
        months.put("june" , "6");
        months.put("jul" , "7");
        months.put("july" , "7");
        months.put("aug" , "8");
        months.put("august" , "8");
        months.put("sep" , "9");
        months.put("september" , "9");
        months.put("oct" , "10");
        months.put("october" , "10");
        months.put("nov" , "11");
        months.put("november" , "11");
        months.put("dec" , "12");
        months.put("december" , "12");
    }

    /**
     * function to parse number with percent to be in the format: number percent.
     * call to the function: number to fix the decimal number.
     * @param str
     * @return String
     */
    public String percent(String str) {
        if(str.contains("percentage")) {
            int index = str.indexOf("percentage");
            String result = numbers(str.substring(0, index - 1)) + " percent";
            return result;
        }
        else if(str.contains("%")) {
            int index = str.indexOf("%");
            String result = numbers(str.substring(0, index - 1)) + " percent";
            return result;
        }
        else {
            return numbers(str.substring(0, str.indexOf(" "))) + " percent";
        }
    }

    /**
     * handle numbers: decimal numbers to be 2 digits after the point and remove comma.
     * @param str
     * @return String
     */
    public static String numbers(String str){
        if(str.contains(".")) {
            String[] parts = str.split(Pattern.quote("."));
            String str0 = parts[0];
            String str1 = parts[1];
            if (str1.length() > 2) {
                int digit = (Integer.parseInt(str1.substring(1,2)))+1;
                str = str0+ "." +Integer.parseInt(str1.substring(0,1)) + digit;
            }
            else
                str = str0+ "." + str1;
        }
        if(str.contains(",")){
            String[] parts = str.split(Pattern.quote(","));
            String str1= "";
            for (int i=0; i<parts.length; i++){
                str1+=parts[i];
            }
            return str1;
        }
        return str;
    }

    public static String dates(String str){
        str = str.toLowerCase();
        if(str.contains("th")){
            str = str.replace("th" , "");
        }
        if(str.contains(",")){
            str = str.replace("," , "");
        }

        String []parts = str.split(" ");

        if(parts.length>2){
            if(parts[2].length() ==2){
                String s = "19" +parts[2];
                parts[2] =s;
            }

            if(months.get(parts[0]) != null){
                parts[0] = months.get(parts[0]);
                str = parts[1] + "/" + parts[0] + "/" + parts[2];
            }
            if(months.get(parts[1]) != null){
                parts[1] = months.get(parts[1]);
                str = parts[0] + "/" + parts[1] + "/" + parts[2];
            }
        }
        else{
            if(months.get(parts[0]) != null){
                if(parts[1].length()==4){
                    str = parts[0] + "/" + parts[1];
                }
                else {
                    parts[0] = months.get(parts[0]);
                    str = parts[1] + "/" + parts[0];
                }
            }
            if(months.get(parts[1]) != null){
                parts[1] = months.get(parts[1]);
                str = parts[0] + "/" + parts[1];
            }
        }
        return str;
    }

    /**
     * change uppercase characters to lowercase.
     * @param str
     * @return
     */
    public String upperCaseWord(String str) {
        return str.toLowerCase();
    }

    /**
     * expressions like Word Word (capital letter at the beginning) will be split to: word, words and word word.
     * @param str
     * @return String[]
     */
    public String[] upperCaseWords(String str) {
        String s = str.toLowerCase();
        String[] result = s.split(Pattern.quote(" "));
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    /**
     * expressions like word-word will be split to: word, words and word word.
     * @param str
     * @return String[]
     */
    public String[] wordsWithHyphen(String str) {
        String s = str.toLowerCase();
        String[] result = s.split(Pattern.quote("-"));
        s.replaceAll("-", " ");
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    /**
     *
     * @param str
     * @return String
     */
    String removeS(String str) {
        return str.substring(0,str.length() - 2);
    }

    /**
     * number$ will turn to: number dollar
     * @param str
     * @return String
     */
    public String dollar(String str) {

        if(str.contains("$")) {
            int index = str.indexOf("$");
            String result = numbers(str.substring(0, index - 1)) + " dollar";
            return result;
        }
        else {
            return numbers(str.substring(0, str.indexOf(" "))) + " dollar";
        }
    }

    /**
     * turn a.b.c to abc.
     * @param str
     * @return String
     */
    String dotsBetweenWords(String str) {
        return str.replaceAll(".", "");
    }

    void parse() {
        Scanner doc = new Scanner(content);
        doc.useDelimiter(" ");

        Pattern dotsP = Pattern.compile("(\\w\\.)+\\w|");
        Pattern wordP = Pattern.compile("\\w+");
        Pattern numberP = Pattern.compile("\\d+");
        Pattern upperCaseP = Pattern.compile("[A-Z]\\w*");

        while(doc.hasNext()) {
            String potentialTerm = doc.next().replaceAll("[\\[\\](){}]","").replaceAll("\"","");
            Matcher dotsM = dotsP.matcher(potentialTerm);
            Matcher wordM = wordP.matcher(potentialTerm);
            Matcher numperM = numberP.matcher(potentialTerm);
            Matcher upperCaseM = upperCaseP.matcher(potentialTerm);


            





        }
    }

}

