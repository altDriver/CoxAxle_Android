package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.DealerSearchInventoryResultsAdapter;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Srinivas B on 07-09-2016.
 */
public class DealerInventorySearchResultsActivity extends Activity{

    ListView list;
    private DealerSearchInventoryResultsAdapter adapter= null;
    ArrayList<VehicleInfo> vehicleinfoList ;
    String strUserId;
    ArrayList<VehicleInfo> vehicleInfoList;
    Context ctx;

    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_inventory);
        list=(ListView)findViewById(R.id.dealer_inventory_list);
        vehicleinfoList = new ArrayList<>();
        UserSessionManager obj = new UserSessionManager(this);
        strUserId = obj.getUserId();
        getListOfVehicles();
        ctx = this;
        //Array list of vehicles
        vehicleInfoList = new ArrayList<>();

        /*title= (TextView)findViewById(R.id.dealer_inventory_title_text);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DealerInventorySearchResultsActivity.this,CarsListActivity.class);
                intent.putParcelableArrayListExtra("vehicleList", vehicleInfoList);
                startActivity(intent);
            }
        });*/
    }

    private void getListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GARAGE_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equals("True")) {
                                    JSONObject jsonData = jsonobjListVehiclesResponse.getJSONObject("response");
                                    JSONArray jsonArray = jsonData.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);

                                        ArrayList<String> arrImageUrls = new ArrayList<>();
                                        JSONArray objUrls= obj.getJSONArray("vechicle_image");
                                        for(int j = 0; j<objUrls.length(); j++)
                                        {
                                            JSONObject objImagePath = objUrls.getJSONObject(j);
                                            String strpath = objImagePath.getString("image_url");
                                            if(strpath.length() !=0)
                                                arrImageUrls.add(strpath);
                                        }

                                        VehicleInfo VehicleInfo = new VehicleInfo(obj.getString("id"),obj.getString("date_entered"),obj.getString("date_modified"),obj.getString("deleted"),obj.getString("name"),
                                                obj.getString("user_id"),obj.getString("dealer_id"),obj.getString("vin"),obj.getString("vehicle_type"),
                                                obj.getString("make"),obj.getString("model"),obj.getString("year"),obj.getString("color"),obj.getString("mileage"),
                                                obj.getString("style"),obj.getString("trim"),obj.getString("tag_renewal_date"),obj.getString("waranty_from"),obj.getString("waranty_to"),
                                                obj.getString("extended_waranty_from"),obj.getString("extended_waranty_to"),obj.getString("kbb_price"),obj.getString("manual"),obj.getString("loan_amount"),
                                                obj.getString("emi"),obj.getString("interest"),obj.getString("loan_tenure"),obj.getString("insurance_document"),obj.getString("extended_waranty_document"),
                                                obj.getString("insurance_expiration_date"),obj.getString("tag_expiration_date"),arrImageUrls);
                                        vehicleInfoList.add(VehicleInfo);
                                        //create an ArrayAdaptar from the String Array
                                        adapter = new DealerSearchInventoryResultsAdapter(ctx,R.layout.activity_dealer_inventory_list_row, vehicleInfoList);
                                        // Assign adapter to ListView
                                        list.setAdapter(adapter);

                                    }
                                } else {
                                    Toast.makeText(DealerInventorySearchResultsActivity.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.v("response44>>>", "" + response);
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
                    //params.put("token", strToken);

                    Log.v("params333>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(DealerInventorySearchResultsActivity.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
