package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.Task;

import static android.view.View.GONE;

public class StatisticsView extends AppCompatActivity {

    private Statistics stats;
    private Button resetButton;
    private int presses = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 9000;
    private int RC_GAMES_SCOPE = 9001;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

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

    public void signIn(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            GoogleSignIn.requestPermissions(this, RC_GAMES_SCOPE,
                    GoogleSignIn.getSignedInAccountFromIntent(data).getResult(),
                    Games.SCOPE_GAMES);
        } else if (requestCode == RC_GAMES_SCOPE) {

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            findViewById(R.id.playServicesLogin).setVisibility(GONE);
            TextView text = findViewById(R.id.accountInfo);
            text.setText("Account:" + account.getDisplayName());
        }
    }
}