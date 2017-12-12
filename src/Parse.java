import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronshmul on 28/11/2017.
 */
public class Parse {

    private static Map<String, String> months;
    private static HashMap terms;
    private HashSet<String> stopWords;
    private Document document;
    private String content;
    private Stack<String> stackOfTempTerms;
    private Pattern dotsP;
    private Pattern wordP;
    private Pattern numberP;
    private Pattern upperCaseP;
    private Pattern belongsP;
    private Pattern hyphenWordsP;
    private Pattern hyphenNumbersP;
    private Pattern textTags;
    private HashSet<Character> specials;


    /**
     * constructor
     */
    public Parse() {
        specials = new HashSet<>();
        stopWords = new HashSet<>();
        stackOfTempTerms = new Stack<>();
        months = new HashMap<String, String>();
        dotsP = Pattern.compile("(\\w\\.)+\\w");
        wordP = Pattern.compile("\\w+(,|)");
        numberP = Pattern.compile("\\d+(,\\d+)*(.\\d+)*(th|)");
        upperCaseP = Pattern.compile("[A-Z][a-z]+|[A-Z]+");
        belongsP = Pattern.compile("\\w+\\'s");
        hyphenWordsP = Pattern.compile("\\w+(-\\w+)+");
        hyphenNumbersP = Pattern.compile("\\d+(-\\d+)+");
        textTags = Pattern.compile("<(.*?)>");

        specials.add('.');
        specials.add(',');
        specials.add(']');
        specials.add('[');
        specials.add('(');
        specials.add(')');
        specials.add('{');
        specials.add('}');
        specials.add(':');
        specials.add(';');
        specials.add('"');

        setMonthMap();
        setStopWords();

        terms = new HashMap();
    }

    /**
     * handle numbers: decimal numbers to be 2 digits after the point and remove comma.
     *
     * @param str
     * @return String
     */
    public static String numbers(String str) {
        if (str.contains(".")) {
            String[] parts = str.split(Pattern.quote("."));
            String str0 = parts[0];
            String str1 = parts[1];
            if (str1.length() > 2) {
                int digit = (Integer.parseInt(str1.substring(1, 2))) + 1;
                str = str0 + "." + Integer.parseInt(str1.substring(0, 1)) + digit;
            } else
                str = str0 + "." + str1;
        }
        if (str.contains(",")) {
            String[] parts = str.split(Pattern.quote(","));
            String str1 = "";
            for (int i = 0; i < parts.length; i++) {
                str1 += parts[i];
            }
            return str1;
        }
        return str;
    }

    public static String dates(String str) {
        str = str.toLowerCase();
        if (str.contains("th")) {
            str = str.replace("th", "");
        }
        if (str.contains(",")) {
            str = str.replace(",", "");
        }

        String[] parts = str.split(" ");

        if (parts.length > 2) {
            if (parts[2].length() == 2) {
                String s = "19" + parts[2];
                parts[2] = s;
            }

            if (months.get(parts[0]) != null) {
                parts[0] = months.get(parts[0]);
                str = parts[1] + "/" + parts[0] + "/" + parts[2];
            }
            if (months.get(parts[1]) != null) {
                parts[1] = months.get(parts[1]);
                str = parts[0] + "/" + parts[1] + "/" + parts[2];
            }
        } else {
            if (months.get(parts[0]) != null) {
                if (parts[1].length() == 4) {
                    str = parts[0] + "/" + parts[1];
                } else {
                    parts[0] = months.get(parts[0]);
                    str = parts[1] + "/" + parts[0];
                }
            }
            if (months.get(parts[1]) != null) {
                parts[1] = months.get(parts[1]);
                str = parts[0] + "/" + parts[1];
            }
        }
        return str;
    }

