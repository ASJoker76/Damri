package com.app.template.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.template.R;

public class SplashScreenActivity extends AppCompatActivity {

    private int DELAYED = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setInitSplashScreen();
    }

    private void setInitSplashScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences pref = getBaseContext().getSharedPreferences("intro", Context.MODE_PRIVATE);
//                int skip_intro = pref.getInt("skip_intro", 0);
//                if (skip_intro == 1){
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
//                }else{
//                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//                    finish();
//                }
            }
        }, DELAYED);

    }
}