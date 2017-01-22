package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {
        DocumentProcessing dc = new DocumentProcessing();
        Classifier cf = new Classifier();

        dc.scanTrainDocuments();
        for (Classes.Class dataClass : Classes.getClasses()) {
            dataClass.filterWords();
        }

        String filepath = "resources/corpus/test/F/";
        String filename = "F-train547.txt";

        HashMap<String, Integer> map = dc.scanTestDocument(filepath + filename);

        Classes.Class probableClass = cf.multinomialClassifier(map);

        System.out.printf("Most probable class for file %s is: %s\n", filename, probableClass.getClassName());
    }


}
