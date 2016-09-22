package coxaxle.cox.automotive.com.android.presentation;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.EngineGroupsInfo;
import coxaxle.cox.automotive.com.android.model.MakesInfo;
import coxaxle.cox.automotive.com.android.model.ModelsInfo;
import coxaxle.cox.automotive.com.android.model.TrimsInfo;
import coxaxle.cox.automotive.com.android.model.VehicleColorInfo;

public class DealerInventorySearchFiltersActivity extends AppCompatActivity {
    Spinner makeSpinner, modelSpinner, yearSpinner;

    ArrayList<MakesInfo> makesInfoArrayList;
    ArrayList<ModelsInfo> modelsInfoArrayList;
    //ArrayList<TrimsInfo> trimsInfoArrayList;
    ArrayList<EngineGroupsInfo> engineGroupInfoArrayList;
    List<String> modelYearArrayList;
    ArrayList<String> bodyStyle;
    ArrayList<VehicleColorInfo> bodyColorList;
    String strMake, strModel, strTrim, strEngineGroup, strYear;
    Typeface fontBoldHelvetica, fontNormalHelvetica;
    TextView  moreOprtionsTv;
    RelativeLayout moreSearchOptionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dealer_inventory_search_filters);
        fontNormalHelvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        makesInfoArrayList = new ArrayList<>();
        modelsInfoArrayList = new ArrayList<>();
        modelYearArrayList = new ArrayList<>();
        modelYearArrayList.add("Year");
        engineGroupInfoArrayList = new ArrayList<>();
        bodyColorList = new ArrayList<>();
        bodyStyle = new ArrayList<>();
        getDealerInventoryFilters();

        loadViews();
    }


    void loadViews() {

        makeSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_make_spinner);
        modelSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_model_spinner);
        yearSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_model_year_spinner);

       final TextView tvColorHeader    = (TextView)findViewById(R.id.dealer_inventory_search_filters_color_header_tv);
        TextView tvSelectedColor  = (TextView)findViewById(R.id.dealer_inventory_search_filters_selected_color_tv);


        tvColorHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*PopupMenu popupMenu = new PopupMenu(DealerInventorySearchFiltersActivity.this, tvColorHeader);
                //Inflating the Popup using xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(DealerInventorySearchFiltersActivity.this,"Button Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();
                */
                }
            });

        moreOprtionsTv = (TextView)findViewById(R.id.dealer_inventory_search_filters_select_more_options_tv);
        moreSearchOptionsLayout = (RelativeLayout)findViewById (R.id.dealer_inventory_search_filters_select_more_options_layout);
        moreSearchOptionsLayout.setVisibility(View.GONE);

        final ImageView moreOptionsImge = (ImageView)findViewById(R.id.dealer_inventory_search_filters_select_more_options_iv);

        moreOprtionsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moreSearchOptionsLayout.getVisibility() != View.VISIBLE){
                    moreOprtionsTv.setText(getString(R.string.fewer_options));
                    moreSearchOptionsLayout.setVisibility(View.VISIBLE);
                    moreOptionsImge.setBackgroundResource(R.mipmap.minus_blue_btn);

                }else {
                    moreOprtionsTv.setText(getString(R.string.select_more_options));
                    moreSearchOptionsLayout.setVisibility(View.GONE);
                    moreOptionsImge.setBackgroundResource(R.mipmap.addvehicle_btn);


                }
            }
        });



        //modelSpinner.setAdapter(dataAdapter);
        //makeSpinner.setAdapter(dataAdapter);

       /* yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                //Toast.makeText(MainActivity.this, spinnerBackgroundChange.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });*/


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


                if (jsonDataObject != null) {
                    JSONArray jsonMakesArray = jsonDataObject.optJSONArray("makes");

                    if (jsonMakesArray.length() > 0) {

                        for (int mk = 0; mk < jsonMakesArray.length(); mk++) {

                            JSONObject jsonMakeObject = jsonMakesArray.getJSONObject(mk);
                            JSONArray modelArrayObject = jsonMakeObject.optJSONArray("models");

                            ModelsInfo modelInfoObject;

                            Log.d("jsonMakeObject>", "1");

                            modelsInfoArrayList = new ArrayList<>();

                            for (int mo = 0; mo < modelArrayObject.length(); mo++) {

                                JSONObject jsonModelsObject = modelArrayObject.getJSONObject(mo);

                                Log.d("jsonModelsObject>", "2");

                                ArrayList<TrimsInfo> trimsInfoArrayList = new ArrayList<>();

                                if (jsonModelsObject.has("trims")) {

                                    JSONArray trimsArrayObject = jsonModelsObject.optJSONArray("trims");

                                    for (int trm = 0; trm < trimsArrayObject.length(); trm++) {


                                        JSONObject trimObject = trimsArrayObject.getJSONObject(trm);

                                        TrimsInfo trimInfoObject = new TrimsInfo(trimObject.getString("code"), trimObject.getString("name"));

                                        trimsInfoArrayList.add(trimInfoObject);

                                        Log.d("trimObject>>", trimObject.toString());
                                    }
                                }
                                ModelsInfo modelsInfoObject = new ModelsInfo(jsonModelsObject.getString("code"), jsonModelsObject.getString("name"), trimsInfoArrayList);

                                modelsInfoArrayList.add(modelsInfoObject);
                            }

                            MakesInfo makesInfoObject = new MakesInfo(jsonMakeObject.getString("code"), jsonMakeObject.getString("name"), modelsInfoArrayList);

                            makesInfoArrayList.add(makesInfoObject);
                        }
                    }

                    JSONArray jsonengineGroupsArray = jsonDataObject.optJSONArray("engineGroups");

                    for (int eng = 0; eng < jsonengineGroupsArray.length(); eng++) {
                        JSONObject jsonEngineGroup = jsonengineGroupsArray.getJSONObject(eng);

                        EngineGroupsInfo engineGroupsInfoObj = new EngineGroupsInfo(jsonEngineGroup.getString("code"), jsonEngineGroup.getString("displayOrder"),
                                jsonEngineGroup.getString("name"), jsonEngineGroup.getString("shortName"));

                        engineGroupInfoArrayList.add(engineGroupsInfoObj);
                    }

                    JSONArray jsonyearsArray = jsonDataObject.optJSONArray("years");
                    for (int yrs = 0; yrs < jsonyearsArray.length(); yrs++) {
                        modelYearArrayList.add(jsonyearsArray.get(yrs).toString());
                    }

                    setAdapters();

                    JSONArray jsonBodyStyleArray = jsonDataObject.optJSONArray("styles");
                    for (int stl = 0; stl < jsonBodyStyleArray.length(); stl++) {
                        JSONObject jsonBodyStyleObj = jsonBodyStyleArray.getJSONObject(stl);
                        bodyStyle.add(jsonBodyStyleObj.getString("name"));
                    }

                    JSONArray jsonBodyColorArray = jsonDataObject.optJSONArray("extColorGroups");
                    for (int clr = 0; clr < jsonBodyColorArray.length(); clr++) {
                        JSONObject jsonBodyColorObj = jsonBodyColorArray.getJSONObject(clr);
                        bodyColorList.add(new VehicleColorInfo(jsonBodyColorObj.getString("name"),jsonBodyColorObj.getString("rgbHex")));
                    }


                }


            } else {
                Toast.makeText(DealerInventorySearchFiltersActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }

    }





    private void AlertDialogView(final String[] items, String title, String rgbHex, final int position) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                DealerInventorySearchFiltersActivity.this, R.layout.alertdialog_custom_view, items);


        AlertDialog.Builder builder = new AlertDialog.Builder(DealerInventorySearchFiltersActivity.this);
        builder.setTitle(title);
        TextView title_txt = new TextView(this);
        title_txt.setGravity(Gravity.CENTER);
        title_txt.setText(title);
        title_txt.setHeight(150);
        title_txt.setTypeface(fontBoldHelvetica);
        title_txt.setTextColor(ContextCompat.getColor(DealerInventorySearchFiltersActivity.this, R.color.colorTextDark));
        title_txt.setTextSize(17);
        builder.setCustomTitle(title_txt);

        builder.setSingleChoiceItems(adapter, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {/*
                        if(position == 1)
                            etMake.setText(items[item]);
                        if(position == 2)
                            etModel.setText(items[item]);
                        if(position == 3)
                            etSelectYear.setText(items[item]);
                        dialog.dismiss();*/
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void setAdapters() {
        //yearSpinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, modelYearArrayList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        yearSpinner.setAdapter(dataAdapter);
        //dataAdapter.notifyDataSetChanged();
    }
}
