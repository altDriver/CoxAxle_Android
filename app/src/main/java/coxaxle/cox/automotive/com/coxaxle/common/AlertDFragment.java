package coxaxle.cox.automotive.com.coxaxle.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lakshmana on 16-08-2016.
 */
public class AlertDFragment extends DialogFragment {
    protected double latitude,longitude, destination_longitude, destination_latitude;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Go to Google maps")
                .setMessage("Are you sure want go to Google maps?")
                .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Geocoder coder = new Geocoder(getContext());
                        try {
                            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName("Cyber Towers, Hyderabad, Telangana", 1);
                            for(Address add : adresses){
                                if(adresses.size()>0) {
                                    destination_longitude = add.getLongitude();
                                    destination_latitude = add.getLatitude();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        GPSTracker tracker = new GPSTracker(getContext());
                        if (!tracker.canGetLocation()) {
                            tracker.showSettingsAlert();
                        } else {
                            latitude = tracker.getLatitude();
                            longitude = tracker.getLongitude();
                        }

                        //String uri = "http://maps.google.com/maps?saddr=" + latitude + ","+ longitude +"&daddr=+destination_latitude+","+destination_longitude;
                        //String uri = "http://maps.google.com/maps?saddr=17.4635067,78.3422077&daddr="+destination_latitude+","+destination_longitude;
                        String uri = "http://maps.google.com/maps?saddr=34.1605214,-84.179311,17&daddr=33.9156235,-84.3432829";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

}
