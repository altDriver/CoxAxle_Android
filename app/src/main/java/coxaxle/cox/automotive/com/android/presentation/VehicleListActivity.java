package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.fragments.ContentHomeScreenFragment;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class VehicleListActivity extends Activity{
    ListView list;
    TextView txtAddNewCars;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private VehicleListAdapter adapter;
    String strUserId;
    int flag_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        list=(ListView)findViewById(R.id.list);
        txtAddNewCars = (TextView) findViewById(R.id.MyCars_AddNewCar);
        vehicleinfoList = new ArrayList<>();
        UserSessionManager obj = new UserSessionManager(VehicleListActivity.this);
        strUserId = obj.getUserId();




        try{
            flag_add = this.getIntent().getIntExtra("Flag_Add", 0);
            if(flag_add == 0) {
                vehicleinfoList = this.getIntent().getParcelableArrayListExtra("vehicleList");
                adapter = new VehicleListAdapter(VehicleListActivity.this,R.layout.activity_cars_list, vehicleinfoList);
                list.setAdapter(adapter);
            }
            else
                getListOfVehicles();
        }catch (Exception e){
            e.printStackTrace();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //if(navToActivity.equals("ScheduleAppointmentActivity")){
                VehicleInfo objdata = vehicleinfoList.get(i);
                Intent intentSchedule = new Intent(VehicleListActivity.this, ScheduleAppointmentActivity.class);
                intentSchedule.putExtra("VehicleInfo", objdata);
                startActivity(intentSchedule);
                VehicleListActivity.this.finish();
                /*}else{
                    VehicleInfo objdata = vehicleinfoList.get(i);
                    Intent intentSchedule = new Intent(VehicleListActivity.this,VehicleDetailsActivity.class);
                    intentSchedule.putExtra("VehicleInfo", objdata);
                    intentSchedule.putExtra("navToActivity", navToActivity);
                    startActivity(intentSchedule);
                }*/

            }
        });
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
                    addVehicleIntent.putExtra("navToActivity", "ScheduleAppointmentActivity");
                    startActivity(addVehicleIntent);
                }
            }
        });

    }


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VehicleListActivity.this,HomeScreen.class);
        startActivity(intent);
    }*/

    public class VehicleListAdapter extends BaseAdapter {

        private ArrayList<VehicleInfo> vehicleInfoList;
        com.android.volley.toolbox.ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();

        public VehicleListAdapter(Context context, int activity_cars_list_row, ArrayList<VehicleInfo> vehicleInfoList) {
            this.vehicleInfoList = new ArrayList<VehicleInfo>();
            this.vehicleInfoList.addAll(vehicleInfoList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (imageLoader == null)
                imageLoader = AxleApplication.getInstance().getImageLoader();
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.activity_cars_list_row, null);
            }
            NetworkImageView imgCar = (NetworkImageView)convertView.findViewById(R.id.cars_list_image);
            TextView txtCarName = (TextView) convertView.findViewById(R.id.cars_list_name);
            TextView txtCarNextServicedate = (TextView) convertView.findViewById(R.id.car_list_next_service_date);
            ImageView imgDeleteVehicle = (ImageView) convertView.findViewById(R.id.CarsList_DeleteCar_iv);

            VehicleInfo objVehicle = this.vehicleInfoList.get(position);


          String arrImageUrls = objVehicle.photo;
            if(arrImageUrls.length()>0)
            {
                arrImageUrls.replace("\\", "");
                imgCar.setImageUrl(arrImageUrls, imageLoader);
            }
            else
                imgCar.setImageResource(R.mipmap.placeholder);
            txtCarName.setText(objVehicle.name);
            txtCarNextServicedate.setText(objVehicle.model);


            imgDeleteVehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    VehicleInfo objVeh = vehicleInfoList.get(position);
                    String strCarId = objVeh.id;
                    vehicleInfoList.remove(position);
                    deleteVehicle(strCarId);
                    notifyDataSetChanged();
                }
            });
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

    private void deleteVehicle(final String strId)
    {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_VEHICLE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("Delete Vehicle>>>", "" + response);

                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);
                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");

                                Toast.makeText(VehicleListActivity.this, strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error delete vehicle>>>", "" + error);
                            Toast.makeText(VehicleListActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("vid", strId);
                    params.put("uid", strUserId);

                    Log.v("params>>>", "" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(VehicleListActivity.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //if Add vehicle from here, call this method to update list
    public void getListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GARAGE_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responce) {

                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(responce);
                                Log.d("MyCArsLIst", responce);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonData = jsonobjListVehiclesResponse.getJSONObject("response");
                                    JSONArray jsonArray = jsonData.getJSONArray("data");
                                    if (jsonArray.length() > 0)
                                    {

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = jsonArray.getJSONObject(i);
                                            ArrayList<String> arrinsurenceDocUrls = new ArrayList<>();

                                            JSONArray objInsUrls = obj.getJSONArray("insurance_document");
                                            if (objInsUrls.length() > 0) {
                                                for (int j = 0; j < objInsUrls.length(); j++) {
                                                    JSONObject objImagePath = objInsUrls.getJSONObject(j);
                                                    String strpath = objImagePath.getString("insurence_url");
                                                    if (strpath != null)
                                                        arrinsurenceDocUrls.add(strpath);
                                                }
                                            }

                                            VehicleInfo vehicleInfo = new VehicleInfo(obj.getString("id"),
                                                    obj.getString("name"),
                                                    obj.getString("vin"),
                                                    obj.getString("vehicle_type"),
                                                    obj.getString("make"),
                                                    obj.getString("model"),
                                                    obj.getString("year"),
                                                    obj.getString("color"),
                                                    obj.getString("mileage"),
                                                    obj.getString("style"),
                                                    obj.getString("trim"),
                                                    obj.getString("waranty_from"),
                                                    obj.getString("waranty_to"),
                                                    obj.getString("extended_waranty_from"),
                                                    obj.getString("extended_waranty_to"),
                                                    obj.getString("manual"),
                                                    arrinsurenceDocUrls,
                                                    obj.getString("extended_waranty_document"),
                                                    obj.getString("photo"), "1000",
                                                    obj.getString("tag_expiration_date"),
                                                    obj.getString("insurance_expiration_date"));

                                            vehicleinfoList.add(vehicleInfo);
                                        }
                                        adapter = new VehicleListAdapter(VehicleListActivity.this,R.layout.activity_cars_list, vehicleinfoList);
                                        list.setAdapter(adapter);
                                    }

                                } else {
                                    Toast.makeText(VehicleListActivity.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("error>>>", "" + error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", strUserId);
                    params.put("dealer_code", Constants.DEALER_CODE);
                    Log.v("params33>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(VehicleListActivity.this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}