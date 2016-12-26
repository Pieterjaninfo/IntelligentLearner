import java.io.File;
import java.util.ArrayList;

/**
 * Created by Pieter Jan on 25-12-2016.
 */
public class DocumentProcessing {

    private File file;
    private ArrayList<String> words;

    public DocumentProcessing() {
        words = new ArrayList<String>();
        words.add("hai");
        words.add("my nam");
        words.add("is");
    }

    public ArrayList<String> getArray() {
        return words;
    }


    public void scanDocument() {

    }


    public void printArray(ArrayList<String> array) {
        for(int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }



}
