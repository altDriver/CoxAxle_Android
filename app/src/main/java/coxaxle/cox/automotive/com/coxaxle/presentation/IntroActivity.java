package coxaxle.cox.automotive.com.coxaxle.presentation;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.adapters.IntroAdapter;


public class IntroActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    int mDots;
    private int dotsCount = 3;
    private ImageView[] dots;
    private IntroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.intro_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new IntroAdapter(getSupportFragmentManager());
        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(mAdapter);
        // dotsCount = mViewPager.getChildCount();
        // Set a PageTransformer
        Typeface face= Typeface.createFromAsset(getAssets(), "font/font.ttf");
        mViewPager.setPageTransformer(true, new IntroPageTransformer());



        //mDots = new IntroAdapter().getCount();

       /* mViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {

            //declare key
            Boolean first = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (first && positionOffset == 0 && positionOffsetPixels == 0){
                    onPageSelected(0);
                    first = false;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //do what need
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    */
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Permissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //code for Accept
                } else {
                    //code for deny
                }
                break;
        }
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

       /* if (position + 1 == dotsCount) {
            //btnNext.setVisibility(View.GONE);
            //btnFinish.setVisibility(View.VISIBLE);
        } else {
            //btnNext.setVisibility(View.VISIBLE);
            //btnFinish.setVisibility(View.GONE);
        }*/
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
