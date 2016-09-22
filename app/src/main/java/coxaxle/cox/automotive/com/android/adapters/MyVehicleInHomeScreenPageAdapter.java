package coxaxle.cox.automotive.com.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.VehicleDetailsActivity;

/**
 * Created by Srinivas on 9/01/2016.
 */
public class MyVehicleInHomeScreenPageAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater mLayoutInflater;
    List<VehicleInfo> objArraylist;
    VehicleInfo objVehicle;
    ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();

    public MyVehicleInHomeScreenPageAdapter(Context context, ArrayList<VehicleInfo> objList) {
        this.context = context;
        objArraylist = objList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objArraylist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(ViewGroup collection,final int position) {

        if (imageLoader == null)
            imageLoader = AxleApplication.getInstance().getImageLoader();
        final View view = mLayoutInflater.inflate(R.layout.content_home_screen_my_cars, null);

        // Locate the TextViews in viewpager_item.xml
        NetworkImageView imageCar = (NetworkImageView) view.findViewById(R.id.my_car_image);
        TextView textCar = (TextView) view.findViewById(R.id.my_car_name);
        TextView textService = (TextView) view.findViewById(R.id.my_car_next_service_date);

        objVehicle = this.objArraylist.get(position);
        ArrayList<String> arrImageUrls = objVehicle.vehicle_image;
        if (arrImageUrls.size() > 0) {
            final String strImg = arrImageUrls.get(0);
            strImg.replace("\\", "");
            //String strUrl = Constants.GET_IMAGES + strImg;
            imageCar.setImageUrl(strImg, imageLoader);


/*            Thread thread = new Thread(new Runnable() {
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
            imageCar.setImageResource(R.mipmap.placeholder);

        textCar.setText(objVehicle.name);
        textService.setText(objVehicle.vehicle_model);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this will log the page number that was click
                //Bundle extra = new Bundle();
                //extra.put
                //int position=(Integer)view.getTag();
                //VehicleInfo obj = objArraylist.get(position);
                VehicleInfo objVehicle = objArraylist.get(position);

                Intent vehicleDetailsIntent = new Intent(context, VehicleDetailsActivity.class);
                //vehicleDetailsIntent.putExtra("CarsDetails", objArraylist.get(position));
                vehicleDetailsIntent.putExtra("navToActivity", "HomeScreenActivity");
                vehicleDetailsIntent.putExtra("VehicleInfo", objVehicle);
                context.startActivity(vehicleDetailsIntent);

            }
        });


        ((ViewPager) collection).addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        //((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }
}
