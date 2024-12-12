package org.example;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ResultMatcher matcher = new ResultMatcher(new File("ski results.csv"));
        matcher.readFilesFromCsv();
    }
}
