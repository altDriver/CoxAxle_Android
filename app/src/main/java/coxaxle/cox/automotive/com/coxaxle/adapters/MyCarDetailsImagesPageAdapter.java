package coxaxle.cox.automotive.com.coxaxle.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.presentation.VehicleDetailsActivity;

/**
 * Created by Kishore on 8/10/2016.
 */
public class MyCarDetailsImagesPageAdapter extends PagerAdapter {

    private Context ctx;


    public MyCarDetailsImagesPageAdapter(Context context) {
        ctx = context;
    }


    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.content_vehicle_details_screen_my_car_images, null);


        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //this will log the page number that was click

                Intent vehicleDetailsIntent = new Intent(ctx, VehicleDetailsActivity.class);
                ctx.startActivity(vehicleDetailsIntent);

                //Log.i("TAG", "This page was clicked: " + pos);
            }
        });



        ((ViewPager) collection).addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public void startUpdate(ViewGroup arg0) {
    }

    @Override
    public void finishUpdate(ViewGroup arg0) {
    }

    /*@Override
    public float getPageWidth(int position)
    {
        return 0.9f;
    }*/

}
