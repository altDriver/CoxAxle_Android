package coxaxle.cox.automotive.com.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas B on 08-09-2016.
 */
public class DealerSearchInventoryResultsAdapter extends BaseAdapter {

    private ArrayList<VehicleInfo> vehicleInfoList;
    ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();

    public DealerSearchInventoryResultsAdapter(Context context, int activity_dealer_inventory_list_row, ArrayList<VehicleInfo> vehicleInfoList) {
        this.vehicleInfoList = new ArrayList<VehicleInfo>();
        this.vehicleInfoList.addAll(vehicleInfoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (imageLoader == null)
            imageLoader = AxleApplication.getInstance().getImageLoader();
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_dealer_inventory_list_row, null);
        }
        NetworkImageView imgCar = (NetworkImageView)convertView.findViewById(R.id.dealer_inventory_list_image);
        TextView txtCarName  = (TextView) convertView.findViewById(R.id.dealer_inventory_list_name);
        TextView txtCarmiles = (TextView) convertView.findViewById(R.id.dealer_inventory_list_miles);
        TextView txtCarprice = (TextView) convertView.findViewById(R.id.dealer_inventory_list_doller);

        VehicleInfo objVehicle = this.vehicleInfoList.get(position);
        ArrayList<String> arrImageUrls = objVehicle.vehicle_image;
        if(arrImageUrls.size()>0)
        {
            final String strImg = arrImageUrls.get(0);
            strImg.replace("\\", "");
            imgCar.setImageUrl(strImg, imageLoader);

            /*Thread thread = new Thread(new Runnable()
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
            thread.start();*/
        }
        else
            imgCar.setImageResource(R.mipmap.placeholder);
        txtCarName.setText(objVehicle.name);
        txtCarmiles.setText(objVehicle.vehicle_mileage+" Miles");
        txtCarprice.setText("$"+objVehicle.kbb_price);
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public int getCount() {
        return vehicleInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicleInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vehicleInfoList.indexOf(getItem(position));
    }
}