package coxaxle.cox.automotive.com.coxaxle.adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import coxaxle.cox.automotive.com.coxaxle.presentation.IntroFragment;

/**
 * Created by Kishore on 8/10/2016.
 */
public class IntroAdapter extends FragmentPagerAdapter {

    int mDots;

    public IntroAdapter(FragmentManager fm){
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // return IntroFragment.newInstance(Color.parseColor("#03A9F4"),position);
                //return IntroFragment.newInstance(Color.parseColor("#03A9F4"), position); // blue
            case 1:
                //return IntroFragment.newInstance(Color.parseColor("#4CAF50"), position); // green

            default:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position); // green
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
