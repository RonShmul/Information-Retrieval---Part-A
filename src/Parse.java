import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parse {

    private static Map<String, String> months;
    private HashSet<String> stopWords;
    private Pattern wordP;
    private Pattern numberP;
    private Pattern upperCaseP;
    private Pattern hyphenWordsP;
    private Pattern hyphenNumbersP;
    private HashSet<Character> specials;
    private boolean isStemm;
    /**
     * constructor
     */
    public Parse(boolean isStemm) {
        this.isStemm = isStemm;
        specials = new HashSet<>();
        stopWords = new HashSet<>();
        months = new HashMap<String, String>();
        wordP = Pattern.compile("\\w+(,|)");
        numberP = Pattern.compile("\\d+(,\\d+)*(.\\d+)*(th|)");
        upperCaseP = Pattern.compile("[A-Z][a-z]+|[A-Z]+");
        hyphenWordsP = Pattern.compile("\\w+((-)+\\w+)+");
        hyphenNumbersP = Pattern.compile("\\d+(-\\d+)+");

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
                try {
                    int digit = (Integer.parseInt(str1.substring(1, 2))) + 1;
                    str = str0 + "." + Integer.parseInt(str1.substring(0, 1)) + digit;
                } catch (NumberFormatException ex) {
                }
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

    /**
     * get the date in the wrong format and change it to the right format
     *
     * @param str
     * @return String
     */
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
     * get HashMap of <Document, String> with all the documents and their content, format the words,
     * calculate data on each term and put in in a HashMap that contains String as key and MataData as value.
     *
     * @param termsOfFile
     * @returnLinkedHashMap<String, MetaData> parsedTerms
     */
    public HashMap<String, MetaData> parse(LinkedHashMap<Document, String> termsOfFile) {
        HashMap<String, MetaData> parsedTerms = new HashMap<>();

        //iterate on all the documents in the file
        for (Map.Entry<Document, String> doc : termsOfFile.entrySet()) {

            //get the document text
            String docText = doc.getValue();
            docText += " ";
//            String[] terms = docText.split("\\W+");
//
//            for (int i = 0; i < terms.length; i++) {
//                updatePotentialTerm(doc.getKey(), terms[i], parsedTerms);
//            }

            //updating the pointers
            int pos = 0;
            int index = 0;
            pos = getPosToAfterWhiteSpaces(pos, index, docText);
            index = docText.indexOf(" ", pos);

            //the main while running on a document
            while (pos < docText.length() && index < docText.length() && pos != -1 && index != -1) {
                String potentialTerm = docText.substring(pos, index);

                //while that skip words with tags, stop words, signs and white spaces
                while (pos != -1 && index != -1 && index + 1 < docText.length() && (stopWords.contains(potentialTerm)
                        || potentialTerm.contains("<") || potentialTerm.contains(">") || potentialTerm.equals("--")
                        || potentialTerm.equals("-") || potentialTerm.equals(",")|| potentialTerm.equals("*")
                        || potentialTerm.equals(".") || potentialTerm.equals("+")
                        || potentialTerm.equals("&")) || potentialTerm.equals("") || potentialTerm.equals(" ")) {

                    pos = getPosToAfterWhiteSpaces(index + 1, docText.indexOf(" ", index+1), docText);
                    index = docText.indexOf(" ", pos);
                    if(pos == -1 || index == -1)
                        break;
                    potentialTerm = docText.substring(pos, index);
                }

                //if end of document - break from the main loop
                if (index + 1 >= docText.length() || index == -1 || pos == -1) {
                    updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                    doc.getKey().setLength(doc.getKey().getLength()+1);
                    break;
                }
                //configure the matcher with the potential term
                Matcher numberM = numberP.matcher(potentialTerm);
                Matcher hyphenNumbersM = hyphenNumbersP.matcher(potentialTerm);
                String tempTermHyphen = cleanTerm(potentialTerm);
                Matcher hyphenWordsM = hyphenWordsP.matcher(tempTermHyphen);

                //if its an expression with only numbers and hyphens
                if (hyphenNumbersM.matches()) {
                    //split the numbers to array
                    String[] potentialTermsArr = potentialTerm.split("-");

                    //iterate the array to parse it's terms
                    for (int i = 0; i < potentialTermsArr.length; i++) {

                        //format the numbers
                        potentialTermsArr[i] = numbers(potentialTermsArr[i]);

                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(), potentialTermsArr[i], parsedTerms);
                        doc.getKey().setLength(doc.getKey().getLength()+1);
                    }

                    //update the pointer
                    if(pos == -1 || index == -1)
                        break;
                    pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                    index = docText.indexOf(" ", pos);
                    if(pos == -1 || index == -1)
                        break;
                    else continue;
                }


                if (hyphenWordsM.matches()) {   //if its an expression with only words and hyphens
                    String tempTerm = potentialTerm;
                    String[] potentialTermsArr = wordsWithHyphen(cleanTerm(tempTerm));
                    for (int i = 0; i < potentialTermsArr.length; i++) {
                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(), potentialTermsArr[i], parsedTerms);
                        doc.getKey().setLength(doc.getKey().getLength()+1);
                    }
                    //update the pointers
                    if(pos == -1 || index == -1)
                        break;
                    pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                    index = docText.indexOf(" ", pos);
                    if(pos == -1 || index == -1)
                        break;
                    else continue;
                }

                //checking numbers with signs
                if (numberM.find()) {
                    char first = potentialTerm.charAt(0);
                    if (potentialTerm.length() > 1 && specials.contains(first)) {
                        potentialTerm = potentialTerm.substring(1, potentialTerm.length());
                    }

                    if (first == '$') {
                        String tempTerm = potentialTerm;
                        potentialTerm = dollar(tempTerm);
                        //check if term exists, if not - create one, else - update the existing one
                        updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                        doc.getKey().setLength(doc.getKey().getLength()+1);
                        if(pos == -1 || index == -1)
                            break;
                        pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                        index = docText.indexOf(" ", pos);
                        if(pos == -1 || index == -1)
                            break;
                        else continue;
                    }
                    if (potentialTerm.length() > 1) {
                        char last = potentialTerm.charAt(potentialTerm.length() - 1);
                        if (last == '%') {
                            String tempTerm = potentialTerm.substring(0, potentialTerm.length() - 1);

                            potentialTerm = percent(potentialTerm);
                            //check if term exists, if not - create one, else - update the existing one
                            updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                            doc.getKey().setLength(doc.getKey().getLength()+1);
                            //update pointers
                            if(pos == -1 || index == -1)
                                break;
                            pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                            index = docText.indexOf(" ", pos);
                            if(pos == -1 || index == -1)
                                break;
                            else continue;
                        }
                        if (specials.contains(last)) {
                            potentialTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                            numberM = numberP.matcher(potentialTerm);
                        }
                    }
                }

                //create indexes for next terms
                int nextPos = index + 1;
                int nextIndex = docText.indexOf(" ", nextPos);
                nextPos = getPosToAfterWhiteSpaces(nextPos, nextIndex, docText);
                nextIndex = docText.indexOf(" ", nextPos);

                //if the term is a legal number (including dots and commas)
                if (numberM.matches()) {
                    if (nextPos != -1 && nextIndex != -1) { // if there is next term
                        //create next term
                        String nextTerm = docText.substring(nextPos, nextIndex);

                        //check if its in a date format
                        Matcher ucNext = upperCaseP.matcher(nextTerm);
                        if (ucNext.matches()) {
                            String tempNextTerm = nextTerm.toLowerCase();
                            if (months.containsKey(tempNextTerm)) {
                                potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month

                                index = nextIndex;


                                nextPos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                nextIndex = docText.indexOf(" ", nextPos);

                                if (nextPos != -1 && nextIndex != -1) {
                                    nextTerm = docText.substring(nextPos, nextIndex);
                                    //checking if the last character is special like 1993. will be changed to 1993
                                    if(nextTerm.length() > 1 && specials.contains(nextTerm.charAt(nextTerm.length()-1))) {
                                        nextTerm = nextTerm.substring(0, nextTerm.length()-1);
                                    }
                                    Matcher isYear = numberP.matcher(nextTerm);
                                    if (isYear.matches()) {
                                        potentialTerm = potentialTerm + " " + nextTerm;  //if i have day + month + year
                                        potentialTerm = dates(potentialTerm);
                                        index = nextIndex;
                                        //check if term exists, if not - create one, else - update the existing one
                                        updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                        doc.getKey().setLength(doc.getKey().getLength()+1);
                                        //update pointers
                                        if(pos == -1 || index == -1)
                                            break;
                                        pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                        index = docText.indexOf(" ", pos);
                                        if(pos == -1 || index == -1)
                                            break;
                                        else continue;
                                    } else {
                                        // the third term is not a year
                                        potentialTerm = dates(potentialTerm);
                                        //check if term exists, if not - create one, else - update the existing one
                                        updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                        doc.getKey().setLength(doc.getKey().getLength()+1);
                                        //update pointers
                                        if(pos == -1 || index == -1)
                                            break;
                                        pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                        index = docText.indexOf(" ", pos);
                                        if(pos == -1 || index == -1)
                                            break;
                                        else continue;
                                    }//end of else - not a year
                                } //there is no nextPos so nothing after month
                            }//not a month
                        } //not uppercase
                    }//no nextPos so nothing after the number

                    if (potentialTerm.contains(".")) {    //decimal number or ip address
                        if (potentialTerm.indexOf(".") == potentialTerm.lastIndexOf(".")) {  //there is only one dot
                            char last = potentialTerm.charAt(potentialTerm.length() - 1);
                            if (potentialTerm.length() > 1 && specials.contains(last)) {
                                potentialTerm = potentialTerm.substring(0, potentialTerm.length() - 1);
                            }
                            potentialTerm = numbers(potentialTerm);
                        }
                    }
                    if (potentialTerm.contains(",")) {
                        potentialTerm = numbers(potentialTerm);
                    }

                    if (index + 1 < docText.length()) {
                        nextPos = index + 1;
                        nextIndex = docText.indexOf(" ", nextPos);
                        nextPos = getPosToAfterWhiteSpaces(nextPos, nextIndex, docText);
                        nextIndex = docText.indexOf(" ", nextPos);
                    } else {
                        nextPos = -1;
                    }

                    if (nextPos != -1 && nextIndex != -1) {
                        String nextTerm = docText.substring(nextPos, nextIndex);

                        String tempNextTerm = nextTerm.toLowerCase();//todo check dots or even clean the term
                        if (tempNextTerm.equals("percent") || tempNextTerm.equals("percentage")) {   //checks if its a percent expression
                            tempNextTerm = potentialTerm + " " + tempNextTerm;
                            potentialTerm = percent(tempNextTerm);

                            // insert to the data structure
                            updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                            doc.getKey().setLength(doc.getKey().getLength()+1);
                            //update pointers
                            if(pos == -1 || index == -1)
                                break;
                            pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                            index = docText.indexOf(" ", pos);
                            if(pos == -1 || index == -1)
                                break;
                            else continue;
                        } else if (tempNextTerm.equals("dollar")) {    //checks if its a money expression
                            tempNextTerm = potentialTerm + " " + tempNextTerm;

                            potentialTerm = dollar(tempNextTerm);
                            //insert to the data structure
                            updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                            doc.getKey().setLength(doc.getKey().getLength()+1);
                            //update pointers
                            if(pos == -1 || index == -1)
                                break;
                            pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                            index = docText.indexOf(" ", pos);
                            if(pos == -1 || index == -1)
                                break;
                            else continue;
                        }
                    }
                    //just a regular number. insert to the data structure
                    updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                    doc.getKey().setLength(doc.getKey().getLength()+1);
                    //update pointers
                    if(pos == -1 || index == -1)
                        break;
                    pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                    index = docText.indexOf(" ", pos);
                    if(pos == -1 || index == -1)
                        break;
                    else continue;
                }     //end of checking numbers

                String tempPotential = cleanTerm(potentialTerm);
                Matcher wordM = wordP.matcher(tempPotential);

                //if the term is a word
                if (wordM.matches()) {
                    char last = potentialTerm.charAt(potentialTerm.length() - 1);
                    potentialTerm = cleanTerm(potentialTerm);
                    Matcher upperCaseM = upperCaseP.matcher(potentialTerm);
                    if (upperCaseM.matches()) {
                        String temp = potentialTerm.toLowerCase();
                        //if its a month
                        if (months.containsKey(temp)) {

                            nextPos = index + 1;
                            nextIndex = docText.indexOf(" ", nextPos);
                            nextPos = getPosToAfterWhiteSpaces(nextPos, nextIndex, docText);
                            if (nextPos != -1) {
                                nextIndex = docText.indexOf(" ", nextPos);
                                if (nextIndex != -1) {
                                    String nextTerm = docText.substring(nextPos, nextIndex);

                                    String tempNextTerm = nextTerm;
                                    if (nextTerm.length() > 1 && nextTerm.charAt(nextTerm.length() - 1) == ',') { //todo tempNextTerm
                                        tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 2);
                                    }
                                    Matcher nextTermIsNumber = numberP.matcher(tempNextTerm);
                                    if (nextTermIsNumber.matches()) {   //if month and day
                                        potentialTerm = potentialTerm + " " + tempNextTerm;
                                        //update pointers:
                                        index = nextIndex;

                                        nextPos = index + 1;
                                        nextIndex = docText.indexOf(" ", nextPos);
                                        nextPos = getPosToAfterWhiteSpaces(nextPos, nextIndex, docText);
                                        nextIndex = docText.indexOf(" ", nextPos);

                                        if (nextPos != -1 && nextIndex != -1) {
                                            nextTerm = docText.substring(nextPos, nextIndex);

                                            tempNextTerm = nextTerm;
                                            if (nextTerm.length() > 1 && specials.contains(nextTerm.charAt(nextTerm.length() - 1))) { //todo tempNextTerm
                                                tempNextTerm = tempNextTerm.substring(0, tempNextTerm.length() - 1);
                                            }
                                            nextTermIsNumber = numberP.matcher(tempNextTerm);
                                            if (nextTermIsNumber.matches()) {    //if month, day and year
                                                potentialTerm = potentialTerm + " " + tempNextTerm;
                                                index = nextIndex;
                                                potentialTerm = dates(potentialTerm);
                                                //insert to the data structure
                                                updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                                doc.getKey().setLength(doc.getKey().getLength()+1);
                                                //update pointers
                                                if(pos == -1 || index == -1)
                                                    break;
                                                pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                                index = docText.indexOf(" ", pos);
                                                if(pos == -1 || index == -1)
                                                    break;
                                                else continue;
                                            } else {  //the third term is not a year
                                                //insert to data structure month + year
                                                updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                                doc.getKey().setLength(doc.getKey().getLength()+1);
                                                //update pointers
                                                if(pos == -1 || index == -1)
                                                    break;
                                                pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                                index = docText.indexOf(" ", pos);
                                                if(pos == -1 || index == -1)
                                                    break;
                                                else continue;
                                            } //end of else - no year
                                        } // there is nothing after the day
                                    } // there is no day after month
                                } // there is nothing after month
                                potentialTerm = potentialTerm.toLowerCase();
                                //insert the month to the data structure
                                updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                doc.getKey().setLength(doc.getKey().getLength()+1);
                                if(pos == -1 || index == -1)
                                    break;
                                pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                index = docText.indexOf(" ", pos);
                                if(pos == -1 || index == -1)
                                    break;
                                else continue;
                            } //nothing after month
                        } else { //the word is not a month

                            if (!(specials.contains(last))) {
                                nextPos = index + 1;
                                nextIndex = docText.indexOf(" ", nextPos);
                                nextPos = getPosToAfterWhiteSpaces(nextPos, nextIndex, docText);
                                nextIndex = docText.indexOf(" ", nextPos);

                                if (nextPos != -1 && nextIndex != -1) {
                                    String nextTerm = docText.substring(nextPos, nextIndex);
                                    String tempNextTerm = cleanTerm(nextTerm);
                                    Matcher nextTermUC = upperCaseP.matcher(tempNextTerm);

                                    while (nextTerm != null && (nextTermUC.matches() || stopWords.contains(tempNextTerm.toLowerCase()))) {//todo toLowerCase? I added..ron?
                                        //take the last char of the next term to check after that if the next term is at the end of a sentance.
                                        char lastChar = ' ';
                                        if (nextTerm.length() > 1) {
                                            lastChar = nextTerm.charAt(nextTerm.length() - 1);
                                        }
                                        //concat the next term because it's also uppercase
                                        potentialTerm = potentialTerm + " " + tempNextTerm;
                                        index = nextIndex;
                                        if (specials.contains(lastChar)) {   //todo check f it works
                                            //change the pointers to be after the nextTerm (the last nextTerm to concat to the potential term)
                                            pos = index + 1;
                                            index = docText.indexOf(" ", pos);
                                            pos = getPosToAfterWhiteSpaces(pos, index, docText);
                                            index = docText.indexOf(" ", pos);
                                            break;
                                        }
                                        //get the next term
                                        nextPos = getPosToAfterWhiteSpaces(index + 1, docText.indexOf(" ", index + 1), docText);
                                        nextIndex = docText.indexOf(" ", nextPos);

                                        if (nextPos != -1 && nextIndex !=-1) {//there is a next term
                                            nextTerm = docText.substring(nextPos, nextIndex);

                                        } else nextTerm = null;//there is no next term
                                        if (nextTerm != null) {
                                            tempNextTerm = cleanTerm(nextTerm);
                                            nextTermUC = upperCaseP.matcher(tempNextTerm);
                                        }
                                    }// and of while
                                }//there is no next term
                            }//there is a special character after the potential term

                            if (potentialTerm.contains(" ")) {
                                String[] potentialTerms = upperCaseWords(potentialTerm);
                                for (int i = 0; i < potentialTerms.length; i++) {
                                    //insert to data structure each cell in the array
                                    updatePotentialTerm(doc.getKey(), potentialTerms[i], parsedTerms);
                                    if(i != potentialTerms.length -1)
                                        doc.getKey().setLength(doc.getKey().getLength()+1);

                                }
                                //update the pointers
                                if(pos == -1 || index == -1)
                                    break;
                                pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                index = docText.indexOf(" ", pos);
                                if(pos == -1 || index == -1)
                                    break;
                                else continue;

                            } else {// potentialTerm is only one word
                                potentialTerm = potentialTerm.toLowerCase();
                                potentialTerm = cleanTerm(potentialTerm);
                                // if this word is stop word. skip it and continue (update the pointers)
                                if (stopWords.contains(potentialTerm)) {
                                    pos = index + 1;
                                    index = docText.indexOf(" ", pos);
                                    pos = getPosToAfterWhiteSpaces(pos, index, docText);
                                    if (pos != -1) {
                                        index = docText.indexOf(" ", pos);
                                        continue;
                                    } else {
                                        break;
                                    }

                                } else {
                                    updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                                    doc.getKey().setLength(doc.getKey().getLength()+1);
                                    if(pos == -1 || index == -1)
                                        break;
                                    pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                                    index = docText.indexOf(" ", pos);
                                    if(pos == -1 || index == -1)
                                        break;
                                    else continue;
                                } // end of else not a stop words
                            } // end of else - only one word
                        } // end of else - not a month. expression handle
                    } else { // not an uppercase
                        potentialTerm = cleanTerm(potentialTerm);
                        potentialTerm = potentialTerm.toLowerCase();
                        if (stopWords.contains(potentialTerm)) {
                            pos = index + 1;
                            index = docText.indexOf(" ", pos);
                            pos = getPosToAfterWhiteSpaces(pos, index, docText);
                            if (pos != -1) {
                                index = docText.indexOf(" ", pos);
                                continue;
                            } else {
                                break;
                            }
                        } else {
                            updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                            doc.getKey().setLength(doc.getKey().getLength()+1);
                            if(pos == -1 || index == -1)
                                break;
                            pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                            index = docText.indexOf(" ", pos);
                            if(pos == -1 || index == -1)
                                break;
                            else continue;
                        } // end of generic word handle
                    } //end of else - not an uppercase
                } //end of if its a word
                updatePotentialTerm(doc.getKey(), potentialTerm, parsedTerms);
                doc.getKey().setLength(doc.getKey().getLength()+1);
                if(pos == -1 || index == -1)
                    break;
                pos = getPosToAfterWhiteSpaces(index+1, docText.indexOf(" ", index+1), docText);
                index = docText.indexOf(" ", pos);
                if(pos == -1 || index == -1)
                    break;
                else continue;
            } // end of while
        }
        return parsedTerms;
    }

    /**
     * get a position indexes and a document text and move the indexes to be atan actual term and not a whitespace.
     * returns the pos index (first index at the begining of the word.) if its end of document - returns -1
     *
     * @param pos
     * @param index
     * @param docText
     * @return int
     */
    private int getPosToAfterWhiteSpaces(int pos, int index, String docText) {
        int resPos = pos;
        int resIndex = index;
        if(resPos == -1 || resIndex == -1)
            return -1;
        while (resIndex < docText.length() && (resIndex != -1) && (resPos == resIndex
                || docText.substring(resPos, resIndex) == ""
                || docText.substring(resPos, resIndex) == " ")) {
            if(resPos == -1 || resIndex == -1)
                return -1;
            resPos = resIndex + 1;
            resIndex = docText.indexOf(" ", resPos);
        }
        return resPos;
    }

    /**
     * get the potential term and add it to the parsed terms hashMap.
     * The hashMap contains String as a key and metaData as the value.
     *
     * @param doc
     * @param term
     * @param map
     */
    public void updatePotentialTerm(Document doc, String term, HashMap<String, MetaData> map) {
        if(term != null && term != "") {
        term = term.replaceAll("[^a-zA-Z0-9 ]", "");
        term = term.toLowerCase();
            if (isStemm) {
                Stemmer stemmer = new Stemmer();
                stemmer.add(term.toCharArray(), term.length());
                stemmer.stem();
                term = stemmer.toString();
            }

            if (map.get(term) == null) {
                MetaData potentialTermMetaData = new MetaData(1, 1, new HashMap<Document, Integer>());
                potentialTermMetaData.getFrequencyInDoc().put(doc, 1);
                map.put(term, potentialTermMetaData);
            } else {
                //if the term already exists, get it's metaData
                MetaData potentialTermMetaData = map.get(term);
                int currFreqInCorpus = potentialTermMetaData.getFrequencyInCorpus();
                potentialTermMetaData.setFrequencyInCorpus(currFreqInCorpus + 1);

                //check if the current doc is already in the metaData, if so increase it's tf, if not - add the doc and increase the df
                if (potentialTermMetaData.getFrequencyInDoc().get(doc) == null) {
                    potentialTermMetaData.setDf(potentialTermMetaData.getDf() + 1);
                    potentialTermMetaData.getFrequencyInDoc().put(doc, 1);
                } else {
                    int currTf = potentialTermMetaData.getFrequencyInDoc().get(doc);
                    potentialTermMetaData.getFrequencyInDoc().put(doc, currTf + 1);
                    if (doc.getMaxTf() < currTf) {
                        doc.setMaxTf(currTf);
                        doc.setCommonTerm(term);
                    }
                }
            }
        }
    }

    /**
     * function to parse number with percent to be in the format: number percent.
     * call to the function: number to fix the decimal number.
     *
     * @param str
     * @return String
     */
    public String percent(String str) {
        if (str.contains("%")) {
            if (str.length() > 1) {
                str = str.substring(0, str.length()-1);
                if (str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == ',')
                    str = str.substring(0, str.length() - 1);
            }
            Matcher number = numberP.matcher(str);
            if(number.matches()) {
                return numbers(str) + " percent";
            }
            else return str + " percent";

        } else {
            str = str.substring(0, str.indexOf(" "));
            Matcher number = numberP.matcher(str);
            if(number.matches()) {
                return numbers(str) + " percent";
            }
            return str + " percent";
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
        s = "";
        for (int i = 0; i < result.length; i++) {
            result[i] = cleanTerm(result[i]);
            s = s + result[i];
            if (i != result.length - 1)
                s = s + " ";
            if (!stopWords.contains(result[i])) {
                temp = temp + result[i];
                if (i != result.length - 1)
                    temp = temp + " ";
            }
        }
        String[] res = temp.split(" ");
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
        String temp = "";
        String[] result = str.split(Pattern.quote("-"));

        for (int i = 0; i < result.length; i++) {
            if(result[i].equals(""))
                continue;
            result[i] = cleanTerm(result[i]);
            result[i].toLowerCase();
            if (i != result.length - 1)
            if (!stopWords.contains(result[i])) {
                temp = temp + result[i];
                if (i != result.length - 1)
                    temp = temp + " ";
            }
        }
        return temp.split(" ");

    }

    /**
     * @param str
     * @return String
     */
    String removeS(String str) {
        if (str.length() > 2 && str.charAt(str.length() - 1) == 's' && str.charAt(str.length() - 2) == '\'') {
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
            if (str.length() > 1) {
                str = str.substring(1, str.length());
                if (str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == ',')
                    str = str.substring(0, str.length() - 1);
            }
            Matcher number = numberP.matcher(str);
            if(number.matches()) {
                return numbers(str) + " dollar";
            }
            else return str + " dollar";

        } else {
            str = str.substring(0, str.indexOf(" "));
            Matcher number = numberP.matcher(str);
            if(number.matches()) {
                return numbers(str) + " dollar";
            }
            return str + " dollar";
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

    /**
     * get string with dots, brackets, etc and clean it from everything but letters or numbers.
     *
     * @param term
     * @return
     */
    public String cleanTerm(String term) {
        String temp = dotsBetweenWords(term);
        temp = removeS(temp);
        if (temp.length() > 1 && specials.contains(temp.charAt(temp.length() - 1))) {
            temp = temp.substring(0, temp.length() - 1);
        }
        if (temp.length() > 1 && specials.contains(temp.charAt(0))) {
            temp = temp.substring(1, temp.length());
        }
        temp = temp.replaceAll("[\\[\\](){}]|\"|:|;", "");
        return temp;
    }

}