package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pieter Jan on 25-12-2016.
 */
public class DocumentProcessing {

    //private String filename = "resources/corpus/train/M/M-test3.txt";
    private HashMap<String, Integer> wordcount;

    public DocumentProcessing() {
        wordcount = new HashMap<String, Integer>();
    }


    /**
     * Reads the given document.
     * @param filepath The name of the file you want to read
     */
    public void scanDocument(String filepath) {
        String line;
        BufferedReader in = null;
        String[] tokenizedWords;
        try {
            in = new BufferedReader(new FileReader(filepath));
            while((line = in.readLine()) != null) {
                tokenizedWords = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                addWords(tokenizedWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterates through the given map and scans all the files inside.
     * @param path The path of the directory containing all the files
     */
    public void scanDocuments(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
                System.out.println(file.getName());
                scanDocument(file.getPath());
            }
        }
    }

    /**
     * Increments the amount of times the word has been added.
     * @param tokenizedWords
     */
    private void addWords(String[] tokenizedWords) {
        for(String word : tokenizedWords) {
            if(wordcount.containsKey(word)){
                //word existed
                wordcount.put(word, wordcount.get(word) + 1);
            } else {
                //word is new
                wordcount.put(word, 1);
            }
        }
    }

    /**
     * Iterates through the wordcount hashmap and displays each word, with how much they occured.
     */
    public void displayWords() {
        for(String word: wordcount.keySet()) {
            System.out.println(word + " " + wordcount.get(word));
        }
    }

}
