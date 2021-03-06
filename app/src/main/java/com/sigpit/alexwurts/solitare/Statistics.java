package com.sigpit.alexwurts.solitare;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by Sigpit on 1/27/2018.
 */

public class Statistics {
    // Ideas for stats
    // Eventually continue through app shut down
    // Timing and scoring
    // Number of Moves

    private int totalPlays, totalTime, lowestMoves, lowestTime;
    private long startTime;
    private long totalTimePrevGame;
    private int currentMoves;
    private long currentTime;
    private boolean gotPBMoves;
    private boolean gotPBTime;
    private Activity main;
    private SharedPreferences sharedPref;


    public Statistics(SharedPreferences sharedPref, Activity act) {
        this.sharedPref = sharedPref;
        this.main = act;
        getStats();
    }


    public Statistics() {

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

    private static String convertToReadableTime(long time) {
        return convertToReadableTime((int) time);
    }

    public void startTimer() {
        gotPBMoves = false;
        gotPBTime = false;
        currentMoves = 0;
        startTime = System.currentTimeMillis();
    }

    public void getStats() {
        totalPlays = sharedPref.getInt(main.getString(R.string.total_plays), -1);
        totalTime = sharedPref.getInt(main.getString(R.string.total_time), -1);
        lowestMoves = sharedPref.getInt(main.getString(R.string.lowest_moves), -1);
        lowestTime = sharedPref.getInt(main.getString(R.string.lowest_time), -1);
    }

    public void save() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(main.getString(R.string.total_plays), totalPlays);
        editor.putInt(main.getString(R.string.total_time), totalTime);
        editor.putInt(main.getString(R.string.lowest_moves), lowestMoves);
        editor.putInt(main.getString(R.string.lowest_time), lowestTime);
        editor.apply();
    }

    private void uploadScores() {
//        LeaderboardsClient client = new Games.getLeaderboardsClient(main.getApplicationContext(), GoogleSignInAccount.)
//                .submitScore(main.getString(R.string.leaderboard_lowest_moves), )
    }

    public String getReadableTime() {
        long currentTime = System.currentTimeMillis() - startTime;
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

    public void endTimer() {
        currentTime = System.currentTimeMillis() - startTime;
        totalPlays += 1;
        totalTime += currentTime;
        calcIfNewRecords();
        save();
    }

    private void calcIfNewRecords() {
        if (lowestMoves == -1) {
            lowestMoves = currentMoves;
            gotPBMoves = true;
            lowestTime = (int) currentTime;
            gotPBTime = true;
            totalPlays += 1;
            totalTime += 1;
            return;
        }
        if (currentMoves < lowestMoves) {
            lowestMoves = currentMoves;
            gotPBMoves = true;
        }

        if (currentTime < lowestTime) {
            lowestTime = (int) currentTime;
            gotPBTime = true;
        }
    }

    public void incMoves() {
        currentMoves++;
    }

    public String getCurrentTimeAsString() {
        return convertToReadableTime(currentTime);
    }

    public String getCurrentMovesAsString() {
        if (!gotPBMoves) return String.format(Locale.US, "%d", currentMoves);
        else return String.format(Locale.US, "%d*", currentMoves);
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

    public StatisticsData getStatsData() {
        return new StatisticsData(totalPlays, totalTime, lowestMoves, lowestTime);
    }

    public void resetStats() {
        totalPlays = -1;
        totalTime = -1;
        lowestMoves = -1;
        lowestTime = -1;
        save();
    }

}
