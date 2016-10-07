package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import coxaxle.cox.automotive.com.android.adapters.VehicleListAdapter;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.AddVehicleActivity;
import coxaxle.cox.automotive.com.android.presentation.HomeScreen;
import coxaxle.cox.automotive.com.android.presentation.HomeScreenActivity;
import coxaxle.cox.automotive.com.android.presentation.VehicleDetailsActivity;
import coxaxle.cox.automotive.com.android.presentation.VehicleListActivity;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class VehicleListFragment extends Fragment {
    ListView list;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private VehicleListAdapter adapter;
    View view;
    FragmentTransaction transaction;

    public VehicleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_cars_list, container, false);

        ((HomeScreenActivity) getActivity()).toolbar_title.setText("My Cars");
        ((HomeScreenActivity) getActivity()).toolbar_icon.setBackgroundResource(R.mipmap.plus_image_grey);
        ((HomeScreenActivity) getActivity()).toolbar_notifications_count.setVisibility(View.INVISIBLE);

        HomeScreenActivity activity = (HomeScreenActivity) getActivity();
        vehicleinfoList = activity.getDataList();


        //create an instance of fragment manager
        FragmentManager manager = getFragmentManager();
        //create an instance of Fragment-transaction
        transaction=manager.beginTransaction();
        //bundle=new Bundle();


        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        RelativeLayout rlHeader = (RelativeLayout) view.findViewById(R.id.vehicle_list_header_bar);
        rlHeader.setVisibility(View.GONE);
        list=(ListView)view.findViewById(R.id.list);
        try{
                adapter = new VehicleListAdapter(getActivity(), vehicleinfoList);
                list.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }

        ((HomeScreenActivity) getActivity()).toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = 0;
                if(list.getAdapter()!=null){
                    count = list.getAdapter().getCount();
                }
                if(count == 5){
                    Toast.makeText(getActivity(), "You can't add more than 5 vehicles. Please delete vehicles before adding a vehicle", Toast.LENGTH_SHORT).show();
                }else{
                   /* AddVehicleFragment addvehiclefragment = new AddVehicleFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle bundle=new Bundle();
                    bundle.putInt("Vehicle_Flag",  0);
                    bundle.putString("navToActivity", navToActivity);
                    addvehiclefragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_place, addvehiclefragment).commit();*/
                    Intent addVehicleIntent = new Intent(getActivity(), AddVehicleActivity.class);
                    addVehicleIntent.putExtra("Vehicle_Flag", 0);
                    addVehicleIntent.putExtra("navToActivity", "vehicleDetails");
                    startActivity(addVehicleIntent);
                }

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                VehicleInfo objdata = vehicleinfoList.get(i);
                Intent intentSchedule = new Intent(getActivity(),VehicleDetailsActivity.class);
                intentSchedule.putExtra("VehicleInfo", objdata);
                intentSchedule.putExtra("Flag_Add", 1);
                intentSchedule.putExtra("navToActivity", "vehicleDetails");
                startActivity(intentSchedule);

            }
        });

    }
    public class VehicleListAdapter extends BaseAdapter {

        Context mContext;
        private ArrayList<VehicleInfo> vehicleInfoList;
        com.android.volley.toolbox.ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();

        public VehicleListAdapter(Context context, int activity_cars_list_row, ArrayList<VehicleInfo> vehicleInfoList) {
            this.vehicleInfoList = new ArrayList<VehicleInfo>();
            this.vehicleInfoList.addAll(vehicleInfoList);
        }
        public VehicleListAdapter( Context context, ArrayList<VehicleInfo> vehicleInfoList) {
            this.vehicleInfoList = vehicleInfoList;
            mContext = context;
            //this.vehicleInfoList.addAll(vehicleInfoList);
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
                //final String strImg = arrImageUrls.get(0);
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

                                Toast.makeText(getActivity(), strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error delete vehicle>>>", "" + error);
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    UserSessionManager obj = new UserSessionManager(getActivity());
                    String strUserId = obj.getUserId();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("vid", strId);
                    params.put("uid", strUserId);

                    Log.v("params>>>", "" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(getActivity(),HomeScreenActivity.class);
        startActivity(intent);
    }

}