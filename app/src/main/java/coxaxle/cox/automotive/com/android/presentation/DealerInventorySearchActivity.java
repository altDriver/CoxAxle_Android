package coxaxle.cox.automotive.com.android.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.MakesInfo;
import coxaxle.cox.automotive.com.android.model.ModelsInfo;

public class DealerInventorySearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_inventory_search);


    }

    void getDealerInventoryFilters() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.INVENTORY_SEARCH_FILTERS_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJsonAndInitFliters(response);
                        //parseLoginResponceAndNavToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                    /*Log.d("Request", requestParams.get("email"));
                    Log.d("Request", requestParams.get("password"));*/
            /*params.put("email", requestParams.get("email"));
            params.put("password", requestParams.get("password"));*/


                return params;
            }
        };

        /*stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void parseJsonAndInitFliters(String responce) {

        boolean isSuccess = false;

        //Log.d("responce 1", jsonObj.trim());

        try {

            JSONObject mainresponceObject = new JSONObject(responce);

            String responceMessage = mainresponceObject.getString("message");

            isSuccess = Boolean.parseBoolean(mainresponceObject.getString("status").toLowerCase());

            if (isSuccess) {
                JSONObject jsonDataObject = new JSONObject(mainresponceObject.getString("response"));
                //Get the instance of JSONArray that contains JSONObjects
                //JSONArray jsonDataArray = jsonDataObject.optJSONArray("data");
                JSONArray jsonMakesArray = jsonDataObject.optJSONArray("data");

                if (jsonMakesArray != null) {
                    if (jsonMakesArray.length() > 0) {

                        HashMap<String,ArrayList<MakesInfo>> makesHashMap = new HashMap<>();
                        ArrayList<MakesInfo> makesInfoArrayList = new ArrayList<>();
                        ArrayList<ModelsInfo> modelsInfoArrayList = new ArrayList<>();
                        String modelCode = "";


                        for (int i = 0; i < jsonMakesArray.length(); i++) {

                            JSONObject jsonMakeObject = jsonMakesArray.getJSONObject(i);

                            //JSONArray jsonMakesArray = jsonDataObject.optJSONArray("makes");


                            if(jsonMakeObject.has("")){

                            }
                            //ModelsInfo objModelsInfo = new ModelsInfo();
/*
                            MakesInfo objMakesInfo = new MakesInfo(jsonMakeObject.getString("makes") ,modelsInfoArrayList);

                            makesInfoArrayList.add(objMakesInfo);*/



                        }
                    }
                }


            } else {
                Toast.makeText(DealerInventorySearchActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }

    }
}
