package com.webmyne.effects.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.webmyne.effects.R;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

public class PickerActivity extends AppCompatActivity {

    private RelativeLayout panel;
    private BrokenView mBrokenView;
    BrokenTouchListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        init();
    }

    private void init() {
        panel = (RelativeLayout) findViewById(R.id.panel);

        mBrokenView = BrokenView.add2Window(this);
        listener = new BrokenTouchListener.Builder(mBrokenView)
                .build();
        panel.setOnTouchListener(listener);

       // panel.setOnClickListener(listener);

        mBrokenView.setCallback(new BrokenCallback() {
            @Override
            public void onStart(View v) {
                super.onStart(v);
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
            }

            @Override
            public void onCancel(View v) {
                super.onCancel(v);
            }

            @Override
            public void onRestart(View v) {
                super.onRestart(v);
            }

            @Override
            public void onFalling(View v) {
                super.onFalling(v);
            }

            @Override
            public void onFallingEnd(View v) {
                super.onFallingEnd(v);
            }

            @Override
            public void onCancelEnd(View v) {
                super.onCancelEnd(v);
            }
        });
    }
}