    private void setStopWords() {
        stopWords.add("a");
        stopWords.add("a's");
        stopWords.add("able");
        stopWords.add("about");
        stopWords.add("above");
        stopWords.add("according");
        stopWords.add("accordingly");
        stopWords.add("across");
        stopWords.add("actually");
        stopWords.add("after");
        stopWords.add("afterwards");
        stopWords.add("again");
        stopWords.add("against");
        stopWords.add("ain't");
        stopWords.add("all");
        stopWords.add("allow");
        stopWords.add("allows");
        stopWords.add("almost");
        stopWords.add("alone");
        stopWords.add("along");
        stopWords.add("already");
        stopWords.add("also");
        stopWords.add("although");
        stopWords.add("always");
        stopWords.add("am");
        stopWords.add("among");
        stopWords.add("amongst");
        stopWords.add("an");
        stopWords.add("and");
        stopWords.add("another");
        stopWords.add("any");
        stopWords.add("anybody");
        stopWords.add("anyhow");
        stopWords.add("anyone");
        stopWords.add("anything");
        stopWords.add("anyway");
        stopWords.add("anyways");
        stopWords.add("anywhere");
        stopWords.add("apart");
        stopWords.add("appear");
        stopWords.add("appreciate");
        stopWords.add("appropriate");
        stopWords.add("are");
        stopWords.add("aren't");
        stopWords.add("around");
        stopWords.add("as");
        stopWords.add("aside");
        stopWords.add("ask");
        stopWords.add("asking");
        stopWords.add("associated");
        stopWords.add("at");
        stopWords.add("available");
        stopWords.add("away");
        stopWords.add("awfully");
        stopWords.add("b");
        stopWords.add("be");
        stopWords.add("became");
        stopWords.add("because");
        stopWords.add("become");
        stopWords.add("becomes");
        stopWords.add("becoming");
        stopWords.add("been");
        stopWords.add("before");
        stopWords.add("beforehand");
        stopWords.add("behind");
        stopWords.add("being");
        stopWords.add("believe");
        stopWords.add("below");
        stopWords.add("beside");
        stopWords.add("besides");
        stopWords.add("best");
        stopWords.add("better");
        stopWords.add("between");
        stopWords.add("beyond");
        stopWords.add("both");
        stopWords.add("brief");
        stopWords.add("but");
        stopWords.add("by");
        stopWords.add("c");
        stopWords.add("c'mon");
        stopWords.add("c's");
        stopWords.add("came");
        stopWords.add("can");
        stopWords.add("can't");
        stopWords.add("cannot");
        stopWords.add("cant");
        stopWords.add("cause");
        stopWords.add("causes");
        stopWords.add("certain");
        stopWords.add("certainly");
        stopWords.add("changes");
        stopWords.add("clearly");
        stopWords.add("co");
        stopWords.add("com");
        stopWords.add("come");
        stopWords.add("comes");
        stopWords.add("concerning");
        stopWords.add("consequently");
        stopWords.add("consider");
        stopWords.add("considering");
        stopWords.add("contain");
        stopWords.add("containing");
        stopWords.add("contains");
        stopWords.add("corresponding");
        stopWords.add("could");
        stopWords.add("couldn't");
        stopWords.add("course");
        stopWords.add("currently");
        stopWords.add("d");
        stopWords.add("definitely");
        stopWords.add("described");
        stopWords.add("despite");
        stopWords.add("did");
        stopWords.add("didn't");
        stopWords.add("different");
        stopWords.add("do");
        stopWords.add("does");
        stopWords.add("doesn't");
        stopWords.add("doing");
        stopWords.add("don't");
        stopWords.add("done");
        stopWords.add("down");
        stopWords.add("downwards");
        stopWords.add("during");
        stopWords.add("e");
        stopWords.add("each");
        stopWords.add("edu");
        stopWords.add("eg");
        stopWords.add("eight");
        stopWords.add("either");
        stopWords.add("else");
        stopWords.add("elsewhere");
        stopWords.add("enough");
        stopWords.add("entirely");
        stopWords.add("especially");
        stopWords.add("et");
        stopWords.add("etc");
        stopWords.add("even");
        stopWords.add("ever");
        stopWords.add("every");
        stopWords.add("everybody");
        stopWords.add("everyone");
        stopWords.add("everything");
        stopWords.add("everywhere");
        stopWords.add("ex");
        stopWords.add("exactly");
        stopWords.add("example");
        stopWords.add("except");
        stopWords.add("f");
        stopWords.add("far");
        stopWords.add("few");
        stopWords.add("fifth");
        stopWords.add("first");
        stopWords.add("five");
        stopWords.add("followed");
        stopWords.add("following");
        stopWords.add("follows");
        stopWords.add("for");
        stopWords.add("former");
        stopWords.add("formerly");
        stopWords.add("forth");
        stopWords.add("four");
        stopWords.add("from");
        stopWords.add("further");
        stopWords.add("furthermore");
        stopWords.add("g");
        stopWords.add("get");
        stopWords.add("gets");
        stopWords.add("getting");
        stopWords.add("given");
        stopWords.add("gives");
        stopWords.add("go");
        stopWords.add("goes");
        stopWords.add("going");
        stopWords.add("gone");
        stopWords.add("got");
        stopWords.add("gotten");
        stopWords.add("greetings");
        stopWords.add("h");
        stopWords.add("had");
        stopWords.add("hadn't");
        stopWords.add("happens");
        stopWords.add("hardly");
        stopWords.add("has");
        stopWords.add("hasn't");
        stopWords.add("have");
        stopWords.add("haven't");
        stopWords.add("having");
        stopWords.add("he");
        stopWords.add("he's");
        stopWords.add("hello");
        stopWords.add("help");
        stopWords.add("hence");
        stopWords.add("her");
        stopWords.add("here");
        stopWords.add("here's");
        stopWords.add("hereafter");
        stopWords.add("hereby");
        stopWords.add("herein");
        stopWords.add("hereupon");
        stopWords.add("hers");
        stopWords.add("herself");
        stopWords.add("hi");
        stopWords.add("him");
        stopWords.add("himself");
        stopWords.add("his");
        stopWords.add("hither");
        stopWords.add("hopefully");
        stopWords.add("how");
        stopWords.add("howbeit");
        stopWords.add("however");
        stopWords.add("i");
        stopWords.add("i'd");
        stopWords.add("i'll");
        stopWords.add("i'm");
        stopWords.add("i've");
        stopWords.add("ie");
        stopWords.add("if");
        stopWords.add("ignored");
        stopWords.add("immediate");
        stopWords.add("in");
        stopWords.add("inasmuch");
        stopWords.add("inc");
        stopWords.add("indeed");
        stopWords.add("indicate");
        stopWords.add("indicated");
        stopWords.add("indicates");
        stopWords.add("inner");
        stopWords.add("insofar");
        stopWords.add("instead");
        stopWords.add("into");
        stopWords.add("inward");
        stopWords.add("is");
        stopWords.add("isn't");
        stopWords.add("it");
        stopWords.add("it'd");
        stopWords.add("it'll");
        stopWords.add("it's");
        stopWords.add("its");
        stopWords.add("itself");
        stopWords.add("j");
        stopWords.add("just");
        stopWords.add("k");
        stopWords.add("keep");
        stopWords.add("keeps");
        stopWords.add("kept");
        stopWords.add("know");
        stopWords.add("knows");
        stopWords.add("known");
        stopWords.add("l");
        stopWords.add("last");
        stopWords.add("lately");
        stopWords.add("later");
        stopWords.add("latter");
        stopWords.add("latterly");
        stopWords.add("least");
        stopWords.add("less");
        stopWords.add("lest");
        stopWords.add("let");
        stopWords.add("let's");
        stopWords.add("like");
        stopWords.add("liked");
        stopWords.add("likely");
        stopWords.add("little");
        stopWords.add("look");
        stopWords.add("looking");
        stopWords.add("looks");
        stopWords.add("ltd");
        stopWords.add("m");
        stopWords.add("mainly");
        stopWords.add("many");
        stopWords.add("may");
        stopWords.add("maybe");
        stopWords.add("me");
        stopWords.add("mean");
        stopWords.add("meanwhile");
        stopWords.add("merely");
        stopWords.add("might");
        stopWords.add("more");
        stopWords.add("moreover");
        stopWords.add("most");
        stopWords.add("mostly");
        stopWords.add("much");
        stopWords.add("must");
        stopWords.add("my");
        stopWords.add("myself");
        stopWords.add("n");
        stopWords.add("name");
        stopWords.add("namely");
        stopWords.add("nd");
        stopWords.add("near");
        stopWords.add("nearly");
        stopWords.add("necessary");
        stopWords.add("need");
        stopWords.add("needs");
        stopWords.add("neither");
        stopWords.add("never");
        stopWords.add("nevertheless");
        stopWords.add("new");
        stopWords.add("next");
        stopWords.add("nine");
        stopWords.add("no");
        stopWords.add("nobody");
        stopWords.add("non");
        stopWords.add("none");
        stopWords.add("noone");
        stopWords.add("nor");
        stopWords.add("normally");
        stopWords.add("not");
        stopWords.add("nothing");
        stopWords.add("novel");
        stopWords.add("now");
        stopWords.add("nowhere");
        stopWords.add("o");
        stopWords.add("obviously");
        stopWords.add("of");
        stopWords.add("off");
        stopWords.add("often");
        stopWords.add("oh");
        stopWords.add("ok");
        stopWords.add("okay");
        stopWords.add("old");
        stopWords.add("on");
        stopWords.add("once");
        stopWords.add("one");
        stopWords.add("ones");
        stopWords.add("only");
        stopWords.add("onto");
        stopWords.add("or");
        stopWords.add("other");
        stopWords.add("others");
        stopWords.add("otherwise");
        stopWords.add("ought");
        stopWords.add("our");
        stopWords.add("ours");
        stopWords.add("ourselves");
        stopWords.add("out");
        stopWords.add("outside");
        stopWords.add("over");
        stopWords.add("overall");
        stopWords.add("own");
        stopWords.add("p");
        stopWords.add("particular");
        stopWords.add("particularly");
        stopWords.add("per");
        stopWords.add("perhaps");
        stopWords.add("placed");
        stopWords.add("please");
        stopWords.add("plus");
        stopWords.add("possible");
        stopWords.add("presumably");
        stopWords.add("probably");
        stopWords.add("provides");
        stopWords.add("q");
        stopWords.add("que");
        stopWords.add("quite");
        stopWords.add("qv");
        stopWords.add("r");
        stopWords.add("rather");
        stopWords.add("rd");
        stopWords.add("re");
        stopWords.add("really");
        stopWords.add("reasonably");
        stopWords.add("regarding");
        stopWords.add("regardless");
        stopWords.add("regards");
        stopWords.add("relatively");
        stopWords.add("respectively");
        stopWords.add("right");
        stopWords.add("s");
        stopWords.add("said");
        stopWords.add("same");
        stopWords.add("saw");
        stopWords.add("say");
        stopWords.add("saying");
        stopWords.add("says");
        stopWords.add("second");
        stopWords.add("secondly");
        stopWords.add("see");
        stopWords.add("seeing");
        stopWords.add("seem");
        stopWords.add("seemed");
        stopWords.add("seeming");
        stopWords.add("seems");
        stopWords.add("seen");
        stopWords.add("self");
        stopWords.add("selves");
        stopWords.add("sensible");
        stopWords.add("sent");
        stopWords.add("serious");
        stopWords.add("seriously");
        stopWords.add("seven");
        stopWords.add("several");
        stopWords.add("shall");
        stopWords.add("she");
        stopWords.add("should");
        stopWords.add("shouldn't");
        stopWords.add("since");
        stopWords.add("six");
        stopWords.add("so");
        stopWords.add("some");
        stopWords.add("somebody");
        stopWords.add("somehow");
        stopWords.add("someone");
        stopWords.add("something");
        stopWords.add("sometime");
        stopWords.add("sometimes");
        stopWords.add("somewhat");
        stopWords.add("somewhere");
        stopWords.add("soon");
        stopWords.add("sorry");
        stopWords.add("specified");
        stopWords.add("specify");
        stopWords.add("specifying");
        stopWords.add("still");
        stopWords.add("sub");
        stopWords.add("such");
        stopWords.add("sup");
        stopWords.add("sure");
        stopWords.add("t");
        stopWords.add("t's");
        stopWords.add("take");
        stopWords.add("taken");
        stopWords.add("tell");
        stopWords.add("tends");
        stopWords.add("th");
        stopWords.add("than");
        stopWords.add("thank");
        stopWords.add("thanks");
        stopWords.add("thanx");
        stopWords.add("that");
        stopWords.add("that's");
        stopWords.add("thats");
        stopWords.add("the");
        stopWords.add("their");
        stopWords.add("theirs");
        stopWords.add("them");
        stopWords.add("themselves");
        stopWords.add("then");
        stopWords.add("thence");
        stopWords.add("there");
        stopWords.add("there's");
        stopWords.add("thereafter");
        stopWords.add("thereby");
        stopWords.add("therefore");
        stopWords.add("therein");
        stopWords.add("theres");
        stopWords.add("thereupon");
        stopWords.add("these");
        stopWords.add("they");
        stopWords.add("they'd");
        stopWords.add("they'll");
        stopWords.add("they're");
        stopWords.add("they've");
        stopWords.add("think");
        stopWords.add("third");
        stopWords.add("this");
        stopWords.add("thorough");
        stopWords.add("thoroughly");
        stopWords.add("those");
        stopWords.add("though");
        stopWords.add("three");
        stopWords.add("through");
        stopWords.add("throughout");
        stopWords.add("thru");
        stopWords.add("thus");
        stopWords.add("to");
        stopWords.add("together");
        stopWords.add("too");
        stopWords.add("took");
        stopWords.add("toward");
        stopWords.add("towards");
        stopWords.add("tried");
        stopWords.add("tries");
        stopWords.add("truly");
        stopWords.add("try");
        stopWords.add("trying");
        stopWords.add("twice");
        stopWords.add("two");
        stopWords.add("u");
        stopWords.add("un");
        stopWords.add("under");
        stopWords.add("unfortunately");
        stopWords.add("unless");
        stopWords.add("unlikely");
        stopWords.add("until");
        stopWords.add("unto");
        stopWords.add("up");
        stopWords.add("upon");
        stopWords.add("us");
        stopWords.add("use");
        stopWords.add("used");
        stopWords.add("useful");
        stopWords.add("uses");
        stopWords.add("using");
        stopWords.add("usually");
        stopWords.add("uucp");
        stopWords.add("v");
        stopWords.add("value");
        stopWords.add("various");
        stopWords.add("very");
        stopWords.add("via");
        stopWords.add("viz");
        stopWords.add("vs");
        stopWords.add("w");
        stopWords.add("want");
        stopWords.add("wants");
        stopWords.add("was");
        stopWords.add("wasn't");
        stopWords.add("way");
        stopWords.add("we");
        stopWords.add("we'd");
        stopWords.add("we'll");
        stopWords.add("we're");
        stopWords.add("we've");
        stopWords.add("welcome");
        stopWords.add("well");
        stopWords.add("went");
        stopWords.add("were");
        stopWords.add("weren't");
        stopWords.add("what");
        stopWords.add("what's");
        stopWords.add("whatever");
        stopWords.add("when");
        stopWords.add("whence");
        stopWords.add("whenever");
        stopWords.add("where");
        stopWords.add("where's");
        stopWords.add("whereafter");
        stopWords.add("whereas");
        stopWords.add("whereby");
        stopWords.add("wherein");
        stopWords.add("whereupon");
        stopWords.add("wherever");
        stopWords.add("whether");
        stopWords.add("which");
        stopWords.add("while");
        stopWords.add("whither");
        stopWords.add("who");
        stopWords.add("who's");
        stopWords.add("whoever");
        stopWords.add("whole");
        stopWords.add("whom");
        stopWords.add("whose");
        stopWords.add("why");
        stopWords.add("will");
        stopWords.add("willing");
        stopWords.add("wish");
        stopWords.add("with");
        stopWords.add("within");
        stopWords.add("without");
        stopWords.add("won't");
        stopWords.add("wonder");
        stopWords.add("would");
        stopWords.add("would");
        stopWords.add("wouldn't");
        stopWords.add("x");
        stopWords.add("y");
        stopWords.add("yes");
        stopWords.add("yet");
        stopWords.add("you");
        stopWords.add("you'd");
        stopWords.add("you'll");
        stopWords.add("you're");
        stopWords.add("you've");
        stopWords.add("your");
        stopWords.add("yours");
        stopWords.add("yourself");
        stopWords.add("yourselves");
        stopWords.add("z");
        stopWords.add("zero");

    }

