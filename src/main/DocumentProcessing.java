package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pieter Jan on 25-12-2016.
 */
public class DocumentProcessing {

    //private String filename = "resources/corpus/train/M/M-test3.txt";
    private static String TRAINPATH = "resources/corpus/train/";
    private static String TESTPATH = "resources/corpus/test/";


    public DocumentProcessing() {
    }

    /**
     * Reads the given document.
     * @param filepath The name of the file you want to read
     */
    public void scanDocument(String filepath, Classes.Class classOfWords) {
        String line;
        BufferedReader in = null;
        String[] tokenizedWords;
        try {
            in = new BufferedReader(new FileReader(filepath));
            while((line = in.readLine()) != null) {
                tokenizedWords = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                classOfWords.addWords(tokenizedWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterates through the given map and scans all the files inside.
     * @param path The path of the directory containing all the files
     */
    public void scanDocumentsInDirectory(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        //List through all the emails in the corresponding class directory
        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
                scanDocument(file.getPath(), Classes.getClass(file.getParentFile().getName()));
            } else {
                System.out.println("[DocumentProcessing.java] File found in class directory which is not of type .txt");
            }
        }
    }

    /**
     * Scan all the documents in the Train folder.
     */
    public void scanTrainDocuments() {
        File folder = new File(TRAINPATH);
        File[] filesList = folder.listFiles();

        //List through all the class directories
        for (File file : filesList) {
            //System.out.println("Filename: " + file.getName());
            (new Classes()).createClass(file.getName());        //Create the class
            scanDocumentsInDirectory(TRAINPATH + file.getName());
        }
    }



}
