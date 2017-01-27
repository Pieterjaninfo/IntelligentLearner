package main;

import java.util.*;

/**
 * The DataClasses to which a document belongs to.
 */
public class DataClass {

    // =====================================================================================
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Static Class Components ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // =====================================================================================

    //MAP CLASSNAME -> CLASS
    private static HashMap<String, DataClass> classes = new HashMap<>();

    /**
     * Returns the all classes contained in a HashMap.
     */
    public static HashMap<String, DataClass> getClasses() { return classes; }

    /**
     * Functionality to retrain the classes entirely using the current documents each DataClass holds
     */
    public static void setupClasses(boolean useChiValue) {
        for (DataClass dataClass : getClasses().values()) {
            dataClass.clearVocabulary();
            dataClass.extractVocabulary();
            dataClass.filterWords(useChiValue);
        }
    }

    /**
     * Returns the amount of documents of al the classes combined.
     */
    public static int getTotalDocs() {
        int result = 0;
        for (DataClass dataClass : classes.values()) {
            result += dataClass.getAmountOfDocs();
        }
        return result;
    }

    /**
     * Returns all the words combined from all the DataClasses.
     */
    public static HashSet<String> getTotalVocabulary() {
        HashSet<String> uniqueWords = new HashSet<>();
        for (DataClass dataClass : classes.values()) {
            uniqueWords.addAll(dataClass.getVocabulary().keySet());
        }
        return uniqueWords;
    }

    /**
     * Returns the total amount of unique words in all class vocabularies combined.
     */
    public static int getTotalVocabularySize() {
        return getTotalVocabulary().size();
    }

    /**
     * Retrieves the class given the unique classname, if not exits returns null.
     * @param className The classname of the wanted Class
     * @return The Class or else null
     */
    public static DataClass getClass(String className) {
        if (classes.containsKey(className)) {
            return classes.get(className);
        } else {
            //System.err.println("[DataClass.java] There exists no class with classname: " + className + "!");
            return null;
        }
    }

    /**
     * Prints the general info of all the classes.
     */
    public static void printClassesInfo() {
        System.out.println("[DataClass.java] Info of all classes: ");
        for (String classname : classes.keySet()) {
            classes.get(classname).printInfo();
        }
    }

    // =====================================================================================
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DataClass Components ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // =====================================================================================

    private String className;                                       // Name of the class
    private HashMap<String, Integer> vocabulary;                    // MAPS WORD -> COUNT
    private HashMap<String, HashMap<String, Integer>> documents;    // MAPS DOCUMENT NAME -> (WORD -> AMOUNT)
    private int amountOfWords = 0;                                  // Amount of words in the vocabulary

    public DataClass(String className) {
        vocabulary = new HashMap<>();
        documents = new HashMap<>();
        this.className = className;
        classes.put(className, this);
    }

    public String getClassName() {
        return className;
    }

    public HashMap<String, Integer> getVocabulary() {
        return vocabulary;
    }

    public HashMap<String, HashMap<String, Integer>> getDocuments() { return documents; }

    public int getAmountOfDocs() { return documents.size(); }

    public int getAmountOfWords() { return amountOfWords; }

    /**
     * Returns how often the given word occurs in the class.
     * @param word The word
     * @return amount of occurrences - and 0 if it does not exist in the class vocabulary
     */
    public int getWordOccurrences(String word) {
        int amount = 0;
        if (vocabulary.containsKey(word)) {
            amount = vocabulary.get(word);
        }
        return amount;
    }

    public void clearVocabulary() { vocabulary.clear(); amountOfWords = 0; }

    public void extractVocabulary() {
        for (HashMap<String, Integer> words : documents.values()) {
            //for each fileMap
            for(Map.Entry<String, Integer> entry : words.entrySet()) {
                String key = entry.getKey();
                if (vocabulary.containsKey(key)) {
                    vocabulary.put(key, vocabulary.get(key) + entry.getValue());
                } else {
                    vocabulary.put(key, entry.getValue());
                }
                amountOfWords += entry.getValue();
            }
        }
    }

    /**
     * Adds the given document to the DataClass
     * @param documentName Name of the document (filename)
     * @param words Map containing the words linked to the amount of the document
     */
    public void addDocument(String documentName, HashMap<String, Integer> words) {
        String newDocumentName = documentName;
        while (documents.containsKey(newDocumentName)) {
//            System.out.println("[DataClass.java] Tried to add already existing document" + newDocumentName + " to the DataClass!");
            newDocumentName += " ";
        }
        documents.put(newDocumentName, words);
    }

    /**
     * Removes unreliable vocabulary
     */
    public void filterWords(boolean useChiValue) {
        Tokenizer.removeStopwords(vocabulary);
        Tokenizer.removeThresholdViolatingWords(vocabulary);

        if(useChiValue) {
            HashSet<String> uselessWords = Tokenizer.getViableChiSquareWords(DataClass.getTotalVocabulary());
            vocabulary.keySet().removeAll(uselessWords);
        } else {
            Tokenizer.removeLowestChiSquareWords(this);
        }
        printInfo();
    }

    /**
     * Print the general information of this Class
     */
    public void printInfo() {
        System.out.println("Class " + className + " vocabulary size: " + vocabulary.size() + " document size: " + documents.size() + ".");

//        for (String documentName : documents.keySet()) {
//            System.out.println("Filename: " + documentName + ".");
//        }
    }


}
