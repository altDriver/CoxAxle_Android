package coxaxle.cox.automotive.com.coxaxle.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
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

import coxaxle.cox.automotive.com.coxaxle.HomeScreen;
import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.common.AxleApplication;
import coxaxle.cox.automotive.com.coxaxle.common.UserSessionManager;
import coxaxle.cox.automotive.com.coxaxle.common.Utility;
import coxaxle.cox.automotive.com.coxaxle.common.WsRequest;
import coxaxle.cox.automotive.com.coxaxle.model.Constants;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

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
    HashMap<String, String> requestParams;
    UserSessionManager mUserSessionManager;
    boolean isrememberUser;
    HashMap<String, String> userDetails;

    //CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isrememberUser = false;
        requestParams = new HashMap<String, String>();
        axleApplication = (AxleApplication) getApplicationContext();
        mUserSessionManager = new UserSessionManager(this);

        //rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        request = new WsRequest();

        if (mUserSessionManager.isRememberMeChecked()) {
            //rememberMeCheckBox.setChecked(true);
            isrememberUser = true;
            //userDetails = mUserSessionManager.getEmailId();
            mEmailView.setText(mUserSessionManager.getEmailId());
        }

        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestParams.put("email", mEmailView.getText().toString());
                String base64 = "";
                try {
                    byte[] data = mPasswordView.getText().toString().getBytes("UTF-8");
                    base64 = Base64.encodeToString(data, Base64.DEFAULT);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                requestParams.put("password", base64);
                //isrememberUser = rememberMeCheckBox.isChecked();
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        /*TextView mUserRegistrationTxt = (TextView) findViewById(R.id.register_user_action_text);
        mUserRegistrationTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callUserRegistrationActivity();
            }
        });


        TextView mGuestUserActionTxt = (TextView) findViewById(R.id.guest_user_action_text);
        mGuestUserActionTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
            }
        });*/

    }

    private void callUserRegistrationActivity() {

        Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        startActivity(intent);
        //finish();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        //AxleApplication
        if (axleApplication.isNetworkAvailable()) {

           wsLogin();

        } else {
            Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
        }
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
                params.put("email", requestParams.get("email"));
                params.put("password", requestParams.get("password"));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void parseLoginResponceAndNavToHomeScreen(String jsonObj) {

        boolean isSuccess = false;

        Log.d("responce 1", jsonObj.trim());

        try {

            JSONObject mainresponceObject = new JSONObject(jsonObj);
            //JSONArray dataArray = mainObject.getJSONArray("data");
            String responceMessage = mainresponceObject.getString("message");

            JSONObject  jsonDataObject = new JSONObject(mainresponceObject.getString("response"));

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonDataArray = jsonDataObject.optJSONArray("data");

            JSONObject jsonObject = jsonDataArray.getJSONObject(0);
            //JSONObject jsonObject = responceArray.getJSONObject(0);

            isSuccess = Boolean.parseBoolean(mainresponceObject.getString("status").toLowerCase());
            //Log.d("responce 2", isSuccess + "====" + mainresponceObject);

            if (isSuccess) {
                mUserSessionManager.createUserLoginSession( Integer.toString(jsonObject.getInt("uid")),jsonObject.getString("email"),isrememberUser);
               /* if (rememberMeCheckBox.isChecked()) {
                   // mUserSessionManager.createUserLoginSession("", "", true);
                }*/

               /* try {
                    JSONObject jsonResponse = new JSONObject(response);

                    String strStatus = jsonResponse.getString("status");
                    String strMessage = jsonResponse.getString("message");
                    if (strStatus.equals("True")) {
                        JSONObject jsonData = jsonResponse.getJSONObject("response");
                        JSONArray jsonArr = jsonData.getJSONArray("data");

                        JSONObject jsonobj = jsonArr.getJSONObject(0);*/

                        String strToken = jsonObject.getString("token");
                        String strUid = jsonObject.getString("uid");
                        Utility.setPreferenceValue(LoginActivity.this, Utility.STR_TOKEN, strToken);
                        Utility.setPreferenceValue(LoginActivity.this, Utility.STR_USERID, strUid);
                        //tvVehicleName.setText(jsonResponse.getString("name"));
                   /* }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }*/

                Toast.makeText(LoginActivity.this, responceMessage, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    void forGotPasswordRequest() {
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


                    /*Log.d("Request", requestParams.get("email"));
                    Log.d("Request", requestParams.get("password"));*/
                params.put("email", requestParams.get("email"));
                params.put("password", requestParams.get("password"));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}