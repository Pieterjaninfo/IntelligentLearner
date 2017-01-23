package main;

import java.io.*;
import java.util.HashMap;

/**
 * The DocumentProcessing class where documents are being read and processed.
 */
public class DocumentProcessing {

    private static String TRAINPATH = "resources/corpus/train/";
    private static String TESTPATH = "resources/corpus/test/";


    public DocumentProcessing() {   }

    /**
     * Reads the test document and returns the words contained in a HashMap.
     * @param filepath - filepath location of the test document
     * @return HashMap filled with words mapping to the amount of occurrences
     */
    public HashMap<String, Integer> scanDocument(String filepath) {
        HashMap<String, Integer> words = new HashMap<>();               //WORDS -> AMOUNT
        String line;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filepath));
            while((line = in.readLine()) != null) {
                Tokenizer.normalizeTextAddToHashMap(line, words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Reads the document using the given filepath and adds the words to the word map of the given Class
     * @param filepath The name of the file you want to read
     * @param classOfWords The class belonging to the scanned words
     */
//    private void scanTrainDocument(String filepath, DataClass classOfWords) {
//        HashMap<String, Integer> words = new HashMap<>();
//        String line;
//        BufferedReader in;
//        try {
//            in = new BufferedReader(new FileReader(filepath));
//            while((line = in.readLine()) != null) {
//                Tokenizer.normalizeTextAddToHashMap(line, words);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //classOfWords.addDocument(); // add document to the class
//        //classOfWords.setAmountOfDocs(classOfWords.getAmountOfDocs() + 1);
//    }

    /**
     * Iterates through the given map and scans all the files inside.
     * @param path The path of the directory containing all the files
     */
    private void scanTrainDocumentsInDirectory(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        //List through all the documents in the corresponding class directory
        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
//                scanTrainDocument(file.getPath(), DataClass.getClass(file.getParentFile().getName()));
//                System.out.printf("path: %s class: %s filename: %s\n", file.getPath(), file.getParentFile().getName(), file.getName());
                DataClass.getClass(file.getParentFile().getName()).addDocument(file.getName(), scanDocument(file.getPath()));
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
            new DataClass(file.getName());                                      //Create the class
            scanTrainDocumentsInDirectory(TRAINPATH + file.getName());
        }
    }

    public void scanTestDocuments() {
        File folder = new File(TESTPATH);
        File[] filesList = folder.listFiles();

        //ITERATE THROUGH CLASSES
        for (File file : filesList) {
            File folder2 = new File(TESTPATH + file.getName());
            File[] filesList2 = folder2.listFiles();
            //ITERATE THROUGH CLASS FILES
            for (File file2 : filesList2) {
                if (file2.isFile() && file2.getName().endsWith(".txt")) {
                    HashMap<String, Integer> words = scanDocument(file2.getPath());

                    //TODO KEEP TRACK OF ACCURACY/RECALL/PRECISION
                }
            }
        }
    }


}
