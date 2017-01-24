package main;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Pieter Jan on 23-1-2017.
 */
public class Utils {

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

    public static void printTable(int[][] table, List<String> classnames) {
        for (int i = 0; i< classnames.size(); i++) {
            System.out.print("\t" + classnames.get(i));
        }
        System.out.print("\n");
        for (int x = 0; x < table.length; x++) {
            System.out.print(classnames.get(x) + " [");
            for (int y = 0; y < table[0].length; y++) {
                System.out.printf("%4d", table[x][y]);
            }
            System.out.println(" ]");
        }
    }

    /**
     * Prints the information of the table
     */
    public static void getStatistics(int[][] table, List<String> classnames) {
        double accuracy = 0;
        HashMap<String, Double> recall = new HashMap<>();
        HashMap<String, Double> precision = new HashMap<>();

        double diagonal = 0;
        double total = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                total += table[i][j];
                if (i == j) {
                    diagonal += table[i][j];
                }

                if (i == 0) {

                }

                if (j == 0) {

                }
            }
        }





        accuracy = diagonal / total;
//        recall = diagonal / total;
//        precision = diagonal / total;

        System.out.printf("Accuracy: %.1f%%.\n", (accuracy * 100));
//        System.out.printf("Accuracy: %.1f%% Average precision: %.1f%% Average recall: %.1f%%.\n", (accuracy * 100), (precision*100), (recall*100));
    }


}
