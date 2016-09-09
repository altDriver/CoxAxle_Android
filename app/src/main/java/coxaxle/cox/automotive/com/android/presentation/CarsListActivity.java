package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.CarsListAdapter;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class CarsListActivity extends Activity {
    ListView list;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private CarsListAdapter adapter;
    String strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        list=(ListView)findViewById(R.id.list);
        try{
            vehicleinfoList = this.getIntent().getParcelableArrayListExtra("vehicleList");

            adapter = new CarsListAdapter(CarsListActivity.this,R.layout.activity_cars_list, vehicleinfoList);

            list.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}