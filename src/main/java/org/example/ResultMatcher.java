package org.example;

import lombok.Data;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * {@code ResultMatcher} class takes a file and calculates the result of a biathlon athlete time.
 * </p>
 *
 * <p>
 * The class computes athlete participants times results and make a ranking based on that.
 * </p>
 */
@Data
public class ResultMatcher {


    List<AthleteStats> athleteResults = new ArrayList<>();
    Set<AthleteStats> athleteResultsSorted = new TreeSet<>(Comparator.comparingInt(AthleteStats::getSkiTimeResults));
    List<Integer> initialTimeList = new ArrayList<>();


    public ResultMatcher(File file) {

        // Reading from a file and saving results in arrayList but with time result formated to integer value in seconds
        Path path = Paths.get(file.getPath());
        try {
            List<String> read = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : read) {
                String[] compose = line.split(",");
                athleteResults.add(new AthleteStats(Integer.parseInt(compose[0]), compose[1], compose[2], calculateStanding(compose[3]), compose[4], compose[5], compose[6]));
            }


//             Adding the arrayList values in a set for sorted order, removing of duplicates and calculating
//             total standing time before adding to set.

//             Total standing time is skiTimeResult + time calculated from shooting range.

            for (AthleteStats athleteStats : athleteResults) {
                athleteResultsSorted.add(new AthleteStats(athleteStats.getAthleteName(),
                        athleteStats.getSkiTimeResults()
                                + (calculateTotalStandingTime(athleteStats.getFirstShootingRange(),
                                athleteStats.getSecondShootingRange(),
                                athleteStats.getThirdShootingRange())), athleteStats.getSkiTimeResults()));

            }

//             Displaying the results and name of the athletes
//
//             Format is: Place of the contestant in the biathlon, name of the athlete,
//             Total time, initial time before shooting range, shooting range penalty in seconds.

            int athleteIndexes = 0;
            for (AthleteStats athleteStats : athleteResultsSorted) {
                if (athleteIndexes == 0) {
                    System.out.println("Winner - " + athleteStats.getAthleteName() + " " + revertToMinutesAndSecondsFormat(athleteStats.getSkiTimeResults()) + " (" + revertToMinutesAndSecondsFormat(athleteStats.getInitialSkiTimeResult()) + " + " + (athleteStats.getSkiTimeResults() - athleteStats.getInitialSkiTimeResult()) + ")");
                } else if (athleteIndexes == 1) {
                    System.out.println("Runner-up - " + athleteStats.getAthleteName() + " " + revertToMinutesAndSecondsFormat(athleteStats.getSkiTimeResults()) + " (" + revertToMinutesAndSecondsFormat(athleteStats.getInitialSkiTimeResult()) + " + " + (athleteStats.getSkiTimeResults() - athleteStats.getInitialSkiTimeResult()) + ")");
                } else if (athleteIndexes == 2) {
                    System.out.println("Third place - " + athleteStats.getAthleteName() + " " + revertToMinutesAndSecondsFormat(athleteStats.getSkiTimeResults()) + " (" + revertToMinutesAndSecondsFormat(athleteStats.getInitialSkiTimeResult()) + " + " + (athleteStats.getSkiTimeResults() - athleteStats.getInitialSkiTimeResult()) + ")");
                } else {
                    System.out.println(athleteIndexes + 1 + "th " + athleteStats.getAthleteName() + " " + revertToMinutesAndSecondsFormat(athleteStats.getSkiTimeResults()) + " (" + revertToMinutesAndSecondsFormat(athleteStats.getInitialSkiTimeResult()) + " + " + (athleteStats.getSkiTimeResults() - athleteStats.getInitialSkiTimeResult()) + ")");
                }
                if (athleteIndexes == 2) {
                    System.out.println("--------------------------------------");
                }

                athleteIndexes++;
            }

        } catch (IOException e) {
            System.out.println("There is not a file named: " + file.getPath());
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("------------------------------------");
            System.out.println("Execution done");
        }
    }


    /**
     * @param time The string to be parsed e.g., 30:15, 20:12
     * @return int value in seconds from parsed string
     * <p>
     * finalTime = (integer value of the string first half * 60) + integer value of last half of the string.
     * e.g, 30:15 = (30 * 60) + 15
     * </p>
     */
    int calculateStanding(String time) {
        int finalTime = 0;
        int index = 0;
        StringBuilder result = new StringBuilder();
        while (time.charAt(index) != ':') {
            result.append(time.charAt(index));
            index++;
        }
        finalTime += Integer.parseInt(result.toString()) * 60;
        result = new StringBuilder();
        index++;
        while (index != time.length()) {
            result.append(time.charAt(index));
            index++;
        }
        finalTime += Integer.parseInt(result.toString());
        initialTimeList.add(finalTime);
        return finalTime;
    }

    /**
     * @param first  first shooting range result
     * @param second second shooting range result
     * @param third  third shooting range result
     * @return integer value of first + second + third shooting range penalties in seconds
     */
    int calculateTotalStandingTime(String first, String second, String third) {
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

    /**
     * @param timeResult timeResult
     * @return String value of timeResult parameter in minutes:seconds format
     */
    String revertToMinutesAndSecondsFormat(int timeResult) {
        int timeInMinutes = 0;
        while (timeResult > 60) {
            timeInMinutes += 1;
            timeResult -= 60;
        }
        return timeInMinutes + ":" + timeResult;
    }
}


