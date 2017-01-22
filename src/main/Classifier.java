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

    //CANNOT BE CALCULATED USING CURRENT SETUP
    @Deprecated
    public Classes.Class binomialClassifier(HashMap<String, Integer> documentWords) {
        return null;
    }

    /**
     * Classifies the given document using multinomial classification and logspace(base e).
     * @param documentWords Words of the document
     * @return The Class which is the most probable
     */
    public Classes.Class multinomialClassifier(HashMap<String, Integer> documentWords) {
        HashMap<Classes.Class, Double> classesProbabilities = new HashMap<>(); // MAP CLASSNAME -> PROBABILITY

        Collection<Classes.Class> classes = Classes.getClasses();
        int wordTypes = Classes.getTotalVocabularySize();

        //iterate though the classes
        for (Classes.Class dataClass : classes) {
            HashMap<String, Integer> classVocabulary = dataClass.getVocabulary();
            double probClass = Math.log((double) dataClass.getAmountOfDocs() / (double) Classes.getTotalDocs());
            double probWordsGivenClass = 0;
            double numerator = 0;
            double denominator = 0;
            double power;
            for (String word : documentWords.keySet()) {
                numerator =  dataClass.getWordOccurrences(word)+ SMOOTHING_FACTOR;
                denominator = dataClass.getAmountOfWords() * SMOOTHING_FACTOR * Classes.getTotalVocabularySize();
                double wordChance = numerator / denominator;
                power = documentWords.get(word);
                probWordsGivenClass += Math.log(wordChance) * power;
                System.out.printf("Word prob = %.10f\n", wordChance);
            }
            classesProbabilities.put(dataClass, probClass+probWordsGivenClass);
            System.out.printf("ClassProb: %.2f probWordsGivenClass: %.200f numerator: %f denominator: %f\n", probClass, probWordsGivenClass, numerator, denominator);
        }

        System.out.println("=======================================================================");
        for (Map.Entry<Classes.Class, Double> entry : classesProbabilities.entrySet()) {
            System.out.printf("Class: %s with probability: %.5f\n", entry.getKey().getClassName(), entry.getValue());
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
