package main;

import java.util.*;

/**
 * Utils class is used for creating and printing statistic tables of the classification outcome. (confusion matrices)
 */
public class Utils {

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
        for (int i = 0; i< classnames.size(); i++) {
//            System.out.print("\t " + classnames.get(i));
            System.out.printf("\t  %2s", classnames.get(i));
        }
        System.out.print("\n");
        for (int x = 0; x < table.length; x++) {
            System.out.printf("%5s [", classnames.get(x));
            for (int y = 0; y < table[0].length; y++) {
                System.out.printf("%4d", table[x][y]);
            }
            System.out.println(" ]");
        }
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

        double baseline = Collections.max(recall.values()) / total;

        //Calculate recall and precision
        for (int i = 0; i < classnames.size(); i++) {
            recall.put(classnames.get(i), table[i][i] / recall.get(classnames.get(i)));
            precision.put(classnames.get(i), table[i][i] / precision.get(classnames.get(i)));
        }
        accuracy = diagonal / total;

        for (String name : classnames) {
            System.out.printf("Class: %s, recall: %.1f%%, precision %.1f%%.\n", name, recall.get(name)*100, precision.get(name)*100);
        }
        System.out.printf("Accuracy: %.1f%% with Baseline: %.1f%%.\n", (accuracy * 100), (baseline*100));


    }




}
