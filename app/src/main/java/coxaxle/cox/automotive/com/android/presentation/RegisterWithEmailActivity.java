package coxaxle.cox.automotive.com.android.presentation;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;

public class RegisterWithEmailActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etPassword, etEmail, etPhoneNumber;
    Button btnSignUpEmail;
    String strFirstName, strLastName, strPassword, strEmail, strPhoneNumber;

    String deviceType, osVersion;

    WebView termsConditionsWebView;
    RelativeLayout webview_layout;
    ScrollView email_signup_scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_with_email);
        getSupportActionBar().hide();
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        loadViews();


    }

    void loadViews() {

        deviceType = android.os.Build.MANUFACTURER+" "+ android.os.Build.MODEL ;
        osVersion = android.os.Build.VERSION.RELEASE+"("+android.os.Build.VERSION.SDK_INT+")";

        etFirstName = (EditText) findViewById(R.id.email_signup_first_name_edt);
        etLastName = (EditText) findViewById(R.id.email_signup_last_name_edt);
        etPassword = (EditText) findViewById(R.id.email_signup_password_edt);
        etEmail = (EditText) findViewById(R.id.email_signup_email_edt);
        etPhoneNumber = (EditText) findViewById(R.id.email_signup_phone_number_edt);
        btnSignUpEmail = (Button) findViewById(R.id.email_signup_button);

        btnSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValidData()) {

                    try {
                        byte[] data = etPassword.getText().toString().getBytes("UTF-8");
                        strPassword = Base64.encodeToString(data, Base64.DEFAULT);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SendUserRegDetails();
                }
            }
        });

        email_signup_scrollView = (ScrollView)findViewById(R.id.email_signup_scrollView);
        webview_layout = (RelativeLayout)findViewById(R.id.signUp_terms_conditions_webview_layout);
        termsConditionsWebView = (WebView)findViewById(R.id.signUp_terms_conditions_webview);
        termsConditionsWebView.getSettings().setLoadsImagesAutomatically(true);
        //xtimeWebView.getSettings().setDomStorageEnabled(true);
        termsConditionsWebView.getSettings().setJavaScriptEnabled(true);
        termsConditionsWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //xtimeWebView.clearCache(true);
        termsConditionsWebView.setWebViewClient(new TermsConditionsBrowserView());
        termsConditionsWebView.loadUrl("http://getaxle.com/terms");
        webview_layout.setVisibility(View.GONE);

        CheckBox terms_conditions_checkbox = (CheckBox)findViewById(R.id.signUp_terms_conditions_checkbox);
        TextView terms_conditions_checkbox_text = (TextView)findViewById(R.id.signUp_terms_conditions_checkbox_text);
        //terms_conditions_checkbox

        terms_conditions_checkbox_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview_layout.setVisibility(View.VISIBLE);
                email_signup_scrollView.setVisibility(View.GONE);
            }
        });




    }


    boolean isValidData() {
        //boolean valid = false;
        boolean cancel = false;
        View focusView = null;

        strFirstName = etFirstName.getText().toString();
        strLastName = etLastName.getText().toString();
        strPassword = etPassword.getText().toString();
        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhoneNumber.getText().toString();
        //Boolean valid_password = Utility.passwordValidation(strPassword);


        // Check for a valid password, if the user entered one.
        if (!Utility.passwordValidation(strPassword)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(strEmail)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!Utility.emailValidate(strEmail)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (strPhoneNumber.length()<10) {
            etPhoneNumber.setError("Enter a valid phone number");
            focusView = etPhoneNumber;
            cancel = true;
        }

        if (TextUtils.isEmpty(strLastName)) {
            etLastName.setError(getString(R.string.error_field_required));
            focusView = etLastName;
            cancel = true;
        }

        if (TextUtils.isEmpty(strFirstName)) {
            etFirstName.setError(getString(R.string.error_field_required));
            focusView = etFirstName;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        }

        return cancel;
    }

    private void SendUserRegDetails() {
        final ProgressDialog dialog = ProgressDialog.show(RegisterWithEmailActivity.this, "",
                "Sending Data,   Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismiss the progress dialog
                        dialog.dismiss();
                        parseJsonAndNavigateToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Dismiss the progress dialog
                        dialog.dismiss();
                        Toast.makeText(RegisterWithEmailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.v("error>>>", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                // strFirstName, strLastName, strPassword, strEmail, strPhoneNumber;

                params.put("first_name",strFirstName);
                params.put("last_name",strLastName);
                params.put("password",strPassword);
                params.put("email", strEmail);
                params.put("phone", strPhoneNumber);
                params.put("dealer_code","KH001");
                params.put("device_token", "");
                params.put("device_type", deviceType);
                params.put("os_version", osVersion);
                params.put("dealer_code",Constants.DEALER_CODE);

                Log.v("params>>>", "" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterWithEmailActivity.this);
        requestQueue.add(stringRequest);

    }


    void parseJsonAndNavigateToHomeScreen(String response) {

        boolean isSuccess = false;
        String responceMessage = "";

        Log.d("Responce>>", response);
        try {
            JSONObject mainObject = new JSONObject(response);
            String strStatus = mainObject.getString("status");
            isSuccess = Boolean.parseBoolean(strStatus.toLowerCase());
            responceMessage = mainObject.getString("message");
            if (isSuccess) {
                JSONObject jsonData = mainObject.getJSONObject("response");
                JSONArray jsonArr = jsonData.getJSONArray("data");
                JSONObject jsonobj = jsonArr.getJSONObject(0);
                String strUid = jsonobj.getString("uid");

                HashMap<String, String> userDetails = new HashMap<String, String>();
                userDetails.put(UserSessionManager.KEY_FIRSTNAME, jsonobj.getString("first_name"));
                userDetails.put(UserSessionManager.KEY_LASTNAME, jsonobj.getString("last_name"));
                userDetails.put(UserSessionManager.KEY_EMAIL, jsonobj.getString("email"));
                userDetails.put(UserSessionManager.KEY_PHONENUMBER, jsonobj.getString("phone"));
                userDetails.put(UserSessionManager.KEY_USERID, strUid);
                userDetails.put(UserSessionManager.IS_USER_LOGGED_IN, isSuccess+"");

                UserSessionManager objManager = new UserSessionManager(this);
                objManager.saveUserDetailsPref(userDetails);

                Intent intent = new Intent(RegisterWithEmailActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(RegisterWithEmailActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    class TermsConditionsBrowserView extends WebViewClient {

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
           /* progress.setVisibility(View.GONE);
            RegisterWithEmailActivity.this.progress.setProgress(100);*/
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
           /* progress.setVisibility(View.VISIBLE);
            RegisterWithEmailActivity.this.progress.setProgress(0);*/
            super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onBackPressed() {

        if( email_signup_scrollView.getVisibility() == View.GONE){
            webview_layout.setVisibility(View.GONE);
            email_signup_scrollView.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
    }
}
