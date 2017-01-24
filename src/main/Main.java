package main;

import java.util.*;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {


    private static String trainpath = "resources/corpus/train/";
    private static String testpath = "resources/corpus/test/";


    public static void main(String[] args) {
        DocumentProcessing dc = new DocumentProcessing();
        Classifier cf = new Classifier();

        //-------AUTOMATIC TESTING---------------------
        dc.scanTrainDocuments(trainpath);
        DataClass.setupClasses();
        System.out.println("Finished setting up all the classes.");


        HashMap<String, HashMap<String, Integer>> stats = dc.scanTestDocuments(testpath);
        System.out.println("Finished reading test documents");


        List<String> sortedClasses = new ArrayList(stats.keySet());
        Collections.sort(sortedClasses);

        int[][] table = Utils.createTable(stats);
        Utils.printTable(table, sortedClasses);
        Utils.getStatistics(table, sortedClasses);
        //---------------------------------------------
    }
}
