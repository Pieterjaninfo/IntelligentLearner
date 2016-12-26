import java.io.IOException;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello PJSalt!");
        System.out.println("It's wednesday my dudes!");


        DocumentProcessing dc = new DocumentProcessing();
        //dc.testRegex();
        //dc.printArray(dc.getArray());
        try {
            dc.scanDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
