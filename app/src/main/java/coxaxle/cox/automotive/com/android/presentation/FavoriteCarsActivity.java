package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 * Created by Lakshmana on 29-09-2016.
 */
public class FavoriteCarsActivity extends Activity {
    ListView list;
    String strUserId;
    TextView txtTitle;
    ArrayList<DealerInventoryCar> objArrDealerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dealer_inventory);
        list=(ListView) findViewById(R.id.dealer_inventory_list);
        txtTitle = (TextView) findViewById(R.id.DealerInventory_Title_tv);
        txtTitle.setText("Favorites");
        UserSessionManager obj = new UserSessionManager(FavoriteCarsActivity.this);
        strUserId = obj.getUserId();
        getFavouriteListOfVehicles();
    }
    private void getFavouriteListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INVENTORY_FAVOURITE_LIST_URL,
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
                                    objArrDealerList = new ArrayList<>();
                                    for(int i =0; i< jsonArray.length(); i++)
                                    {
                                        JSONObject objJson = jsonArray.getJSONObject(i);
                                        DealerInventoryCar obj = new DealerInventoryCar();
                                        obj.strYear = objJson.getString("year");
                                        obj.strDerivedPrice = objJson.getString("price");
                                        obj.strImageUrl = objJson.getString("image_url");
                                        obj.strListingType = objJson.getString("vehicle_type");
                                        obj.strMake = objJson.getString("vehicle_name");
                                        obj.strMillage = objJson.getString("miles");
                                        //JSONArray arrayjsonBodystyle = objJson.getJSONArray("bodyStyles");
                                        //JSONObject objBodyStyle = arrayjsonBodystyle.getJSONObject(0);
                                        obj.strBodyStyle_Name = objJson.getString("sub_model");
                                        objArrDealerList.add(obj);
                                    }

                                    list.setAdapter(new DealerFavoriteAdapter(FavoriteCarsActivity.this, objArrDealerList));


                                } else {
                                    Toast.makeText(FavoriteCarsActivity.this, strMessage, Toast.LENGTH_LONG).show();
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

                    Log.v("params333>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(FavoriteCarsActivity.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class DealerFavoriteAdapter extends BaseAdapter {

        ArrayList<DealerInventoryCar> DealerVehiclesList;
        ImageLoader imageLoader = AxleApplication.getInstance().getImageLoader();
        Context context;

        public DealerFavoriteAdapter(Context ctx, ArrayList<DealerInventoryCar> vehicleList) {
            DealerVehiclesList = new ArrayList<>();
            this.DealerVehiclesList = vehicleList;
            this.context = ctx;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
            ImageView imgSetFavorite = (ImageView) convertView.findViewById(R.id.dealer_inventory_list_wish_image);
            imgSetFavorite.setImageResource(R.mipmap.wish_fill);
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
}
