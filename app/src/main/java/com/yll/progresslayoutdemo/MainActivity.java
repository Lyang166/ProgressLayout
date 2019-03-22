package com.yll.progresslayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yll.progresslayout.view.ProgressLayout;

public class MainActivity extends AppCompatActivity {

    private ProgressLayout mProgressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressLayout = ((ProgressLayout) findViewById(R.id.activity_main_pl));

        mProgressLayout.showLoading();

        mProgressLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mProgressLayout.showContent();
//                mProgressLayout.showNoData();
                mProgressLayout.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"点击刷新",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 1500);
    }
}
