package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.VehicleDetailsImagesPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.common.SemicircularProgressBar;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Lakshmana on 11-08-2016.
 */
public class VehicleDetailsActivity extends Activity implements View.OnClickListener, MyCustomDialog.onSubmitListener {
    ImageView ivCallAgent, ivMailtoAgent, ivDeleteVehicle;
    TextView tvVehicleName, tvMyCarName, tvModel, tvMonthsRemaining, tvMake,  tvFuel, tvEngine, tvVIN, tvLicenceNo, tvTagExpirationDate, tvLoanAmountFinanced,
            tvLoanPaymentTerms, tvInsuranceAgentName, tvInsuranceCompanyName, tvInsurancePolicyNo, tvInsuranceExpiresOn, tvLastServiceddate,
            tvUpcomingService, tvWarrantyCompanyName, tvKBBTradeinValue, tvKBBPrivatePartyValue;//tvMiles,
    ProgressDialog progressDialog;
    Button buttonEditVehicle;

    ViewPager mMyCarsViewPager;
    VehicleInfo vehicleListItem;
    ArrayList<String> arrImages;
    LinearLayout llDotsCount;
    private ImageView[] dots;
    private int dotsCount;
    String navToActivity;
    private SimpleDateFormat dateFormatter;
    int deleteFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);


        Bundle bundle = this.getIntent().getExtras();
        arrImages = new ArrayList<>();
        vehicleListItem = bundle.getParcelable("VehicleInfo");
        navToActivity = bundle.getString("navToActivity");
        deleteFlag = bundle.getInt("Flag_Add");
       if( vehicleListItem.photo!=null) {
           String image = vehicleListItem.photo;
           arrImages.add(image);
       }else{

           arrImages.add("");
       }
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        loadViews();
        SemicircularProgressBar semiCircleProgressBarView = (SemicircularProgressBar) findViewById(R.id.vehicle_details_manufacturer_warranty_months_indicator);
        semiCircleProgressBarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        semiCircleProgressBarView.setProgressBarWidth(80);
        ImageView imgProgressNeedle = (ImageView) findViewById(R.id.vehicle_details_pb_indicator_iv);


        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String strFromDate = vehicleListItem.waranty_from;
        String strToDate = vehicleListItem.waranty_to;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Date Fromdate = sdf.parse(strFromDate);
            Date Todate = sdf.parse(strToDate);
            if (System.currentTimeMillis() > Todate.getTime()) {
                semiCircleProgressBarView.setProgress(100);
                imgProgressNeedle.setRotation(180);
                tvMonthsRemaining.setText("0");
            }else {

                String strCurrentDate = sdf.format(Calendar.getInstance().getTime());
                Date startDate = sdf.parse(strFromDate);
                Date CurrentDate = sdf.parse(strCurrentDate);

                Calendar startCalendar = new GregorianCalendar();
                startCalendar.setTime(startDate);
                Calendar CurrentCalendar = new GregorianCalendar();
                CurrentCalendar.setTime(CurrentDate);

                int diffYear = CurrentCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
                int diffMonth = diffYear * 12 + CurrentCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
                int percent = diffMonth*100/36;

                semiCircleProgressBarView.setProgress(percent);
                float angle = (percent * 180) / 100;
                imgProgressNeedle.setRotation( angle);

                int remaing = 36-diffMonth;
                tvMonthsRemaining.setText(remaing+"");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        /*Matrix matrix = new Matrix();
        imgProgressNeedle.setScaleType(ImageView.ScaleType.MATRIX);   //required
        matrix.postRotate((float) angle, pivotX, pivotY);
        imgProgressNeedle.setImageMatrix(matrix);*/

    }

    private void loadViews() {

        mMyCarsViewPager = (ViewPager) findViewById(R.id.vehicle_details_images_viewpager);

        mMyCarsViewPager.setAdapter(new VehicleDetailsImagesPageAdapter(this, arrImages));
        mMyCarsViewPager.getAdapter().notifyDataSetChanged();
        mMyCarsViewPager.setOffscreenPageLimit(arrImages.size()-1);

        llDotsCount = (LinearLayout) findViewById(R.id.VehicleDetails_viewPagerCountDots);
        dotsCount = arrImages.size();
        drawPageSelectionIndicator();

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


        initProfileIndicator();
        //initWarrentyMilesIndicatorProgress();
        tvVehicleName = (TextView) findViewById(R.id.vehicle_details_name);
        tvMyCarName = (TextView) findViewById(R.id.vehicle_details_my_car_name);
        tvModel = (TextView) findViewById(R.id.vehicle_details_my_car_model);
        tvVIN = (TextView) findViewById(R.id.vehicle_details_vin_details_tv);
        tvTagExpirationDate = (TextView) findViewById(R.id.vehicle_details_tag_expiration_detail_tv);
        tvMonthsRemaining = (TextView) findViewById(R.id.VehicleDetails_MonthsRemaining);

        tvVehicleName.setText(vehicleListItem.getName());
        tvMyCarName.setText(vehicleListItem.getName());
        tvModel.setText(vehicleListItem.getModel()+" • "+vehicleListItem.getMileage()+"Miles");

        tvVIN.setText(vehicleListItem.getVin());
        //tvTagExpirationDate.setText(vehicleListItem.getag_expiration_date());
        buttonEditVehicle = (Button) findViewById(R.id.vehicle_details_edit_vehicle);
        buttonEditVehicle.setOnClickListener(this);
        ivDeleteVehicle = (ImageView) findViewById(R.id.vehicle_details_delete_vehicle_iv);
        ivDeleteVehicle.setOnClickListener(this);
    }


    public void drawPageSelectionIndicator() {
        if(dotsCount>1){


        if (llDotsCount != null) {
            llDotsCount.removeAllViews();
        }
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(VehicleDetailsActivity.this);
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            llDotsCount.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
        }
    }

    void initProfileIndicator() {
        //Resources res = getResources();
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.horizontal_progress_bg);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.vehicle_details_profile_indicator);
        mProgress.setProgress(25);   // Main Progress
        mProgress.setSecondaryProgress(50); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

        /*ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 50);
        animation.setDuration(990);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/
    }

    @Override
    public void onClick(View v) {

        /*if (v == ivCallAgent) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:1234567890"));
            startActivity(callIntent);
        }
        if (v == ivMailtoAgent) {
            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"xxx@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Add Message here");
            emailIntent.setType("message/rfc822");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }*/
        if (v == buttonEditVehicle) {
            Intent intent = new Intent(VehicleDetailsActivity.this, AddVehicleActivity.class);
            intent.putExtra("VehicleInfo", vehicleListItem);
            intent.putExtra("Vehicle_Flag", 1);
            intent.putExtra("navToActivity", navToActivity);
            startActivity(intent);
        }
        if(v == ivDeleteVehicle)
        {
            MyCustomDialog fragmentDialog = new MyCustomDialog();
            fragmentDialog.mListener = VehicleDetailsActivity.this;
            fragmentDialog.setDialog(R.layout.custom_dialog, VehicleDetailsActivity.this, 1, "Cox Axle", "Are you sure want to delete vehicle?", "Ok", "Cancel");
            fragmentDialog.show(getFragmentManager(), "");
        }

    }
    @Override
    public void setOnSubmitListener(int flag) {
        deleteVehicle();
    }
    private void deleteVehicle()
    {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_VEHICLE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("Delete Vehicle>>>", "" + response);

                            try {
                                JSONObject jsonobjListVehiclesResponse = new JSONObject(response);
                                String strStatus = jsonobjListVehiclesResponse.getString("status");
                                String strMessage = jsonobjListVehiclesResponse.getString("message");
                                if (strStatus.equals("True")) {
                                    Intent intent = new Intent(VehicleDetailsActivity.this, HomeScreenActivity.class);
                                    intent.putExtra("Flag_Add", deleteFlag);
                                    startActivity(intent);
                                    finish();
                                }
                                Toast.makeText(VehicleDetailsActivity.this, strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error delete vehicle>>>", "" + error);
                            Toast.makeText(VehicleDetailsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    UserSessionManager obj = new UserSessionManager(VehicleDetailsActivity.this);
                    String strUid = obj.getUserId();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("vid", vehicleListItem.id);
                    params.put("uid", strUid);

                    Log.v("params>>>", "" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(VehicleDetailsActivity.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
