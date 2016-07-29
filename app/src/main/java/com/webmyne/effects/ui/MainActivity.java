package com.webmyne.effects.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webmyne.effects.R;
import com.webmyne.effects.adpater.ImageAdapter;
import com.webmyne.effects.image_processing.ImageProcessingConstants;
import com.webmyne.effects.image_processing.ImageProcessor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String file;
    private ImageView mOriginalImageView, imgHome, imgPic, imgSave;
    private Gallery mGallery;
    private Bitmap[] mBitmapArray;
    private ImageProcessor mImageProcessor;
    private Bitmap bitmap;
    private LinearLayout bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = getIntent().getStringExtra("file");
        initialize();
        // loadBitmaps();
        //setAdapterAndListener();
    }

    private void initialize() {
        bottom_sheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        imgPic = (ImageView) findViewById(R.id.imgPic);
       /* imgSave = (ImageView) findViewById(R.id.imgSave);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });*/

        imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom_sheet.isShown()) {
                    bottom_sheet.setVisibility(View.GONE);
                   /* bottom_sheet.animate()
                            .translationYBy(0)
                            .translationY(bottom_sheet.getHeight())
                            .setDuration(2000)
                            .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            bottom_sheet.setVisibility(View.GONE);
                        }
                    });;;*/
                   // bottom_sheet.animate().translationY(0);
                } else {
                    bottom_sheet.setVisibility(View.VISIBLE);
                   // bottom_sheet.animate().translationY(bottom_sheet.getHeight()).setDuration(1500);

                  /*  bottom_sheet.setAlpha(0.0f);

                // Start the animation
                    bottom_sheet.animate()
                            .translationY(bottom_sheet.getHeight())
                            .alpha(1.0f)
                            .setDuration(2000)
                            .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            bottom_sheet.setVisibility(View.VISIBLE);
                        }
                    });*/

                }
            }
        });

        mOriginalImageView = (ImageView) findViewById(R.id.imageView);
        if (!TextUtils.isEmpty(file)) {
            bitmap = BitmapFactory.decodeFile(file);
            mOriginalImageView.setImageBitmap(bitmap);
        }

        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
            }
        });

      //  mGallery = (Gallery) findViewById(R.id.gallery);
        mImageProcessor = new ImageProcessor();
    }

    private void saveImage() {
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/Photo Effects/";
        File sdIconStorageDir = new File(iconsStoragePath);

        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        try {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd_hhmmss");

            String filename = "IMG_" + df.format(date) + ".jpg";

            String filePath = sdIconStorageDir.toString() + "/" + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            Bitmap bitmap1 = ((BitmapDrawable) mOriginalImageView.getDrawable()).getBitmap();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
        Toast.makeText(this, "Image Save successfully", Toast.LENGTH_LONG).show();
    }

    private void loadBitmaps() {
        // Bitmap skullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.skull);
        //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        mBitmapArray = new Bitmap[]{
                /*skullBitmap,
                mImageProcessor.doHighlightImage(skullBitmap, 15, Color.RED),
                mImageProcessor.doInvert(skullBitmap), mImageProcessor.doGreyScale(skullBitmap),
                mImageProcessor.doGamma(skullBitmap, 0.6, 0.6, 0.6),
                mImageProcessor.doGamma(skullBitmap, 1.8, 1.8, 1.8),
                mImageProcessor.doColorFilter(skullBitmap, 1, 0, 0),
                mImageProcessor.doColorFilter(skullBitmap, 0, 1, 0),
                mImageProcessor.doColorFilter(skullBitmap, 0, 0, 1),
                mImageProcessor.doColorFilter(skullBitmap, 0.5, 0.5, 0.5),
                mImageProcessor.doColorFilter(skullBitmap, 1.5, 1.5, 1.5),
                mImageProcessor.createSepiaToningEffect(skullBitmap, 150, 0.7, 0.3, 0.12),
                mImageProcessor.createSepiaToningEffect(skullBitmap, 150, 0.8, 0.2, 0),
                mImageProcessor.createSepiaToningEffect(skullBitmap, 150, 0.12, 0.7, 0.3),
                mImageProcessor.createSepiaToningEffect(skullBitmap, 150, 0.12, 0.3, 0.7),
                mImageProcessor.decreaseColorDepth(skullBitmap, 32),
                mImageProcessor.decreaseColorDepth(skullBitmap, 64),
                mImageProcessor.decreaseColorDepth(skullBitmap, 128),
                mImageProcessor.createContrast(skullBitmap, 50),
                mImageProcessor.createContrast(skullBitmap, 100), mImageProcessor.rotate(skullBitmap, 40),
                mImageProcessor.rotate(skullBitmap, 340), mImageProcessor.doBrightness(skullBitmap, -60),
                mImageProcessor.doBrightness(skullBitmap, 30),
                mImageProcessor.doBrightness(skullBitmap, 80),
                mImageProcessor.applyGaussianBlur(skullBitmap), mImageProcessor.createShadow(skullBitmap),
                mImageProcessor.sharpen(skullBitmap, 11), mImageProcessor.applyMeanRemoval(skullBitmap),
                mImageProcessor.smooth(skullBitmap, 100), mImageProcessor.emboss(skullBitmap),
                mImageProcessor.engrave(skullBitmap),
                mImageProcessor.boost(skullBitmap, ImageProcessingConstants.RED, 1.5),
                mImageProcessor.boost(skullBitmap, ImageProcessingConstants.GREEN, 0.5),
                mImageProcessor.boost(skullBitmap, ImageProcessingConstants.BLUE, 0.67),
                mImageProcessor.roundCorner(skullBitmap, 45),
                mImageProcessor.flip(skullBitmap, ImageProcessingConstants.FLIP_VERTICAL),
                mImageProcessor.tintImage(skullBitmap, 50),
                mImageProcessor.replaceColor(skullBitmap, Color.BLACK, Color.BLUE),
                mImageProcessor.applyFleaEffect(skullBitmap), mImageProcessor.applyBlackFilter(skullBitmap),
                mImageProcessor.applySnowEffect(skullBitmap),
                mImageProcessor.applyShadingFilter(skullBitmap, Color.MAGENTA),
                mImageProcessor.applyShadingFilter(skullBitmap, Color.BLUE),
                mImageProcessor.applySaturationFilter(skullBitmap, 1),
                mImageProcessor.applySaturationFilter(skullBitmap, 5),
                mImageProcessor.applyHueFilter(skullBitmap, 1),
                mImageProcessor.applyHueFilter(skullBitmap, 5),
                mImageProcessor.applyReflection(skullBitmap),*/
                bitmap,
                //mImageProcessor.doHighlightImage(bitmap, 15, Color.RED),
                // mImageProcessor.doInvert(bitmap), mImageProcessor.doGreyScale(bitmap),
                mImageProcessor.doGamma(bitmap, 0.6, 0.6, 0.6),
                mImageProcessor.doGamma(bitmap, 1.8, 1.8, 1.8),
                mImageProcessor.doColorFilter(bitmap, 1, 0, 0),
                mImageProcessor.doColorFilter(bitmap, 0, 1, 0),
                mImageProcessor.doColorFilter(bitmap, 0, 0, 1),
                mImageProcessor.doColorFilter(bitmap, 0.5, 0.5, 0.5),
                mImageProcessor.doColorFilter(bitmap, 1.5, 1.5, 1.5),
                mImageProcessor.createSepiaToningEffect(bitmap, 150, 0.7, 0.3, 0.12),
                mImageProcessor.createSepiaToningEffect(bitmap, 150, 0.8, 0.2, 0),
                mImageProcessor.createSepiaToningEffect(bitmap, 150, 0.12, 0.7, 0.3),
                mImageProcessor.createSepiaToningEffect(bitmap, 150, 0.12, 0.3, 0.7),
                mImageProcessor.decreaseColorDepth(bitmap, 32),
                mImageProcessor.decreaseColorDepth(bitmap, 64),
                mImageProcessor.decreaseColorDepth(bitmap, 128),
                mImageProcessor.createContrast(bitmap, 50),
                mImageProcessor.createContrast(bitmap, 100), mImageProcessor.rotate(bitmap, 40),
                // mImageProcessor.rotate(bitmap, 340), mImageProcessor.doBrightness(bitmap, -60),
                mImageProcessor.doBrightness(bitmap, 30), mImageProcessor.doBrightness(bitmap, 80),
                mImageProcessor.applyGaussianBlur(bitmap), mImageProcessor.createShadow(bitmap),
                mImageProcessor.sharpen(bitmap, 11), mImageProcessor.applyMeanRemoval(bitmap),
                mImageProcessor.smooth(bitmap, 100), mImageProcessor.emboss(bitmap),
                mImageProcessor.engrave(bitmap),
                mImageProcessor.boost(bitmap, ImageProcessingConstants.RED, 1.5),
                mImageProcessor.boost(bitmap, ImageProcessingConstants.GREEN, 0.5),
                mImageProcessor.boost(bitmap, ImageProcessingConstants.BLUE, 0.67),
                mImageProcessor.roundCorner(bitmap, 45),
                mImageProcessor.flip(bitmap, ImageProcessingConstants.FLIP_VERTICAL),
                mImageProcessor.tintImage(bitmap, 50),
                //mImageProcessor.replaceColor(bitmap, Color.BLACK, Color.BLUE),
                mImageProcessor.applyFleaEffect(bitmap), mImageProcessor.applyBlackFilter(bitmap),
                mImageProcessor.applySnowEffect(bitmap),
                mImageProcessor.applyShadingFilter(bitmap, Color.MAGENTA),
                mImageProcessor.applyShadingFilter(bitmap, Color.BLUE),
                mImageProcessor.applySaturationFilter(bitmap, 1),
                mImageProcessor.applySaturationFilter(bitmap, 5),
                mImageProcessor.applyHueFilter(bitmap, 1), mImageProcessor.applyHueFilter(bitmap, 5),
                //mImageProcessor.applyReflection(bitmap)
        };
        //   writeToDisk();
    }

    private void setAdapterAndListener() {
        mGallery.setAdapter(new ImageAdapter(this, mBitmapArray));
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mOriginalImageView.setImageBitmap(mBitmapArray[position]);
            }
        });
    }
}
