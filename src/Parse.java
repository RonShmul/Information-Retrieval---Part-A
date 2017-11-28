import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */
public class Parse {
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
            return str;
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
            str=str1;

        }
        return str;
    }

}
