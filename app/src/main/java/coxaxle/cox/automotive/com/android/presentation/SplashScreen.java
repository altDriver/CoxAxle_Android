package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.DealerInfo;

/**
 */
public class SplashScreen extends Activity {
    private AnimationDrawable animationDrawable;
    private ImageView mProgressBar;
    UserSessionManager mUserSessionManager;
    String strDealerLogoUrl;
    AxleApplication axleApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        axleApplication = (AxleApplication) getApplicationContext();
        mUserSessionManager = new UserSessionManager(this);
        getDealerInfo();
        /*mProgressBar =(ImageView) findViewById(R.id.main_progress);
        mProgressBar.setBackgroundResource(R.drawable.progress_wheel_animation);
        animationDrawable = (AnimationDrawable)mProgressBar.getBackground();
        mProgressBar.setVisibility(View.VISIBLE);
        animationDrawable.start();*/

        /*WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);*/

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    if (mUserSessionManager.isUserLoggedIn()) {
                        Intent intent = new Intent(SplashScreen.this, HomeScreenActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                        //intent.putExtra("DealerLogo", strDealerLogoUrl);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
    }

    private void getDealerInfo() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Dealer Info",response.toString());

                            DealerInfo objDealerInfo;
                            try {
                                JSONObject dealerInfoResponse = new JSONObject(response);

                                String strStatus = dealerInfoResponse.getString("status");
                                String strMessage = dealerInfoResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonData = dealerInfoResponse.getJSONObject("response");
                                    ArrayList<String> arrImageUrls = new ArrayList<>();
                                    JSONArray jsonBanners = jsonData.getJSONArray("banner_image");
                                    for (int i = 0; i< jsonBanners.length(); i++)
                                    {
                                        JSONObject obj = jsonBanners.getJSONObject(i);
                                        String imageUrl = obj.getString("banner");
                                        arrImageUrls.add(imageUrl);
                                    }

                                    objDealerInfo = new DealerInfo(jsonData.getString("name"),jsonData.getString("phone"),
                                            jsonData.getString("address"),jsonData.getString("dealer_code"),jsonData.getString("email"),
                                            jsonData.getString("dealer_twiter_page_link"),jsonData.getString("dealer_fb_page_link"),jsonData.getString("main_contact_number"),
                                            jsonData.getString("sale_contact"),jsonData.getString("service_desk_contact"),jsonData.getString("collision_desk_contact"),
                                            jsonData.getString("dealer_logo"),"",jsonData.getString("privacy_policy"),arrImageUrls);

                                    strDealerLogoUrl = jsonData.getString("dealer_logo");
                                    axleApplication.dealerLogoUrl = strDealerLogoUrl;

                                    AxleApplication.dealerInfo.add(objDealerInfo);

                                } else {
                                    Toast.makeText(SplashScreen.this, strMessage, Toast.LENGTH_LONG).show();
                                }
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
                    params.put("dealer_code", "1000");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
