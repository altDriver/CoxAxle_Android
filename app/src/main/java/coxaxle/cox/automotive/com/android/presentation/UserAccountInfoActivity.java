package coxaxle.cox.automotive.com.android.presentation;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Hashtable;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;

public class UserAccountInfoActivity extends AppCompatActivity {

    UserSessionManager usermanager;

    String userId, language, firstName, lastName, email, phoneNumber, zipcode;
    EditText language_edt, firstName_edt, lastName_edt, email_edt, phoneNumber_edt, zipcode_edt;
    Button edit_or__update_user_btn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_info);
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        usermanager = new UserSessionManager(this);
        loadViews();
        userId = usermanager.getUserId();
        getUserDetails();
        //updateUserDetails();
    }


    void loadViews() {

        firstName_edt = (EditText) findViewById(R.id.settings_user_first_name);
        lastName_edt = (EditText) findViewById(R.id.settings_user_first_name);
        email_edt = (EditText) findViewById(R.id.settings_user_first_name);
        language_edt = (EditText) findViewById(R.id.settings_user_first_name);
        phoneNumber_edt = (EditText) findViewById(R.id.settings_user_first_name);
        zipcode_edt = (EditText) findViewById(R.id.settings_user_first_name);

        disableEditText(language_edt);
        disableEditText(firstName_edt);
        disableEditText(lastName_edt);
        disableEditText(email_edt);
        disableEditText(phoneNumber_edt);
        disableEditText(zipcode_edt);

        edit_or__update_user_btn = (Button) findViewById(R.id.settings_edit_update_button);

        edit_or__update_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edit_or__update_user_btn.getText().equals("Edit")){
                    enableEditText(language_edt);
                    enableEditText(firstName_edt);
                    enableEditText(lastName_edt);
                    enableEditText(email_edt);
                    enableEditText(phoneNumber_edt);
                    enableEditText(zipcode_edt);
                    edit_or__update_user_btn.setText("Update");
                }else{
                    disableEditText(language_edt);
                    disableEditText(firstName_edt);
                    disableEditText(lastName_edt);
                    disableEditText(email_edt);
                    disableEditText(phoneNumber_edt);
                    disableEditText(zipcode_edt);
                    updateUserDetails();
                    edit_or__update_user_btn.setText("Edit");
                }
            }
        });
    }


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setInputType(InputType.TYPE_NULL);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setInputType(InputType.TYPE_NULL);
        //editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }


    void getUserDetails() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GET_ACCOUNT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responce) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(UserAccountInfoActivity.this, responce, Toast.LENGTH_LONG).show();
                        Log.d("responce ==========", responce);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(UserAccountInfoActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put("uid", userId);

                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    void updateUserDetails() {

        //final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_ACCOUNT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responce) {
                        //Disimissing the progress dialog
                        // loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(UserAccountInfoActivity.this, responce, Toast.LENGTH_LONG).show();

                        Log.d("responce ==========", responce);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();
                        Log.d("onErrorResponse", volleyError.toString());
                        //Showing toast
                        Toast.makeText(UserAccountInfoActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //params.put("uid",usermanager.getUserId());
                params.put("uid", "81");
                params.put("language", "en_US");
                params.put("email", "kishor.thorata@vensaiinc.com");
                params.put("first_name", "kishor");
                params.put("last_name", "thorata");
                params.put("phone", "9700964530");
                params.put("dealer_code", "KH001");
                Log.d("getParams()", params.toString());
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    void parseResponce(String responce) {
        try {
            JSONObject mainresponceObject = new JSONObject(responce);
            //JSONArray dataArray = mainObject.getJSONArray("data");
            String responceMessage = mainresponceObject.getString("message");
            JSONArray responceArray = mainresponceObject.getJSONArray("response");
            JSONObject jsonDataObject = new JSONObject(mainresponceObject.getString("response"));

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonDataArray = jsonDataObject.optJSONArray("data");

            JSONObject jsonObject = jsonDataArray.getJSONObject(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
