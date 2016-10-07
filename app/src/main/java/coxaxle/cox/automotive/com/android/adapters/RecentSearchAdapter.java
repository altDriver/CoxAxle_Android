package coxaxle.cox.automotive.com.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.model.DealerInventoryCar;

/**
 * Created by Srinivas B on 03-10-2016.
 */
public class RecentSearchAdapter extends BaseAdapter {

    private Context context;
    ArrayList<HashMap<String,String>> objCar;
    public RecentSearchAdapter(Context ctx, ArrayList<HashMap<String,String>> savedCars)
    {
        this.objCar = savedCars;
        this.context = ctx;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        try {
            if (view == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.recent_search_list_row, null);
            }
            TextView txtMake = (TextView) view.findViewById(R.id.RecentSearch_CarMake);
            TextView txtModel = (TextView) view.findViewById(R.id.RecentSearch_CarModel);
            TextView txtdate = (TextView) view.findViewById(R.id.RecentSearch_Date);

            HashMap<String, String> hashmap_Current = new HashMap<String, String>();
            hashmap_Current = objCar.get(position);

            txtModel.setText(hashmap_Current.get("vehicle_model"));
            txtMake.setText(hashmap_Current.get("vehicle_make"));
            txtdate.setText(hashmap_Current.get("current_date_time"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public int getCount() {
        return objCar.size();
    }

    @Override
    public Object getItem(int i) {
        return objCar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return objCar.indexOf(getItem(i));
    }
}
