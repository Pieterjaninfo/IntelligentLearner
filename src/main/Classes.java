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

    ArrayList<Class> classes;

    public Classes() {
        classes = new ArrayList<Class>();
    }

    /**
     * Create the words container belonging to a specific class
     * @param classname
     */
    public void createClass(String classname) {
        classes.add(new Class(classname));
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public Class getClass(String classname) {
        for (Class singleClass : getClasses()) {
            if(singleClass.getClassName().equals(classname)) {
                return singleClass;
            }
        }
        return null;
    }

    public void printClassesInfo() {
        for (Class singleClass : getClasses()) {
            System.out.println("Class " + singleClass.getClassName() + ":");
            singleClass.displayWords();
            System.out.println("----------------------------------------");
        }
    }

    /**
     *  Represents a single class (Male, Female, Ham, Spam, etc).
     */
    public class Class {

        private String className;
        private HashMap<String, Integer> words;

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
            for(String word: words.keySet()) {
                System.out.println(word + " " + words.get(word));
            }
        }
    }

}
