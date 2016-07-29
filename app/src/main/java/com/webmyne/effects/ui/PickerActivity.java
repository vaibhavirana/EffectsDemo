package com.webmyne.effects.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.webmyne.effects.R;

public class PickerActivity extends AppCompatActivity {

    private RelativeLayout panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        init();
    }

    private void init() {
        panel = (RelativeLayout) findViewById(R.id.panel);

    }
}
