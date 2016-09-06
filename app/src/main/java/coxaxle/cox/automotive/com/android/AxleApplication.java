package coxaxle.cox.automotive.com.android;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;

/**
 * Created by Kishore on 7/21/2016.
 */
public class AxleApplication extends Application{


    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}