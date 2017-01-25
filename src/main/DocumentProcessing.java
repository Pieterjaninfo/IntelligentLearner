package main;

import java.io.*;
import java.util.HashMap;

/**
 * The DocumentProcessing class where documents are being read and processed.
 */
public class DocumentProcessing {

//    private static String trainpath = "resources/corpus/train/";
//    private static String testpath = "resources/corpus/test/";


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
     * Iterates through the given map and scans all the files inside.
     * @param path The path of the directory containing all the files
     */
    private void scanTrainDocumentsInDirectory(String path) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();

        //List through all the documents in the corresponding class directory
        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".txt")) {
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
    public void scanTrainDocuments(String trainpath) {
        File folder = new File(trainpath);
        File[] filesList = folder.listFiles();

        //List through all the class directories
        for (File file : filesList) {
            new DataClass(file.getName());                                      //Create the class
            scanTrainDocumentsInDirectory(trainpath + file.getName());
        }
    }

    public HashMap<String, HashMap<String, Integer>> scanTestDocuments(String testpath) {
        File folder = new File(testpath);
        File[] filesList = folder.listFiles();

        HashMap<String, HashMap<String, Integer>> stats = new HashMap<>(); // MAPS classname -> (class -> good classified)

        //ITERATE THROUGH CLASSES
        for (File file : filesList) {
            File folder2 = new File(testpath + file.getName());
            File[] filesList2 = folder2.listFiles();

            String className = file.getName();
            if (!stats.containsKey(file.getName())) { stats.put(className, new HashMap<>()); }  //Add realClass
            for (File classnames : filesList) {stats.get(className).put(classnames.getName(), 0); } //Fill with all the predClasses

            HashMap<String, Integer> innerStats = stats.get(className);

            //ITERATE THROUGH CLASS FILES
            for (File file2 : filesList2) {
                if (file2.isFile() && file2.getName().endsWith(".txt")) {
                    HashMap<String, Integer> words = scanDocument(file2.getPath());
                    DataClass predictedClass = (new Classifier()).multinomialClassifier(words);
                    innerStats.put(predictedClass.getClassName(), innerStats.get(predictedClass.getClassName()) + 1);
                }
            }
        }
        return stats;
    }


}
