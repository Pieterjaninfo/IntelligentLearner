package main;

import java.util.*;

/**
 * Utils class is used for creating and printing statistic tables of the classification outcome. (confusion matrices)
 */
public class Utils {

    private static String log = "";

    public static String getLog() { return log; }
    public static void resetLog() { log = ""; }

    /**
     * Creates the confusion matrix.
     */
    public static int[][] createTable(HashMap<String, HashMap<String, Integer>> stats) {
        int[][] table = new int[stats.size()][stats.size()];

        TreeMap<String, HashMap<String, Integer>> data = new TreeMap<>(stats);

        int i = 0;
        for (String realClass : data.keySet()) {
            TreeMap<String, Integer> predClasses = new TreeMap<>(data.get(realClass));

            int j = 0;
            for (String predClass : predClasses.keySet()) {
                table[i][j] = predClasses.get(predClass);
                j++;
            }
            i++;
            j = 0;
        }
        return table;
    }

    /**
     * Prints the confusion matrix.
     */
    public static void printTable(int[][] table, List<String> classnames) {
        int fontSize = getMaxLength(classnames);
        log += String.format("%" + (fontSize + 2) + "s", " ");
        for (int i = 0; i< classnames.size(); i++) {
            log += String.format("%" + fontSize + "s", classnames.get(i));
        }
        log += "   <--- Predicted Classes";
        log += "\n";
        for (int x = 0; x < table.length; x++) {

            log += String.format("%" + fontSize + "s [", classnames.get(x));
            for (int y = 0; y < table[0].length; y++) {
                log += String.format("%" + fontSize + "d", table[x][y]);
            }
            log += " ]\n";
        }
    }

    private static int getMaxLength(List<String> classNames) {
        int max = 4;
        for (String className : classNames) { max = Math.max(max, className.length()); }
        return max + 1;
    }

    /**
     * Prints statistics derived from the confusion matrix.
     */
    public static void getStatistics(int[][] table, List<String> classnames) {
        double accuracy = 0;
        HashMap<String, Double> recall = new HashMap<>();           //rows
        HashMap<String, Double> precision = new HashMap<>();        //cols
        for (int i = 0; i < classnames.size(); i++) {               //Prepare recall/precision map
            recall.put(classnames.get(i), 0.0);
            precision.put(classnames.get(i), 0.0);
        }

        double diagonal = 0;
        double total = 0;

        //Calculate average and calculate row and column sums
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                total += table[i][j];
                if (i == j) { diagonal += table[i][j]; }
                recall.put(classnames.get(i), recall.get(classnames.get(i)) + table[i][j]);
                precision.put(classnames.get(j), precision.get(classnames.get(j)) + table[i][j]);
            }
        }

        double testBaseline = Collections.max(recall.values()) / total;
        double trainBaseline = getTrainBaseline(classnames);
        //Calculate recall and precision
        for (int i = 0; i < classnames.size(); i++) {
            recall.put(classnames.get(i), table[i][i] / recall.get(classnames.get(i)));
            precision.put(classnames.get(i), table[i][i] / precision.get(classnames.get(i)));
        }
        accuracy = diagonal / total;

        log += "\n";
        printStatistics(recall, precision, classnames);
        log += String.format("Accuracy: %.1f%% with (Train) Baseline: %.1f%% and (Test) Baseline: %.1f%%.\n", (accuracy * 100), (trainBaseline*100), (testBaseline*100));
        log += "\n\n";

    }

    public static void printStatistics(HashMap<String, Double> recall, HashMap<String, Double> precision, List<String> classNames) {

        int formatSize = getMaxLength(classNames);
        log += "   Recall   Precision\n";
        for (String className : classNames) {
            log += String.format("    %.3f       %.3f   %s\n", recall.get(className), precision.get(className), className);
        }
    }

    private static double getTrainBaseline(List<String> classnames) {
        int maxDocs = 0;
        int totalDocs = 0;
        for (String className : classnames) {
            DataClass dataClass = DataClass.getClass(className);
            maxDocs = Math.max(maxDocs, dataClass.getAmountOfDocs());
            totalDocs += dataClass.getAmountOfDocs();
        }
        return (double) maxDocs / (double) totalDocs;
    }


}
