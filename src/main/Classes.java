package main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pieter Jan on 28-12-2016.
 */

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
     * Create the words container belonging to a specific class if the unique classname did not exist
     * @param classname
     */
    public void createClass(String classname) {
        if (!classes.containsKey(classname)) {
            classes.put(classname, new Class(classname));
        } else {
            //THROW EXCEPTION CLASSNAME_ALREADY_EXISTS
            System.out.println("There already exists a Class with the given classname: " + classname + "!");
        }
    }

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
    public void printClasseInfo() {
        for (String classname : classes.keySet()) {
            System.out.println("Class " + classname + ":");
            //classes.get(classname).displayWords();
            System.out.println("----------------------------------------");
        }
    }

    /**
     *  Represents a single class (Male, Female, Ham, Spam, etc).
     *  Each class must have an unique classname!
     */
    public class Class {

        private String className;
        private HashMap<String, Integer> words;     // MAPS WORD -> COUNT

        public Class(String classname) {
            words = new HashMap<String, Integer>();
            className = classname;
        }

        public String getClassName() {
            return className;
        }

        public HashMap<String, Integer> getWords() {
            return words;
        }

        public void displayWords() {
            if (words.isEmpty()) {
                System.out.println("Class " + className + " is empty.");
                return;
            }
            for (String word: words.keySet()) {
                System.out.println(word + " " + words.get(word));
            }
        }

        /**
         * Adds the given word to the hashmap or if already existed, increases the amount count.
         * @param word Word to be added to the words map
         */
        public void addWord(String word) {
            if (words.containsKey(word)){
                words.put(word, words.get(word) + 1);
            } else {
                words.put(word, 1);
            }
        }

        public void addWords(String[] words) {
            for (String word : words) {
                addWord(word);
            }
        }

        public void filterWords() {
            Utils.removeStopwords(words);
            Utils.removeThresholdViolatingWords(words);
        }
    }

}
