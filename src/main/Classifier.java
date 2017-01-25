package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Classifier class with tools to classify a given HashMap of words to an existing DataClass.
 */
public class Classifier {

    private static boolean debug = false;
    private static double smoothingFactor = 1.0;

    public void setSmoothingFactor(double smoothingFactor) {
        this.smoothingFactor = smoothingFactor;
    }

    //TODO IMPLEMENT
    @Deprecated
    public DataClass binomialClassifier(HashMap<String, Integer> documentWords) {
        return null;
    }

    /**
     * Classifies the given document using multinomial classification and logspace(base e).
     * @param documentWords Words of the document
     * @return The Class which is the most probable
     */
    public DataClass multinomialClassifier(HashMap<String, Integer> documentWords) {
        HashMap<DataClass, Double> classesProbabilities = new HashMap<>(); // MAP CLASSNAME -> PROBABILITY

        Collection<DataClass> classes = DataClass.getClasses().values();
        int wordTypes = DataClass.getTotalVocabularySize();

        //iterate though the classes
        for (DataClass dataClass : classes) {
            HashMap<String, Integer> classVocabulary = dataClass.getVocabulary();
            double probClass = Math.log( dataClass.getAmountOfDocs() / (double) DataClass.getTotalDocs());


            double probWordsGivenClass = 0;
            double numerator = 0;
            double denominator = dataClass.getAmountOfWords() * smoothingFactor * DataClass.getTotalVocabularySize();
            double power;

            for (String word : documentWords.keySet()) {
                numerator =  dataClass.getWordOccurrences(word) + smoothingFactor;
                double wordChance = numerator / denominator;
                power = documentWords.get(word);
                probWordsGivenClass += Math.log(wordChance) * power;
            }

            classesProbabilities.put(dataClass, probClass+probWordsGivenClass);
            if (debug) System.out.printf("ClassProb: %.3f probWordsGivenClass: %.15f numerator: %.1f denominator: %.1f\n", probClass, probWordsGivenClass, numerator, denominator);
        }

        if (debug) {
            System.out.println("=======================================================================");
            for (Map.Entry<DataClass, Double> entry : classesProbabilities.entrySet()) {
                System.out.printf("Class: %s with probability: %.5f\n", entry.getKey().getClassName(), entry.getValue());
            }
        }
        return getProbableClass(classesProbabilities);
    }

    /**
     * Returns the most probable Class given in the Map which links a Class to its probability.
     * @param probabilities HashMap linking Class class to Double probability
     * @return Class with the highest probability
     */
    public DataClass getProbableClass(HashMap<DataClass, Double> probabilities) {
        Map.Entry<DataClass, Double> maxEntry = null;
        for (Map.Entry<DataClass, Double> entry : probabilities.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }


}
