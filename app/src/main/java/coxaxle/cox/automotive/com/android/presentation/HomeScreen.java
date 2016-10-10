package coxaxle.cox.automotive.com.android.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.MyVehicleInHomeScreenPageAdapter;
import coxaxle.cox.automotive.com.android.adapters.VehicleDetailsImagesPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.GPSTracker;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MyCustomDialog.onSubmitListener{


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ViewPager mMyCarsViewPager, dealerBannersViewPager;
    ScrollView mHomeScreenScrollView;
    ImageView ivDrection, ivAddNewCars, ivCall, ivMail;
    //MyVehicleInHomeScreenPageAdapter mMyCarsPageAdapter;
    ArrayList<String> arrDealerBanners;
    String strUserId, strDealerPhone, strDealerEmail;;
    UserSessionManager mUserSessionManager;

    ArrayList<VehicleInfo> vehicleinfoList;

    LinearLayout linearLayout, linearLayoutBanners;
    private ImageView[] dots, dotsBanners;
    private int dotsCount, dotsCountBanners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        vehicleinfoList = new ArrayList<>();

        loadViews();

    }


    void loadViews() {
        mUserSessionManager = new UserSessionManager(this);
        strUserId = mUserSessionManager.getUserId();

        getListOfVehicles();
        getDealerInfo();
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
        linearLayout = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        //mMyCarsViewPager.setAdapter(new MyVehicleInHomeScreenPageAdapter(this,vehicleinfoList));

        mMyCarsViewPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        mMyCarsViewPager.setPadding(40, 0, 40, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        mMyCarsViewPager.setPageMargin(10);

        mMyCarsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
                    //ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null);
                }
                dots[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        dealerBannersViewPager = (ViewPager) findViewById(R.id.viewpager_DealerBanners);
        linearLayoutBanners = (LinearLayout) findViewById(R.id.viewPagerCountDots_DealerBanners);
        dealerBannersViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCountBanners; i++) {
                    dotsBanners[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
                }
                dotsBanners[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        RelativeLayout serchUsedAndNewCars = (RelativeLayout) findViewById(R.id.nav_new_car_layout);
        serchUsedAndNewCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleDealerInventoryIntent = new Intent(HomeScreen.this, DealerInventorySearchResultsActivity.class);
                startActivity(scheduleDealerInventoryIntent);

            }
        });


        RelativeLayout scheduleAnAppointment = (RelativeLayout) findViewById(R.id.nav_schedule_an_appointment_layout);

        scheduleAnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navToListOfMyCars("ScheduleAppointmentActivity");

                /*Intent scheduleAppointment = new Intent(HomeScreen.this, ScheduleAppointmentActivity.class);
                startActivity(scheduleAppointment);*/


            }
        });

        RelativeLayout savedCars = (RelativeLayout) findViewById(R.id.nav_saved_cars_layout);
        savedCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedCarsIntent = new Intent(HomeScreen.this, SavedSearchActivity.class);
                startActivity(savedCarsIntent);
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
        fontsOverrideobj.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    public void drawPageSelectionIndicator() {

        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        dotsCount = vehicleinfoList.size();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(HomeScreen.this);
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            linearLayout.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
        //dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }
    public void drawPageSelectionIndicatorforBanners() {

        if (linearLayoutBanners != null) {
            linearLayoutBanners.removeAllViews();
        }
        dotsCountBanners = arrDealerBanners.size();
        dotsBanners = new ImageView[dotsCountBanners];
        for (int i = 0; i < dotsCountBanners; i++) {
            dotsBanners[i] = new ImageView(HomeScreen.this);
            dotsBanners[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            linearLayoutBanners.addView(dotsBanners[i], params);
        }
        dotsBanners[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
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

        } else if (id == R.id.nav_my_cars) {

            navToListOfMyCars("vehicleDetails");


        } else if (id == R.id.nav_dealer_services) {

            Intent changePasswordIntent = new Intent(HomeScreen.this, DealerInventorySearchFiltersActivity.class);
            startActivity(changePasswordIntent);


        } else if (id == R.id.nav_ondemand_services) {

            Intent changePasswordIntent = new Intent(HomeScreen.this, CarShoppingActivity.class);
            startActivity(changePasswordIntent);

        } else if (id == R.id.nav_changepassword) {

            Intent changePasswordIntent = new Intent(HomeScreen.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);

        } else if (id == R.id.nav_logout) {
            logoutUser();

        } else if (id == R.id.nav_twitter) {
            Intent intent = null;
            try {
                // get the Twitter app if possible
                this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=@VensaiInc"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VensaiInc"));
            }
            this.startActivity(intent);

        } else if (id == R.id.nav_facebook) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/426253597411506"));  // fb id wrong
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/VensaiTechnologies")));
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void navToListOfMyCars(String navToActivity) {

        Intent intent = new Intent(HomeScreen.this, VehicleListActivity.class);
        intent.putParcelableArrayListExtra("vehicleList", vehicleinfoList);
        intent.putExtra("navToActivity",navToActivity);
        startActivity(intent);

    }


    @Override
    public void onClick(View view) {

        if (view == ivDrection) {
            MyCustomDialog fragment1 = new MyCustomDialog();
            fragment1.mListener = HomeScreen.this;
            fragment1.setDialog(R.layout.custom_dialog, HomeScreen.this, 1, "Cox Axle", "Are you sure want to go to maps?", "Ok", "Cancel");
            fragment1.show(getFragmentManager(), "");
        }
        if (view == ivAddNewCars) {
            int count = 0;
            if (mMyCarsViewPager.getAdapter() != null) {
                count = mMyCarsViewPager.getAdapter().getCount();
            }
            if (count >= 5) {
                Toast.makeText(getApplicationContext(), "You can't add more than 5 vehicles. Please delete vehicles before adding a vehicle", Toast.LENGTH_SHORT).show();
            } else {
                Intent addVehicleIntent = new Intent(HomeScreen.this, AddVehicleActivity.class);
                addVehicleIntent.putExtra("Vehicle_Flag", 0);
                addVehicleIntent.putExtra("navToActivity", "HomeScreenActivity");
                startActivity(addVehicleIntent);
            }


        }
        if (view == ivCall) {
            MyCustomDialog fragmentDialog = new MyCustomDialog();
            fragmentDialog.mListener = HomeScreen.this;
            fragmentDialog.setDialog(R.layout.custom_dialog, HomeScreen.this, 2, "Cox Axle", "Are you sure want to make a call?", "Ok", "Cancel");
            fragmentDialog.show(getFragmentManager(), "");
        }
        if (view == ivMail) {
            MyCustomDialog fragment1 = new MyCustomDialog();
            fragment1.mListener = HomeScreen.this;
            fragment1.setDialog(R.layout.custom_dialog, HomeScreen.this, 3, "Cox Axle", "Are you sure want to send a mail?", "Ok", "Cancel");
            fragment1.show(getFragmentManager(), "");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //getListOfVehicles();
    }

    private void getListOfVehicles() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GARAGE_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("getListOfVehicles()",response.toString());
                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);

                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonData = jsonobjListVehiclesResponse.getJSONObject("response");
                                    JSONArray jsonArray = jsonData.getJSONArray("data");

                                    Log.d("MyCArsLIst", jsonArray.length() + "");

                                    if (jsonArray.length() < 0) {
                                        mMyCarsViewPager.setVisibility(View.INVISIBLE);
                                    } else {

                                        mMyCarsViewPager.setVisibility(View.VISIBLE);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject obj = jsonArray.getJSONObject(i);
                                            //String strUserId = obj.getString("user_id");
                                            ArrayList<String> arrImageUrls = new ArrayList<>();
                                            JSONArray objUrls = obj.getJSONArray("vechicle_image");
                                            for (int j = 0; j < objUrls.length(); j++) {
                                                JSONObject objImagePath = objUrls.getJSONObject(j);
                                                String strpath = objImagePath.getString("image_url");
                                                if (strpath.length() != 0)
                                                    arrImageUrls.add(strpath);
                                            }
                                           /* VehicleInfo VehicleInfo = new VehicleInfo(obj.getString("id"), obj.getString("date_entered"), obj.getString("date_modified"), obj.getString("deleted"), obj.getString("name"),
                                                    obj.getString("user_id"), obj.getString("dealer_id"), obj.getString("vin"), obj.getString("vehicle_type"),
                                                    obj.getString("make"), obj.getString("model"), obj.getString("year"), obj.getString("color"), obj.getString("mileage"),
                                                    obj.getString("style"), obj.getString("trim"), obj.getString("tag_renewal_date"), obj.getString("waranty_from"), obj.getString("waranty_to"),
                                                    obj.getString("extended_waranty_from"), obj.getString("extended_waranty_to"), obj.getString("kbb_price"), obj.getString("manual"), obj.getString("loan_amount"),
                                                    obj.getString("emi"), obj.getString("interest"), obj.getString("loan_tenure"), obj.getString("insurance_document"), obj.getString("extended_waranty_document"),
                                                    obj.getString("insurance_expiration_date"), obj.getString("tag_expiration_date"), arrImageUrls);
                                            vehicleinfoList.add(VehicleInfo);*/

                                           /* ArrayList<String> arrImageUrls = new ArrayList<>();
                                            JSONArray objUrls = obj.getJSONArray("vechicle_image");
                                            for (int j = 0; j < objUrls.length(); j++) {
                                                JSONObject objImagePath = objUrls.getJSONObject(j);
                                                String strpath = objImagePath.getString("image_url");
                                                if (strpath.length() != 0)
                                                    arrImageUrls.add(strpath);
                                            }
                                            objVehicle.vehicle_image = arrImageUrls;*/


                                            // vehicleinfoList.add(objVehicle);
                                        }


                                        mMyCarsViewPager.setAdapter(new MyVehicleInHomeScreenPageAdapter(HomeScreen.this, vehicleinfoList));
                                        mMyCarsViewPager.getAdapter().notifyDataSetChanged();
                                        mMyCarsViewPager.setOffscreenPageLimit(vehicleinfoList.size()-1);
                                        dotsCount = vehicleinfoList.size();
                                        drawPageSelectionIndicator();

                                    }

                                } else {
                                    Toast.makeText(HomeScreen.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Log.v("response33>>>", "" + response);
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

    private void getDealerInfo() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DEALER_INFO_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Dealer Info",response.toString());
                            try {
                                JSONObject dealerInfoResponse = new JSONObject(response);

                                String strStatus = dealerInfoResponse.getString("status");
                                String strMessage = dealerInfoResponse.getString("message");
                                if (strStatus.equalsIgnoreCase("True")) {
                                    JSONObject jsonData = dealerInfoResponse.getJSONObject("response");

                                    //strDealerPhone = jsonData.getString("phone");
                                    strDealerEmail = jsonData.getString("email");
                                    // get Twitter id, FB id, Dealer Logo
                                    arrDealerBanners = new ArrayList<>();
                                    JSONArray jsonBanners = jsonData.getJSONArray("banner_image");
                                    for (int i = 0; i< jsonBanners.length(); i++)
                                    {
                                        JSONObject obj = jsonBanners.getJSONObject(i);
                                        String imageUrl = obj.getString("banner");
                                        arrDealerBanners.add(imageUrl);
                                    }

                                    dealerBannersViewPager.setAdapter(new VehicleDetailsImagesPageAdapter(HomeScreen.this, arrDealerBanners));
                                    dealerBannersViewPager.getAdapter().notifyDataSetChanged();
                                    dealerBannersViewPager.setOffscreenPageLimit(arrDealerBanners.size()-1);
                                    dotsCountBanners = arrDealerBanners.size();
                                    drawPageSelectionIndicatorforBanners();

                                } else {
                                    Toast.makeText(HomeScreen.this, strMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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
                    params.put("dealer_id", "37");

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


    void logoutUser() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to Logout");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(HomeScreen.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                //userLogOutRequest();
                mUserSessionManager.logoutUser();
                HomeScreen.this.finish();

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

    void parseDealerDetails(String response) {

    }

    @Override
    public void setOnSubmitListener(int flag) {
        if(flag ==1) {
            Geocoder coder = new Geocoder(getApplicationContext());
            try {
                ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName("Cyber Towers, Hyderabad, Telangana", 1);
                for (Address add : adresses) {
                    if (adresses.size() > 0) {
                        double destination_longitude = add.getLongitude();
                        double destination_latitude = add.getLatitude();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            GPSTracker tracker = new GPSTracker(this);
           /* if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {

            }*/
            double latitude = tracker.getLatitude();
            double longitude = tracker.getLongitude();
            //String uri = "http://maps.google.com/maps?saddr=" + latitude + ","+ longitude +"&daddr=+destination_latitude+","+destination_longitude;
            //String uri = "http://maps.google.com/maps?saddr=17.4635067,78.3422077&daddr="+destination_latitude+","+destination_longitude;
            String uri = "http://maps.google.com/maps?saddr=34.1605214,-84.179311,17&daddr=33.9156235,-84.3432829";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
        else if(flag == 2)
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:1234567890"));
            startActivity(callIntent);
        }
        else if(flag == 3)
        {
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
}
