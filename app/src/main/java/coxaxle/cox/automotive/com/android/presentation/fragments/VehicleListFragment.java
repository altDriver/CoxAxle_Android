package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.VehicleListAdapter;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.ScheduleAppointmentActivity;
import coxaxle.cox.automotive.com.android.presentation.VehicleDetailsActivity;

/**
 * Created by Srinivas on 29-08-2016.
 */
public class VehicleListFragment extends Fragment {
    ListView list;
    ArrayList<VehicleInfo> vehicleinfoList ;
    private VehicleListAdapter adapter;
    String strUserId;
    String navToActivity;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_cars_list, null, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        list=(ListView)view.findViewById(R.id.list);

        try{
            vehicleinfoList = getActivity().getIntent().getParcelableArrayListExtra("vehicleList");
            navToActivity = getActivity().getIntent().getStringExtra("navToActivity");

            adapter = new VehicleListAdapter(getActivity(),R.layout.activity_cars_list, vehicleinfoList);

            list.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(navToActivity.equals("ScheduleAppointmentActivity")){
                    VehicleInfo objdata = vehicleinfoList.get(i);
                    Intent intentSchedule = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                    intentSchedule.putExtra("VehicleInfo", objdata);
                    startActivity(intentSchedule);
                    getActivity().finish();
                }else{
                    VehicleInfo objdata = vehicleinfoList.get(i);
                    Intent intentSchedule = new Intent(getActivity(),VehicleDetailsActivity.class);
                    intentSchedule.putExtra("VehicleInfo", objdata);
                    startActivity(intentSchedule);
                }

            }
        });

    }
}