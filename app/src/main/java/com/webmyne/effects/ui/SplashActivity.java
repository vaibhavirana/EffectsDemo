package com.webmyne.effects.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.webmyne.effects.R;
import com.webmyne.effects.helper.Functions;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Functions.fireIntent(SplashActivity.this, MainActivity.class);
                finish();
            }
        }.start();
    }

    private void init() {
        ShimmerTextView shimmerTextView = (ShimmerTextView) findViewById(R.id.shimmerTextView);
        shimmerTextView.setTypeface(Functions.getBoldFont(this));
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(1500);
        shimmer.start(shimmerTextView);
    }
}
