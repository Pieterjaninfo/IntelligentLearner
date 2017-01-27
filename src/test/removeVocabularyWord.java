package test;


import main.DataClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pieter Jan on 27-1-2017.
 */
public class removeVocabularyWord {

    public static void main(String[] args) {
        System.out.println("helloooooo?>");
        new DataClass("M");

        DataClass classM = DataClass.getClass("M");


        HashMap<String, Integer> words = new HashMap<>();

        words.put("hello", 10);
        words.put("sup", 5);
        words.put("bonjour", 3);

        classM.addDocument("randomfile", words);
        classM.clearVocabulary();
        classM.extractVocabulary();
        printVoc(classM);

        classM.clearVocabulary();
        classM.extractVocabulary();
        classM.getVocabulary().remove("sup");
        printVoc(classM);
    }

    public static void printVoc(DataClass dataClass) {
        for (Map.Entry<String, Integer> entry : dataClass.getVocabulary().entrySet()) {
            System.out.println("Word: " + entry.getKey() + " amount: " + entry.getValue() + ".");
        }
        System.out.println("------------------------------------------------------------");
    }
}
