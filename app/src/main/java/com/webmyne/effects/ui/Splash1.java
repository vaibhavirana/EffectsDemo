package com.webmyne.effects.ui;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.webmyne.effects.R;

/**
 * Created by vaibhavirana on 29-07-2016.
 */
public class Splash1 extends AppCompatActivity {
    ImageView[] img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //mageView demoImage = (ImageView) findViewById(R.id.DemoImage);
        final ImageView mImageOrange = (ImageView) findViewById(R.id.image_orange);
        final ImageView mImageGrey = (ImageView) findViewById(R.id.image_grey);
        final ImageView mImageGrey1 = (ImageView) findViewById(R.id.image_grey1);
        final ImageView mImageGrey2 = (ImageView) findViewById(R.id.image_grey2);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageOrange.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator animator1 = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageGrey1.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        ValueAnimator animator2 = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mImageGrey2.setAlpha((Float) animation.getAnimatedValue());
            }
        });


        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator,animator1,animator2);
        animator.setDuration(5000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(-1);
        animator.start();

    }

}
