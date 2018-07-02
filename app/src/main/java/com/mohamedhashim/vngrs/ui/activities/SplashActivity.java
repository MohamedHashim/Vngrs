package com.mohamedhashim.vngrs.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mohamedhashim.vngrs.BuildConfig;
import com.mohamedhashim.vngrs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Hashim on 7/2/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.vngrsLogo)
    ImageView vngrsLogo;
    private Long SPLASH_TIME_OUT = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        ButterKnife.setDebug(BuildConfig.DEBUG);
        YoYo.with(Techniques.BounceInUp).duration(1500).playOn(vngrsLogo);
        YoYo.with(Techniques.ZoomIn).duration(1500).playOn(vngrsLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
