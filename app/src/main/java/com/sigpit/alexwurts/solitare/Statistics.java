package com.sigpit.alexwurts.solitare;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Sigpit on 1/27/2018.
 */

public class Statistics implements Serializable {
    // Ideas for stats
    // Eventually continue through app shut down
    // Timing and scoring
    // Number of Moves

    private int totalPlays, totalTime, savedLowestMoves, savedLowestTime;
    private long startTime;
    private long totalTimePrevGame;
    private int currentMoves;
    private long currentTime;
    private boolean gotPBMoves;
    private boolean gotPBTime;


//    public Statistics(Context context) {
//        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
//        int totalPlays = sharedPref.getInt(act.getChar(R.string.totalPlays), 0);
//        int totalTime = sharedPref.getInt(act.getChar(R.string.totalTime), 0);
//        int savedLowestMoves = sharedPref.getInt(act.getChar(R.string.savedLowestMoves), 0);
//        int savedLowestTime = sharedPref.getInt(act.getChar(R.string.savedLowestTime), 0);
//    }
//

    public Statistics() {

    }

    public void startTimer() {
        gotPBMoves = false;
        gotPBTime = false;
        startTime = System.currentTimeMillis();
    }

    public void endTimer() {
        currentTime = System.currentTimeMillis() - startTime;
        calcIfNewRecords();
    }

    private void calcIfNewRecords() {
        if (currentMoves < savedLowestMoves) {
            savedLowestMoves = currentMoves;
            gotPBMoves = true;
        }

        if (currentTime < savedLowestTime) {
            savedLowestTime = (int) currentTime;
            gotPBTime = true;
        }
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

    public void incMoves() {
        currentMoves++;
    }


    public int getCurrentMoves() {
        return currentMoves;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public String getCurrentTimeAsString() {
        return String.format(Locale.US, "%d", currentTime);
    }

    public int getTotalPlays() {
        return totalPlays;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getSavedLowestMoves() {
        return savedLowestMoves;
    }

    public int getSavedLowestTime() {
        return savedLowestTime;
    }

    public String getTotalPlaysAsString() {

        return String.format(Locale.US, "%d", totalPlays);
    }

    public String getTotalTimeAsString() {
        return String.format(Locale.US, "%d", totalTime);
    }

    public String getSavedLowestMovesAsString() {
        return String.format(Locale.US, "%d", savedLowestMoves);
    }

    public String getSavedLowestTimeAsString() {
        return String.format(Locale.US, "%d", savedLowestTime);
    }

    public String getCurrentMovesAsString() {
        if (!gotPBMoves) return String.format(Locale.US, "%d", currentMoves);
        else return String.format(Locale.US, "%d*", currentMoves);
    }
}
