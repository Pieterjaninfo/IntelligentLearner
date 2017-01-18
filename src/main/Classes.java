package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Encapsulates all the classes containing them in an ArrayList.
 */
public class Classes {

    //MAP CLASSNAME -> CLASS
    private static HashMap<String, Class> classes = new HashMap<String, Class>();


    public Classes() {
        //Could not get method createClass static, else this whole Classes class would be made static~!
    }

    /**
     * Create the vocabulary container belonging to a specific class if the unique classname did not exist
     * @param classname The classname belonging to the wanted Class
     */
    public void createClass(String classname) {
        if (!classes.containsKey(classname)) {
            classes.put(classname, new Class(classname));
        } else {
            //THROW EXCEPTION CLASSNAME_ALREADY_EXISTS
            System.out.println("There already exists a Class with the given classname: " + classname + "!");
        }
    }

    /**
     * Returns the all classes contained in a HashMap.
     */
    public static Collection<Class> getClasses() {
        return classes.values();
    }

    /**
     * Returns the amount of documents of al the classes combined.
     * @return
     */
    public static int getTotalDocs() {
        int result = 0;
        for (Classes.Class dataClass : getClasses()) {
            result += dataClass.amountOfDocs;
        }
        return result;
    }

    /**
     * Returns the total amount of unique words in all class vocabularies combined.
     */
    public static int getTotalVocabularySize() {
        int amount = 0;
        ArrayList<String> uniqueWords = new ArrayList<String>();
        for (Classes.Class dataClass : getClasses()) {
            uniqueWords.addAll(dataClass.getVocabulary().keySet());
        }
        return uniqueWords.size();
    }

    /**
     * Retrieves the class given the unique classname, if not exits returns null.
     * @param classname The classname of the wanted Class
     * @return The Class or else null
     */
    public static Class getClass(String classname) {
        if (classes.containsKey(classname)) {
            return classes.get(classname);
        } else {
            System.out.println("[Classes.java] There exists no class with classname: " + classname + "!");
            return null;
        }
    }

    /**
     * Prints the general info of all the classes.
     */
    public static void printClassesInfo(boolean printwords) {
        System.out.println("[Classes.java] Info of all classes: ");
        for (String classname : classes.keySet()) {
            classes.get(classname).printInfo(printwords);
        }
    }

    /**
     *  Represents a single class (Male, Female, Ham, Spam, etc).
     *  Each class must have an unique classname!
     */
    public class Class {

        private String className;
        private HashMap<String, Integer> vocabulary;    // MAPS WORD -> COUNT
        private int amountOfWords = 0;                  // Amount of vocabulary in the vocabulary
        private int amountOfDocs = 0;

        public Class(String classname) {
            vocabulary = new HashMap<String, Integer>();
            className = classname;
        }

        public String getClassName() {
            return className;
        }

        public HashMap<String, Integer> getVocabulary() {
            return vocabulary;
        }

        public int getAmountOfDocs() { return amountOfDocs; }

        public int getAmountOfWords() { return amountOfWords; }

        public void setAmountOfDocs(int amount) { this.amountOfDocs = amount; }

        /**
         * Returns how often the given word occurs in the class.
         * @param word The word
         * @return amount of occurences - and 0 if it does not exist in the class vocabulary
         */
        public int getWordOccurrences(String word) {
            int amount = 0;
            if (vocabulary.containsKey(word)) {
                amount = vocabulary.get(word);
            }
            return amount;
        }



        /**
         * Print the general information of this Class
         */
        public void printInfo(boolean printwords) {
            System.out.println("Class " + className + " contains " + vocabulary.size() + " vocabulary:");

            if (vocabulary.isEmpty()) {
                System.out.println("Class " + className + " is empty.");
                return;
            }
            if (printwords) {
                for (String word : vocabulary.keySet()) {
                    System.out.printf("%15s %d\n", word, vocabulary.get(word));
                }
            }
            System.out.println("-----------------------------------------------------------------");
        }

        /**
         * Adds the given word to the hashmap or if already existed, increases the amount count.
         * @param word Word to be added to the vocabulary map
         */
        public void addWord(String word) {
            if (vocabulary.containsKey(word)){
                vocabulary.put(word, vocabulary.get(word) + 1);
            } else {
                vocabulary.put(word, 1);
            }
            amountOfWords++;
        }

        /**
         * Adds vocabulary from the given String array to the HashMap
         * @param words Words in a string array to be added to the vocabulary map
         */
        public void addWords(String[] words) {
            for (String word : words) {
                addWord(word);
            }
        }

        /**
         * Remove unreliable vocabulary
         */
        public void filterWords() {
            Tokenizer.removeStopwords(vocabulary);
            Tokenizer.removeThresholdViolatingWords(vocabulary);
        }
    }

}
