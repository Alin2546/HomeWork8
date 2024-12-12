package org.example;

import java.io.*;

public class ResultMatcher {


    BufferedReader reader;
    File file;

    public ResultMatcher(File file) {
        this.file = file;
    }


    void readFilesFromCsv() {
        try {
            reader = new BufferedReader(new FileReader("ski results.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

