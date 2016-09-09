package coxaxle.cox.automotive.com.android.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etEmail, etCurrentPassword, etNewPassword,etConfirmNewPassword ;

    String strCurrentPassword, strNewPassword, strConfirmNewPassword, strEmail;
    UserSessionManager mUserSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        loadViews();
    }


    void loadViews() {

        etEmail = (EditText) findViewById(R.id.change_password_email_edt);
        etCurrentPassword = (EditText) findViewById(R.id.change_password_current_password_edt);
        etNewPassword = (EditText) findViewById(R.id.change_password_new_password_edt);
        etConfirmNewPassword = (EditText) findViewById(R.id.change_password_confirm_new_password_edt);

        Button btnChangePassword = (Button) findViewById(R.id.change_password_button);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValidData()){

                    changePassword();
                }

            }
        });
    }


    boolean isValidData() {

        boolean valid = false;


        strEmail = etEmail.getText().toString();
        strCurrentPassword = etCurrentPassword.getText().toString();
        strNewPassword = etNewPassword.getText().toString();
        strConfirmNewPassword = etConfirmNewPassword.getText().toString();

        /*strFirstName = etFirstName.getText().toString();
        strLastName = etLastName.getText().toString();
        strPassword = etPassword.getText().toString();
        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhoneNumber.getText().toString();*/
        //Boolean valid_password = Utility.passwordValidation(strPassword);


        // Check for a valid email address.
        if (!TextUtils.isEmpty(strEmail)) {
            etEmail.setError(getString(R.string.error_field_required));
            etEmail = etEmail;
            valid = true;
        } else if (Utility.emailValidate(strEmail)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            //focusView = etEmail;
            valid = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(strCurrentPassword) && !Utility.passwordValidation(strCurrentPassword)) {
            etCurrentPassword.setError(getString(R.string.error_invalid_password));
            //focusView = etPassword;
            valid = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(strConfirmNewPassword) && !Utility.passwordValidation(strConfirmNewPassword)) {
            etCurrentPassword.setError(getString(R.string.error_invalid_password));
            //focusView = etPassword;
            valid = true;
        }


        if (!valid) {
            //focusView.requestFocus();
        }

        if(strNewPassword.equals(strConfirmNewPassword)){
            valid = true;
        }else{
            Toast.makeText(ChangePasswordActivity.this, "New password and confirm password are different", Toast.LENGTH_LONG).show();
        }

        return valid;
    }

    String strBase64NewPassword = "", strBase64CurrentPassword = "";
    String strUserId;
    private void changePassword() {
        mUserSessionManager = new UserSessionManager(this);
        strUserId = mUserSessionManager.getUserId();

        try {
            byte[] currentPasswordData = strCurrentPassword.getBytes("UTF-8");
            strBase64CurrentPassword = Base64.encodeToString(currentPasswordData, Base64.DEFAULT);

            byte[] newPasswordData = strNewPassword.getBytes("UTF-8");
            strBase64NewPassword = Base64.encodeToString(newPasswordData, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final ProgressDialog dialog = ProgressDialog.show(ChangePasswordActivity.this, "",
                "Sending Data,   Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CHANGE_PASSWORD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismiss the progress dialog
                        Log.d("changePassword>>", response);
                        dialog.dismiss();
                        parseJsonAndNavigateToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Dismiss the progress dialog
                        dialog.dismiss();
                        Toast.makeText(ChangePasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.v("error>>>", "" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid", strUserId);
                params.put("old_password", strBase64CurrentPassword);
                params.put("new_password", strBase64NewPassword);
                //uid":"[userid]","old_password":"[old_password]","new_password":"[
                        Log.v("params>>>", "" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
        requestQueue.add(stringRequest);

    }


    void parseJsonAndNavigateToHomeScreen(String response) {

        boolean isSuccess = false;
        String responceMessage = "";

        try {
            JSONObject mainObject = new JSONObject(response);
            String strStatus = mainObject.getString("status");
            isSuccess = Boolean.parseBoolean(strStatus.toLowerCase());
            responceMessage = mainObject.getString("message");
            if (isSuccess) {
                /*JSONObject jsonData = mainObject.getJSONObject("response");
                JSONArray jsonArr = jsonData.getJSONArray("data");
                JSONObject jsonobj = jsonArr.getJSONObject(0);*/
                Intent intent = new Intent(ChangePasswordActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();

            }/*else {

            }*/
            Toast.makeText(ChangePasswordActivity.this, responceMessage, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