    public void setMonthMap() {
        months.put("jan", "1");
        months.put("january", "1");
        months.put("feb", "2");
        months.put("february", "2");
        months.put("mar", "3");
        months.put("march", "3");
        months.put("apr", "4");
        months.put("april", "4");
        months.put("may", "5");
        months.put("jun", "6");
        months.put("june", "6");
        months.put("jul", "7");
        months.put("july", "7");
        months.put("aug", "8");
        months.put("august", "8");
        months.put("sep", "9");
        months.put("september", "9");
        months.put("oct", "10");
        months.put("october", "10");
        months.put("nov", "11");
        months.put("november", "11");
        months.put("dec", "12");
        months.put("december", "12");
    }

    /**
     * function to parse number with percent to be in the format: number percent.
     * call to the function: number to fix the decimal number.
     *
     * @param str
     * @return String
     */
    public String percent(String str) {
        if (str.contains("percentage")) {
            int index = str.indexOf("percentage");
            String result = numbers(str.substring(0, index - 1)) + " percent";
            return result;
        } else if (str.contains("%")) {
            int index = str.indexOf("%");
            String result = numbers(str.substring(0, index - 1)) + " percent";
            return result;
        } else {
            return numbers(str.substring(0, str.indexOf(" "))) + " percent";
        }
    }


