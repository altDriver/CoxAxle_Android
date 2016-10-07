package coxaxle.cox.automotive.com.android.presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.CustomDialogSaveSearch;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.DealerInventoryCar;
import coxaxle.cox.automotive.com.android.model.EngineGroupsInfo;
import coxaxle.cox.automotive.com.android.model.MakesInfo;
import coxaxle.cox.automotive.com.android.model.ModelsInfo;
import coxaxle.cox.automotive.com.android.model.TrimsInfo;
import coxaxle.cox.automotive.com.android.model.VehicleColorInfo;
import coxaxle.cox.automotive.com.android.twothumbseekbar.RangeSeekBar;
import coxaxle.cox.automotive.com.android.twothumbseekbar.RangeSeekBarwithSymbol;

public class DealerInventorySearchFiltersActivity extends AppCompatActivity implements View.OnClickListener, CustomDialogSaveSearch.onSubmitListener {
    Spinner makeSpinner, modelSpinner, yearSpinner;
    RangeSeekBarwithSymbol price;
    RangeSeekBar mileage;
    AlertDialog alert;
    //ArrayList<MakesInfo> makesInfoArrayList;
    //ArrayList<ModelsInfo> modelsInfoArrayList;
    //ArrayList<TrimsInfo> trimsInfoArrayList;
    // ArrayList<EngineGroupsInfo> engineGroupInfoArrayList;
    //ArrayList<VehicleColorInfo> bodyColorList;
    List<String> modelYearArrayList, makeArraylist, modelArraylist;
    ArrayList<String> bodyStyleArrayList,engineTypeArrayList, colorArrayList, colorHexArrayList;
    String strMake="", strModel="", strTrim="", strEngine="", strYear="", strColor="", strBodystyle="",
    strSearchName="",strMinPrice="",strMaxPrice="",strMinMileage="",strMaxMileage="";

    Typeface fontBoldHelvetica, fontNormalHelvetica;
    TextView  moreOprtionsTv, tvBodyStyleHeader, tvEngineHeader, tvColorHeader, tvSelectedColor, tvSelectedEngine, tvSelectedBodyStyle, tvSelectedColorHex;
    RelativeLayout moreSearchOptionsLayout;
    ImageView moreOptionsImge;
    //Button recently_searched;
    //RangeSeekBar seekBarPrice;
    TextView txtShowResults, txtHeader, txtSaveSearch;
    TextView rlShowResults;
    ProgressDialog pdialog;
    //AxleApplication axleApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dealer_inventory_search_filters);
        getSupportActionBar().hide();

        //axleApplication = (AxleApplication) getApplicationContext();

        fontNormalHelvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        /*makesInfoArrayList = new ArrayList<>();
        modelsInfoArrayList = new ArrayList<>();
        engineGroupInfoArrayList = new ArrayList<>();
        bodyColorList = new ArrayList<>();*/
        modelYearArrayList = new ArrayList<>();
        modelYearArrayList.add("Year");
        makeArraylist = new ArrayList<>();
        makeArraylist.add("Make");

        bodyStyleArrayList = new ArrayList<>();
        engineTypeArrayList = new ArrayList<>();
        colorArrayList = new ArrayList<>();
        colorHexArrayList = new ArrayList<>();
        pdialog = new ProgressDialog(DealerInventorySearchFiltersActivity.this);
        pdialog.setMessage("Loading...");
        pdialog.show();
        getDealerInventoryFilters();

