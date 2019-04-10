package com.example.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView timerCountdownTextView;
    private Button timerStartPauseButton;
    private Button timerResetButton;

    private CountDownTimer timerCountdown;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerCountdownTextView = findViewById(R.id.text_view_countdown);
        timerStartPauseButton = findViewById(R.id.timer_start_pause);
        timerResetButton = findViewById(R.id.timer_reset);

        timerStartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        timerResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
        public void startTimer(){
            timerCountdown = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountdownText();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    timerStartPauseButton.setText(getString(R.string.timerStartText));
                    timerStartPauseButton.setVisibility(View.INVISIBLE);
                    timerResetButton.setVisibility(View.VISIBLE);
                }
            }.start();

            timerRunning = true;
            timerStartPauseButton.setText(getString(R.string.timerStopText));
            timerResetButton.setVisibility(View.INVISIBLE);
        }

        public void pauseTimer(){
            timerCountdown.cancel();
            timerRunning = false;
            timerStartPauseButton.setText(getString(R.string.timerStartText));
            timerResetButton.setVisibility(View.VISIBLE);
        }

        public void resetTimer(){
            timeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountdownText();
            timerResetButton.setVisibility(View.INVISIBLE);
            timerStartPauseButton.setVisibility(View.VISIBLE);
        }

        public void updateCountdownText(){
            int minutes = (int) (timeLeftInMillis / 1000) / 60;
            int seconds = (int) (timeLeftInMillis / 1000) % 60;

            String timeLeftOnScreen = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

            timerCountdownTextView.setText(timeLeftOnScreen);
        }
    }

