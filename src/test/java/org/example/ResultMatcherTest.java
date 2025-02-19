package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultMatcherTest {

    ResultMatcher resultMatcher;

    /**
     * Setup
     */
    @BeforeEach
    void setUp() {
        resultMatcher = new ResultMatcher();

    }

    @Test
    void calculateStandingNullInput() {

        assertEquals(resultMatcher.calculateStanding(null), 0);

    }

    @Test
    void calculateStandingNoInput() {

        assertEquals(resultMatcher.calculateStanding(""), 0);

    }

    @Test
    void calculateStandingOneInput() {

        assertEquals(resultMatcher.calculateStanding("30:15"), 1815);

    }

    @ParameterizedTest
    @CsvSource({
            "12:11, 731",
            "30:12, 1812",
            "15:15, 915",
            "10:10, 610",
            "30:21, 1821",
            "20:22, 1222",
            "10:19, 619"
    })
    void calculateStandingManyInputs(String time, int expected) {
        assertEquals(resultMatcher.calculateStanding(time), expected);
    }

    @Test
    void calculateStandingWrongInput() {

        assertEquals(resultMatcher.calculateStanding("asdcdedsaafs@#@3>>>:#@323a1234"), 0);

    }

    @Test
    void calculateShootingRangeTimeNoInput() {

        assertEquals(resultMatcher.calculateShootingRangeTime("", "", ""), 0);

    }

    @Test
    void calculateShootingRangeTimeOneInput() {

        assertEquals(resultMatcher.calculateShootingRangeTime("xxxoo", "xxoxx", "xoxox"), 50);

    }

    @ParameterizedTest
    @CsvSource({
            "ooooo,ooooo,ooooo, 150",
            "ooxxo, oxxox,oxoxo, 80",
            "xxxxx,xxooo,oooxx, 60",
            "oooox, xoooo, oooox, 120",
            "oxoxo,xoxox,oxoxo, 80",
            "xxxoo, oooxx, xoxox, 70"
    })
    void calculateShootingRangeManyInputs(String first, String second, String third, int expected) {

        assertEquals(resultMatcher.calculateShootingRangeTime(first, second, third), expected);


    }

    @Test
    void calculateShootingRangeTimeWrongInput() {

        assertEquals(resultMatcher.calculateShootingRangeTime("xoooo", "xxAxo", "xooox"), 0);
        assertEquals(resultMatcher.calculateShootingRangeTime("xoooo", "xxoxo", "xo5ox"), 0);
        assertEquals(resultMatcher.calculateShootingRangeTime("xo2oo", "xxoxo", "xoxox"), 0);


    }

    @Test
    void revertToMinutesAndSecondsFormatNoInput() {
        assertEquals(resultMatcher.revertToMinutesAndSecondsFormat(0), "00:00");
    }

    @Test
    void revertToMinutesAndSecondsFormatOneInput() {
        assertEquals(resultMatcher.revertToMinutesAndSecondsFormat(1530), "25:30");
    }

    @ParameterizedTest
    @CsvSource({
            "1200, 20:00",
            "850, 14:10",
            "220, 3:40",
            "1830, 30:30",
            "1000, 16:40",
            "1500, 25:00",
            "280, 4:40"
    })
    void revertToMinutesAndSecondsFormatManyInputs(int timeInSeconds, String expected) {
        String result = resultMatcher.revertToMinutesAndSecondsFormat(timeInSeconds);
        assertEquals(result, expected);
    }


}