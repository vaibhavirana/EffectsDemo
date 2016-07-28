package com.webmyne.effects.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.webmyne.effects.R;

public class PickerActivity extends AppCompatActivity {

    private ImageView imageView;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        file = getIntent().getStringExtra("file");
        Log.e("file", file);

        init();
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.imageView);
        if (!TextUtils.isEmpty(file)) {
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            imageView.setImageBitmap(bitmap);
        }
    }
}
