package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;

/**
 */
public class SplashScreen extends Activity {
     private AnimationDrawable animationDrawable;
     private ImageView mProgressBar;
    UserSessionManager mUserSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mUserSessionManager = new UserSessionManager(this);
        getDeviceDetails();

        /*mProgressBar =(ImageView) findViewById(R.id.main_progress);
        mProgressBar.setBackgroundResource(R.drawable.progress_wheel_animation);
        animationDrawable = (AnimationDrawable)mProgressBar.getBackground();
        mProgressBar.setVisibility(View.VISIBLE);
        animationDrawable.start();*/

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (mUserSessionManager.isUserLoggedIn()) {
                        Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
    }

    void getDeviceDetails(){

        //int deviceOS = ;
        String deviceDetails = android.os.Build.MANUFACTURER+" "+ android.os.Build.MODEL +" "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.VERSION.SDK_INT+")";
        //String deviceMan = ;

        Log.d("Device Details>>",deviceDetails);
    }
}
