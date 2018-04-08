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

import model.Deck;

public class MainActivity extends AppCompatActivity {

    SolitareCanvas canvas;
    PopupWindow popupWindow;
    Button closePopUp;
    Button yes;
    Button no;
    boolean isPopupOpen = false;
    private Deck deckSaver = new Deck();
    boolean setup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = findViewById(R.id.surfaceView);
        canvas.setup(this, deckSaver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (setup) {
            canvas.setDeck(deckSaver);
        }
        setup = true;
    }


    /**
     * Displays Solitaire on the canvas
     */
    public void dispSolitaire() {
        canvas.drawSolitare();
    }

    /***
     * Bound to button on Statistics Button on UI. Brings up Statistics Screen.
     */
    public void dispStatistics(View v) {
        deckSaver = canvas.getDeck();
        Intent intent = new Intent(this, StatisticsView.class);
        startActivity(intent);
    }

    /***
     * Opens the end game statistics screen
     * @param v view that called function
     */
    public void openFinishedWindow(View v) {
        // Checks to see if popup already open
        if (isPopupOpen) {
            return;
        }
        isPopupOpen = true;
        // Loads popup
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.winpopout, null);
        closePopUp = customView.findViewById(R.id.exitButton);
        ConstraintLayout window = findViewById(R.id.popupLayout);


        popupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(window, Gravity.CENTER, 0, 0);

        // Sets TextView Values to correct values
        TextView timeValue = customView.findViewById(R.id.timeValue);
        TextView movesValue = customView.findViewById(R.id.movesValue);
//        TextView totalMovesValue = customView.findViewById(R.id.totalPlaysValue);

        Statistics stats = canvas.getStats();

        timeValue.setText(stats.getReadableTime());
        movesValue.setText(stats.getCurrentMovesAsString());
//        totalMovesValue.setText(stats.getTotalPlaysAsString());

        // setup for listener to dismiss popup
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                isPopupOpen = false;
            }
        });
    }

    /**
     * Popup window for restart so it the user cant restart automatically
     *
     * @param v
     */
    public void handleRestart(View v) {
        if (isPopupOpen) {
            return;
        }
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.restartpopup, null);
        yes = customView.findViewById(R.id.yesButton);
        no = customView.findViewById(R.id.noButton);
        ConstraintLayout window = findViewById(R.id.popupLayout);


        popupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(window, Gravity.CENTER, 0, 0);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                dispSolitaire();
                isPopupOpen = false;
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                isPopupOpen = false;
            }
        });
    }
}
