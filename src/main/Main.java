package main;

import java.util.HashMap;

/**
 * Created by Pieter Jan on 21-12-2016.
 */
public class Main {
    public static void main(String[] args) {
        DocumentProcessing dc = new DocumentProcessing();
        dc.scanTrainDocuments();

        Classes.getClass("F").filterWords();
        Classes.getClass("M").filterWords();

        Classes.printClassesInfo(false);
    }
}
