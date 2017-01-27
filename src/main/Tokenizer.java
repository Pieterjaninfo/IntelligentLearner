package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * The tokenizer class with tools to tokenize words.
 * Corpus1: T(200,6000)
 * Corpus2: T(50/6000) Chi(0.0) => 96.2%
 */
public class Tokenizer {

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(new String[] {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"}));
    private static final int minThreshold = 50;          // Words that occur less than this amount are considered rare words
    private static final int maxThreshold = 6000;      // Words that occur more than this amount are considered often occurring words

    private static int countStopwords = 0;          // How many stopwords were deleted
    private static int countMinThreshold = 0;       // How many words below threshold were deleted
    private static int countMaxThreshold = 0;       // How many words above threshold were deleted

    private static double chiSquareValue = 1.0;       // CHI SQUARE VALUE FOR FILTERING OUT
    private static int MAX_CHI_WORDS = 20000;             // Amount of highest chi square words allowed

    private static boolean debug = true;
    private static boolean debugHard = false;

    private Tokenizer() {   }

    public static void setChiSquareValue(double newChiSquareValue) { chiSquareValue = newChiSquareValue; }

    /**
     * Normalizes the given string and returns the separate normalized words in a string array.
     * @param line Raw untokenized string
     * @return Normalized words in a string array
     */
    public static String[] normalizeText(String line) {
        return line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
    }

    /**
     * Normalize the given string and add it to the given HashMap
     * @param line Raw untokenized string
     * @param words HashMap of WORDS -> COUNT
     */
    public static void normalizeTextAddToHashMap(String line, HashMap<String, Integer> words) {
        String[] normalizedWords = normalizeText(line);
        for (String word : normalizedWords) {
            if (words.containsKey(word)){
                words.put(word, words.get(word) + 1);
            } else {
                words.put(word, 1);
            }
        }
    }

    /**
     * Removes stopwords from the given HashMap
     * @param words The HashMap that need t
     */
    public static void removeStopwords(HashMap<String, Integer> words) {
        int countStopwords = 0;
        Iterator<Map.Entry<String, Integer>> iter = words.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            if (STOPWORDS.contains(entry.getKey())) {
                if(debugHard) System.out.println("[Tokenizer.java] Removed stopword: " + entry.getKey() + ".");
                iter.remove();
                countStopwords++;
            }
        }
        if (debug) System.out.println("[Tokenizer.java] Removed " + countStopwords + " stopwords.");
    }

    /**
     * Removes words that violate that maximum or minimum amount of allowed occurrences.
     * @param words Words Map that need to be rid of violating occurrences
     */
    public static void removeThresholdViolatingWords(HashMap<String, Integer> words) {
        int countMinThreshold = 0;
        int countMaxThreshold = 0;
        Iterator<Map.Entry<String, Integer>> iter = words.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = iter.next();
            if (entry.getValue() < minThreshold) {
                if (debugHard) System.out.println("[Tokenizer.java] Removed word: " + entry.getKey() + " with " + entry.getValue() + " occurrences.");
                iter.remove();
                countMinThreshold++;
            } else if (entry.getValue() > maxThreshold) {
                if(debugHard) System.out.println("[Tokenizer.java] Removed word: " + entry.getKey() + " with " + entry.getValue() + " occurrences.");
                iter.remove();
                countMaxThreshold++;
            }
        }
        if (debug) System.out.println("[Tokenizer.java] Removed " + countMinThreshold + " words below " + minThreshold + " occurrences and " +
                "removed " + countMaxThreshold + " words above " + maxThreshold + " occurrences.");
    }

    //----------------------------------------------------------------------------------------------------------------
    //  Chi-Square Feature Selection
    //----------------------------------------------------------------------------------------------------------------

    public static HashSet<String> getViableChiSquareWords(Collection<String> words) {
        HashSet<String> infoGainLessWords = new HashSet<>();
        HashMap<DataClass, Integer> frequencyTable = new HashMap<>();    // MAPS CLASS -> amount of docs word occurs in

        //FOREACH word setup frequency table
        for (String word : words) {
            for (DataClass dataClass : DataClass.getClasses().values()) {
                //FOREACH DOCUMENT
                int docs = 0;
                for (HashMap<String, Integer> documents : dataClass.getDocuments().values()) {
                    if (documents.containsKey(word)) {
                        docs++;
                    }
                }
                frequencyTable.put(dataClass, docs);
            }
            int[][] table = createChiTable(frequencyTable);
            double chiSquareValue = calculateChiSquareValue(table);

//            printTable(table);
//            System.out.println("WORD: " + word + " CHI SQUARE VALUE: " + chiSquareValue);
            if (chiSquareValue < Tokenizer.chiSquareValue) { infoGainLessWords.add(word); }
        }
        if (debug) System.out.println("A total of " + infoGainLessWords.size() + " words have a deficient information gain.");
        return infoGainLessWords;
    }

