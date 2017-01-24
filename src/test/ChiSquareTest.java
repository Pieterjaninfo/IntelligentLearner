package test;

import main.DataClass;
import main.Tokenizer;

/**
 * Created by Pieter Jan on 22-1-2017.
 */
public class ChiSquareTest {
    public static void main(String[] args) {
        Tokenizer.getViableChiSquareWords(DataClass.getTotalVocabulary());
        int[][] table = {
                {1,2,3,6},
                {4,5,6,15},
                {5,7,9,21}

        };
        Tokenizer.printTable(table);
        System.out.println(Tokenizer.calculateChiSquareValue(table));
    }

//    for (String word : Tokenizer.getViableChiSquareWords(DataClass.getTotalVocabulary()) ) {
//        System.out.println(word);
//    }
}
