package com.sigpit.alexwurts.solitare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sigpit.alexwurts.solitare.R;
import com.sigpit.alexwurts.solitare.SolitareCanvas;

public class MainActivity extends AppCompatActivity {

    SolitareCanvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = findViewById(R.id.surfaceView);
        canvas.setup();
    }


    public void dispSolitare(View v) {
        canvas.drawSolitare();

    }
}
