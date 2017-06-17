package com.poscustomer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

/**
 * Created by Abhishek on 28-03-2017.
 */

public class SplashActivity extends Activity {
    private static final int SPLASH_DURATION_MS = 2000;

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mHandler.postDelayed(mEndSplash, SPLASH_DURATION_MS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEndSplash.run();
        return super.onTouchEvent(event);
    }

    private Runnable mEndSplash = new Runnable() {
        public void run() {
            if (!isFinishing()) {
                mHandler.removeCallbacks(this);

                startActivity(new Intent(
                        SplashActivity.this, Login_Activity.class
                ));

                finish();
            }

        }

    };
}