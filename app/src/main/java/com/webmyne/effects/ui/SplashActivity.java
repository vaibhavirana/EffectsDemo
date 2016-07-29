package com.webmyne.effects.ui;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.webmyne.effects.R;
import com.webmyne.effects.helper.Functions;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtAppName;
    private ImageView imgCapture, imgGallery;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = SplashActivity.this;
        setBackgroundAnim();
        init();
    }

    private void setBackgroundAnim() {
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

    private void init() {
        Typeface ttf = Typeface.createFromAsset(getAssets(), "marguerite.ttf");
        txtAppName = (TextView) findViewById(R.id.txtAppName);
        assert txtAppName != null;
        txtAppName.setTypeface(ttf);

        imgCapture = (ImageView) findViewById(R.id.imgCapture);
        imgGallery = (ImageView) findViewById(R.id.imgGallery);

        actionListener();

    }

    private void actionListener() {
        imgCapture.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        Functions.setPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                switch (v.getId()) {
                    case R.id.imgCapture:
                        captureImage();
                        break;

                    case R.id.imgGallery:
                        pickImage();
                        break;
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pickImage() {
        RxImagePicker.with(context).requestImage(Sources.GALLERY)
                .flatMap(new Func1<Uri, Observable<File>>() {
                    @Override
                    public Observable<File> call(Uri uri) {
                        return RxImageConverters.uriToFile(context, uri, createFile());
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("file", file.getAbsolutePath());
                        startActivity(intent);
                    }
                });
    }

    private void captureImage() {
        RxImagePicker.with(context).requestImage(Sources.CAMERA)
                .flatMap(new Func1<Uri, Observable<File>>() {
                    @Override
                    public Observable<File> call(Uri uri) {
                        return RxImageConverters.uriToFile(context, uri, createFile());
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("file", file.getAbsolutePath());
                        startActivity(intent);
                    }
                });

    }

    private File createFile() {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "_image.jpeg");
    }
}
