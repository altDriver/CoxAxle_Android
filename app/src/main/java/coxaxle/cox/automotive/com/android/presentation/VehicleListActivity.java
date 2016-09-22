package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.VehicleListAdapter;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class VehicleListActivity extends Activity {
    ListView list;
    TextView txtAddNewCars;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private VehicleListAdapter adapter;
    String strUserId;
    String navToActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        list=(ListView)findViewById(R.id.list);
        txtAddNewCars = (TextView) findViewById(R.id.MyCars_AddNewCar);
        txtAddNewCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                if(list.getAdapter()!=null){
                    count = list.getAdapter().getCount();
                }
                if(count == 5){
                    Toast.makeText(getApplicationContext(), "You can't add more than 5 vehicles. Please delete vehicles before adding a vehicle", Toast.LENGTH_SHORT).show();
                }else{
                    Intent addVehicleIntent = new Intent(VehicleListActivity.this, AddVehicleActivity.class);
                    addVehicleIntent.putExtra("Vehicle_Flag", 0);
                    addVehicleIntent.putExtra("navToActivity", navToActivity);
                    startActivity(addVehicleIntent);
                }
            }
        });

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
                    intentSchedule.putExtra("navToActivity", navToActivity);
                    startActivity(intentSchedule);
                }

            }
        });

    }
}