package main;

import java.util.*;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {
        DocumentProcessing dc = new DocumentProcessing();
        Classifier cf = new Classifier();


        dc.scanTrainDocuments();

        DataClass.setupClasses();
        System.out.println("Finished setting up all the classes.");


        HashMap<String, HashMap<String, Integer>> stats = dc.scanTestDocuments();

        System.out.println("Finished reading test documents");

        List<String> sortedClasses = new ArrayList(stats.keySet());
        Collections.sort(sortedClasses);

        int[][] table = Utils.createTable(stats);
        Utils.printTable(table, sortedClasses);
        Utils.getStatistics(table, sortedClasses);


//        String filepath = "resources/corpus/test/F/";
//        String filename = "F-train591.txt";
//
//        HashMap<String, Integer> map = dc.scanDocument(filepath + filename);
//        DataClass probableClass = cf.multinomialClassifier(map);
//        System.out.printf("Most probable class for file %s is: %s\n", filename, probableClass.getClassName());

    }
}
