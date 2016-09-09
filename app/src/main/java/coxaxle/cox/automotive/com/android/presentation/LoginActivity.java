package coxaxle.cox.automotive.com.android.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.NoInternetDialogFragment;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.common.WsRequest;
import coxaxle.cox.automotive.com.android.model.Constants;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    WsRequest request;
    AxleApplication axleApplication;
    //HashMap<String, String> requestParams;
    UserSessionManager mUserSessionManager;
    //boolean isrememberUser;
    HashMap<String, String> userDetails;
    String strEmail, strPassword;
    String base64Password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        //isrememberUser = false;
        //requestParams = new HashMap<String, String>();
        axleApplication = (AxleApplication) getApplicationContext();
        mUserSessionManager = new UserSessionManager(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        request = new WsRequest();

        mPasswordView = (EditText) findViewById(R.id.password);

        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateUserDetails()){
                    attemptLogin();
                }

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        TextView mGuestUserActionTxt = (TextView) findViewById(R.id.login_forgot_password_tv);
        mGuestUserActionTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordEmailResetActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        //AxleApplication
        if (axleApplication.isNetworkAvailable()) {

            try {
                byte[] data = strPassword.getBytes("UTF-8");
                base64Password = Base64.encodeToString(data, Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            wsLogin();

        } else {
            NoInternetDialogFragment dialogFragment = new NoInternetDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    boolean validateUserDetails() {
        boolean valid = true;
        strEmail = mEmailView.getText().toString();
        strPassword = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(strEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            //focusView = etEmail;
            valid = false;
        } else if (!Utility.emailValidate(strEmail)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            //focusView = etEmail;
            valid = false;
        }
        if (TextUtils.isEmpty(strPassword) && !Utility.passwordValidation(strPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            valid = false;
        }


        return valid;

    }


    void wsLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJsonAndNavToHomeScreen(response);
                        parseLoginResponceAndNavToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                    /*Log.d("Request", requestParams.get("email"));
                    Log.d("Request", requestParams.get("password"));*/
                params.put("email", strEmail);
                params.put("password", base64Password);


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

    void parseLoginResponceAndNavToHomeScreen(String jsonObj) {

        boolean isSuccess = false;

        Log.d("responce 1", jsonObj.trim());

        try {

            JSONObject mainresponceObject = new JSONObject(jsonObj);

            String responceMessage = mainresponceObject.getString("message");

            isSuccess = Boolean.parseBoolean(mainresponceObject.getString("status").toLowerCase());

            if (isSuccess) {
                JSONObject jsonDataObject = new JSONObject(mainresponceObject.getString("response"));
                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonDataArray = jsonDataObject.optJSONArray("data");

                JSONObject jsonObject = jsonDataArray.getJSONObject(0);

                HashMap<String, String> userDetails = new HashMap<String, String>();
                userDetails.put(UserSessionManager.KEY_FIRSTNAME, jsonObject.getString("first_name"));
                userDetails.put(UserSessionManager.KEY_LASTNAME, jsonObject.getString("last_name"));
                userDetails.put(UserSessionManager.KEY_EMAIL, jsonObject.getString("email"));
                userDetails.put(UserSessionManager.KEY_PHONENUMBER, jsonObject.getString("phone"));
                userDetails.put(UserSessionManager.KEY_USERID, jsonObject.getString("uid"));
                userDetails.put(UserSessionManager.IS_USER_LOGGED_IN, isSuccess + "");

                UserSessionManager objManager = new UserSessionManager(this);
                objManager.saveUserDetailsPref(userDetails);

                Toast.makeText(LoginActivity.this, responceMessage, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /*void forGotPasswordRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FORGOT_PASSWORD_RESET_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJsonAndNavToHomeScreen(response);
                        //parseJsonAndNavToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                    *//*Log.d("Request", requestParams.get("email"));
                    Log.d("Request", requestParams.get("password"));*//*
                params.put("email", requestParams.get("email"));
                params.put("password", requestParams.get("password"));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }*/


}