package com.sigpit.alexwurts.solitare;

import java.io.Serializable;
import java.util.Locale;

public class StatisticsData implements Serializable {
    private int totalPlays, totalTime, lowestMoves, lowestTime;

    public StatisticsData(int totalPlays, int totalTime, int lowestMoves, int lowestTime) {
        this.totalPlays = totalPlays;
        this.totalTime = totalTime;
        this.lowestMoves = lowestMoves;
        this.lowestTime = lowestTime;

    }

    private static String convertToReadableTime(int time) {
        long currentTime = time;
        currentTime /= 1000;
        int hours = (int) currentTime / 3600;
        int minutes = ((int) currentTime - (hours * 3600)) / 60;
        int seconds = ((int) currentTime - (hours * 3600) - (minutes * 60));

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public String getTotalPlaysAsString() {
        if (totalPlays == -1) {
            return "No Wins Yet";
        }

        return String.format(Locale.US, "%d", totalPlays);
    }

    public String getTotalTimeAsString() {
        if (totalTime == -1) {
            return "No Wins Yet";
        }
        return convertToReadableTime(totalTime);
    }

    public String getLowestMovesAsString() {
        if (lowestMoves == -1) {
            return "No Wins Yet";
        }
        return String.format(Locale.US, "%d", lowestMoves);
    }

    public String getLowestTimeAsString() {
        if (lowestTime == -1) {
            return "No Wins Yet";
        }
        return convertToReadableTime(lowestTime);
    }
}
