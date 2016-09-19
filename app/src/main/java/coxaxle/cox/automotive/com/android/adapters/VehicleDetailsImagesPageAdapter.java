package coxaxle.cox.automotive.com.android.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;

/**
 * Created by Kishore on 8/10/2016.
 */
public class VehicleDetailsImagesPageAdapter extends PagerAdapter {

    private Context ctx;
    ArrayList<String> objArrayList;
    LayoutInflater mLayoutInflater;
    com.android.volley.toolbox.ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();

    public VehicleDetailsImagesPageAdapter(Context context, ArrayList<String> objList) {
        this.ctx = context;
        objArrayList = objList;
        mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objArrayList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        //LayoutInflater layoutInflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (imageLoader == null)
            imageLoader = AxleApplication.getInstance().getImageLoader();
        View view = mLayoutInflater.inflate(R.layout.content_vehicle_details_screen_my_car_images, null);


        NetworkImageView imgCar = (NetworkImageView) view.findViewById(R.id.VehicleDetails_my_car_image);

        if (objArrayList.size() > 0) {
            final String strImg = objArrayList.get(position);
            strImg.replace("\\", "");

            imgCar.setImageUrl(strImg, imageLoader);

            /*Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(strImg);//"http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicles/7_9007_25080.png"
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        imgCar.setImageBitmap(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();*/
        } else
            imgCar.setImageResource(R.mipmap.placeholder);



        ((ViewPager) collection).addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        //((ViewPager) collection).removeView((View) view);
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
