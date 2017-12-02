import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */
public class Parse {

    private Document document;
    private String content;
    private static Dictionary<String , String> months;

    public Parse(Document document, String content) {
        this.document = document;
        this.content = content;
    }

    public void setDictionary(){
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

    public String upperCaseWord(String str) {
        return str.toLowerCase();
    }

    public String[] upperCaseWords(String str) {
        String s = str.toLowerCase();
        String[] result = s.split(Pattern.quote(" "));
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = s;
        return result;
    }
}

