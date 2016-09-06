package coxaxle.cox.automotive.com.android.common;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kishore on 7/27/2016.
 */

public class WsRequest {

    String mResponce;
    Context context;
    boolean success = false;
    //boolean guestUser;

//GET, POST, PUT, DELETE, and PURGE

//    GET    /session/new gets the webpage that has the login form
//    POST   /session authenticates credentials against database
//    DELETE /session destroys session and redirect to /
//    GET  /users/new gets the webpage that has the registration form
//    POST /users records the entered information into database as a new /user/xxx
//    GET  /users/xxx // gets and renders current user data in a profile view
//    POST /users/xxx // updates new information about user

   /* public WsRequest(boolean guestUser){
        this.guestUser = guestUser;
    }*/

   /* WsRequest(){

    }*/

     public String wsPostCall() {


        return mResponce;
    }

    public String wsPutCall() {


        return mResponce;
    }

    private void userLogin() {

    }


    public boolean volleyWSRequest(final HashMap<String, String> requestParams, String url, Context context, final boolean guestUser) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")) {
                                //openProfile();
                                 success = true;
                                Log.d("responce", response.trim().toString());
                            } else {
                                success = false;
                                Log.d("responce", response.trim().toString());
                                // Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    if(guestUser){
                        params.put("user_id", "0");
                    }else{
                        Log.d("Request", requestParams.get("email"));
                        Log.d("Request", requestParams.get("password"));
                        params.put("email", requestParams.get("email"));
                        params.put("password", requestParams.get("password"));
                    }


                   /* params.put("first_name", "vensai01");
                    params.put("last_name", "tech");
                    params.put("password", " ven123");
                    params.put("email", "ven@123.com ");
                    params.put("phone", "565656");
                    params.put("user_type", " ");
                    params.put("dealer_code", "KH001");

                    *//*params.put("dl_expiry_date", " ");
                    params.put("dl_photo", " ");*//*

                    params.put("vin", " ");
                    params.put("dongle_name", " ");
                    params.put("waranty_from", " ");
                    params.put("waranty_to", " ");
                    params.put("extended_waranty_from", " ");
                    params.put("extended_waranty_to", " ");
                    params.put("loan_amount", " ");
                    params.put("emi", "");
                    params.put("interest", "");
                    params.put("loan_tenure", " ");
                    params.put("language", " ");
                    params.put("tag_renewal_date", " ");*/

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        return success;
        }
    }




