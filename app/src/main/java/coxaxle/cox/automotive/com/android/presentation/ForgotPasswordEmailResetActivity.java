package coxaxle.cox.automotive.com.android.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;

public class ForgotPasswordEmailResetActivity extends AppCompatActivity {
    String strEmail;
    EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_email_reset);

        etEmail = (EditText) findViewById(R.id.forgot_password_email_edt);

        Button btnFrogotPasswordSubmit = (Button) findViewById(R.id.forgot_password_email_reset_btn);

        btnFrogotPasswordSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                strEmail = etEmail.getText().toString();

                if(validEmail()){

                    resetPasswordRequest();

                }

            }
        });


    }

    boolean validEmail(){
boolean valid = true;
        // Check for a valid email address.
        if (TextUtils.isEmpty(strEmail)) {
            etEmail.setError(getString(R.string.error_field_required));
            //focusView = etEmail;
            valid = false;
        } else if (!Utility.emailValidate(strEmail)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            //focusView = etEmail;
            valid = false;
        }
        return valid;
    }

    void resetPasswordRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FORGOT_PASSWORD_RESET_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseResponceNavTOLoginScreen(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ForgotPasswordEmailResetActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", strEmail);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    void parseResponceNavTOLoginScreen(String responce) {

        boolean isSuccess = false;

        Log.d("responce 1", responce.trim());

        try {

            JSONObject mainresponceObject = new JSONObject(responce);

            String responceMessage = mainresponceObject.getString("message");

            isSuccess = Boolean.parseBoolean(mainresponceObject.getString("status").toLowerCase());

            Toast.makeText(ForgotPasswordEmailResetActivity.this, responceMessage, Toast.LENGTH_LONG).show();

            if (isSuccess) {
                //JSONObject jsonDataObject = new JSONObject(mainresponceObject.getString("response"));

                Intent navToLoginIntent = new Intent(ForgotPasswordEmailResetActivity.this,LoginActivity.class);

                startActivity(navToLoginIntent);
                finish();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
