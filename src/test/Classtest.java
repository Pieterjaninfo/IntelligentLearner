package test;

import main.*;

/**
 * Created by Pieter Jan on 29-12-2016.
 */
public class Classtest {

    public static void main(String[] args) {
        // Test if the classes work as intended.

        new DataClass("M");
        new DataClass("F");

        /*for(DataClass.Class singleClass : classes.getClasses()) {
            if(singleClass.getClassName().equals("M")) {
                singleClass.getVocabulary().put("sup", 3);
                singleClass.getVocabulary().put("bruh", 69);
            }
        }
        classes.printClassesInfo();*/
    }
}
