package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticsView extends AppCompatActivity {

    private Statistics stats;
    private Button resetButton;
    private int presses = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);
        stats = new Statistics(
                getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE),
                this);
        dispStats();
        resetButton = findViewById(R.id.resetStatsButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, R.anim.slide_out_right);

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.do_nothing);
    }

    public void dispStats() {
        TextView bestMoves = findViewById(R.id.bestMovesStats);
        TextView bestTime = findViewById(R.id.bestTimesStats);
        TextView totalPlays = findViewById(R.id.totalPlayStats);
        TextView totalTime = findViewById(R.id.totalTimeStats);

        bestMoves.setText(stats.getLowestMovesAsString());
        bestTime.setText(stats.getLowestTimeAsString());
        totalPlays.setText(stats.getTotalPlaysAsString());
        totalTime.setText(stats.getTotalTimeAsString());
    }

    public void resetStatistics(View v) {
        switch (presses) {
            case 0:
                resetButton.setText("Are you sure?");
                presses = 1;
                break;
            case 1:
                stats.resetStats();
                resetButton.setText("Reset Statistics?");
                presses = 0;
                break;
        }
        dispStats();

    }
}
