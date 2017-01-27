package test;
import main.*;

/**
 * Created by Pieter Jan on 29-12-2016.
 */
public class DocumentReading {

    public static void main(String[] args) {

        String path = "resources/corpus/train/M/";

        main.DocumentProcessing dc = new main.DocumentProcessing();
        long begin = System.currentTimeMillis();
        //dc.scanDocumentsInDirectory(path);
        //dc.scanDocument("resources/corpus/train/M/M-test3.txt");

        System.out.println("Scanning the file and updating word count took: " + (System.currentTimeMillis() - begin) + " milliseconds.");
       // dc.displayWords();
    }
}
