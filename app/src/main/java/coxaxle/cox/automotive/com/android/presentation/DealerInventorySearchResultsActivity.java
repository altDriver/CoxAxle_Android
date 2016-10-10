package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
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
import coxaxle.cox.automotive.com.android.model.DealerInventoryCar;

/**
 * Created by Lakshman on 07-09-2016.
 */
public class DealerInventorySearchResultsActivity extends Activity{

    ListView list;
    String strUserId;
    Typeface fontBoldHelvetica;
    TextView txtTitle;
    ImageView imgSearchFilters;
    ArrayList<DealerInventoryCar> objArrDealerList;
    int flag = 0;
    String strMake="", strModel="", strTrim="", strEngine="", strYear="", strColor="", strBodystyle="", strSearchName="";
    //ImageView imgSetFavorite;
    private ProgressDialog pdialog;
    int pageValue = 1, total_count;
    boolean loadingMore = false;
    DealerSearchInventoryResultsAdapter objAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dealer_inventory);
        list=(ListView)findViewById(R.id.dealer_inventory_list);
        imgSearchFilters = (ImageView) findViewById(R.id.dealer_inventory_menu_icon);
        txtTitle = (TextView) findViewById(R.id.DealerInventory_Title_tv);
        fontBoldHelvetica = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");
        txtTitle.setTypeface(fontBoldHelvetica);

        pdialog = new ProgressDialog(DealerInventorySearchResultsActivity.this);
        pdialog.setMessage("Loading...");
        //pdialog.show();
        UserSessionManager obj = new UserSessionManager(this);
        strUserId = obj.getUserId();

        Bundle data = this.getIntent().getExtras();
        if(data!=null) {
            flag = data.getInt("Flag");
            if(flag == 1) {
                HashMap<String, String> searchValues = (HashMap<String, String>) data.getSerializable("SearchValues");
                strMake = searchValues.get("vehicle_make");
                strModel = searchValues.get("vehicle_model");
                strEngine = searchValues.get("vehicle_engine");
                strYear = searchValues.get("vehicle_year");
                strBodystyle = searchValues.get("vehicle_bodystyle");
                strSearchName = searchValues.get("search_name");
            }
        }
        objArrDealerList = new ArrayList<>();
        getDealerListOfVehicles();
        imgSearchFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DealerInventorySearchResultsActivity.this, DealerInventorySearchFiltersActivity.class);
                startActivity(intent);
            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                final int lastItem = firstVisibleItem + visibleItemCount;
                if((lastItem == totalItemCount)&& !(loadingMore)){
                    if(total_count > totalItemCount) {
                        pageValue = pageValue+1;
                        getDealerListOfVehicles();
                    }
                }

            }
        });

    }

    private void getDealerListOfVehicles() {
        pdialog.show();
        loadingMore = true;
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
                                        total_count = jsonobjListVehiclesResponse.getInt("total_count");

                                        objAdapter = new DealerSearchInventoryResultsAdapter(DealerInventorySearchResultsActivity.this, objArrDealerList);
                                        list.setAdapter(objAdapter);//DealerSearchInventoryResultsAdapter(DealerInventorySearchResultsActivity.this, objArrDealerList)
                                        loadingMore = false;
                                        if(objArrDealerList.size()>9)
                                            list.setSelection(objArrDealerList.size() - 10);
                                    }
                                    pdialog.dismiss();

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
                    params.put("make", strMake);
                    params.put("model", strModel);
                    params.put("year", strYear);
                    params.put("trim",strTrim);
                    params.put("style", strBodystyle);
                    params.put("engineGroup", strEngine);
                    params.put("search_name", strSearchName);
                    params.put("color", strColor);
                    params.put("dealer_code", Constants.DEALER_CODE);
                    params.put("page", String.valueOf(pageValue));

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

    public class DealerSearchInventoryResultsAdapter extends BaseAdapter {

        ArrayList<DealerInventoryCar> DealerVehiclesList;
        ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();
        Context context;

        public DealerSearchInventoryResultsAdapter(Context ctx, ArrayList<DealerInventoryCar> vehicleList) {
            DealerVehiclesList = new ArrayList<>();
            this.DealerVehiclesList = vehicleList;
            this.context = ctx;

        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (imageLoader == null)
                imageLoader = AxleApplication.getInstance().getImageLoader();
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)parent.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.activity_dealer_inventory_list_row, null);
            }
            NetworkImageView imgCar = (NetworkImageView)convertView.findViewById(R.id.dealer_inventory_list_image);
            TextView txtCarName  = (TextView) convertView.findViewById(R.id.dealer_inventory_list_name);
            TextView txtCarmiles = (TextView) convertView.findViewById(R.id.dealer_inventory_list_miles);
            TextView txtCarprice = (TextView) convertView.findViewById(R.id.dealer_inventory_list_doller);
            TextView txtCarType = (TextView) convertView.findViewById(R.id.dealer_inventory_list_showtext);
            final ImageView imgSetFavorite = (ImageView) convertView.findViewById(R.id.dealer_inventory_list_wish_image);

            DealerInventoryCar objVehicle = this.DealerVehiclesList.get(position);
            String imageUrl = objVehicle.strImageUrl;
            if(imageUrl != null )
            {
                imageUrl.replace("\\", "");
                imgCar.setDefaultImageResId(R.mipmap.placeholder);
                imgCar.setImageUrl(imageUrl, imageLoader);
            }
            else
                imgCar.setDefaultImageResId(R.mipmap.placeholder);
            txtCarName.setText(objVehicle.strYear +" "+ objVehicle.strMake);
            txtCarmiles.setText(objVehicle.strMillage+" Miles • "+ objVehicle.strBodyStyle_Name);
            txtCarprice.setText("$"+objVehicle.strDerivedPrice);
            txtCarType.setText(objVehicle.strListingType);
            String likeflag = objVehicle.like;
            if(likeflag.equals("0")) {
                imgSetFavorite.setImageResource(R.mipmap.wish_icon);
                //imgSetFavorite.setTag(R.mipmap.wish_icon);
            }
            else {
                imgSetFavorite.setImageResource(R.mipmap.wish_fill);
                //imgSetFavorite.setTag(R.mipmap.wish_fill);
            }
            imgSetFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdialog.show();
                    DealerInventoryCar objCar = DealerVehiclesList.get(position);
                    setFavoriteDealerCar(objCar.strListing_id);
                    String tag = objCar.like;

                    if(tag.equals("0"))
                        objCar.like = "1";
                    else
                        objCar.like = "0";
                    DealerVehiclesList.remove(position);
                    DealerVehiclesList.add(position, objCar);

                    notifyDataSetChanged();

                    //View child = parent.getChildAt(position);
                    //ImageView imgFavorite = (ImageView) child.findViewById(R.id.dealer_inventory_list_wish_image);

                    /*int tag = (int) imgSetFavorite.getTag();
                    if(tag == R.mipmap.wish_icon){
                        //Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.mipmap.wish_fill);
                        //imgSetFavorite.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.wish_fill, null));
                        imgSetFavorite.setImageResource(R.mipmap.wish_fill);
                        //imgSetFavorite.setImageBitmap(bitmap);
                        imgSetFavorite.setTag(R.mipmap.wish_fill);
                    }else{
                        //Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.mipmap.wish_icon);
                        //imgSetFavorite.setImageBitmap(bitmap);
                        imgSetFavorite.setImageResource(R.mipmap.wish_icon);
                        //imgSetFavorite.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.mipmap.wish_icon, null));
                        imgSetFavorite.setTag(R.mipmap.wish_icon);
                    }*/

                }
            });
            notifyDataSetChanged();
            return convertView;
        }

        @Override
        public int getCount() {
            return DealerVehiclesList.size();
        }

        @Override
        public Object getItem(int position) {
            return DealerVehiclesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return DealerVehiclesList.indexOf(getItem(position));
        }
    }

    public void setFavoriteDealerCar(final String strListingId)
    {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_SET_FAVORITE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String strStatus = jsonResponse.getString("status");
                                //String strMessage = jsonResponse.getString("message");
                                if (strStatus.equals("True"))
                                {
                                    JSONObject obj = jsonResponse.getJSONObject("response");
                                }
                                pdialog.dismiss();

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
                    params.put("listing_id", strListingId);
                    params.put("dealer_code", Constants.DEALER_CODE);

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
