package org.example;


import lombok.Data;

@Data

public class AthleteStats {
    private int AthleteNumber;
    private String AthleteName;
    private String countryCode;
    private int SkiTimeResults;
    private String firstShootingRange;
    private String secondShootingRange;
    private String thirdShootingRange;
    private int initialSkiTimeResult;

    public AthleteStats(int athleteNumber, String athleteName, String countryCode, int skiTimeResults, String firstShootingRange, String secondShootingRange, String thirdShootingRange) {
        AthleteNumber = athleteNumber;
        AthleteName = athleteName;
        this.countryCode = countryCode;
        SkiTimeResults = skiTimeResults;
        this.firstShootingRange = firstShootingRange;
        this.secondShootingRange = secondShootingRange;
        this.thirdShootingRange = thirdShootingRange;
    }

    public AthleteStats(String athleteName, int skiTimeResults, int initialSkiTimeResult) {
        AthleteName = athleteName;
        SkiTimeResults = skiTimeResults;
        this.initialSkiTimeResult = initialSkiTimeResult;
    }
}
