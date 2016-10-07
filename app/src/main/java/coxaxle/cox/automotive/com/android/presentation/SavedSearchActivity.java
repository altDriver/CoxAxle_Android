package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.DealerInventoryCar;
import coxaxle.cox.automotive.com.android.presentation.HomeScreen;

/**
 * Created by Lakshmana on 21-09-2016.
 */

public class SavedSearchActivity extends Activity {
    ListView list;
    TextView txtAddNewCars, txtHeader;
    ArrayList<DealerInventoryCar> objArrayList;
    String strUid;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);
        list=(ListView)findViewById(R.id.list);
        txtAddNewCars = (TextView) findViewById(R.id.MyCars_AddNewCar);
        txtAddNewCars.setVisibility(View.GONE);
        txtHeader = (TextView) findViewById(R.id.vehicle_details_name);
        txtHeader.setText("Saved Searches");

        UserSessionManager obj = new UserSessionManager(SavedSearchActivity.this);
        strUid = obj.getUserId();
        pdialog = new ProgressDialog(SavedSearchActivity.this);
        pdialog.setMessage("Loading...");
        pdialog.show();
        getSavedSearchVehicles();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DealerInventoryCar objdata = objArrayList.get(i);
                HashMap<String, String> searchValues = new HashMap<String, String>();
                searchValues.put("vehicle_bodystyle", "");
                searchValues.put("vehicle_engine", "");
                searchValues.put("vehicle_make", "");
                searchValues.put("vehicle_model", "");
                searchValues.put("vehicle_year", "");
                searchValues.put("search_name", objdata.strBodyStyle_Name );
                Intent intent = new Intent(SavedSearchActivity.this, DealerInventorySearchResultsActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("SearchValues", searchValues);
                extras.putInt("Flag", 1);
                intent.putExtras(extras);
                startActivity(intent);
                SavedSearchActivity.this.finish();

            }
        });
    }

    public class SavedSearchCarsAdapter extends BaseAdapter
    {
        //int count = 10;
        private Context context;
        ArrayList<DealerInventoryCar> objCar;
        public SavedSearchCarsAdapter(Context ctx, ArrayList<DealerInventoryCar> objCars)
        {
            this.objCar = objCars;
            this.context = ctx;
        }

        @Override
        public int getCount() {
            return objCar.size();
        }

        @Override
        public Object getItem(int i) {
            return objCar.get(i);
        }

        @Override
        public long getItemId(int i) {
            return objCar.indexOf(getItem(i));
        }

        @Override
        public View getView(final int i, View view, final ViewGroup viewGroup) {
            try {
                if (view == null) {
                    LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = vi.inflate(R.layout.savedsearch_listrow, null);
                }
                TextView txtCarName = (TextView) view.findViewById(R.id.SavedSearch_CarName_tv);
                TextView txtdate = (TextView) view.findViewById(R.id.SavedSearch_DateTime_tv);
                ImageView deleteSavedCar = (ImageView) view.findViewById(R.id.SavedSearch_DeleteRow_iv);
                DealerInventoryCar obj = this.objCar.get(i);
                txtCarName.setText(obj.strBodyStyle_Name);
                String strDate = obj.strYear;
                //String date = "yyyy-mm-dd hh:mm:ss";
                StringTokenizer tk = new StringTokenizer(strDate);

                String date = tk.nextToken();  // <---  yyyy-mm-dd
                String time = tk.nextToken();  // <---  hh:mm:ss
                //String result = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
                //String s = "12:18:00";
                DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = f1.parse(time);
                DateFormat f2 = new SimpleDateFormat("hh:mm a");
                String time2 = f2.format(d).toUpperCase();
                txtdate.setText("Saved "+ date + "\n" + time2);
                deleteSavedCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Integer index = (Integer) v.getTag();
                        //viewGroup.removeViewAt(i);
                        DealerInventoryCar objCarDelete = objArrayList.get(i);
                        String strSearchName = objCarDelete.strBodyStyle_Name;
                        pdialog.show();
                        deleteSavedSearchVehicle(strSearchName);
                        objArrayList.remove(i);
                        notifyDataSetChanged();
                    }
                });
                notifyDataSetChanged();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return view;
        }
    }

    private void getSavedSearchVehicles()
    {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_SAVED_SEARCH_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String strStatus = jsonResponse.getString("status");
                                String strMessage = jsonResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonObject = jsonResponse.getJSONObject("response");
                                    JSONArray objArray = jsonObject.getJSONArray("data");
                                    objArrayList = new ArrayList<>();
                                    for (int i =0; i<objArray.length(); i++)
                                    {
                                        JSONObject obj = objArray.getJSONObject(i);
                                        DealerInventoryCar objCar = new DealerInventoryCar();
                                        objCar.strBodyStyle_Name = obj.getString("name");
                                        objCar.strYear = obj.getString("date");
                                        objArrayList.add(objCar);
                                    }
                                    SavedSearchCarsAdapter adapter = new SavedSearchCarsAdapter(SavedSearchActivity.this, objArrayList);
                                    list.setAdapter(adapter);
                                    pdialog.dismiss();
                                }

                                Toast.makeText(SavedSearchActivity.this, strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SavedSearchActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", strUid);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SavedSearchActivity.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSavedSearchVehicle(final String strSearchname)
    {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_SAVED_SEARCH_DELETE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String strStatus = jsonResponse.getString("status");
                                String strMessage = jsonResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    pdialog.dismiss();
                                }

                                Toast.makeText(SavedSearchActivity.this, strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SavedSearchActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", strUid);
                    params.put("dealer_code", Constants.DEALER_CODE);
                    params.put("search_name", strSearchname);

                    Log.v("params>>>", "" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SavedSearchActivity.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
