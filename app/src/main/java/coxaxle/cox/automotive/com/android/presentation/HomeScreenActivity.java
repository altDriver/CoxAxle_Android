package coxaxle.cox.automotive.com.android.presentation;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.NotificationAdapter;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.NotificationInfo;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.fragments.AccidentHelp;
import coxaxle.cox.automotive.com.android.presentation.fragments.CarShopingFragment;
import coxaxle.cox.automotive.com.android.presentation.fragments.ContactDealership;
import coxaxle.cox.automotive.com.android.presentation.fragments.ContentHomeScreenFragment;
import coxaxle.cox.automotive.com.android.presentation.fragments.RoadSideAssistance;
import coxaxle.cox.automotive.com.android.presentation.fragments.ServicesFragment;
import coxaxle.cox.automotive.com.android.presentation.fragments.VehicleListFragment;

public class HomeScreenActivity extends AppCompatActivity implements MyCustomDialog.onSubmitListener {

    //Defining Variables
    public Toolbar toolbar;
    public ImageView toolbar_icon;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    ImageView facebook_iv, twitter_iv;
    FragmentTransaction fragmentTransaction;
    public TextView toolbar_title, toolbar_notifications_count, toolbar_right_text;
    ViewPager mMyCarsViewPager;
    String strUserId;
    ArrayList<VehicleInfo> vehicleinfoList;
    UserSessionManager mUserSessionManager;
    String navToActivity;
    String FACEBOOK_URL = "https://www.facebook.com/VensaiTechnologies";
    int flag=0;
    MyCustomDialog fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_with_fragments);
        vehicleinfoList = new ArrayList<>();
        flag = this.getIntent().getIntExtra("Flag_Add", 0);
        loadViews();
        fragment = new MyCustomDialog();
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar_title = (TextView) findViewById(R.id.app_bar_home_title_tv);
        toolbar.setNavigationIcon(R.mipmap.hamburger_icon);
        toolbar_title = (TextView) findViewById(R.id.app_bar_home_title_tv);
        toolbar_notifications_count = (TextView) findViewById(R.id.toolbar_notifications_count);
        toolbar_right_text = (TextView) findViewById(R.id.toolbar_right_text);
        toolbar_icon = (ImageView) findViewById(R.id.toolbar_icon);
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.mListener = HomeScreenActivity.this;
                fragment.setDialog(R.layout.notification_dialog, HomeScreenActivity.this, 3, "", "", "", "Ok");
                fragment.show(getFragmentManager(), "");
            }
        });
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        facebook_iv = (ImageView) headerview.findViewById(R.id.facebook);
        twitter_iv = (ImageView) headerview.findViewById(R.id.twittwer);
        facebook_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.mListener = HomeScreenActivity.this;
                fragment.setDialog(R.layout.custom_dialog, HomeScreenActivity.this, 1, "Cox Axle", "Are you sure want to go to Facebook?", "Ok", "Cancel");
                fragment.show(getFragmentManager(), "");
            }
        });
        twitter_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment.mListener = HomeScreenActivity.this;
                fragment.setDialog(R.layout.custom_dialog, HomeScreenActivity.this, 2, "Cox Axle", "Are you sure want to go to Twitter?", "Ok", "Cancel");
                fragment.show(getFragmentManager(), "");
            }
        });

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                Fragment fragment = null;

                switch (menuItem.getItemId()) {

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        fragment = new ContentHomeScreenFragment();
                        break;
                    case R.id.nav_my_cars:
                        fragment = new VehicleListFragment();
                        Bundle args = new Bundle();
                        args.putInt("Flag_Add", 0);
                        fragment.setArguments(args);
                        break;
                    case R.id.nav_dealer_services:
                        fragment = new ServicesFragment();
                        break;
                    case R.id.road_side_assistance:
                        fragment = new RoadSideAssistance();
                        break;
                   /* case R.id.accident_help:
                        fragment = new AccidentHelp();
                        break;*/
                    case R.id.car_shopping:
                        fragment = new CarShopingFragment();
                        break;
                    case R.id.contact_dealership:
                        fragment = new ContactDealership();
                        break;
                    case R.id.settings:
                        //fragment = new ChangePasswordFragment();
                        logoutUser();
                        break;
                    default:
                        fragment = new ContentHomeScreenFragment();
                        break;

                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_place, fragment).commit();
                }

                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //Setting the actionbar_icon to drawer layout
        //actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        //actionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.hamburger_icon);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    void loadViews() {

        mUserSessionManager = new UserSessionManager(this);
        strUserId = mUserSessionManager.getUserId();

        getListOfVehicles(flag);
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

    @Override
    protected void onResume() {
        super.onResume();

        //getListOfVehicles();
    }

    private void getListOfVehicles(final int listflag) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GARAGE_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responce) {

                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(responce);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonData = jsonobjListVehiclesResponse.getJSONObject("response");
                                    JSONArray jsonArray = jsonData.getJSONArray("data");

                                    Log.d("MyCArsLIst", responce);
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = jsonArray.getJSONObject(i);
                                            //String strUserId = obj.getString("user_id");

                                            ArrayList<String> arrinsurenceDocUrls = new ArrayList<>();
                                            JSONArray objInsUrls = obj.getJSONArray("insurance_document");
                                            if (objInsUrls.length() > 0) {
                                                for (int j = 0; j < objInsUrls.length(); j++) {
                                                    JSONObject objImagePath = objInsUrls.getJSONObject(j);
                                                    String strpath = objImagePath.getString("insurence_url");
                                                    if (strpath != null)
                                                        arrinsurenceDocUrls.add(strpath);
                                                }
                                            }
                                            //need change after api update
                                       /* ArrayList<String> arrExtWarrentyDocUrls = new ArrayList<>();
                                        JSONArray objWarUrls = obj.getJSONArray("vechicle_image");
                                        for (int j = 0; j < objWarUrls.length(); j++) {
                                            JSONObject objImagePath = objWarUrls.getJSONObject(j);
                                            String strpath = objImagePath.getString("image_url");
                                            if (strpath.length() != 0)
                                                arrExtWarrentyDocUrls.add(strpath);
                                        }


                                        ArrayList<String> arrImageUrls = new ArrayList<>();
                                        JSONArray objUrls = obj.getJSONArray("vechicle_image");
                                        for (int j = 0; j < objUrls.length(); j++) {
                                            JSONObject objImagePath = objUrls.getJSONObject(j);
                                            String strpath = objImagePath.getString("image_url");
                                            if (strpath.length() != 0)
                                                arrImageUrls.add(strpath);
                                        }*/
                                            /*VehicleInfo VehicleInfo = new VehicleInfo(obj.getString("id"), obj.getString("date_entered"), obj.getString("date_modified"), obj.getString("deleted"), obj.getString("name"),
                                                    obj.getString("user_id"), obj.getString("dealer_id"), obj.getString("vin"), obj.getString("vehicle_type"),
                                                    obj.getString("make"), obj.getString("model"), obj.getString("year"), obj.getString("color"), obj.getString("mileage"),
                                                    obj.getString("style"), obj.getString("trim"), obj.getString("tag_renewal_date"), obj.getString("waranty_from"), obj.getString("waranty_to"),
                                                    obj.getString("extended_waranty_from"), obj.getString("extended_waranty_to"), obj.getString("kbb_price"), obj.getString("manual"), obj.getString("loan_amount"),
                                                    obj.getString("emi"), obj.getString("interest"), obj.getString("loan_tenure"), obj.getString("insurance_document"), obj.getString("extended_waranty_document"),
                                                    obj.getString("insurance_expiration_date"), obj.getString("tag_expiration_date"), arrImageUrls);
*/

                                            VehicleInfo vehicleInfo = new VehicleInfo(obj.getString("id"),
                                                    obj.getString("name"),
                                                    obj.getString("vin"),
                                                    obj.getString("vehicle_type"),
                                                    obj.getString("make"),
                                                    obj.getString("model"),
                                                    obj.getString("year"),
                                                    obj.getString("color"),
                                                    obj.getString("mileage"),
                                                    obj.getString("style"),
                                                    obj.getString("trim"),
                                                    obj.getString("waranty_from"),
                                                    obj.getString("waranty_to"),
                                                    obj.getString("extended_waranty_from"),
                                                    obj.getString("extended_waranty_to"),
                                                    "",
                                                    arrinsurenceDocUrls,
                                                    obj.getString("extended_waranty_document"),
                                                    obj.getString("photo"), "1000", obj.getString("tag_expiration_date"), obj.getString("insurance_expiration_date"));


                                            vehicleinfoList.add(vehicleInfo);
                                        }
                                    }


                                } else {
                                    Toast.makeText(HomeScreenActivity.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_place, new ContentHomeScreenFragment()).commit();

                            }
                            //Log.v("response33>>>", "" + response);
                            if(listflag == 1)
                            {
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_place, new VehicleListFragment()).commit();
                            }
                            else {
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_place, new ContentHomeScreenFragment()).commit();
                            }
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
                    params.put("dealer_code", Constants.DEALER_CODE);
                    Log.v("params33>>>", "" + params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(HomeScreenActivity.this);
            requestQueue.add(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void logoutUser() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to Logout");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(HomeScreen.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                //userLogOutRequest();
                mUserSessionManager.logoutUser();
                HomeScreenActivity.this.finish();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                //alertDialogBuilder.
                //alertDialog.
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    void userLogOutRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //parseJsonAndNavToHomeScreen(response);
                        //parseLogoutResponceAndNavToLoginScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(HomeScreen.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("uid", strUserId);


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

    @Override
    public void setOnSubmitListener(int flag) {
        if (flag == 1) {
            try {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                //open the Facebook app using newer versions of fb app
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else {
                    // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/336227679757310")));
                }
            } catch (PackageManager.NameNotFoundException e) {
                // Facebook is not installed. Open the browser
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)));
            }
        } else if (flag == 2) {
            Intent intent = null;
            try {
                // get the Twitter app if possible
                getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=VensaiInc"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VensaiInc"));
            }
            startActivity(intent);
        }
    }


    // Craeting Interface to passing arraylist of vehicleinfoList
    public ArrayList<VehicleInfo> getDataList() {
        return vehicleinfoList;
    }
}
