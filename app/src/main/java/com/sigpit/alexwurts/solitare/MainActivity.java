package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SolitareCanvas canvas;
    PopupWindow popupWindow;
    Button closePopUp;
    boolean isPopupOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = findViewById(R.id.surfaceView);
        canvas.setup(this);
    }


    public void dispSolitare(View v) {
        canvas.drawSolitare();
    }

    /***
     * Bound to button on Statistics Button on UI. Brings up Statistics Screen.
     * @param v
     */
    public void dispStatistics(View v) {
        Intent intent = new Intent(this, StatisticsView.class);
        startActivity(intent);

    }

    /***
     * Opens the end game statistics screen
     * @param v view that called function
     */
    public void openFinishedWindow(View v) {
        isPopupOpen = true;
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.winpopout, null);
        closePopUp = customView.findViewById(R.id.exitButton);
        ConstraintLayout window = findViewById(R.id.popupLayout);


        popupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(window, Gravity.CENTER, 0, 0);

        // Sets TextView Values to correct values
        TextView timeValue = customView.findViewById(R.id.timeValue);
        TextView movesValue = customView.findViewById(R.id.movesValue);
        TextView totalMovesValue = customView.findViewById(R.id.totalPlaysValue);

        Statistics stats = canvas.getStats();

        timeValue.setText(stats.getReadableTime());
        movesValue.setText(stats.getCurrentMovesAsString());
        totalMovesValue.setText(stats.getTotalPlaysAsString());


        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                isPopupOpen = false;
            }
        });
    }
}
