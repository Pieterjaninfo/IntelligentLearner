import java.io.IOException;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {

        String path = "resources/corpus/train/M/";

//        DocumentProcessing dc = new DocumentProcessing();
//        long begin = System.currentTimeMillis();
//        dc.scanDocuments(path);
//        //dc.scanDocument("resources/corpus/train/M/M-test3.txt");
//
//
//
//
//        System.out.println("Scanning the file and updating wordcount took: " + (System.currentTimeMillis() - begin) + " milliseconds.");
//        dc.displayWords(

        Classes classes = new Classes();
        classes.createClass("M");
        classes.createClass("F");

        for(Classes.Class singleClass : classes.getClasses()) {
            if(singleClass.getClassName().equals("M")) {
                singleClass.getWords().put("sup", 3);
                singleClass.getWords().put("bruh", 69);
            }
        }
        classes.printClassesInfo();
    }
}
