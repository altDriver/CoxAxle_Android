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
 * Created by Srinivas B on 08-09-2016.
 */
public class DealerSearchInventoryResultsAdapter extends ArrayAdapter<VehicleInfo> {

    private ArrayList<VehicleInfo> vehicleInfoList;

    public DealerSearchInventoryResultsAdapter(Context context, int activity_dealer_inventory_list_row, ArrayList<VehicleInfo> vehicleInfoList) {
        super( context, activity_dealer_inventory_list_row, vehicleInfoList);
        this.vehicleInfoList = new ArrayList<VehicleInfo>();
        this.vehicleInfoList.addAll(vehicleInfoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_dealer_inventory_list_row, null);
        }
        final ImageView imgCar = (ImageView)convertView.findViewById(R.id.dealer_inventory_list_image);
        TextView txtCarName  = (TextView) convertView.findViewById(R.id.dealer_inventory_list_name);
        TextView txtCarmiles = (TextView) convertView.findViewById(R.id.dealer_inventory_list_miles);
        TextView txtCarprice = (TextView) convertView.findViewById(R.id.dealer_inventory_list_doller);

        VehicleInfo objVehicle = this.vehicleInfoList.get(position);
        ArrayList<String> arrImageUrls = objVehicle.vehicle_image;
        if(arrImageUrls.size()>0)
        {
            final String strImg = arrImageUrls.get(0);
            strImg.replace("\\", "");

            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        URL url = new URL(strImg);
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
        txtCarmiles.setText(objVehicle.vehicle_mileage+" Miles");
        txtCarprice.setText("$"+objVehicle.kbb_price);

        return convertView;
    }

}