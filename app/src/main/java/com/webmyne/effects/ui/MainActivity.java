package com.webmyne.effects.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.webmyne.effects.R;
import com.webmyne.effects.adpater.ImageAdapter;
import com.webmyne.effects.image_processing.ImageProcessingConstants;
import com.webmyne.effects.image_processing.ImageProcessor;

public class MainActivity extends AppCompatActivity {

    private ImageView mOriginalImageView;
    private Gallery mGallery;
    private Bitmap[] mBitmapArray;
    private ImageProcessor mImageProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        loadBitmaps();
        setAdapterAndListener();
    }
    private void initialize() {
        mOriginalImageView = (ImageView) findViewById(R.id.imageView);
        mGallery = (Gallery) findViewById(R.id.gallery);
        mImageProcessor = new ImageProcessor();
    }

    private void loadBitmaps() {
        Bitmap skullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.skull);
        Bitmap carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        mBitmapArray = new Bitmap[] {
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
                carBitmap,
                mImageProcessor.doHighlightImage(carBitmap, 15, Color.RED),
                mImageProcessor.doInvert(carBitmap), mImageProcessor.doGreyScale(carBitmap),
                mImageProcessor.doGamma(carBitmap, 0.6, 0.6, 0.6),
                mImageProcessor.doGamma(carBitmap, 1.8, 1.8, 1.8),
                mImageProcessor.doColorFilter(carBitmap, 1, 0, 0),
                mImageProcessor.doColorFilter(carBitmap, 0, 1, 0),
                mImageProcessor.doColorFilter(carBitmap, 0, 0, 1),
                mImageProcessor.doColorFilter(carBitmap, 0.5, 0.5, 0.5),
                mImageProcessor.doColorFilter(carBitmap, 1.5, 1.5, 1.5),
                mImageProcessor.createSepiaToningEffect(carBitmap, 150, 0.7, 0.3, 0.12),
                mImageProcessor.createSepiaToningEffect(carBitmap, 150, 0.8, 0.2, 0),
                mImageProcessor.createSepiaToningEffect(carBitmap, 150, 0.12, 0.7, 0.3),
                mImageProcessor.createSepiaToningEffect(carBitmap, 150, 0.12, 0.3, 0.7),
                mImageProcessor.decreaseColorDepth(carBitmap, 32),
                mImageProcessor.decreaseColorDepth(carBitmap, 64),
                mImageProcessor.decreaseColorDepth(carBitmap, 128),
                mImageProcessor.createContrast(carBitmap, 50),
                mImageProcessor.createContrast(carBitmap, 100), mImageProcessor.rotate(carBitmap, 40),
                mImageProcessor.rotate(carBitmap, 340), mImageProcessor.doBrightness(carBitmap, -60),
                mImageProcessor.doBrightness(carBitmap, 30), mImageProcessor.doBrightness(carBitmap, 80),
                mImageProcessor.applyGaussianBlur(carBitmap), mImageProcessor.createShadow(carBitmap),
                mImageProcessor.sharpen(carBitmap, 11), mImageProcessor.applyMeanRemoval(carBitmap),
                mImageProcessor.smooth(carBitmap, 100), mImageProcessor.emboss(carBitmap),
                mImageProcessor.engrave(carBitmap),
                mImageProcessor.boost(carBitmap, ImageProcessingConstants.RED, 1.5),
                mImageProcessor.boost(carBitmap, ImageProcessingConstants.GREEN, 0.5),
                mImageProcessor.boost(carBitmap, ImageProcessingConstants.BLUE, 0.67),
                mImageProcessor.roundCorner(carBitmap, 45),
                mImageProcessor.flip(carBitmap, ImageProcessingConstants.FLIP_VERTICAL),
                mImageProcessor.tintImage(carBitmap, 50),
                mImageProcessor.replaceColor(carBitmap, Color.BLACK, Color.BLUE),
                mImageProcessor.applyFleaEffect(carBitmap), mImageProcessor.applyBlackFilter(carBitmap),
                mImageProcessor.applySnowEffect(carBitmap),
                mImageProcessor.applyShadingFilter(carBitmap, Color.MAGENTA),
                mImageProcessor.applyShadingFilter(carBitmap, Color.BLUE),
                mImageProcessor.applySaturationFilter(carBitmap, 1),
                mImageProcessor.applySaturationFilter(carBitmap, 5),
                mImageProcessor.applyHueFilter(carBitmap, 1), mImageProcessor.applyHueFilter(carBitmap, 5),
                mImageProcessor.applyReflection(carBitmap)
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
