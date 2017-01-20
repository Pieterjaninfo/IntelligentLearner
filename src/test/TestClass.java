package test;

import main.Classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pieter Jan on 20-1-2017.
 */
public class TestClass {

    private static Set<TestClass> classes = new HashSet<>(); // not initialized
    private static HashMap<String, TestClass> classes2 = new HashMap<>();

    public static Collection<TestClass> getClasses() {
        return classes;
    }
    private static HashMap<String, TestClass> getClasses2() { return classes2; }


    public static void printClassesInfo(boolean printwords) {
        System.out.println("[Classes.java] Info of all classes: ");
        for (TestClass dataClass : classes) {
            dataClass.printInfo(printwords);
        }
    }



    public TestClass(String className, HashMap<String, Integer> vocabulary) {
        this.className = className;
        this.vocabulary = vocabulary;
        classes.add(this);
//        classes2.put(className, this);
    }


    private String className;
    private HashMap<String, Integer> vocabulary;    // MAPS WORD -> COUNT

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


    public static void main(String[] args) {

    }
}
