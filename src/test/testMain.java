package test;

import main.*;

import java.util.*;

/**
 * Created by Pieter Jan on 20-1-2017.
 */
public class testMain {

    private static String trainpath = "resources/corpus2/train/";
    private static String testpath = "resources/corpus2/test/";

    public static void main(String[] args) {


        DocumentProcessing dc = new DocumentProcessing();
        Classifier cf = new Classifier();

        //-------AUTOMATIC TRAINING AND TESTING---------------------
        try {
            dc.scanTrainDocuments(trainpath);
        } catch (EmptyFolderException e) {
            e.printStackTrace();
        }
        DataClass.setupClasses(false);
        System.out.println("Finished setting up all the classes.");


        HashMap<String, HashMap<String, Integer>> stats = dc.scanTestDocuments(testpath);
        System.out.println("Finished reading test documents");

        //---------------------Create test results------------------------

        int[][] tab = new int[][] {
                {100,3,1},
                {10,30,1},
                {20,10,300}
        };
        List<String> list = Arrays.asList(new String[] {"F", "M", "O" });
        System.out.println("============================================================================");
        Utils.printTable(tab, list);
        Utils.getStatistics(tab, list);
        System.out.println(Utils.getLog());

        //---------------------------------------------
    }
}
