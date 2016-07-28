package com.webmyne.effects.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.webmyne.effects.R;


public class SplashActivity extends AppCompatActivity {

    private TextView txtAppName;
    private ImageView imageBackground;
    Animation fadeIn, fadeOut, fadInRev, fadeOutRev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadInRev = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOutRev = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        init();
    }

    private void init() {
        Typeface ttf = Typeface.createFromAsset(getAssets(), "marguerite.ttf");
        txtAppName = (TextView) findViewById(R.id.txtAppName);
        txtAppName.setTypeface(ttf);

        imageBackground = (ImageView) findViewById(R.id.imageBackground);

        imageBackground.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBackground.setImageResource(R.drawable.bg4);
                imageBackground.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBackground.setImageResource(R.drawable.bg3);
                imageBackground.startAnimation(fadInRev);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadInRev.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBackground.setImageResource(R.drawable.bg3);
                imageBackground.startAnimation(fadeOutRev);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOutRev.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageBackground.setImageResource(R.drawable.bg1);
                imageBackground.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
