package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Pieter Jan on 17-1-2017.
 */
public class Classifier {

    private static final double SMOOTHING_FACTOR = 1.0;

    public Class binomialClassifier(HashMap<String, Integer> documentWords) {
        String classname;
        double chance;

        Collection<Classes.Class> classes = Classes.getClasses();

        //iterate though the classes
        for (Classes.Class dataClass : classes) {
            double classChance = 1;
            for (String word : documentWords.keySet()) {
                if (dataClass.getVocabulary().containsKey(word)) {
                    //Do something
                }
            }
        }
        return null;
    }

    public Class multinomialClassifier(HashMap<String, Integer> documentWords) {
        HashMap<String, Double> classesProbabilities = new HashMap<String, Double>();

        Collection<Classes.Class> classes = Classes.getClasses();
        int wordTypes = Classes.getTotalVocabularySize();

        //iterate though the classes
        for (Classes.Class dataClass : classes) {
            HashMap<String, Integer> classVocabulary = dataClass.getVocabulary();
            double classProb = dataClass.getAmountOfDocs() / Classes.getTotalDocs();
            double probWordsGivenClass = 1;
            for (String word : documentWords.keySet()) {
                if (dataClass.getVocabulary().containsKey(word)) {
                    probWordsGivenClass *= (((double) classVocabulary.get(word) + SMOOTHING_FACTOR) / (dataClass.getAmountOfWords() * SMOOTHING_FACTOR * Classes.getTotalVocabularySize()));
                } else {

                }
            }

            classesProbabilities.put(dataClass.getClassName(), classProb);
        }


      return null;
    }
}
