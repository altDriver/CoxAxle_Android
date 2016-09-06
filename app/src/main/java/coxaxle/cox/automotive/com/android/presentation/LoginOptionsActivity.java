package coxaxle.cox.automotive.com.android.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.model.Constants;

public class LoginOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        setContentView(R.layout.activity_login_options);

        ImageButton navToLoginActivityBtn = (ImageButton)findViewById(R.id.imageButton_login);

        ImageButton navToHomeActivityBtn = (ImageButton)findViewById(R.id.imageButton_Guest);

        TextView navToRegistrationActivityTxt = (TextView)findViewById(R.id.nav_to_registration_action_txt);


        navToRegistrationActivityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginOptionsActivity.this, RegisterWithEmailActivity.class);
                startActivity(intent);
            }
        });

        navToLoginActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginOptionsActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });


        navToHomeActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //wsLogin();
                Intent intent = new Intent(LoginOptionsActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();

            }
        });
    }

    void wsLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJsonAndNavToHomeScreen(response);
                        parseJsonAndNavToHomeScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginOptionsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", "0");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void parseJsonAndNavToHomeScreen(String jsonObj) {
        boolean isSuccess = false;

        try {

            JSONObject mainObject = new JSONObject(jsonObj);
            //JSONArray dataArray = mainObject.getJSONArray("data");
            String responceMessage = mainObject.getString("message");

            isSuccess = Boolean.parseBoolean(mainObject.getString("status").toLowerCase());

            Log.d("responce", isSuccess + "====" + responceMessage);
            if (isSuccess) {
                Intent intent = new Intent(LoginOptionsActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginOptionsActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
