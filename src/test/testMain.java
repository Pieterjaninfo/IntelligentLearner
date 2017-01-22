package test;

import java.util.HashMap;

/**
 * Created by Pieter Jan on 20-1-2017.
 */
public class testMain {

    public static void main(String[] args) {
        HashMap<String, Integer> map1 = new HashMap<>();
        map1.put("hello", 2);
        map1.put("sup", 1);
        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("wassup",2);

        new TestClass("M", map1);
        new TestClass("F", map2);


        TestClass.printClassesInfo(true);
    }
}
