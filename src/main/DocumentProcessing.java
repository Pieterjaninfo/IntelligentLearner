package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pieter Jan on 25-12-2016.
 */
public class DocumentProcessing {

    private static String TRAINPATH = "resources/corpus/train/";
    private static String TESTPATH = "resources/corpus/test/";


    public DocumentProcessing() {   }

    public HashMap<String, Integer> scanTestDocument(String filepath) {
        HashMap<String, Integer> voc = new HashMap<String, Integer>();
        String line;
        BufferedReader in = null;
        String[] tokenizedWords;
        try {
            in = new BufferedReader(new FileReader(filepath));
            while((line = in.readLine()) != null) {
                tokenizedWords = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                Utils.addWords(voc, tokenizedWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voc;
    }



    /**
     * Reads the document using the given filepath and adds the words to the word map of the given Class
     * @param filepath The name of the file you want to read
     * @param classOfWords The class belonging to the scanned words
     */
    private void scanDocument(String filepath, Classes.Class classOfWords) {
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
    private void scanDocumentsInDirectory(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        //List through all the emails in the corresponding class directory
        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
                scanDocument(file.getPath(), Classes.getClass(file.getParentFile().getName()));
            } else {
                //THROW UNKNOWN_FILE_IN_CLASS_DIRECTORY ERROR
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
            (new Classes()).createClass(file.getName());        //Create the class
            scanDocumentsInDirectory(TRAINPATH + file.getName());
        }
    }


}
