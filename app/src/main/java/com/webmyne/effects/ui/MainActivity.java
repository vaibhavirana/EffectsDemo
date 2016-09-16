package com.webmyne.effects.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.webmyne.effects.R;
import com.webmyne.effects.adpater.FilterAdapter;
import com.webmyne.effects.effects.Styler;
import com.webmyne.effects.helper.Constants;
import com.webmyne.effects.helper.Functions;
import com.webmyne.effects.helper.ResizeAnimation;
import com.webmyne.effects.image_processing.ImageProcessor;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {


    private String file;
    private ImageView mOriginalImageView, imgHome, imgPic, imgSave, imgCrop, imgMagicWard, imgFiltre;
    private ImageView imgBrightness, imgContrast, imgSaturation, imgHue, imgSharpness;
   // private Gallery mGallery;
    // private Bitmap[] mBitmapArray;
    private ArrayList<Integer> mBitmapArray;
    private ArrayList<String> mBitmapStringArray;
    private ImageProcessor mImageProcessor;
    private Bitmap bitmap;
    private LinearLayout bottom_sheet;
    private RelativeLayout main_content;
    private SeekBar seekBar1;
    private Uri mCropImageUri;
    LinearLayout layoutfilter, layoutmagicward, layoutBottom;
    Animation slideUp;
    private Styler styler;
    private int selected_effect;
    private int brightnessValue, saturationValue, contrasValue, sharpnessValue, hueValue;
    private int height;
    private RecyclerView rvFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setActivityToFullScreen(this);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        file = getIntent().getStringExtra("file");
        initialize();
    }

    private void initialize() {

        bottom_sheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
        main_content = (RelativeLayout) findViewById(R.id.main_content);

        bottom_sheet.setVisibility(View.GONE);
        bottom_sheet.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        height = main_content.getHeight() - bottom_sheet.getMeasuredHeight();

        // bottom_sheet.setVisibility(View.GONE);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgCrop = (ImageView) findViewById(R.id.imgCrop);
        imgMagicWard = (ImageView) findViewById(R.id.imgMagicWard);
        imgFiltre = (ImageView) findViewById(R.id.imgFiltre);

        imgBrightness = (ImageView) findViewById(R.id.imgBrightness);
        imgContrast = (ImageView) findViewById(R.id.imgContrast);
        imgSaturation = (ImageView) findViewById(R.id.imgSaturation);
        imgHue = (ImageView) findViewById(R.id.imgHue);
        imgSharpness = (ImageView) findViewById(R.id.imgSharpness);

        layoutfilter = (LinearLayout) findViewById(R.id.layoutfilter);
        layoutmagicward = (LinearLayout) findViewById(R.id.layoutmagicward);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(this);
        changeSeekBarSetting(Constants.BRIGHTNESS_MAX, Constants.BRIGHTNESS_PROGRESS);
        selected_effect = Constants.REQUEST_BRIGHTNESS;

        brightnessValue = Constants.BRIGHTNESS_PROGRESS;
        contrasValue = Constants.CONTRAST_PROGRESS;
        saturationValue = Constants.CONTRAST_PROGRESS;
        sharpnessValue = Constants.SHARPNESS_PROGRESS;
        hueValue = Constants.SHARPNESS_PROGRESS;

        imgHome.setOnClickListener(this);
        imgPic.setOnClickListener(this);
        imgSave.setOnClickListener(this);
        imgCrop.setOnClickListener(this);
        imgMagicWard.setOnClickListener(this);
        imgFiltre.setOnClickListener(this);

        imgBrightness.setOnClickListener(this);
        imgContrast.setOnClickListener(this);
        imgSaturation.setOnClickListener(this);
        imgHue.setOnClickListener(this);
        imgSharpness.setOnClickListener(this);
        imgHome.setOnClickListener(this);

        mOriginalImageView = (ImageView) findViewById(R.id.imageView);
        styler = new Styler.Builder(mOriginalImageView, Styler.Mode.NONE).enableAnimation(500).build();

        if (!TextUtils.isEmpty(file)) {
            bitmap = BitmapFactory.decodeFile(file);
            mOriginalImageView.setImageBitmap(bitmap);
        }
        rvFilter=(RecyclerView)findViewById(R.id.rvFilter);
        rvFilter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mBitmapArray = new ArrayList<>();
        mBitmapArray.add(R.drawable.dummy_girl);
        mBitmapArray.add(R.drawable.minimal);
        mBitmapArray.add(R.drawable.false1);
        mBitmapArray.add(R.drawable.monochrome);
        mBitmapArray.add(R.drawable.instant);
        mBitmapArray.add(R.drawable.tone_curve);
        mBitmapArray.add(R.drawable.chrome);
        mBitmapArray.add(R.drawable.fade);
        mBitmapArray.add(R.drawable.mono);
        mBitmapArray.add(R.drawable.noir);
        mBitmapArray.add(R.drawable.process);
        mBitmapArray.add(R.drawable.tonal);
        mBitmapArray.add(R.drawable.transfer);
        mBitmapArray.add(R.drawable.linear_curve);
        mBitmapArray.add(R.drawable.hatched);
        mBitmapArray.add(R.drawable.half_tone);

        mBitmapStringArray = new ArrayList<>();
        mBitmapStringArray.add(getResources().getString(R.string.original));
        mBitmapStringArray.add(getResources().getString(R.string.minimal));
        mBitmapStringArray.add(getResources().getString(R.string.false1));
        mBitmapStringArray.add(getResources().getString(R.string.monochrome));
        mBitmapStringArray.add(getResources().getString(R.string.instant));
        mBitmapStringArray.add(getResources().getString(R.string.tone_curve));
        mBitmapStringArray.add(getResources().getString(R.string.chrome));
        mBitmapStringArray.add(getResources().getString(R.string.fade));
        mBitmapStringArray.add(getResources().getString(R.string.mono));
        mBitmapStringArray.add(getResources().getString(R.string.noir));
        mBitmapStringArray.add(getResources().getString(R.string.process));
        mBitmapStringArray.add(getResources().getString(R.string.tonal));
        mBitmapStringArray.add(getResources().getString(R.string.transfer));
        mBitmapStringArray.add(getResources().getString(R.string.lienar_curve));
        mBitmapStringArray.add(getResources().getString(R.string.hatched));
        mBitmapStringArray.add(getResources().getString(R.string.half_tone));

        rvFilter.setAdapter(new FilterAdapter(this,mBitmapStringArray,mBitmapArray));
       // mGallery = (Gallery) findViewById(R.id.gallery);
       // loadBitmaps();
       // setAdapterAndListener();

        mImageProcessor = new ImageProcessor();

        changeUi(2);

    }


    private void changeSeekBarSetting(int max, int value) {
        seekBar1.setMax(max);
        seekBar1.setProgress(value);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgPic:

                setLayoutBottomAction();

                break;
            case R.id.imgSave:
                //changeUi(4);
                saveImage();
                break;
            case R.id.imgCrop:
                changeUi(1);
                //startCropImageActivity(ResourceToUri(MainActivity.this, R.drawable.bg1));
                startCropImageActivity(getImageUri(MainActivity.this, bitmap));
                break;
            case R.id.imgMagicWard:
                changeUi(2);
                break;

            case R.id.imgHome:
                finish();
                break;
            case R.id.imgFiltre:
                changeUi(3);
                break;

            case R.id.imgBrightness:
                if (brightnessValue == Constants.BRIGHTNESS_PROGRESS)
                    changeSeekBarSetting(Constants.BRIGHTNESS_MAX, Constants.BRIGHTNESS_PROGRESS);
                else
                    changeSeekBarSetting(Constants.BRIGHTNESS_MAX, brightnessValue);
                selected_effect = Constants.REQUEST_BRIGHTNESS;
                break;

            case R.id.imgContrast:
                if (contrasValue == Constants.CONTRAST_PROGRESS)
                    changeSeekBarSetting(Constants.CONTRAST_MAX, Constants.CONTRAST_PROGRESS);
                else
                    changeSeekBarSetting(Constants.CONTRAST_MAX, contrasValue);
                selected_effect = Constants.REQUEST_CONTRAST;

                break;

            case R.id.imgSaturation:
                if (saturationValue == Constants.CONTRAST_PROGRESS)
                    changeSeekBarSetting(Constants.CONTRAST_MAX, Constants.CONTRAST_PROGRESS);
                else
                    changeSeekBarSetting(Constants.CONTRAST_MAX, saturationValue);

                selected_effect = Constants.REQUEST_SATURATION;
                break;

            case R.id.imgHue:
                if (hueValue == Constants.HUE_PROGRESS)
                    changeSeekBarSetting(Constants.HUE_MAX, Constants.HUE_PROGRESS);
                else
                    changeSeekBarSetting(Constants.HUE_MAX, hueValue);

                selected_effect = Constants.REQUEST_HUE;
                break;

            case R.id.imgSharpness:

                if (sharpnessValue == Constants.SHARPNESS_PROGRESS)
                    changeSeekBarSetting(Constants.SHARPNESS_MAX, Constants.SHARPNESS_PROGRESS);
                else
                    changeSeekBarSetting(Constants.SHARPNESS_MAX, sharpnessValue);

                selected_effect = Constants.REQUEST_SHARPNESS;

                break;
        }
    }

    private void setLayoutBottomAction() {

        if (bottom_sheet.isShown()) {
            slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            layoutBottom.startAnimation(slideUp);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    ResizeAnimation resizeAnimation = new ResizeAnimation(
                            mOriginalImageView,
                            main_content.getHeight(),
                            main_content.getHeight() - bottom_sheet.getHeight()
                    );
                    resizeAnimation.setDuration(4000);
                    mOriginalImageView.startAnimation(resizeAnimation);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    bottom_sheet.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        } else {
            bottom_sheet.setVisibility(View.VISIBLE);
            slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            layoutBottom.startAnimation(slideUp);
            ResizeAnimation resizeAnimation = new ResizeAnimation(
                    mOriginalImageView,
                    height,
                    main_content.getHeight()
            );
            resizeAnimation.setDuration(2000);
            mOriginalImageView.startAnimation(resizeAnimation);

            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    bottom_sheet.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

    }

    private void changeUi(int pos) {
        switch (pos) {
            case 1:
                imgCrop.setImageResource(R.drawable.ic_crop_selected);
                imgMagicWard.setImageResource(R.drawable.ic_magic_wand);
                imgFiltre.setImageResource(R.drawable.ic_filters);
                imgSave.setImageResource(R.drawable.ic_action_save);

                layoutmagicward.setVisibility(View.GONE);
                layoutfilter.setVisibility(View.INVISIBLE);
                //seekBar1.setVisibility(View.GONE);
                break;

            case 2:
                imgCrop.setImageResource(R.drawable.ic_crop);
                imgMagicWard.setImageResource(R.drawable.ic_magic_wand_selected);
                imgFiltre.setImageResource(R.drawable.ic_filters);
                imgSave.setImageResource(R.drawable.ic_action_save);

                layoutmagicward.setVisibility(View.VISIBLE);
                layoutfilter.setVisibility(View.GONE);
                // seekBar1.setVisibility(View.VISIBLE);


                break;

            case 3:
                imgCrop.setImageResource(R.drawable.ic_crop);
                imgMagicWard.setImageResource(R.drawable.ic_magic_wand);
                imgFiltre.setImageResource(R.drawable.ic_filters_selected);
                imgSave.setImageResource(R.drawable.ic_action_save);

                layoutmagicward.setVisibility(View.GONE);
                layoutfilter.setVisibility(View.VISIBLE);
                //seekBar1.setVisibility(View.GONE);

                break;

            case 4:
                imgCrop.setImageResource(R.drawable.ic_crop);
                imgMagicWard.setImageResource(R.drawable.ic_magic_wand);
                imgFiltre.setImageResource(R.drawable.ic_filters);
                imgSave.setImageResource(R.drawable.ic_action_save_selected);

                layoutmagicward.setVisibility(View.GONE);
                layoutfilter.setVisibility(View.INVISIBLE);
                //seekBar1.setVisibility(View.GONE);
                break;
        }
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
        setLayoutBottomAction();
    }

    private void loadBitmaps() {
        // Bitmap skullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.skull);
      /*  @"Original", @"Minimal", @"False", @"Monochrome", @"Instant",
        @"Tone Curve", @"Chrome", @"Fade", @"Mono", @"Noir",
        @"Process", @"Tonal", @"Transfer", @"Lienar Curve", @"Hatched", @"Half Tone"*/
        mBitmapArray = new ArrayList<>();
        mBitmapArray.add(R.drawable.dummy_girl);
        mBitmapArray.add(R.drawable.minimal);
        mBitmapArray.add(R.drawable.false1);
        mBitmapArray.add(R.drawable.monochrome);
        mBitmapArray.add(R.drawable.instant);
        mBitmapArray.add(R.drawable.tone_curve);
        mBitmapArray.add(R.drawable.chrome);
        mBitmapArray.add(R.drawable.fade);
        mBitmapArray.add(R.drawable.mono);
        mBitmapArray.add(R.drawable.noir);
        mBitmapArray.add(R.drawable.process);
        mBitmapArray.add(R.drawable.tonal);
        mBitmapArray.add(R.drawable.transfer);
        mBitmapArray.add(R.drawable.linear_curve);
        mBitmapArray.add(R.drawable.hatched);
        mBitmapArray.add(R.drawable.half_tone);

        //mImageProcessor.doGamma(bitmap, 0.6, 0.6, 0.6);

     /*   mBitmapArray = new Bitmap[]{
                *//*skullBitmap,
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
                mImageProcessor.applyReflection(skullBitmap),*//*
                bitmap,
                //mImageProcessor.doHighlightImage(bitmap, 15, Color.RED),
                // mImageProcessor.doInvert(bitmap), mImageProcessor.doGreyScale(bitmap),
             *//*   mImageProcessor.doGamma(bitmap, 0.6, 0.6, 0.6),
                mImageProcessor.doGamma(bitmap, 1.8, 1.8, 1.8),
                mImageProcessor.doBrightness(bitmap, 0),
                mImageProcessor.doBrightness(bitmap, 30),
                mImageProcessor.doBrightness(bitmap, 50),
                mImageProcessor.doBrightness(bitmap, 80),*//*
               *//* mImageProcessor.doColorFilter(bitmap, 1, 0, 0),
                mImageProcessor.doColorFilter(bitmap, 0, 1, 0),
                mImageProcessor.doColorFilter(bitmap, 0, 0, 1),
                mImageProcessor.doColorFilter(bitmap, 0.5, 0.5, 0.5),
                mImageProcessor.doColorFilter(bitmap, 1.5, 1.5, 1.5),*//*
               *//* mImageProcessor.createSepiaToningEffect(bitmap, 150, 0.7, 0.3, 0.12),
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
                mImageProcessor.applyHueFilter(bitmap, 1), mImageProcessor.applyHueFilter(bitmap, 5),*//*
                //mImageProcessor.applyReflection(bitmap)
        };*/
        //   writeToDisk();
    }

    private void setAdapterAndListener() {
       /* mGallery.setAdapter(new ImageAdapter(this, mBitmapArray));
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // mOriginalImageView.setImageBitmap(mBitmapArray[position]);
            }
        });*/
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
               // bitmap=result.getUri()
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                   // mOriginalImageView.setImageURI(result.getUri());
                    mOriginalImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeUi(2);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public static Uri ResourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //Toast.makeText(getApplicationContext(), "seekbar progress: " + progress, Toast.LENGTH_SHORT).show();
        if (fromUser) {
            switch (selected_effect) {
                case Constants.REQUEST_BRIGHTNESS:
                    styler.setBrightness(progress - 255).updateStyle();
                    brightnessValue = progress;
                    break;
                case Constants.REQUEST_CONTRAST:
                    styler.setContrast(progress / 100F).updateStyle();
                    contrasValue = progress;
                    break;
                case Constants.REQUEST_SATURATION:
                    styler.setSaturation(progress / 100F).updateStyle();
                    saturationValue = progress;
                    break;
                case Constants.REQUEST_HUE:
                    mOriginalImageView.setImageBitmap(mImageProcessor.applyHueFilter(bitmap, progress));
                    hueValue = progress;
                    break;
                case Constants.REQUEST_SHARPNESS:
                    mOriginalImageView.setImageBitmap(mImageProcessor.sharpen(bitmap, progress));
                    sharpnessValue = progress;
                    // styler.setBrightness(progress - 255).updateStyle();
                    break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Toast.makeText(getApplicationContext(), "seekbar touch started!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Toast.makeText(getApplicationContext(), "seekbar touch stopped!", Toast.LENGTH_SHORT).show();
    }

}
