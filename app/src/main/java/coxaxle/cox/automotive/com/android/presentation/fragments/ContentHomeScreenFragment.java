package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.MyVehicleInHomeScreenPageAdapter;
import coxaxle.cox.automotive.com.android.adapters.VehicleDetailsImagesPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.GPSTracker;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.DealerInfo;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;
import coxaxle.cox.automotive.com.android.presentation.AddVehicleActivity;
import coxaxle.cox.automotive.com.android.presentation.DealerInventorySearchResultsActivity;
import coxaxle.cox.automotive.com.android.presentation.HomeScreen;
import coxaxle.cox.automotive.com.android.presentation.HomeScreenActivity;
import coxaxle.cox.automotive.com.android.presentation.SavedSearchActivity;
import coxaxle.cox.automotive.com.android.presentation.VehicleListActivity;

/**
 * Created by Srinivas B on 12-09-2016.
 */

public class ContentHomeScreenFragment extends Fragment implements View.OnClickListener , MyCustomDialog.onSubmitListener{

    ViewPager mMyCarsViewPager, dealerBannersViewPager;
    ScrollView mHomeScreenScrollView;
    ImageView ivDrection, ivAddNewCars, ivCall, ivMail;
    RelativeLayout serchUsedAndNewCars, scheduleAnAppointment, savedSearchs;
    //MyVehicleInHomeScreenPageAdapter mMyCarsPageAdapter;
    ArrayList<VehicleInfo> vehicleinfoList;

    LinearLayout linearLayout, linearLayoutBanners;
    private ImageView[] dots, dotsBanners;
    private int dotsCount, dotsCountBanners;

