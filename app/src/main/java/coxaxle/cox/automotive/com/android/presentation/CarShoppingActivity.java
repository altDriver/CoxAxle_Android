package coxaxle.cox.automotive.com.android.presentation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.DealerInventoryCar;

public class CarShoppingActivity extends AppCompatActivity {

    private ImageView calculatorsLearnMoreIv, vehicleValuesLearnMoreIv;
    private RelativeLayout calculatorsHeaderLayout, calculatorsSubLayout,
            vehicleValuesHeaderLayout, vehicleValuesSubLayout;
    //String strMake="", strModel="", strTrim="", strEngine="", strYear="", strColor="", strBodystyle="", strSearchName="";
    private ProgressDialog pdialog;
    DealerInventorySearchResultsActivity.DealerSearchInventoryResultsAdapter objAdapter;
    ArrayList<DealerInventoryCar> objArrDealerList;
    String strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_shopping);
        getSupportActionBar().hide();

        loadViews();


    }

    void loadViews() {
        UserSessionManager obj = new UserSessionManager(this);
        strUserId = obj.getUserId();

        objArrDealerList = new ArrayList<>();
        getDealerListOfVehicles();


        calculatorsHeaderLayout = (RelativeLayout) findViewById(R.id.car_shopping_calculators_header_layout);
        calculatorsSubLayout = (RelativeLayout) findViewById(R.id.car_shopping_calculators_sub_layout);
        vehicleValuesHeaderLayout = (RelativeLayout) findViewById(R.id.car_shopping_vehicle_values_header_layout);
        vehicleValuesSubLayout = (RelativeLayout) findViewById(R.id.car_shopping_vehicle_values_sub_layout);
        calculatorsLearnMoreIv = (ImageView) findViewById(R.id.car_shopping_calculators_iv);
        vehicleValuesLearnMoreIv = (ImageView) findViewById(R.id.car_shopping_vehicle_values_iv);

        calculatorsHeaderLayout.setVisibility(View.GONE);
        calculatorsSubLayout.setVisibility(View.GONE);
        vehicleValuesHeaderLayout.setVisibility(View.GONE);
        vehicleValuesSubLayout.setVisibility(View.GONE);


        calculatorsHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorsSubLayout.getVisibility() == View.VISIBLE) {

                    calculatorsSubLayout.setVisibility(View.GONE);
                    calculatorsLearnMoreIv.setRotation(0);


                } else {
                    calculatorsSubLayout.setVisibility(View.VISIBLE);
                    calculatorsLearnMoreIv.setRotation(90);

                }
            }
        });

        vehicleValuesHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicleValuesSubLayout.getVisibility() == View.VISIBLE) {
                    vehicleValuesSubLayout.setVisibility(View.GONE);
                    vehicleValuesLearnMoreIv.setRotation(0);
                } else {
                    vehicleValuesSubLayout.setVisibility(View.VISIBLE);
                    vehicleValuesLearnMoreIv.setRotation(90);
                }
            }
        });

    }








    private void getDealerListOfVehicles() {
        pdialog.show();

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_SEARCH_ON_AUTOTRADER_URL,
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

                                    if(jsonArray.length()>0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject objJson = jsonArray.getJSONObject(i);
                                            DealerInventoryCar obj = new DealerInventoryCar();
                                            obj.strYear = objJson.getString("year");
                                            obj.strDerivedPrice = objJson.getString("derivedPrice");
                                            obj.strImageUrl = objJson.getString("images");
                                            obj.strListingType = objJson.getString("listingType");
                                            obj.strMake = objJson.getString("make");
                                            obj.strMillage = objJson.getString("mileage");
                                            obj.like = objJson.getString("like");
                                            JSONArray arrayjsonBodystyle = objJson.getJSONArray("bodyStyles");
                                            JSONObject objBodyStyle = arrayjsonBodystyle.getJSONObject(0);
                                            obj.strBodyStyle_Name = objBodyStyle.getString("name");
                                            obj.strListing_id = objJson.getString("listing_id");

                                            objArrDealerList.add(obj);
                                        }

                                        pdialog.dismiss();
                                    }
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
                    params.put("make", "");
                    params.put("model", "");
                    params.put("year", "");
                    params.put("trim","");
                    params.put("style", "");
                    params.put("engineGroup", "");
                    params.put("search_name", "");
                    params.put("color", "");
                    params.put("dealer_code", Constants.DEALER_CODE);
                    params.put("page", "1");

                    Log.v("params333>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(CarShoppingActivity.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
