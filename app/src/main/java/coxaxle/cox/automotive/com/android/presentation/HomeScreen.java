package coxaxle.cox.automotive.com.android.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.MyCarsPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.AlertDFragment;
import coxaxle.cox.automotive.com.android.model.Constants;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ViewPager mMyCarsViewPager;
    ScrollView mHomeScreenScrollView;
    ImageView ivDrection, ivAddNewCars, ivCall, ivMail;
    //MyCarsPageAdapter mMyCarsPageAdapter;
    String strUserId;
    UserSessionManager mUserSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        loadViews();

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



    }


     void loadViews(){
         mUserSessionManager = new UserSessionManager(this);
         strUserId = mUserSessionManager.getUserId();

         getListOfVehicles();

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         toolbar.setNavigationIcon(R.mipmap.hamburger_icon);

         ivDrection = (ImageView) findViewById(R.id.map_image);
         ivDrection.setOnClickListener(this);
         ivAddNewCars = (ImageView) findViewById(R.id.add_new_vehicle);
         ivCall = (ImageView) findViewById(R.id.call_us_image);
         ivMail = (ImageView) findViewById(R.id.message_image);
         ivAddNewCars.setOnClickListener(this);
         ivCall.setOnClickListener(this);
         ivMail.setOnClickListener(this);

         mMyCarsViewPager = (ViewPager) findViewById(R.id.viewpager);
         mHomeScreenScrollView = (ScrollView) findViewById(R.id.scrollView);

         mMyCarsViewPager.setAdapter(new MyCarsPageAdapter(this));

         mMyCarsViewPager.setClipToPadding(false);
         // set padding manually, the more you set the padding the more you see of prev & next page
         mMyCarsViewPager.setPadding(40, 0, 40, 0);
         // sets a margin b/w individual pages to ensure that there is a gap b/w them
         mMyCarsViewPager.setPageMargin(20);

         RelativeLayout scheduleAnAppointment = (RelativeLayout) findViewById(R.id.nav_schedule_an_appointment_layout);

         scheduleAnAppointment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent scheduleAppointment = new Intent(HomeScreen.this, ScheduleAppointmentActivity.class);

                 startActivity(scheduleAppointment);


             }
         });

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.setDrawerListener(toggle);
         toggle.syncState();

         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

         FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
         fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_notifications) {
           /* Intent settingsScreenIntent = new Intent(HomeScreen.this, ImageUploadActivity.class);

            startActivity(settingsScreenIntent);*/
        } else if (id == R.id.nav_settings) {

            Intent settingsScreenIntent = new Intent(HomeScreen.this, UserAccountInfoActivity.class);

            startActivity(settingsScreenIntent);

        } else if (id == R.id.nav_my_garage) {




        } else if (id == R.id.nav_dealer_services) {

        } else if (id == R.id.nav_ondemand_services) {

        } else if (id == R.id.nav_changepassword) {

            Intent changePasswordIntent = new Intent(HomeScreen.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view == ivDrection) {
            FragmentManager fm = getSupportFragmentManager();
            AlertDFragment alertdFragment = new AlertDFragment();
            alertdFragment.show(fm, "Alert Dialog Fragment");
        }
        if (view == ivAddNewCars) {
            Intent addVehicleIntent = new Intent(HomeScreen.this, AddVehicleActivity.class);
            startActivity(addVehicleIntent);
        }
        if (view == ivCall) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:1234567890"));
            startActivity(callIntent);
        }
        if (view == ivMail) {
            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"xyz@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Add Message here");
            emailIntent.setType("message/rfc822");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GARAGE_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equals("True")) {
                                    JSONObject jsonData = jsonobjListVehiclesResponse.getJSONObject("response");
                                    JSONArray jsonArray = jsonData.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        //String strUserId = obj.getString("user_id");
                                        Parcel dest = null;
                                        dest.writeStringArray(new String[]{
                                                obj.getString("id"),
                                                obj.getString("date_entered"),
                                                obj.getString("date_modified"),
                                                obj.getString("deleted"),
                                                obj.getString("name"),
                                                obj.getString("user_id"),
                                                obj.getString("dealer_id"),
                                                obj.getString("vin"),
                                                obj.getString("vehicle_type"),
                                                obj.getString("make"),
                                                obj.getString("model"),
                                                obj.getString("year"),
                                                obj.getString("color"),
                                                obj.getString("mileage"),
                                                obj.getString("style"),
                                                obj.getString("hypothecate"),
                                                obj.getString("trim"),
                                                obj.getString("tag_renewal_date"),
                                                obj.getString("waranty_from"),
                                                obj.getString("waranty_to"),
                                                obj.getString("extended_waranty_from"),
                                                obj.getString("extended_waranty_to"),
                                                obj.getString("kbb_price"),
                                                obj.getString("manual"),
                                                obj.getString("loan_amount"),
                                                obj.getString("emi"),
                                                obj.getString("interest"),
                                                obj.getString("loan_tenure"),
                                                obj.getString("insurance_document"),
                                                obj.getString("vechicle_image"),
                                        });
                                        //VehicleInfo objinfo = new VehicleInfo();
                                        //objinfo.writeToParcel(dest, i);
                                    }
                                } else {
                                    Toast.makeText(HomeScreen.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.v("response33>>>", "" + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("error>>>", "" + error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", strUserId);

                    Log.v("params>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(HomeScreen.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