    View view;
    private Toolbar toolbar;
    TextView toolbar_title;
    ArrayList<String> arrImageUrls;
    //VehicleInfo dealerInfo;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        view = inflater.inflate(R.layout.content_menu_home_screen_with_fragment, container, false);
        FontsOverride fontsOverrideobj = new FontsOverride(getActivity().getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup) getActivity().findViewById(android.R.id.content));
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar_title = (TextView) view.findViewById(R.id.app_bar_home_title_tv);

        HomeScreenActivity activity = (HomeScreenActivity) getActivity();
        vehicleinfoList = activity.getDataList();

        ((HomeScreenActivity) getActivity()).toolbar_title.setText("Home");
        ((HomeScreenActivity) getActivity()).toolbar_icon.setVisibility(View.VISIBLE);
        ((HomeScreenActivity) getActivity()).toolbar_notifications_count.setVisibility(View.VISIBLE);
        ((HomeScreenActivity) getActivity()).toolbar_icon.setBackgroundResource(R.mipmap.notification_icon);
        loadViews();
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    void loadViews() {

        //dotsCount = vehicleinfoList.size();
        ivDrection = (ImageView) view.findViewById(R.id.map_image);
        ivDrection.setOnClickListener(this);
        ivAddNewCars = (ImageView) view.findViewById(R.id.add_new_vehicle);
        ivCall = (ImageView) view.findViewById(R.id.call_us_image);
        ivMail = (ImageView) view.findViewById(R.id.message_image);
        ivAddNewCars.setOnClickListener(this);
        ivCall.setOnClickListener(this);
        ivMail.setOnClickListener(this);

        mMyCarsViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mHomeScreenScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        dealerBannersViewPager = (ViewPager)view.findViewById(R.id.viewpager_DealerBanners);

        linearLayoutBanners = (LinearLayout)view.findViewById(R.id.viewPagerCountDots_DealerBanners);
        getDealerInfo();
        serchUsedAndNewCars = (RelativeLayout) view.findViewById(R.id.nav_new_car_layout);
        serchUsedAndNewCars.setOnClickListener(this);

        scheduleAnAppointment = (RelativeLayout) view.findViewById(R.id.nav_schedule_an_appointment_layout);
        scheduleAnAppointment.setOnClickListener(this);

        savedSearchs = (RelativeLayout) view.findViewById(R.id.nav_saved_cars_layout);
        savedSearchs.setOnClickListener(this);
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




        if (vehicleinfoList.size() < 0) {
            mMyCarsViewPager.setVisibility(View.INVISIBLE);
        } else {
            mMyCarsViewPager.setVisibility(View.VISIBLE);
            mMyCarsViewPager.setAdapter(new MyVehicleInHomeScreenPageAdapter(this.getActivity(), vehicleinfoList));
            mMyCarsViewPager.getAdapter().notifyDataSetChanged();
            mMyCarsViewPager.setOffscreenPageLimit(vehicleinfoList.size()-1);
            dotsCount = vehicleinfoList.size();
            drawPageSelectionIndicator();
        }
    }
    private void getDealerInfo() {
       /* try {
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

                                    JSONArray jsonBanners = jsonData.getJSONArray("banner_image");
                                    for (int i = 0; i< jsonBanners.length(); i++)
                                    {
                                        JSONObject obj = jsonBanners.getJSONObject(i);
                                        String imageUrl = obj.getString("banner");
                                        arrImageUrls.add(imageUrl);
                                    }*/
                                  /*  dealerInfo = new VehicleInfo(jsonData.getString("name"), jsonData.getString("dealer_code"), jsonData.getString("phone"), jsonData.getString("address"),
                                            jsonData.getString("email"), jsonData.getString("dealer_twiter_page_link"), jsonData.getString("dealer_fb_page_link"), jsonData.getString("main_contact_number"),jsonData.getString("sale_contact"),
                                            jsonData.getString("service_desk_contact"), jsonData.getString("collision_desk_contact"), jsonData.getString("dealer_logo"), jsonData.getString("date_modified"), arrImageUrls);
*/
                                      //DealerInfo objDealerInfo = AxleApplication.dealerInfo.get(0);
                                      arrImageUrls = new ArrayList<>();
                                      arrImageUrls = AxleApplication.dealerInfo.get(0).getBanner_image_urls();

                                       Log.d("bannerUrl>>",arrImageUrls.get(0));

                                    if(arrImageUrls.size()>0)
                                    {

                                        dealerBannersViewPager.setAdapter(new VehicleDetailsImagesPageAdapter(getActivity(), arrImageUrls));
                                        dealerBannersViewPager.getAdapter().notifyDataSetChanged();
                                        dealerBannersViewPager.setOffscreenPageLimit(arrImageUrls.size()-1);
                                        dotsCountBanners = arrImageUrls.size();
                                        drawPageSelectionIndicatorforBanners();
                                    }


    }

    public void drawPageSelectionIndicator() {
        dotsCount = vehicleinfoList.size();
        if(dotsCount>0) {

            if (linearLayout != null) {
                linearLayout.removeAllViews();
            }

            dots = new ImageView[dotsCount];
            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);
                linearLayout.addView(dots[i], params);
            }
            dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
        }
        //dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }
    public void drawPageSelectionIndicatorforBanners() {

        if (linearLayoutBanners != null) {
            linearLayoutBanners.removeAllViews();
        }
        dotsCountBanners = arrImageUrls.size();
        dotsBanners = new ImageView[dotsCountBanners];
        for (int i = 0; i < dotsCountBanners; i++) {
            dotsBanners[i] = new ImageView(getActivity());
            dotsBanners[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            linearLayoutBanners.addView(dotsBanners[i], params);
        }
        dotsBanners[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
    }

   /* View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View view) {
            Fragment fragment = null;
            if(view == scheduleAnAppointment){
                fragment = new VehicleListFragment();
                Bundle args = new Bundle();
                args.putString("navToActivity", "ScheduleAppointmentActivity");
                fragment.setArguments(args);
                //toolbar_title.setText("My Cars");
            }if(view == serchUsedAndNewCars){

            *//*    fragment = new DealerInventorySearchResultsFragment();
                //toolbar_title.setText("DealerInventory");*//*
            }

            FragmentManager manager = getActivity().getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_place, fragment);
            transaction.commit();

        }
    };*/

    @Override
    public void onClick(View view) {

        if (view == ivDrection) {
            MyCustomDialog fragment1 = new MyCustomDialog();
            fragment1.mListener = ContentHomeScreenFragment.this;
            fragment1.setDialog(R.layout.custom_dialog, getActivity(), 1, "Cox Axle", "Are you sure want to go to maps?", "Ok", "Cancel");
            fragment1.show(getFragmentManager(), "");
        }
        if (view == ivAddNewCars) {
            int count = 0;
            if (mMyCarsViewPager.getAdapter() != null) {
                count = mMyCarsViewPager.getAdapter().getCount();
            }
            if (count >= 5) {
                Toast.makeText(getActivity(), "You can't add more than 5 vehicles. Please delete vehicle before adding a vehicle", Toast.LENGTH_SHORT).show();
            } else {



                /*AddVehicleFragment addvehiclefragment = new AddVehicleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("Vehicle_Flag",  0);
                bundle.putString("navToActivity", "HomeScreenActivity");
                addvehiclefragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_place, addvehiclefragment).commit();*/
                Intent addVehicleIntent = new Intent(getActivity(), AddVehicleActivity.class);
                addVehicleIntent.putExtra("Vehicle_Flag", 0);
                addVehicleIntent.putExtra("navToActivity", "HomeScreenActivity");
                startActivity(addVehicleIntent);
            }


        }
        if (view == ivCall) {
            MyCustomDialog fragmentDialog = new MyCustomDialog();
            fragmentDialog.mListener = ContentHomeScreenFragment.this;
            fragmentDialog.setDialog(R.layout.custom_dialog, getActivity(), 2, "Cox Axle", "Are you sure want to make a call?", "Ok", "Cancel");
            fragmentDialog.show(getFragmentManager(), "");
        }
        if (view == ivMail) {
            MyCustomDialog fragment1 = new MyCustomDialog();
            fragment1.mListener = ContentHomeScreenFragment.this;
            fragment1.setDialog(R.layout.custom_dialog, getActivity(), 3, "Cox Axle", "Are you sure want to send a mail?", "Ok", "Cancel");
            fragment1.show(getFragmentManager(), "");
        }

    if(view == scheduleAnAppointment){
        Intent intent = new Intent(getActivity(), VehicleListActivity.class);
        intent.putParcelableArrayListExtra("vehicleList", vehicleinfoList);
        intent.putExtra("navToActivity","ScheduleAppointmentActivity");
        startActivity(intent);

    }if(view == serchUsedAndNewCars){
        Intent scheduleDealerInventoryIntent = new Intent(getActivity(), DealerInventorySearchResultsActivity.class);
        startActivity(scheduleDealerInventoryIntent);
    }
    if(view == savedSearchs)
    {
        Intent savedSearchIntent = new Intent(getActivity(), SavedSearchActivity.class);
        startActivity(savedSearchIntent);
    }
}

  /*  public Intent doPositiveClickMaps() {
        Geocoder coder = new Geocoder(getActivity());
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

        GPSTracker tracker = new GPSTracker(getActivity());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            double latitude = tracker.getLatitude();
            double longitude = tracker.getLongitude();
        }

        //String uri = "http://maps.google.com/maps?saddr=" + latitude + ","+ longitude +"&daddr=+destination_latitude+","+destination_longitude;
        //String uri = "http://maps.google.com/maps?saddr=17.4635067,78.3422077&daddr="+destination_latitude+","+destination_longitude;
        String uri = "http://maps.google.com/maps?saddr=34.1605214,-84.179311,17&daddr=33.9156235,-84.3432829";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        return intent;
    }*/

    @Override
    public void onResume() {
        super.onResume();

        //getListOfVehicles();
    }
    @Override
    public void setOnSubmitListener(int flag) {
        if(flag ==1) {
            Geocoder coder = new Geocoder(getActivity());
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

            GPSTracker tracker = new GPSTracker(getActivity());
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
                Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

