import java.io.IOException;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {

        String path = "resources/corpus/train/M/";

        DocumentProcessing dc = new DocumentProcessing();
        //dc.testRegex();
        //dc.printArray(dc.getArray());
        long begin = System.currentTimeMillis();
        //dc.scanDocuments(path);
        dc.scanDocument("resources/corpus/train/M/M-test3.txt");
        System.out.println("Scanning the file and updating wordcount took: " + (System.currentTimeMillis() - begin) + " milliseconds.");
        dc.displayWords();


    }
}
