package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import coxaxle.cox.automotive.com.android.R;

/**
 */
public class SplashScreen extends Activity {

   /* private AnimationDrawable animationDrawable;
    private ImageView mProgressBar;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*mProgressBar =(ImageView) findViewById(R.id.main_progress);
        mProgressBar.setBackgroundResource(R.drawable.progress_wheel_animation);
        animationDrawable = (AnimationDrawable)mProgressBar.getBackground();
        mProgressBar.setVisibility(View.VISIBLE);
        animationDrawable.start();*/

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, IntroActivity.class);

                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}