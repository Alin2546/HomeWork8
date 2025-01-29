package org.example;

import java.io.File;

/**
 * App class
 */
public class App {

    /**
     * Entry point of the program
     *
     * @param args (not used here)
     */
    public static void main(String[] args) {

        ResultMatcher matcher = new ResultMatcher(new File("ski results.csv"));


    }
}