        loadViews();
    }


    void loadViews() {

        makeSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_make_spinner);
        modelSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_model_spinner);
        yearSpinner = (Spinner) findViewById(R.id.dealer_inventory_search_filters_model_year_spinner);
        price = (RangeSeekBarwithSymbol) findViewById(R.id.dealer_inventory_search_filters_price_seek_bar);
        mileage = (RangeSeekBar) findViewById(R.id.dealer_inventory_search_filters_milage_seek_bar);
        price.setOnRangeSeekBarChangeListener(new RangeSeekBarwithSymbol.OnRangeSeekBarChangeListener<Float>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBarwithSymbol<Float> bar, Float minValue, Float maxValue) {
                strMinPrice = minValue.toString();
                strMaxPrice = maxValue.toString();
                Log.v("minp and maxp>>>>", strMinPrice + ", MAX=" + strMaxPrice);
            }
        });

        mileage.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<Integer> bar, Integer minValue, Integer maxValue) {
                strMinMileage = minValue.toString();
                strMaxMileage = maxValue.toString();
                Log.v("min and max>>>>", strMinMileage + ", MAX=" + strMaxMileage);
            }
        });

        tvColorHeader    = (TextView)findViewById(R.id.dealer_inventory_search_filters_color_header_tv);
        tvSelectedColor  = (TextView)findViewById(R.id.dealer_inventory_search_filters_selected_color_tv);
        tvSelectedColorHex  = (TextView)findViewById(R.id.dealer_inventory_search_filters_selected_color_look_tv);
        tvBodyStyleHeader    = (TextView)findViewById(R.id.dealer_inventory_search_filters_body_style_header_tv);
        tvSelectedBodyStyle  = (TextView)findViewById(R.id.dealer_inventory_search_filters_selected_body_type_tv);
        tvEngineHeader    = (TextView)findViewById(R.id.dealer_inventory_search_filters_engine_type_header_tv);
        tvSelectedEngine  = (TextView)findViewById(R.id.dealer_inventory_search_filters_selected_engine_type_tv);
        //recently_searched = (Button) findViewById(R.id.recently_searched);

        moreOprtionsTv = (TextView)findViewById(R.id.dealer_inventory_search_filters_select_more_options_tv);
        moreSearchOptionsLayout = (RelativeLayout)findViewById (R.id.dealer_inventory_search_filters_select_more_options_layout);
        moreSearchOptionsLayout.setVisibility(View.GONE);
        moreOptionsImge = (ImageView)findViewById(R.id.dealer_inventory_search_filters_select_more_options_iv);

        //seekBarPrice = (RangeSeekBar) findViewById(R.id.dealer_inventory_search_filters_price_seek_bar);
        //txtShowResults = (TextView) findViewById(R.id.dealer_inventory_search_filters_show_vehicles_btn);
        rlShowResults = (TextView) findViewById(R.id.rl_searchfilters_footer);
        txtHeader = (TextView) findViewById(R.id.SearchFilters_Headrname);
        txtHeader.setTypeface(fontBoldHelvetica);
        txtSaveSearch = (TextView) findViewById(R.id.SearchFilters_saveSearch);

        moreOprtionsTv.setOnClickListener(this);
        tvColorHeader.setOnClickListener(this);
        tvBodyStyleHeader.setOnClickListener(this);
        tvEngineHeader.setOnClickListener(this);

        rlShowResults.setOnClickListener(this);
        txtSaveSearch.setOnClickListener(this);
        //recently_searched.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == moreOprtionsTv)
        {
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
        if(view ==tvColorHeader)
        {
            AlertDialogViewForColor(colorArrayList, colorHexArrayList, "Select Color");
        }
        if(view ==tvBodyStyleHeader)
        {
            AlertDialogView(bodyStyleArrayList, "Select Body Style", 1);
        }
        if(view ==tvEngineHeader)
        {
            AlertDialogView(engineTypeArrayList, "Select Engine Type", 2);
        }
        if(view == rlShowResults)
        {
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            HashMap<String, String> searchValues = LoadValues();
            searchValues.put("search_name", strSearchName );
            searchValues.put("current_date_time", currentDateTimeString );

            if(searchValues.get("vehicle_model").equals("")&&searchValues.get("vehicle_make").equals("")){

            }else {
                if (AxleApplication.recentSerches.size() < 10) {
                    AxleApplication.recentSerches.add(0,searchValues);
                } else {
                    AxleApplication.recentSerches.remove(9);
                    AxleApplication.recentSerches.add(0,searchValues);
                }
            }
            Intent intent = new Intent(DealerInventorySearchFiltersActivity.this, DealerInventorySearchResultsActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("SearchValues", searchValues);
            extras.putInt("Flag", 1);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }
        if(view == txtSaveSearch)
        {
            FragmentManager fm = getSupportFragmentManager();
            CustomDialogSaveSearch fragmentDialog = new CustomDialogSaveSearch();
            fragmentDialog.mListener = DealerInventorySearchFiltersActivity.this;
            //fragmentDialog.setDialog(R.layout.save_search_dialog, DealerInventorySearchFiltersActivity.this, 1, "", "", "", "");
            fragmentDialog.show(fm, "");
        }
       /* if(view == recently_searched)
        {
            Intent intent = new Intent(DealerInventorySearchFiltersActivity.this, RecentSearchActivity.class);
            //Bundle extras = new Bundle();
            startActivity(intent);
        }*/
    }

    /*public void setOnSubmitListener(int flag) {

        HashMap<String, String> searchValues = LoadValues();
        Intent intent = new Intent(DealerInventorySearchFiltersActivity.this, SavedSearchActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("SearchValues", searchValues);
        extras.putInt("Flag", 1);
        intent.putExtras(extras);
        startActivity(intent);
    }*/

    @Override
    public void setOnSubmitListener(String strSearchText) {
        HashMap<String, String> searchValues = LoadValues();
        strSearchName = strSearchText;
        searchValues.put("search_name", strSearchText );
        pdialog.show();
        saveDealerListOfVehicles();
    }

    private void saveDealerListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_SAVE_SEARCH_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");

                                pdialog.dismiss();
                                Intent intent = new Intent(DealerInventorySearchFiltersActivity.this, SavedSearchActivity.class);
                                startActivity(intent);
                                finish();

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
                    UserSessionManager obj = new UserSessionManager(DealerInventorySearchFiltersActivity.this);
                    String strUserId = obj.getUserId();
                    params.put("uid", strUserId);
                    params.put("make", strMake);
                    params.put("model", strModel);
                    params.put("year", strYear);
                    params.put("trim",strTrim);
                    params.put("style", strBodystyle);
                    params.put("engineGroup", strEngine);
                    params.put("search_name", strSearchName);

                    Log.v("SearchFiltersReq>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(DealerInventorySearchFiltersActivity.this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, String> LoadValues()
    {
        if(makeSpinner.getSelectedItemPosition() != 0) {
            strMake = makeSpinner.getSelectedItem().toString();
            if(modelSpinner.getSelectedItemPosition() != 0) {
                strModel = modelSpinner.getSelectedItem().toString();
            }
        }

        if(yearSpinner.getSelectedItemPosition() != 0) {
            strYear = yearSpinner.getSelectedItem().toString();
        }
            /*if(tvSelectedColor.getText() != null)
            {
                strColor = tvSelectedColor.getText().toString();
            }*/
        if(tvSelectedEngine.getText() != null)
        {
            strEngine = tvSelectedEngine.getText().toString();
        }
        if(tvSelectedBodyStyle.getText() != null)
        {
            strBodystyle = tvSelectedBodyStyle.getText().toString();
        }
        HashMap<String, String> searchValues = new HashMap<String, String>();
        searchValues.put("vehicle_color", strColor);
        searchValues.put("vehicle_bodystyle", strBodystyle);
        searchValues.put("vehicle_engine", strEngine);
        searchValues.put("vehicle_make", strMake);
        searchValues.put("vehicle_model", strModel);
        searchValues.put("vehicle_year", strYear);
        searchValues.put("vehicle_minprice", strMinPrice);
        searchValues.put("vehicle_maxprice", strMaxPrice);
        searchValues.put("vehicle_minmileage", strMinMileage);
        searchValues.put("vehicle_maxmileage", strMaxMileage);
        return searchValues;
    }


    void getDealerInventoryFilters() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.INVENTORY_SEARCH_FILTERS_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJsonAndInitFliters(response);
                        //Log.d("SearchFilters", response);
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
                            String strMake = jsonMakeObject.getString("name");
                            makeArraylist.add(strMake);

                            /*JSONArray modelArrayObject = jsonMakeObject.optJSONArray("models");

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

                            makesInfoArrayList.add(makesInfoObject);*/
                        }
                        setMakeAdapter(jsonMakesArray);
                    }

                    JSONArray jsonengineGroupsArray = jsonDataObject.optJSONArray("engineGroups");
                    for (int eng = 0; eng < jsonengineGroupsArray.length(); eng++) {
                        JSONObject jsonEngineGroup = jsonengineGroupsArray.getJSONObject(eng);
                        String strEngineType = jsonEngineGroup.getString("name");
                        engineTypeArrayList.add(strEngineType);
                        //EngineGroupsInfo engineGroupsInfoObj = new EngineGroupsInfo(jsonEngineGroup.getString("code"), jsonEngineGroup.getString("displayOrder"),
                                //jsonEngineGroup.getString("name"), jsonEngineGroup.getString("shortName"));
                        //engineGroupInfoArrayList.add(engineGroupsInfoObj);
                    }

                    JSONArray jsonyearsArray = jsonDataObject.optJSONArray("years");
                    for (int yrs = 0; yrs < jsonyearsArray.length(); yrs++) {
                        modelYearArrayList.add(jsonyearsArray.get(yrs).toString());
                    }
                    setYearAdapter();

                    JSONArray jsonBodyStyleArray = jsonDataObject.optJSONArray("styles");
                    for (int stl = 0; stl < jsonBodyStyleArray.length(); stl++) {
                        JSONObject jsonBodyStyleObj = jsonBodyStyleArray.getJSONObject(stl);
                        bodyStyleArrayList.add(jsonBodyStyleObj.getString("name"));
                    }

                    JSONArray jsonBodyColorArray = jsonDataObject.optJSONArray("extColorGroups");
                    for (int clr = 0; clr < jsonBodyColorArray.length(); clr++) {
                        JSONObject jsonBodyColorObj = jsonBodyColorArray.getJSONObject(clr);
                        String strColor = jsonBodyColorObj.getString("name");
                        String strHexvalue = "#"+jsonBodyColorObj.getString("rgbHex");
                        colorArrayList.add(strColor);
                        colorHexArrayList.add(strHexvalue);
                        //bodyColorList.add(new VehicleColorInfo(jsonBodyColorObj.getString("name"),jsonBodyColorObj.getString("rgbHex")));
                    }
                    pdialog.dismiss();
                }

            } else {
                Toast.makeText(DealerInventorySearchFiltersActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }

    }


    private void AlertDialogView(final ArrayList<String> items, String title, final int position) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
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
                    public void onClick(DialogInterface dialog, int item) {
                        if(position == 1)
                            tvSelectedBodyStyle.setText(items.get(item));
                        if(position == 2)
                            tvSelectedEngine.setText(items.get(item));
                        //if(position == 3)
                            //etSelectYear.setText(items[item]);
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void AlertDialogViewForColor(final ArrayList<String> color, final ArrayList<String> hexvalue,String title) {
        ColorAdapter objAdapter = new ColorAdapter(color, hexvalue);
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
        builder.setAdapter(objAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                tvSelectedColor.setText(color.get(i));
                tvSelectedColorHex.setBackgroundColor(Color.parseColor(hexvalue.get(i)));
                alert.dismiss();
            }
        });
        alert = builder.create();
        alert.show();
    }

    void setYearAdapter() {
        //yearSpinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, modelYearArrayList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        yearSpinner.setAdapter(dataAdapter);
        yearSpinner.setSelection(0);
        //dataAdapter.notifyDataSetChanged();
    }
    void setMakeAdapter(final JSONArray jsonArrObj) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, makeArraylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setAdapter(dataAdapter);
        makeSpinner.setSelection(0);
        //dataAdapter.notifyDataSetChanged();

        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(i ==0)
                        modelSpinner.setAdapter(null);
                    else {
                        modelArraylist = new ArrayList<>();
                        modelArraylist.add("Model");
                        String selectedMake = String.valueOf(makeSpinner.getSelectedItem());
                        JSONObject jsonModelObj = jsonArrObj.getJSONObject(i - 1);
                        JSONArray objArray = jsonModelObj.getJSONArray("models");
                        for (int model = 0; model < objArray.length(); model++) {
                            JSONObject obj = objArray.getJSONObject(model);
                            String strModel = obj.getString("name");
                            modelArraylist.add(strModel);
                        }
                        setModelAdapter();//jsonModelArrrayObj
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                modelSpinner.setAdapter(null);
            }
        });
    }
    void setModelAdapter()
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, modelArraylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }




    public class ColorAdapter extends BaseAdapter
    {
        ArrayList<String> objColor, objHex;
        public ColorAdapter(ArrayList<String> color, ArrayList<String> hex)
        {
            this.objColor = color;
            this.objHex = hex;
        }

        @Override
        public int getCount() {
            return objColor.size();
        }

        @Override
        public Object getItem(int i) {
            return objColor.get(i);
        }

        @Override
        public long getItemId(int i) {
            return objColor.indexOf(getItem(i));
        }

        @Override
        public View getView( final int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater vi = (LayoutInflater)viewGroup.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.customdialog_color, null);
            }

            TextView txtColorName = (TextView) view.findViewById(R.id.colordialog_Color);
            TextView txtColorHex = (TextView) view.findViewById(R.id.colordialog_hexValue);

            RadioButton rbSelect = (RadioButton) view.findViewById(R.id.radioButton_ColorSelect);
            rbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    tvSelectedColor.setText(objColor.get(position));
                    tvSelectedColorHex.setBackgroundColor(Color.parseColor(objHex.get(position)));
                    alert.dismiss();
                }
            });
            txtColorName.setText(objColor.get(position));
            txtColorHex.setBackgroundColor(Color.parseColor(objHex.get(position)));
            notifyDataSetChanged();
            return view;
        }
    }

}
