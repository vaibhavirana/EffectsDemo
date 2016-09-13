package com.webmyne.effects.ui;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.webmyne.effects.R;
import com.webmyne.effects.bitmap.UriToUrl;
import com.webmyne.effects.helper.Constants;
import com.webmyne.effects.helper.Functions;
import com.webmyne.effects.helper.Toaster;

public class SplashActivityRevised extends AppCompatActivity implements View.OnClickListener {

    private TextView txtAppName;
    private ImageView imgCapture, imgGallery;
    private Context context;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setActivityToFullScreen(this);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        context = SplashActivityRevised.this;
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
       switch (v.getId())
       {
           case R.id.imgCapture:
               displayCamera();
               break;

           case R.id.imgGallery:
               displayGallery();
               break;
       }
    }

    private void displayGallery() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, Constants.REQUEST_GALLERY);
        } else {
            Toaster.make(getApplicationContext(), R.string.no_media);
        }
    }

    private void displayCamera() {
        imageUri = getOutputMediaFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }
    private Uri getOutputMediaFile(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Camera Pro");
        values.put(MediaStore.Images.Media.DESCRIPTION, "www.appsroid.org");
        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void displayPhotoActivity(int source_id) {
        Log.e("Image uri in Splash",imageUri+" || " + source_id);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_IMAGE_SOURCE, source_id);
        intent.setData(imageUri);
        intent.putExtra("file",imageUri);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CAMERA) {
            try{
                if (resultCode == RESULT_OK) {
                    displayPhotoActivity(1);
                } else {
                    UriToUrl.deleteUri(getApplicationContext(), imageUri);
                }
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_GALLERY) {
            try {
                imageUri = data.getData();
                displayPhotoActivity(2);
            } catch (Exception e) {
                Toaster.make(getApplicationContext(), R.string.error_img_not_found);
            }
        }
    }

}
