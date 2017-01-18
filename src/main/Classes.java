package main;

import java.util.HashMap;

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
    public HashMap<String, Class> getClasses() {
        return classes;
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
        private HashMap<String, Integer> vocabulary;     // MAPS WORD -> COUNT
        private int amountOfWords = 0;              // Amount of vocabulary in the vocabulary

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
