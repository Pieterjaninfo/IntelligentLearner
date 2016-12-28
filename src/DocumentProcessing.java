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

    public void scanDocument(String filename) {
        String line;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filename));
            while((line = in.readLine()) != null) {
                String[] tokenizedWords = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                addWords(tokenizedWords);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void scanDocuments(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
                scanDocument(file.getName());
            }
        }
    }

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

    public void displayWords() {
        for(String word: wordcount.keySet()) {
            System.out.println(word + " " + wordcount.get(word));
        }
    }

}
