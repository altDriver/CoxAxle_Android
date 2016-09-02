package coxaxle.cox.automotive.com.coxaxle.presentation;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.common.UserSessionManager;
import coxaxle.cox.automotive.com.coxaxle.model.Constants;

public class ScheduleAppointmentActivity extends AppCompatActivity {

    WebView xtimeWebView;
    ProgressBar progress;

    HashMap<String, String> userData;// = getUserDetails();
    UserSessionManager mUserSessionManager;

    String strFirstName, strLastName, strEmail, strPhoneNumber, strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_appointment);
        mUserSessionManager = new UserSessionManager(this);

        userData = mUserSessionManager.getUserDetails();

        strFirstName = userData.get(UserSessionManager.KEY_FIRSTNAME);
        strLastName = userData.get(UserSessionManager.KEY_LASTNAME);
        strEmail = userData.get(UserSessionManager.KEY_EMAIL);
        strPhoneNumber = userData.get(UserSessionManager.KEY_PHONENUMBER);
        //strUserId = userData.get(UserSessionManager.KEY_USERID);
       /* String url = "https://consumer-ptr1.xtime.com/scheduling/?webKey=HUS20131206112630208569&VIN=&Provider=COAXEL&Keyword=" +
                "SCHEDULE&cfn=JOAN&cln=SMITH&cpn=6785551212cem=JSMITH@FAKEMAIL." +
                "COM&NOTE=NOTE4Q3&extid=SCHEDULE&extctxt=COAXLE";*/

        String url = "https://consumer-ptr1.xtime.com/scheduling/?webKey=hus20131206112630208569" +
                "&VIN=5J6RE3H74AL049448" +
                "&Provider=COXAXLE" +
                "&Keyword=SCHEDULE" +
                "&cfn="+strFirstName +
                "&cln="+strLastName+
                "&cpn="+strPhoneNumber+
                "&cem="+strEmail+
                "&NOTE=NOTE4Q3" +
                "&extid=SCHEDULE" +
                "&extctxt=COXAXLE" +
                "&dest=VEHICLE";

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        xtimeWebView = (WebView) findViewById(R.id.schedule_appointment_webview);


        xtimeWebView.getSettings().setLoadsImagesAutomatically(true);
        //xtimeWebView.getSettings().setDomStorageEnabled(true);
        xtimeWebView.getSettings().setJavaScriptEnabled(true);
        xtimeWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //xtimeWebView.clearCache(true);
        xtimeWebView.setWebViewClient(new XTimeBrowser());
        xtimeWebView.loadUrl(url);
    }

    /*  }
  });
}*/
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



    void getUrlAndPassToWebView(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJsonAndNavToHomeScreen(response);
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

                    /*Log.d("Request", requestParams.get("email"));
                    Log.d("Request", requestParams.get("password"));*/
              /*  params.put("email", requestParams.get("email"));
                params.put("password", requestParams.get("password"));*/


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    void parseResponceAndOpenWebview(String responce){



    }
}
