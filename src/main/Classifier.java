package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Pieter Jan on 17-1-2017.
 */
public class Classifier {

    private static final double SMOOTHING_FACTOR = 1.0;

    public Classes.Class binomialClassifier(HashMap<String, Integer> documentWords) {
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

    public Classes.Class multinomialClassifier(HashMap<String, Integer> documentWords) {
        HashMap<Classes.Class, Double> classesProbabilities = new HashMap<>(); // MAP CLASSNAME -> PROBABILITY

        Collection<Classes.Class> classes = Classes.getClasses();
        int wordTypes = Classes.getTotalVocabularySize();

        //iterate though the classes
        for (Classes.Class dataClass : classes) {
            HashMap<String, Integer> classVocabulary = dataClass.getVocabulary();
            double probClass = (double) dataClass.getAmountOfDocs() / (double) Classes.getTotalDocs();
            double probWordsGivenClass = 1;
            double numerator;
            double denominator;
            double power;
            for (String word : documentWords.keySet()) {
                numerator =  dataClass.getWordOccurrences(word)+ SMOOTHING_FACTOR;
                denominator = dataClass.getAmountOfWords() * SMOOTHING_FACTOR * Classes.getTotalVocabularySize();
                power = documentWords.get(word);
                probWordsGivenClass *= Math.pow(numerator/denominator, power);
            }
            classesProbabilities.put(dataClass, probClass*probWordsGivenClass);
        }
        return getProbableClass(classesProbabilities);
    }

    /**
     * Returns the most probable Class given in the Map which links a Class to its probability.
     * @param probabilities HashMap linking Class class to Double probability
     * @return Class with the highest probability
     */
    public Classes.Class getProbableClass(HashMap<Classes.Class, Double> probabilities) {
        Map.Entry<Classes.Class, Double> maxEntry = null;
        for (Map.Entry<Classes.Class, Double> entry : probabilities.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
}
