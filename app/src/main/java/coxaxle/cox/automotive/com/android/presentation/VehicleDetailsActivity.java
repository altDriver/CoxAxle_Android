package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.VehicleDetailsImagesPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Lakshmana on 11-08-2016.
 */
public class VehicleDetailsActivity extends Activity implements View.OnClickListener {
    ImageView ivCallAgent, ivMailtoAgent;
    TextView tvVehicleName, tvMyCarName, tvModel, tvNxtSheduleService, tvMake,  tvFuel, tvEngine, tvVIN, tvLicenceNo, tvTagExpirationDate, tvLoanAmountFinanced,
            tvLoanPaymentTerms, tvInsuranceAgentName, tvInsuranceCompanyName, tvInsurancePolicyNo, tvInsuranceExpiresOn, tvLastServiceddate,
            tvUpcomingService, tvWarrantyCompanyName, tvKBBTradeinValue, tvKBBPrivatePartyValue;//tvMiles,
    ProgressDialog progressDialog;
    Button buttonEditVehicle;

    ViewPager mMyCarsViewPager;
    VehicleInfo vehicleListItem;
    ArrayList<String> arrImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        Bundle bundle = this.getIntent().getExtras();
        vehicleListItem = bundle.getParcelable("VehicleInfo");
        arrImages = vehicleListItem.vehicle_image;
       /* vehicleListItem.getName();
        vehicleListItem.getVehicle_image();
        Log.e("sssssaaaaa",""+vehicleListItem.getVehicle_image());*/

        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        loadViews();
        //new LoadVehicleDetailsAsync().execute();
    }

    private void loadViews() {

        mMyCarsViewPager = (ViewPager) findViewById(R.id.vehicle_details_images_viewpager);

        mMyCarsViewPager.setAdapter(new VehicleDetailsImagesPageAdapter(this, arrImages));
        mMyCarsViewPager.getAdapter().notifyDataSetChanged();
        mMyCarsViewPager.setOffscreenPageLimit(arrImages.size()-1);


        initProfileIndicator();
        initProfileIndicator();
        initWarrentyMilesIndicatorProgress();
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

    void initWarrentyMonthsIndicatorProgress() {
        Resources res = getResources();
        //Drawable drawable = res.getDrawable(R.drawable.horizontal_progress_bg);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.vehicle_details_manufacturer_warranty_months_indicator);
        mProgress.setProgress(25);   // Main Progress
        mProgress.setSecondaryProgress(50); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        //mProgress.setProgressDrawable(drawable);

       /* ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 50);
        animation.setDuration(990);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/
    }

    void initWarrentyMilesIndicatorProgress() {
        Resources res = getResources();
        //Drawable drawable = res.getDrawable(R.drawable.horizontal_progress_bg);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.vehicle_details_manufacturer_warranty_miles_indicator);
        mProgress.setProgress(25);   // Main Progress
        mProgress.setSecondaryProgress(50); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        //mProgress.setProgressDrawable(drawable);

       /* ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 50);
        animation.setDuration(990);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

        tvVehicleName = (TextView) findViewById(R.id.vehicle_details_name);
        tvMyCarName = (TextView) findViewById(R.id.vehicle_details_my_car_name);
        tvModel = (TextView) findViewById(R.id.vehicle_details_my_car_model);
        //tvMiles = (TextView) findViewById(R.id.vehicle_details_my_car_miles);
        tvVIN = (TextView) findViewById(R.id.vehicle_details_vin_details_tv);
        tvTagExpirationDate = (TextView) findViewById(R.id.vehicle_details_tag_expiration_detail_tv);

        tvVehicleName.setText(vehicleListItem.getName());
        tvMyCarName.setText(vehicleListItem.getName());
        tvModel.setText(vehicleListItem.getVehicle_model()+" • "+vehicleListItem.getVehicle_mileage()+"Miles");
        //tvMiles.setText("  "+vehicleListItem.getVehicle_mileage()+"Miles");
        tvVIN.setText(vehicleListItem.getVehicle_vin());
        tvTagExpirationDate.setText(vehicleListItem.getVehicle_tag_expiration_date());
        buttonEditVehicle = (Button) findViewById(R.id.vehicle_details_edit_vehicle);
        buttonEditVehicle.setOnClickListener((View.OnClickListener)this);
        /*tvNxtSheduleService = (TextView) findViewById(R.id.vehicle_details_my_car_scheduled_next_service);
        tvMake = (TextView) findViewById(R.id.textView_Makeyear);
        tvFuel = (TextView) findViewById(R.id.textView_Fuel);
        tvEngine = (TextView) findViewById(R.id.textView_Engine);
        tvLicenceNo = (TextView) findViewById(R.id.textView_Licence_plateno);
        tvLoanAmountFinanced = (TextView) findViewById(R.id.textView_Amount_Financed);
        tvLoanPaymentTerms = (TextView) findViewById(R.id.textView_Payment_terms);
        tvInsuranceAgentName = (TextView) findViewById(R.id.textView_Agentname);
        tvInsuranceCompanyName = (TextView) findViewById(R.id.textView_Companyname);
        tvInsurancePolicyNo = (TextView) findViewById(R.id.textView_Policy_no);
        tvInsuranceExpiresOn = (TextView) findViewById(R.id.textView_Policy_Expireson);
        tvLastServiceddate = (TextView) findViewById(R.id.textView_LastServiceddate);
        tvUpcomingService = (TextView) findViewById(R.id.textView_UpcomingService);
        tvWarrantyCompanyName = (TextView) findViewById(R.id.textView_ExtendedWarranty_Companyname);
        tvKBBTradeinValue = (TextView) findViewById(R.id.textView_TradeinValue);
        tvKBBPrivatePartyValue = (TextView) findViewById(R.id.textView_PrivatePartyValue);
        ivCallAgent = (ImageView) findViewById(R.id.imageView_CallAgent);
        ivMailtoAgent = (ImageView) findViewById(R.id.imageView_MailToAgent);
        ;*/

        /*ivCallAgent.setOnClickListener((View.OnClickListener) this);
        ivMailtoAgent.setOnClickListener((View.OnClickListener)this);
        buttonEditVehicle.setOnClickListener((View.OnClickListener)this);*/
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
            startActivity(intent);
        }

    }
}
