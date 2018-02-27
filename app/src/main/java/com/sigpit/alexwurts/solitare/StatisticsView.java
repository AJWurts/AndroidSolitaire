package com.sigpit.alexwurts.solitare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StatisticsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
