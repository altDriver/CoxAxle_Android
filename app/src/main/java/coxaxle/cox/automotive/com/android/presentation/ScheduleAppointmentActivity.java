package coxaxle.cox.automotive.com.android.presentation;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

public class ScheduleAppointmentActivity extends AppCompatActivity {

    WebView xtimeWebView;
    ProgressBar progress;

    HashMap<String, String> userData;
    UserSessionManager mUserSessionManager;

    String strFirstName, strLastName, strEmail, strPhoneNumber, strVIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);

        getVehicleDetails();


        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        xtimeWebView = (WebView) findViewById(R.id.schedule_appointment_webview);


        xtimeWebView.getSettings().setLoadsImagesAutomatically(true);
        //xtimeWebView.getSettings().setDomStorageEnabled(true);
        xtimeWebView.getSettings().setJavaScriptEnabled(true);
        xtimeWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //xtimeWebView.clearCache(true);
        xtimeWebView.setWebViewClient(new XTimeBrowser());
        getUrlAndPassToWebView();

    }

    /*  }
  });
}*/
    void getVehicleDetails() {

        VehicleInfo objVehicle = this.getIntent().getParcelableExtra("VehicleInfo");
        strVIN = objVehicle.vehicle_vin;

        mUserSessionManager = new UserSessionManager(this);
        userData = mUserSessionManager.getUserDetails();

        strFirstName = userData.get(UserSessionManager.KEY_FIRSTNAME);
        strLastName = userData.get(UserSessionManager.KEY_LASTNAME);
        strEmail = userData.get(UserSessionManager.KEY_EMAIL);
        strPhoneNumber = userData.get(UserSessionManager.KEY_PHONENUMBER);

    }

    class XTimeBrowser extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

       /* @TargetApi(Build.VERSION_CODES.N)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest webResourceRequest) {
            WebResourceResponse response = super.shouldInterceptRequest(view, webResourceRequest);
            String url = webResourceRequest.getUrl().toString();
            return tryToInterceptRequests(view, url, response);
        }*/


        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            ScheduleAppointmentActivity.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            ScheduleAppointmentActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }


    void getUrlAndPassToWebView() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.XTIME_INFO_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponceAndOpenWebview(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScheduleAppointmentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    void parseResponceAndOpenWebview(String responce) {

        boolean isSuccess = false;

        Log.d("responce 1", responce.trim());
        String strWebKey = "",
                strProvider = "", strKeyword = "", strExtid = "", strExtctxt = "";
        try {

            JSONObject mainresponceObject = new JSONObject(responce);

            String responceMessage = mainresponceObject.getString("message");

            isSuccess = Boolean.parseBoolean(mainresponceObject.getString("status").toLowerCase());

            if (isSuccess) {

                JSONObject jsonDataObject = new JSONObject(mainresponceObject.getString("response"));

                strWebKey = jsonDataObject.getString("WebKey");
                strProvider = jsonDataObject.getString("Provider");
                strKeyword = jsonDataObject.getString("Keyword");
                strExtid = jsonDataObject.getString("Extid");
                strExtctxt = jsonDataObject.getString("Extctxt");

                String url = "https://consumer-ptr1.xtime.com/scheduling/" +

                        "?webKey=" +
                        strWebKey +
                        "&VIN=" +
                        strVIN +
                        "&Provider=" +
                        strProvider +
                        "&Keyword=" +
                        strKeyword +
                        "&cfn=" + strFirstName +
                        "&cln=" + strLastName +
                        "&cpn=" + strPhoneNumber +
                        "&cem=" + strEmail +
                        "&NOTE=NOTE4Q3" +
                        "&extid=" +
                        strExtid +
                        "&extctxt=" +
                        strExtctxt +
                        "&dest=" +
                        "VEHICLE";

                xtimeWebView.loadUrl(url);

                Log.d("ScheduleURL","---"+url);

            } else {
                Toast.makeText(ScheduleAppointmentActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
