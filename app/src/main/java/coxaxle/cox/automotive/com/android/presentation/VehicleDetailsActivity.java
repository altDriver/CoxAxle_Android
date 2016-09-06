package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.MyCarDetailsImagesPageAdapter;
import coxaxle.cox.automotive.com.android.common.FontsOverride;

/**
 * Created by Lakshmana on 11-08-2016.
 */
public class VehicleDetailsActivity extends Activity { //implements View.OnClickListener {
    ViewPager mMyCarsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        loadViews();
        //new LoadVehicleDetailsAsync().execute();
    }

    private void loadViews() {

        mMyCarsViewPager = (ViewPager) findViewById(R.id.vehicle_details_images_viewpager);


        mMyCarsViewPager.setAdapter(new MyCarDetailsImagesPageAdapter(this));

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
    }

    /*ImageView ivCallAgent, ivMailtoAgent;
    TextView tvVehicleName, tvMake, tvMiles, tvFuel, tvEngine, tvVIN, tvLicenceNo, tvTagExpirationDate, tvLoanAmountFinanced,
            tvLoanPaymentTerms, tvInsuranceAgentName, tvInsuranceCompanyName, tvInsurancePolicyNo, tvInsuranceExpiresOn, tvLastServiceddate,
            tvUpcomingService, tvWarrantyCompanyName, tvKBBTradeinValue, tvKBBPrivatePartyValue;
    ProgressDialog progressDialog;
    Button buttonEditVehicle;



    private void LoadViews() {
        tvVehicleName = (TextView) findViewById(R.id.textView_VehicleName);
        tvMake = (TextView) findViewById(R.id.textView_Makeyear);
        tvMiles = (TextView) findViewById(R.id.textView_Miles);
        tvFuel = (TextView) findViewById(R.id.textView_Fuel);
        tvEngine = (TextView) findViewById(R.id.textView_Engine);
        tvVIN = (TextView) findViewById(R.id.textView_VIN_VehicleDetails);
        tvLicenceNo = (TextView) findViewById(R.id.textView_Licence_plateno);
        tvTagExpirationDate = (TextView) findViewById(R.id.textView_Tag_Expiration);
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
        buttonEditVehicle = (Button) findViewById(R.id.button_EditVehicle);

        ivCallAgent.setOnClickListener(this);
        ivMailtoAgent.setOnClickListener(this);
        buttonEditVehicle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivCallAgent) {
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
        }
        if (v == buttonEditVehicle) {
            Intent intent = new Intent(VehicleDetailsActivity.this, AddVehicleActivity.class);
            intent.putExtra("edit_vehicle", 1);
            startActivity(intent);
        }
    }

    private class LoadVehicleDetailsAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(VehicleDetailsActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VEHICLE_INFO_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(VehicleDetailsActivity.this, response, Toast.LENGTH_LONG).show();
                                Log.v("response1>>>", "" + response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(VehicleDetailsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                Log.v("error>>>", "" + error);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("vid", "TS");
                        params.put("uid", "1");

                        Log.v("params>>>", "" + params);
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(VehicleDetailsActivity.this);
                requestQueue.add(stringRequest);

                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }
    }*/
}
