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
}
