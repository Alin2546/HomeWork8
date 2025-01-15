package org.example;

import lombok.Data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Data
public class ResultMatcher {


    List<AthleteStats> athleteResults = new ArrayList<>();
    Set<AthleteStats> athleteResultsSorted = new TreeSet<>(Comparator.comparingInt(AthleteStats::getSkiTimeResults));
    Path path = Paths.get("ski results.csv");


    public ResultMatcher(File file) {
        try {
            Path path = Paths.get("ski results.csv");
            List<String> read = Files.readAllLines(path);
            for (String line : read) {
                String[] compose = line.split(",");
                athleteResults.add(new AthleteStats(Integer.parseInt(compose[0]), compose[1], compose[2], calculateStanding(compose[3]), compose[4], compose[5], compose[6]));
            }
            int index = 0;
            for (AthleteStats athleteStats : athleteResults) {
                athleteResultsSorted.add(new AthleteStats(athleteResults.get(index).getAthleteName(),
                        Integer.valueOf(athleteResults.get(index).getSkiTimeResults()
                                + calculateTotalStandingTime(athleteResults.get(index).getFirstShootingRange(),
                                athleteResults.get(index).getSecondShootingRange(),
                                athleteResults.get(index).getThirdShootingRange()))));
                index++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int calculateStanding(String time) {
        int finalTime = 0;
        int index = 0;
        String result = "";
        while (time.charAt(index) != ':') {
            result = result + time.charAt(index);
            index++;
        }
        finalTime += Integer.valueOf(result) * 60;
        result = "";
        index++;
        while (index != time.length()) {
            result = result + time.charAt(index);
            index++;
        }
        finalTime += Integer.valueOf(result);

        return finalTime;
    }

    public int calculateTotalStandingTime(String first, String second, String third) {
        int count = 0;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) == 'o') {
                count += 10;
            }
            if (second.charAt(i) == 'o') {
                count += 10;
            }
            if (third.charAt(i) == 'o') {
                count += 10;
            }
        }
        return count;
    }
}


