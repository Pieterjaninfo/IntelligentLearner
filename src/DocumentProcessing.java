import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Pieter Jan on 25-12-2016.
 */
public class DocumentProcessing {

    private String filename = "resources/corpus/train/M/M-test3.txt";
    private ArrayList<String> words;

    public DocumentProcessing() {
        words = new ArrayList<String>();
    }

    public ArrayList<String> getArray() {
        return words;
    }

    public void testRegex() {
        String teststring = "Hello, my name is PewDiePie.    That's pretty good!";
        String[] tokenizedWords = teststring.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        for(String word : tokenizedWords) {
            words.add(word);
        }

    }


    public void scanDocument() throws IOException {
        String line;
        String[] tokenizedWords;
        BufferedReader in = new BufferedReader(new FileReader(filename));
        while((line = in.readLine()) != null) {
            tokenizedWords = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            printArray2(tokenizedWords);
        }

    }


    public void printArray(ArrayList<String> array) {
        for(int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }

    public void printArray2(String[] words) {
        for(String word : words) {
            System.out.print(word + " ");
        }
        System.out.print("\n");
    }



}
