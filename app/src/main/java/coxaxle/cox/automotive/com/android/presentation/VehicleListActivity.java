package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.VehicleListAdapter;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class VehicleListActivity extends Activity {
    ListView list;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private VehicleListAdapter adapter;
    String strUserId;
    String navToActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        list=(ListView)findViewById(R.id.list);


        try{
            vehicleinfoList = this.getIntent().getParcelableArrayListExtra("vehicleList");
            navToActivity = this.getIntent().getStringExtra("navToActivity");

            adapter = new VehicleListAdapter(VehicleListActivity.this,R.layout.activity_cars_list, vehicleinfoList);

            list.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(navToActivity.equals("ScheduleAppointmentActivity")){
                VehicleInfo objdata = vehicleinfoList.get(i);
                Intent intentSchedule = new Intent(VehicleListActivity.this, ScheduleAppointmentActivity.class);
                intentSchedule.putExtra("VehicleInfo", objdata);
                startActivity(intentSchedule);
                    VehicleListActivity.this.finish();
                }else{
                    VehicleInfo objdata = vehicleinfoList.get(i);
                    Intent intentSchedule = new Intent(VehicleListActivity.this,VehicleDetailsActivity.class);
                    intentSchedule.putExtra("VehicleInfo", objdata);
                    startActivity(intentSchedule);
                }

            }
        });

    }
}