    /**
     *  Returns the words with the highest ChiSquare values.
     */
    public static HashSet<String> getHighestChiSquareWords(Collection<String> words) {
        HashMap<Double, HashSet<String>> chiValues = new HashMap<>();
        HashMap<DataClass, Integer> frequencyTable = new HashMap<>();    // MAPS CLASS -> amount of docs word occurs in

        //FOREACH word setup frequency table
        for (String word : words) {
            for (DataClass dataClass : DataClass.getClasses().values()) {
                //FOREACH DOCUMENT
                int docs = 0;
                for (HashMap<String, Integer> documents : dataClass.getDocuments().values()) {
                    if (documents.containsKey(word)) {
                        docs++;
                    }
                }
                frequencyTable.put(dataClass, docs);
            }
            int[][] table = createChiTable(frequencyTable);
            double chiSquareValue = calculateChiSquareValue(table);

            if (!chiValues.containsKey(chiSquareValue)) { chiValues.put(chiSquareValue, new HashSet<>()); }
            chiValues.get(chiSquareValue).add(word);
        }
        return getTopChiSquareWords(chiValues);
    }

    /*
     * Calculates words with highest ChiSquare values.
     */
    private static HashSet<String> getTopChiSquareWords(HashMap<Double, HashSet<String>> chiWords) {
        HashSet<String> highestChiValues = new HashSet<>();
        HashSet<String> lowestChiValues = new HashSet<>();
        TreeMap<Double, HashSet<String>> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(chiWords);
        writeChiValues(sortedMap);

        int i = 0;
        A: for (Double chiValue : sortedMap.keySet()) {
            for (String word : sortedMap.get(chiValue)) {
                if (chiValue > 0) {
                    if (debugHard) System.out.printf("Word: %9s with ChiSquare Value: %.2f\n", word, chiValue);
                    highestChiValues.add(word);
                    i++;
                } else {
                    lowestChiValues.add(word);
                }
                if (i == MAX_CHI_WORDS) { break A; }
            }
        }
        return lowestChiValues;
    }

    public static int[][] createChiTable(HashMap<DataClass, Integer> frequencies) {
        int classAmount = frequencies.keySet().size();
        int[][] table = new int[3][classAmount + 1]; //COLUMNS: indicate Class ROWS:0=word occurs in amount docs 2=total docs in class

        int j = 0;
        for (DataClass dataClass : frequencies.keySet()) {
            table[0][j] = frequencies.get(dataClass);
            table[2][j] = dataClass.getAmountOfDocs();
            table[1][j] = table[2][j] - table[0][j];

            table[0][classAmount] = table[0][classAmount] + table[0][j];
            table[1][classAmount] = table[1][classAmount] + table[1][j];
            table[2][classAmount] = table[2][classAmount] + table[2][j];

            j++;
        }
        return table;
    }

    public static double calculateChiSquareValue(int[][] table) {
        double result = 0;
        int rows = table.length - 1;            //Index of largest row and column
        int cols = table[0].length - 1;

        double expectedValue;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                expectedValue = table[rows][j] * table[i][cols] / (double) table[rows][cols];
                result += Math.pow((table[i][j] - expectedValue), 2) / expectedValue;
            }
        }
        return result;
    }

    public static void printTable(int[][] table) {
        for (int x = 0; x < table.length; x++) {
            System.out.print("[");
            for (int y = 0; y < table[0].length; y++) {
                System.out.printf("%6d", table[x][y]);
            }
            System.out.println(" ]");
        }
        System.out.print("\n");
    }

    public static void writeChiValues(TreeMap<Double, HashSet<String>> chiValues) {
        String filepath = "resources/textfiles/chiValues.txt";
        String line = "";

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath,true));

            for (Double chiValue : chiValues.keySet()) {
                for (String word : chiValues.get(chiValue)) {
                    line = String.format("%20s: %.2f\n", word, chiValue);
                    System.out.print(line);
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }

    }


}