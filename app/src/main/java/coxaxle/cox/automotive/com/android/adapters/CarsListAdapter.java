package coxaxle.cox.automotive.com.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class CarsListAdapter extends ArrayAdapter<VehicleInfo> {

    private ArrayList<VehicleInfo> vehicleInfoList;

    public CarsListAdapter(Context context, int activity_dealer_inventory_list_row, ArrayList<VehicleInfo> vehicleInfoList) {
        super( context, activity_dealer_inventory_list_row, vehicleInfoList);
        this.vehicleInfoList = new ArrayList<VehicleInfo>();
        this.vehicleInfoList.addAll(vehicleInfoList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_cars_list_row, null);
        }
        // Locate the TextViews in viewpager_item.xml
        final ImageView imgCar = (ImageView)convertView.findViewById(R.id.cars_list_image);
        TextView txtCarName = (TextView) convertView.findViewById(R.id.cars_list_name);
        TextView txtCarNextServicedate = (TextView) convertView.findViewById(R.id.car_list_next_service_date);

        VehicleInfo objVehicle = this.vehicleInfoList.get(position);
        ArrayList<String> arrImageUrls = objVehicle.vehicle_image;
        if(arrImageUrls.size()>0)
        {
            final String strImg = arrImageUrls.get(0);
            strImg.replace("\\", "");
            //String strUrl = Constants.GET_IMAGES + strImg;

            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        URL url = new URL(strImg);//"http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicles/7_9007_25080.png"
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        imgCar.setImageBitmap(image);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        else
            imgCar.setImageResource(R.mipmap.placeholder);
        txtCarName.setText(objVehicle.name);
        txtCarNextServicedate.setText(objVehicle.vehicle_model);

        return convertView;
    }

}