    /**
     * expressions like Word Word (capital letter at the beginning) will be split to: word, words and word word.
     *
     * @param str
     * @return String[]
     */
    public String[] upperCaseWords(String str) {
        String s = str.toLowerCase();
        String temp = "";
        String[] result = s.split(Pattern.quote(" "));
        s="";
        for(int i=0; i<result.length; i++){
            result[i] = cleanTerm(result[i]);
            s=s+result[i];
            if (i != result.length - 1)
                s = s + " ";
            if (!stopWords.contains(result[i])) {
                temp = temp + result[i];
                if (i != result.length - 1)
                    temp = temp + " ";
            }
        }
        String []res = temp.split(" ");
        result = Arrays.copyOf(res, result.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    /**
     * expressions like word-word will be split to: word, words and word word.
     *
     * @param str
     * @return String[]
     */
    public String[] wordsWithHyphen(String str) {
        String s = "";
        String temp = "";
        String[] result = str.split(Pattern.quote("-"));
        for (int i = 0; i < result.length; i++) {
            result[i] = cleanTerm(result[i]);
            result[i].toLowerCase();
            s = s + result[i];
            if (i != result.length - 1)
                s = s + " ";
            if (!stopWords.contains(result[i])) {
                temp = temp + result[i];
                if (i != result.length - 1)
                    temp = temp + " ";
            }
        }
        String[]res = temp.split(" ");
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    /**
     * @param str
     * @return String
     */
    String removeS(String str) {
        if (str.length()>2 && str.charAt(str.length() - 1) == 's' && str.charAt(str.length() - 2) == '\'') {
            return str.substring(0, str.length() - 2);
        }
        return str;
    }

    /**
     * number$ will turn to: number dollar
     *
     * @param str
     * @return String
     */
    public String dollar(String str) {

        if (str.contains("$")) {
            int index = str.indexOf("$");
            String result = numbers(str.substring(0, index - 1)) + " dollar";
            return result;
        } else {
            return numbers(str.substring(0, str.indexOf(" "))) + " dollar";
        }
    }

    /**
     * turn a.b.c to abc.
     *
     * @param str
     * @return String
     */
    public String dotsBetweenWords(String str) {
        return str.replaceAll("\\.", "");

    }

    public String cleanTerm(String term) {
        String temp = dotsBetweenWords(term);
        temp = removeS(temp);
        if(temp.length()>1 && specials.contains(temp.charAt(temp.length()-1))){
            temp = temp.substring(0,temp.length()-1);
        }
        if(temp.length()>1 && specials.contains(temp.charAt(0))){
            temp = temp.substring(1,temp.length());
        }
        temp = temp.replaceAll("[\\[\\](){}]|\"|:|;", "");
        return temp;
    }
    public String checkIfNull(StringTokenizer doc){
        String potentialTerm = null;
        if (stackOfTempTerms.empty()) {
            if(doc.hasMoreTokens())
                potentialTerm = doc.nextToken();
        } else {
            potentialTerm = stackOfTempTerms.pop();
        }
        return potentialTerm;
    }


    public LinkedHashMap<String, MetaData> parse(LinkedHashMap<Document, String> termsOfFile) {
        LinkedHashMap<String, MetaData> parsedTerms = new LinkedHashMap<>();

        //iterate on all the documents in the file
        for( Map.Entry<Document, String> doc : termsOfFile.entrySet()){

            //get the document text
            String docText = doc.getValue().concat(" ");


            int pos = 0;
            int index = 1;
            while(pos < docText.length()) {
                pos = index;
                index = docText.indexOf(" ", pos);
                String potentialTerm = docText.substring(pos, index);

                while (index + 1 < docText.length() && (stopWords.contains(potentialTerm) || potentialTerm.contains("<")
                        || potentialTerm.contains(">") || potentialTerm.equals("--") || potentialTerm.equals("&")) || potentialTerm.equals("") ||
                        potentialTerm.equals(" ")) {
                    pos = index + 1;
                    index = docText.indexOf(" ", pos);
                    potentialTerm = docText.substring(pos, index);
                }

                if(index + 1 >= docText.length())
                    break;

                Matcher numberM = numberP.matcher(potentialTerm);
                Matcher hyphenWordsM = hyphenWordsP.matcher(potentialTerm);
                Matcher hyphenNumbersM = hyphenNumbersP.matcher(potentialTerm);

                if (hyphenNumbersM.matches()) {    //if its an expression with only numbers and hyphens
                    //split the numbers to array
                    String[] potentialTermsArr = potentialTerm.split("-");

                    //iterate the array to parse it's terms
                    for (int i=0;i<potentialTermsArr.length;i++){

                        //format the numbers
                        potentialTermsArr[i] = numbers(potentialTermsArr[i]);

                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(), potentialTermsArr[i], parsedTerms);
                    }
                    if(index + 1 < docText.length()) {

                        continue;
                    }
                    else{
                        break;
                    }
                }

                if (hyphenWordsM.matches()) {   //if its an expression with only words and hyphens
                    String tempTerm = potentialTerm;
                    String[] potentialTermsArr = wordsWithHyphen(tempTerm);
                    for (int i=0;i<potentialTermsArr.length;i++){
                        //check if term exists, if not - create one, else - update the existing one
                            updatePotentialTerm(doc.getKey(), potentialTermsArr[i], parsedTerms);
                    }
                    if(index + 1 < docText.length()) {

                        continue;
                    }
                    else {
                        break;
                    }

                }

                if (numberM.find()) {    //numbers with signs
                    char first = potentialTerm.charAt(0);
                    char last = potentialTerm.charAt(potentialTerm.length() - 1);
                    if(potentialTerm.length()>1 && specials.contains(first)){
                        potentialTerm = potentialTerm.substring(1,potentialTerm.length());
                    }
                    if (last == '$') {
                        String tempTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                        potentialTerm = dollar(tempTerm);
                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);

                        if(index + 1 < docText.length()) {

                            continue;
                        }
                        else {
                            break;
                        }

                    }
                    if (last == '%') {
                        String tempTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                        potentialTerm = percent(tempTerm);
                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                        if(index + 1 < docText.length()) {

                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    if (specials.contains(last)) {
                        potentialTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                        numberM = numberP.matcher(potentialTerm);
                    }
                }
                int nextPos = index+1;
                int nextIndex = docText.indexOf(" ", nextPos);
                if (numberM.matches()) { //if the term is a legal number (including dots and commas)
                    if(index + 1 < docText.length()) {
                        String nextTerm = docText.substring(nextPos, nextIndex);
                        while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                            nextPos = nextIndex + 1;
                            nextIndex = docText.indexOf(" ", nextPos);
                            nextTerm = docText.substring(nextPos, nextIndex);
                        }
                        Matcher ucNext = upperCaseP.matcher(nextTerm);
                        if (ucNext.matches()) {     // checks dates
                            String tempNextTerm = nextTerm.toLowerCase();
                            if (months.containsKey(tempNextTerm)) {
                                potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month
                                //update pointers:
                                pos = nextIndex + 1;
                                index = docText.indexOf(" ", nextPos);

                                if(index + 1 < docText.length()) {
                                    nextTerm = docText.substring(nextPos, nextIndex);  //todo - not sure
                                    while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                                        nextPos = nextIndex + 1;
                                        nextIndex = docText.indexOf(" ", nextPos);
                                        nextTerm = docText.substring(nextPos, nextIndex);
                                    }
                                    Matcher isYear = numberP.matcher(nextTerm);
                                    if (isYear.matches()) {
                                        potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month + year
                                        pos = nextIndex + 1;
                                        index = docText.indexOf(" ", nextPos);
                                        potentialTerm = dates(potentialTerm);
                                        //check if term exists, if not - create one, else - update the existing one
                                        updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                                        if(index + 1 < docText.length()) {
                                            continue;
                                        }
                                        else {
                                            break;
                                        }
                                    } else {
                                        // the third term is not a year - save in stack
                                        potentialTerm = dates(potentialTerm);
                                        //check if term exists, if not - create one, else - update the existing one
                                        updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                                        if(index + 1 < docText.length()) {
                                            continue;
                                        }
                                        else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (potentialTerm.contains(".")) {    //decimal number or ip address
                        if (potentialTerm.indexOf(".") == potentialTerm.lastIndexOf(".")) {  //there is only one dot
                            potentialTerm = numbers(potentialTerm);
                        }
                    }
                    if(index + 1 < docText.length()) {
                        nextPos = index+1;
                        nextIndex = docText.indexOf(" ", nextPos);
                        String nextTerm = docText.substring(nextPos, nextIndex);
                        while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                            nextPos = nextIndex + 1;
                            nextIndex = docText.indexOf(" ", nextPos);
                            nextTerm = docText.substring(nextPos, nextIndex);
                        }
                        String tempNextTerm = nextTerm.toLowerCase();
                        if (tempNextTerm.equals("percent") || tempNextTerm.equals("percentage")) {   //checks if its a percent expression
                            tempNextTerm = potentialTerm + " " + tempNextTerm;
                            pos = nextIndex + 1;
                            index = docText.indexOf(" ", nextPos);
                            potentialTerm = percent(tempNextTerm);
                            updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                            if(index + 1 < docText.length()) {
                                continue;
                            }
                            else {
                                break;
                            }
                        } else if (tempNextTerm.equals("dollar")) {    //checks if its a money expression
                            tempNextTerm = potentialTerm + " " + tempNextTerm;
                            pos = nextIndex + 1;
                            index = docText.indexOf(" ", nextPos);
                            potentialTerm = dollar(tempNextTerm);
                            updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                            if(index + 1 < docText.length()) {
                                continue;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                    if(index + 1 < docText.length()) {
                        continue;
                    }
                    else {
                        break;
                    }
                }     //end of checking numbers

                String tempPotential = cleanTerm(potentialTerm);
                Matcher wordM = wordP.matcher(tempPotential);

                if (wordM.matches()) {  //if the term is a word
                    char last = potentialTerm.charAt(potentialTerm.length()-1);
                    potentialTerm = cleanTerm(potentialTerm);
                    Matcher upperCaseM = upperCaseP.matcher(potentialTerm);
                    if (upperCaseM.matches()) {
                        String temp = potentialTerm.toLowerCase();
                        if (months.containsKey(temp)) {   //if its a month
                            if(index + 1 < docText.length()) {
                                nextPos = index+1;
                                nextIndex = docText.indexOf(" ", nextPos);
                                String nextTerm = docText.substring(nextPos, nextIndex);
                                while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                                    nextPos = nextIndex + 1;
                                    nextIndex = docText.indexOf(" ", nextPos);
                                    nextTerm = docText.substring(nextPos, nextIndex);
                                }
                                String tempNextTerm = nextTerm;
                                if (nextTerm.length()>1 && nextTerm.charAt(nextTerm.length() - 1) == ',') { //todo tempNextTerm
                                    tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 2);
                                }
                                Matcher nextTermIsNumber = numberP.matcher(tempNextTerm);
                                if (nextTermIsNumber.matches()) {   //if month and day
                                    potentialTerm = potentialTerm + " " + tempNextTerm;
                                    //update pointers:
                                    pos = nextIndex + 1;
                                    index = docText.indexOf(" ", nextPos);

                                    if(index + 1 < docText.length()) {
                                        nextPos = index+1;
                                        nextIndex = docText.indexOf(" ", nextPos);
                                        nextTerm = docText.substring(nextPos, nextIndex);
                                        while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                                            nextPos = nextIndex + 1;
                                            nextIndex = docText.indexOf(" ", nextPos);
                                            nextTerm = docText.substring(nextPos, nextIndex);
                                        }
                                        tempNextTerm = nextTerm;
                                        if (nextTerm.length()>1 && specials.contains(nextTerm.charAt(nextTerm.length()-1))) { //todo tempNextTerm
                                            tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 1);
                                        }
                                        nextTermIsNumber = numberP.matcher(tempNextTerm);
                                        if (nextTermIsNumber.matches()) {    //if month, day and year
                                            potentialTerm = potentialTerm + " " + tempNextTerm;
                                            pos = nextIndex + 1;
                                            index = docText.indexOf(" ", nextPos);
                                            potentialTerm = dates(potentialTerm);
                                            updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                                            if(index + 1 < docText.length()) {
                                                continue;
                                            }
                                            else {
                                                break;
                                            }
                                        } else {  //the third term is not a year
                                            updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                                            if(index + 1 < docText.length()) {
                                                continue;
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                    }
                                }
                                potentialTerm = potentialTerm.toLowerCase();
                                updatePotentialTerm(doc.getKey(),potentialTerm, parsedTerms);
                                if(index + 1 < docText.length()) {
                                    continue;
                                }
                                else {
                                    break;
                                }
                            }
                        } else {

                            if(!(specials.contains(last))) {
                                if(index + 1 < docText.length()) {
                                    nextPos = index+1;
                                    nextIndex = docText.indexOf(" ", nextPos);
                                    String nextTerm = docText.substring(nextPos, nextIndex);
                                    while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                                        nextPos = nextIndex + 1;
                                        nextIndex = docText.indexOf(" ", nextPos);
                                        nextTerm = docText.substring(nextPos, nextIndex);
                                    }
                                    String tempNextTerm = cleanTerm(nextTerm);
                                    Matcher nextTermUC = upperCaseP.matcher(tempNextTerm);

                                    while (nextTerm != null && (nextTermUC.matches() || stopWords.contains(tempNextTerm))) {
                                        char lastChar =' ';
                                        if(nextTerm.length()>1) {
                                            lastChar = nextTerm.charAt(nextTerm.length() - 1);
                                        }
                                        potentialTerm = potentialTerm + " " + tempNextTerm;
                                        pos = nextIndex + 1;
                                        index = docText.indexOf(" ", nextPos);
                                        if (specials.contains(lastChar)) {   //todo check f it works
                                            pos = index + 1;
                                            index = docText.indexOf(" ", pos);
                                            break;
                                        }
                                        if(index + 1 < docText.length()){
                                            nextPos = index+1;
                                            nextIndex = docText.indexOf(" ", nextPos);
                                            nextTerm = docText.substring(nextPos, nextIndex);
                                            while(nextTerm.equals("") ||nextTerm.equals(" ") ){
                                                nextPos = nextIndex + 1;
                                                nextIndex = docText.indexOf(" ", nextPos);
                                                nextTerm = docText.substring(nextPos, nextIndex);
                                            }
                                        }
                                        else nextTerm = null;
                                        if (nextTerm != null) {
                                            tempNextTerm = cleanTerm(nextTerm);
                                            nextTermUC = upperCaseP.matcher(tempNextTerm);
                                        }
                                    }
                                    continue;

                                }
                            }

                            if (potentialTerm.contains(" ")) {
                                String[] potentialTerms = upperCaseWords(potentialTerm);
                                for (int i=0;i<potentialTerms.length;i++){
                                    //check if term exists, if not - create one, else - update the existing one
                                    updatePotentialTerm(doc.getKey(), potentialTerms[i], parsedTerms);
                                }
                                if(index + 1 < docText.length()) {
                                    continue;
                                }
                                else {
                                    break;
                                }
                            } else {
                                potentialTerm = potentialTerm.toLowerCase();
                                potentialTerm = cleanTerm(potentialTerm);
                                if(stopWords.contains(potentialTerm)){
                                    continue;
                                }
                                else {
                                    updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                    if(index + 1 < docText.length()) {

                                        continue;
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        potentialTerm = cleanTerm(potentialTerm);
                        if(stopWords.contains(potentialTerm)){
                            continue;
                        }
                        else {
                            updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                            if(index + 1 < docText.length()) {

                                continue;
                            }
                            else {
                                break;
                            }
                        }
                    }

                }
                updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);

            } // end of while




        }





//        String [] terms;
//        while (doc.hasMoreTokens()) {
//            String potentialTerm = checkIfNull(doc);
//            if(potentialTerm != null) {
//                terms = parseTerm(doc, potentialTerm);
//                if(terms != null) {
//                    for (int i = 0; i < terms.length; i++) {
//                        System.out.println(terms[i]);
//                    }
//                }
//            }
//        }

        return parsedTerms;
    }

        public void updatePotentialTerm(Document doc, String term, LinkedHashMap<String, MetaData> map) {
            if (map.get(term) == null) {
                MetaData potentialTermMetaData = new MetaData(1, 1, new HashMap<Document, Integer>());
                potentialTermMetaData.getFrequencyInDoc().put(doc, 1);
                map.put(term, potentialTermMetaData);
            } else {
                //if the term already exists, get it's metaData
                MetaData potentialTermMetaData = map.get(term);

                //check if the current doc is already in the metaData, if so increase it's tf, if not - add the doc and increase the df
                if(potentialTermMetaData.getFrequencyInDoc().get(doc) == null) {
                    potentialTermMetaData.setDf(potentialTermMetaData.getDf() + 1);
                    potentialTermMetaData.getFrequencyInDoc().put(doc, 1);
                }
                else {
                    potentialTermMetaData.getFrequencyInDoc().put(doc, potentialTermMetaData.getFrequencyInDoc().get(doc)+1);
                }
            }
        }


   /* public String[] parseTerm(StringTokenizer doc, String potentialTerm) {
        StringTokenizer ff = new StringTokenizer(doc.toString(), ". ");
        Matcher isTag = textTags.matcher((potentialTerm));

        String[] potentialTermsArr;
        String nextTerm;

        while (stopWords.contains(potentialTerm)) {
            potentialTerm = checkIfNull(doc);
        }

        if (isTag.matches()) {
            potentialTerm = checkIfNull(doc);
        }

        Matcher numberM = numberP.matcher(potentialTerm);
        Matcher hyphenWordsM = hyphenWordsP.matcher(potentialTerm);
        Matcher hyphenNumbersM = hyphenNumbersP.matcher(potentialTerm);

        if (hyphenNumbersM.matches()) {    //if its an expression with only numbers and hyphens
            String tempTerm = potentialTerm;
            if(specials.contains(potentialTerm.charAt(potentialTerm.length() - 1))){
                tempTerm = potentialTerm.substring(0,potentialTerm.length()-1);
            }
            potentialTermsArr = tempTerm.split("-");
            for (int i=0;i<potentialTermsArr.length;i++){
                potentialTermsArr[i] = numbers(potentialTermsArr[i]);
            }
            return potentialTermsArr;
        }
        if (hyphenWordsM.matches()) {   //if its an expression with only words and hyphens
            String tempTerm = potentialTerm;
            potentialTermsArr = wordsWithHyphen(tempTerm);
            return potentialTermsArr;
        }

        if (numberM.find()) {    //numbers with signs
            char first = potentialTerm.charAt(0);
            char last = potentialTerm.charAt(potentialTerm.length() - 1);
            if(potentialTerm.length()>1 && specials.contains(first)){
                potentialTerm = potentialTerm.substring(1,potentialTerm.length());
            }
            if (last == '$') {
                String tempTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                potentialTerm = dollar(tempTerm);
                potentialTermsArr = new String[1];
                potentialTermsArr[0] = potentialTerm;
                return potentialTermsArr;
            }
            if (last == '%') {
                String tempTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                potentialTerm = percent(tempTerm);
                potentialTermsArr = new String[1];
                potentialTermsArr[0] = potentialTerm;
                return potentialTermsArr;
            }
            if (specials.contains(last)) {
                potentialTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                numberM = numberP.matcher(potentialTerm);
            }
        }

        if (numberM.matches()) { //if the term is a legal number (including dots and commas)
            nextTerm = checkIfNull(doc);
            if(nextTerm != null) {
                Matcher ucNext = upperCaseP.matcher(nextTerm);
                if (ucNext.matches()) {     // checks dates
                    String tempNextTerm = nextTerm.toLowerCase();
                    if (months.containsKey(tempNextTerm)) {
                        potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month
                        nextTerm = checkIfNull(doc);
                        if (nextTerm != null) {
                            Matcher isYear = numberP.matcher(nextTerm);
                            if (isYear.matches()) {
                                potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month + year
                                potentialTerm = dates(potentialTerm);
                                potentialTermsArr = new String[1];
                                potentialTermsArr[0] = potentialTerm;
                                return potentialTermsArr;
                            } else {
                                stackOfTempTerms.push(nextTerm);   // the third term is not a year - save in stack
                                potentialTerm = dates(potentialTerm);
                                potentialTermsArr = new String[1];
                                potentialTermsArr[0] = potentialTerm;
                                return potentialTermsArr;
                            }
                        }
                    } else {
                        stackOfTempTerms.push(nextTerm);   //the second term is not a month - save in stack
                    }
                } else {
                    stackOfTempTerms.push(nextTerm);    //the second term is not upper case - save in stack
                }
            }

            if (potentialTerm.contains(".")) {    //decimal number or ip address
                if (potentialTerm.indexOf(".") == potentialTerm.lastIndexOf(".")) {  //there is only one dot
                    potentialTerm = numbers(potentialTerm);
                }
            }
            nextTerm = checkIfNull(doc);
            if(nextTerm != null) {
                String tempNextTerm = nextTerm.toLowerCase();
                if (tempNextTerm.equals("percent") || tempNextTerm.equals("percentage")) {   //checks if its a percent expression
                    tempNextTerm = potentialTerm + " " + tempNextTerm;
                    potentialTerm = percent(tempNextTerm);
                    potentialTermsArr = new String[1];
                    potentialTermsArr[0] = potentialTerm;
                    return potentialTermsArr;
                } else if (tempNextTerm.equals("dollar")) {    //checks if its a money expression
                    tempNextTerm = potentialTerm + " " + tempNextTerm;
                    potentialTerm = dollar(tempNextTerm);
                    potentialTermsArr = new String[1];
                    potentialTermsArr[0] = potentialTerm;
                    return potentialTermsArr;
                } else {
                    stackOfTempTerms.push(nextTerm);   //if its not a percent or money
                }
            }
        }     //end of checking numbers

/////////////todo here
        String tempPotential = cleanTerm(potentialTerm);
        Matcher wordM = wordP.matcher(tempPotential);

        if (wordM.matches()) {  //if the term is a word
            char last = potentialTerm.charAt(potentialTerm.length()-1);
            potentialTerm = cleanTerm(potentialTerm);
            Matcher upperCaseM = upperCaseP.matcher(potentialTerm);
            if (upperCaseM.matches()) {
                String temp = potentialTerm.toLowerCase();
                if (months.containsKey(temp)) {   //if its a month
                    nextTerm = checkIfNull(doc);
                    if(nextTerm!=null){
                    String tempNextTerm = nextTerm;
                    if (nextTerm.charAt(nextTerm.length() - 1) == ',') {
                        tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 2);
                    }
                    Matcher nextTermIsNumber = numberP.matcher(tempNextTerm);
                    if (nextTermIsNumber.matches()) {   //if month and day
                        potentialTerm = potentialTerm + " " + tempNextTerm;
                        nextTerm = checkIfNull(doc);
                        if (nextTerm != null) {
                            tempNextTerm = nextTerm;
                            if (specials.contains(nextTerm.charAt(nextTerm.length()-1))) {
                                tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 1);
                            }
                            nextTermIsNumber = numberP.matcher(tempNextTerm);
                            if (nextTermIsNumber.matches()) {    //if month, day and year
                                potentialTerm = potentialTerm + " " + tempNextTerm;
                                potentialTerm = dates(potentialTerm);
                                potentialTermsArr = new String[1];
                                potentialTermsArr[0] = potentialTerm;
                                return potentialTermsArr;
                            } else {  //the third term is not a year
                                stackOfTempTerms.push(nextTerm);
                                potentialTerm = dates(potentialTerm);
                                potentialTermsArr = new String[1];
                                potentialTermsArr[0] = potentialTerm;
                                return potentialTermsArr;
                            }
                        }
                    } else {   //the second term is not a day
                        stackOfTempTerms.push(nextTerm);
                    }
                    potentialTerm = potentialTerm.toLowerCase();
                    potentialTermsArr = new String[1];
                    potentialTermsArr[0] = potentialTerm;
                    return potentialTermsArr;
                }
                } else {

                    if(!(specials.contains(last))) {
                        nextTerm = checkIfNull(doc);
                        if (nextTerm != null) {
                            String tempNextTerm = cleanTerm(nextTerm);
                            Matcher nextTermUC = upperCaseP.matcher(tempNextTerm);

                            while (tempNextTerm != null && (nextTermUC.matches() || stopWords.contains(tempNextTerm))) {
                                char lastChar =' ';
                                if(nextTerm.length()>1) {
                                    lastChar = nextTerm.charAt(nextTerm.length() - 1);
                                }
                                potentialTerm = potentialTerm + " " + tempNextTerm;
                                if (specials.contains(lastChar)) {
                                    nextTerm = null;
                                    break;
                                }
                                nextTerm = checkIfNull(doc);
                                if (nextTerm != null) {
                                    tempNextTerm = cleanTerm(nextTerm);
                                    nextTermUC = upperCaseP.matcher(tempNextTerm);
                                }
                            }
                            if (nextTerm != null) {
                                stackOfTempTerms.push(nextTerm);
                            }
                        }
                    }

                    if (potentialTerm.contains(" ")) {
                        String[] potentialTerms = upperCaseWords(potentialTerm);  //todo - no need to pass all the function
                        return potentialTerms;
                    } else {
                        potentialTerm = potentialTerm.toLowerCase();
                        potentialTerm = cleanTerm(potentialTerm);
                        if(stopWords.contains(potentialTerm)){
                            potentialTerm = null;
                        }
                        if (potentialTerm!=null) {
                            potentialTermsArr = new String[1]; //todo - no need to pass all the function
                            potentialTermsArr[0] = potentialTerm;
                            return potentialTermsArr;
                        }
                    }
                }
            }
        }
        if(potentialTerm!=null) {
            potentialTermsArr = new String[1]; //todo - no need to pass all the function
            potentialTermsArr[0] = potentialTerm;
            return potentialTermsArr;
        }
        else return null;
    }*/

}

