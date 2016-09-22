package coxaxle.cox.automotive.com.android.presentation;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.MyCustomDialog;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;
import coxaxle.cox.automotive.com.android.model.VehicleInfo;

/**
 * Created by Lakshman on 24-08-2016.
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener, MyCustomDialog.onSubmitListener{
    Button btnNext, btnDelete, btnFindVIN;
    LinearLayout llNew, llUsed, llCPO;
    TextView tvNew, tvUsed, tvCPO, tvAddPhotoText, tvVehicleDetailsHeader;
    ImageView imgAddPhoto; //imgAddPhotoMain
    EditText etVehicleName, etVin, etMake, etModel, etMilesDriven, etTagExpiration, etSelectYear;
    final String[] modelitems = { "Accent", "Azera", "Elantra", "SE Sedan 4D" , "Limited Sedan 4D", "Sport Sedan 4D", "Coupe 2D", "GT Hatchback 4D", "Equus", "Genesis", "Genesis Coupe", "Santa Fe", "Santa Fe Sport"};
    final String[] makeitems = { "Acura", "Alfa Romeo", "Aston Martin", "Audi" , "Bentley", "BMW", "Bugatti", "Buick"};
    String[] yearArray;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    static final int DATE_PICKER_ID = 1111;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private DatePickerDialog tagDatePickerDialog;
    private SimpleDateFormat dateFormatter, dateFormateToSend;
    String strphotob64, strType, navToActivity;
    Typeface fontBoldHelvetica, fontNormalHelvetica;
    //Edit vehile details
    VehicleInfo vehicleListItem;
    int flag;

    ArrayList<Bitmap> bitmapArray;
    ViewPager AddCarsViewPager;
    LinearLayout llDotsCount;
    private ImageView[] dots;
    private int dotsCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_vehicle);
        fontNormalHelvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        loadViews();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        dateFormateToSend = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setTagExpirationDate();
        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1980; i--) {
            years.add(Integer.toString(i));
        }
        yearArray = new String[years.size()];
        yearArray = years.toArray(yearArray);

        bitmapArray = new ArrayList<>();

        //Edit Vehicle
        flag = this.getIntent().getIntExtra("Vehicle_Flag", 0);
        navToActivity = this.getIntent().getStringExtra("navToActivity");
        if(flag == 1)
        {
            vehicleListItem = this.getIntent().getParcelableExtra("VehicleInfo");
            setDataToView();
        }

        etVehicleName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etVehicleName.setTypeface(fontBoldHelvetica);
                else
                    etVehicleName.setTypeface(fontNormalHelvetica);
            }
        });
        etMake.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etMake.setTypeface(fontBoldHelvetica);
                else
                    etMake.setTypeface(fontNormalHelvetica);
            }
        });
        etVin.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etVin.setTypeface(fontBoldHelvetica);
                else
                    etVin.setTypeface(fontNormalHelvetica);
            }
        });
        etModel.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etModel.setTypeface(fontBoldHelvetica);
                else
                    etModel.setTypeface(fontNormalHelvetica);
            }
        });
        etSelectYear.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etSelectYear.setTypeface(fontBoldHelvetica);
                else
                    etSelectYear.setTypeface(fontNormalHelvetica);
            }
        });
        etTagExpiration.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etTagExpiration.setTypeface(fontBoldHelvetica);
                else
                    etTagExpiration.setTypeface(fontNormalHelvetica);
            }
        });
        etMilesDriven.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    etMilesDriven.setTypeface(fontBoldHelvetica);
                else
                    etMilesDriven.setTypeface(fontNormalHelvetica);
            }
        });
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    AlertDialogView(makeitems, "Select Make", 1);
                }
            }
        });
        etModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    AlertDialogView(modelitems, "Select Model", 2);
                }
            }
        });
        etTagExpiration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    //showDialog(DATE_PICKER_ID);
                    tagDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    tagDatePickerDialog.show();
                }
            }
        });
        etSelectYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    if(b) {

                        AlertDialogView(yearArray, "Select Year", 3);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public  void setDataToView()
    {
        tvVehicleDetailsHeader.setText("Edit vehicle");
        btnDelete.setVisibility(View.VISIBLE);
        etVehicleName.setText(vehicleListItem.name);
        etVin.setText(vehicleListItem.vehicle_vin);
        etMake.setText(vehicleListItem.vehicle_make);
        etModel.setText(vehicleListItem.vehicle_model);
        etMilesDriven.setText(vehicleListItem.vehicle_mileage);
        String strDate = vehicleListItem.vehicle_tag_expiration_date;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(strDate);
            strDate = dateFormatter.format(date.getTime());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        etTagExpiration.setText(strDate);
        etSelectYear.setText(vehicleListItem.vehicle_year);

        strType = vehicleListItem.vehicle_type;
        if(strType.equalsIgnoreCase("New")) {
            tvNew.setTypeface(fontBoldHelvetica);
            tvUsed.setTypeface(fontNormalHelvetica);
            tvCPO.setTypeface(fontNormalHelvetica);
        }else if(strType.equalsIgnoreCase("Used")) {
            tvNew.setTypeface(fontNormalHelvetica);
            tvUsed.setTypeface(fontBoldHelvetica);
            tvCPO.setTypeface(fontNormalHelvetica);
        }
        else {
            tvNew.setTypeface(fontNormalHelvetica);
            tvUsed.setTypeface(fontNormalHelvetica);
            tvCPO.setTypeface(fontBoldHelvetica);
        }

        if (vehicleListItem.vehicle_image.size() > 0) {
            AddCarsViewPager.setVisibility(View.VISIBLE);
            imgAddPhoto.setVisibility(View.INVISIBLE);
            tvAddPhotoText.setVisibility(View.INVISIBLE);
            ArrayList<String> arrImages = vehicleListItem.vehicle_image;
            ArrayList<String> arrimages = new ArrayList<>();
            for(int i =0; i< arrImages.size(); i++) {
                String strImg = arrImages.get(i);
                strImg.replace("\\", "");
                arrimages.add(strImg);
            }

            new GetImagesFromServer().execute(arrimages);

                /*Cache cache = AxleApplication.getInstance().getRequestQueue().getCache();
                Cache.Entry entry = cache.get(strImg);
                if(entry != null){
                    try {
                        String data = new String(entry.data, "UTF-8");
                        // handle data, like converting it to xml, json, bitmap etc.,


                        byte[] encodeByte = Base64.decode(data, Base64.URL_SAFE);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        bitmapArray.add(bitmap);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else{
                // Cached response doesn't exists. Make network call here
                    try
                    {
                        imageLoader.get(strImg,
                                new ImageLoader.ImageListener(){

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }

                                    @Override
                                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                                            Bitmap bpm = response.getBitmap();
                                            if (bpm != null)
                                                bitmapArray.add(bpm);

                                    }
                                });
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }*/


        } else {
            AddCarsViewPager.setVisibility(View.INVISIBLE);
            llDotsCount.removeAllViews();
            imgAddPhoto.setVisibility(View.VISIBLE);
            tvAddPhotoText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == llNew) {
            strType = "New";
            tvNew.setTypeface(fontBoldHelvetica);
            tvUsed.setTypeface(fontNormalHelvetica);
            tvCPO.setTypeface(fontNormalHelvetica);
        }
        if (view == llUsed) {
            strType = "Used";
            tvNew.setTypeface(fontNormalHelvetica);
            tvUsed.setTypeface(fontBoldHelvetica);
            tvCPO.setTypeface(fontNormalHelvetica);
        }
        if (view == llCPO) {
            strType = "CPO";
            tvNew.setTypeface(fontNormalHelvetica);
            tvUsed.setTypeface(fontNormalHelvetica);
            tvCPO.setTypeface(fontBoldHelvetica);
        }
        if (view == imgAddPhoto) {
            AddCarsViewPager.setVisibility(View.VISIBLE);
            if(bitmapArray.size() <= 5)
                selectImage();
            else
                Toast.makeText(getApplicationContext(), "You can't add more than 5 Vehicle Images. Please delete image", Toast.LENGTH_SHORT).show();
        }
        if(view == btnDelete)
        {
            MyCustomDialog fragmentDialog = new MyCustomDialog();
            fragmentDialog.mListener = AddVehicleActivity.this;
            fragmentDialog.setDialog(R.layout.custom_dialog, AddVehicleActivity.this, 1, "Cox Axle", "Are you sure want to delete vehicle?", "Ok", "Cancel");
            fragmentDialog.show(getFragmentManager(), "");
            //deleteVehicle();
        }
        if(view == btnFindVIN)
        {
            MyCustomDialog fragmentDialog = new MyCustomDialog();
            fragmentDialog.mListener = AddVehicleActivity.this;
            fragmentDialog.setDialog(R.layout.find_my_vin_dialog, AddVehicleActivity.this, 0, "", "", "", "");
            fragmentDialog.show(getFragmentManager(), "");
        }
        if(view == btnNext)
        {
            String strname = etVehicleName.getText().toString();
            String strVIN = etVin.getText().toString();
            String strMake = etMake.getText().toString();
            String strModel = etModel.getText().toString();
            String strYear = etSelectYear.getText().toString();
            String strMiles = etMilesDriven.getText().toString();
            String strTagExp = etTagExpiration.getText().toString();

            Boolean valid_vin = Utility.VinValidation(strVIN);
            Boolean valid_vehicle_type = Utility.CommonValidation(strType);
            Boolean valid_vehicle_make = Utility.CommonValidation(strMake);
            Boolean valid_vehicle_model = Utility.CommonValidation(strModel);
            Boolean valid_vehicle_year = Utility.VehicleYearValidation(strYear);
            Boolean valid_name = Utility.CommonValidation(strname);
            Boolean valid_tagExp = Utility.CommonValidation(strTagExp);

            Boolean valid_vehicle_photo;
            if (bitmapArray.size() > 0) {
                valid_vehicle_photo = true;
                for (int i = 0; i < bitmapArray.size(); i++) {
                    Bitmap imgbitmap = bitmapArray.get(i);
                    byte[] photo1_byte = convertBitmapToByteArray(imgbitmap);
                    String strphotoInsurance64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
                    strphotob64 = strphotoInsurance64 + ",";
                }
            }else
                valid_vehicle_photo = false;

            if (!valid_vin) {
                Toast.makeText(AddVehicleActivity.this, "Enter 16 digit VIN", Toast.LENGTH_SHORT).show();
                etVin.requestFocus();
            }else if (!valid_vehicle_type) {
                Toast.makeText(AddVehicleActivity.this, "Select valid Vehicle Type", Toast.LENGTH_SHORT).show();
            }else if (!valid_vehicle_photo) {
                Toast.makeText(AddVehicleActivity.this, "Select valid Vehicle Photo", Toast.LENGTH_SHORT).show();
            }else if (!valid_vehicle_make) {
                Toast.makeText(AddVehicleActivity.this, "Enter valid Vehicle Make", Toast.LENGTH_SHORT).show();
                etMake.requestFocus();
            }else if (!valid_vehicle_model) {
                Toast.makeText(AddVehicleActivity.this, "Enter valid Vehicle Model", Toast.LENGTH_SHORT).show();
                etModel.requestFocus();
            }else if (!valid_vehicle_year) {
                Toast.makeText(AddVehicleActivity.this, "Enter valid Vehicle Year", Toast.LENGTH_SHORT).show();
                etSelectYear.requestFocus();
            }else if (!valid_name) {
                Toast.makeText(AddVehicleActivity.this, "Enter valid Vehicle Name", Toast.LENGTH_SHORT).show();
                etVehicleName.requestFocus();
            }else if (!valid_tagExp) {
                Toast.makeText(AddVehicleActivity.this, "Enter valid Tag Expiration date", Toast.LENGTH_SHORT).show();
                etTagExpiration.requestFocus();
            }else{
                try {

                    HashMap<String, String> page1Values = new HashMap<String, String>();
                    page1Values.put("vehicle_name", strname);
                    page1Values.put("vehicle_vin", strVIN);
                    page1Values.put("vehicle_make", strMake);
                    page1Values.put("vehicle_model", strModel);
                    page1Values.put("vehicle_year", strYear);
                    page1Values.put("vehicle_miles", strMiles);

                    Date date = dateFormatter.parse(strTagExp);
                    strTagExp = dateFormateToSend.format(date.getTime());
                    page1Values.put("vehicle_tagexpiration", strTagExp);
                    page1Values.put("vehicle_type", strType);
                    // if (strphotob64.charAt(strphotob64.length() - 1) == ',')
                    //strphotob64 = strphotob64.substring(0, strphotob64.length() - 1);
                    page1Values.put("vehicle_photo", strphotob64);

                    if (flag == 1) {
                        Intent intent = new Intent(AddVehicleActivity.this, AddVehicle2of4Activity.class);

                        Bundle extras = new Bundle();
                        extras.putParcelable("VehicleInfo", vehicleListItem);
                        extras.putSerializable("Page1Values", page1Values);
                        extras.putString("navToActivity", navToActivity);
                        extras.putInt("Vehicle_Flag", 1);
                        intent.putExtras(extras);

                        //intent.putExtra("Page1Values", page1Values);
                        //intent.putExtra("VehicleInfo", vehicleListItem);
                        //intent.putExtra("Vehicle_Flag", 1);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(AddVehicleActivity.this, AddVehicle2of4Activity.class);
                        //intent.putExtra("Page1Values", page1Values);
                        Bundle extras = new Bundle();
                        extras.putSerializable("Page1Values", page1Values);
                        extras.putInt("Vehicle_Flag", 0);
                        extras.putString("navToActivity", navToActivity);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
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
                                    Intent intent = new Intent(AddVehicleActivity.this, HomeScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                                Toast.makeText(AddVehicleActivity.this, strMessage, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error delete vehicle>>>", "" + error);
                            Toast.makeText(AddVehicleActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    UserSessionManager obj = new UserSessionManager(AddVehicleActivity.this);
                    String strUid = obj.getUserId();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("vid", vehicleListItem.id);
                    params.put("uid", strUid);

                    Log.v("params>>>", "" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(AddVehicleActivity.this);
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadViews()
    {
        llDotsCount = (LinearLayout) findViewById(R.id.AddVehicle_viewPagerCountDots);
        AddCarsViewPager = (ViewPager) findViewById(R.id.Add_vehicle_viewpager);
        tvVehicleDetailsHeader = (TextView) findViewById(R.id.AddVehicle_VehicleDetailsHeader_tv);
        tvNew = (TextView)findViewById(R.id.AddVehicle_New_tv);
        tvUsed  = (TextView)findViewById(R.id.AddVehicle_Used_tv);
        tvCPO = (TextView)findViewById(R.id.AddVehicle_CPO_tv);
        tvAddPhotoText = (TextView)findViewById(R.id.AddVehicle_AddPhotoText_tv);
        etVehicleName = (EditText) findViewById(R.id.AddVehicle_VehicleName_et);
        etVin = (EditText) findViewById(R.id.AddVehicle_VIN_et);
        etMake = (EditText) findViewById(R.id.AddVehicle_Make_et);
        etModel = (EditText) findViewById(R.id.Addvehicle_Model_et);
        etMilesDriven = (EditText) findViewById(R.id.AddVehicle_MilesDriven_et);
        etTagExpiration = (EditText) findViewById(R.id.AddVehicle_TagExpiration_et);
        etSelectYear = (EditText) findViewById(R.id.AddVehicle_SelectYear_et);
        imgAddPhoto = (ImageView) findViewById(R.id.AddVehicle_AddImage_iv);
        btnNext = (Button) findViewById(R.id.AddVehicle_next_button);
        btnDelete = (Button) findViewById(R.id.Add_Vehicle_Delete_vehicle);
        btnFindVIN = (Button) findViewById(R.id.AddVehicle_findMyVIN);
        btnFindVIN.setPaintFlags(btnFindVIN.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        llNew = (LinearLayout) findViewById(R.id.AddVehicle_New_ll);
        llUsed = (LinearLayout) findViewById(R.id.AddVehicle_Used_ll);
        llCPO = (LinearLayout) findViewById(R.id.AddVehicle_CPO_ll);
        llNew.setOnClickListener(this);
        llUsed.setOnClickListener(this);
        llCPO.setOnClickListener(this);
        imgAddPhoto.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnFindVIN.setOnClickListener(this);

        btnNext.setTypeface(fontBoldHelvetica);
        tvVehicleDetailsHeader.setTypeface(fontBoldHelvetica);
        btnDelete.setTypeface(fontNormalHelvetica);

        AddCarsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
                }
                dots[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void drawPageSelectionIndicator() {
        if (llDotsCount != null) {
            llDotsCount.removeAllViews();
        }
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(AddVehicleActivity.this);
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_unselected, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            llDotsCount.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.item_selected, null));
    }

    public void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddVehicleActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= coxaxle.cox.automotive.com.android.common.Permissions.checkPermission(AddVehicleActivity.this);

                if (items[item].equals("Take Photo")) {
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    if(result)
                        galleryIntent();

                }
            }
        });
        builder.show();
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(thumbnail != null) {
            bitmapArray.add(0, thumbnail);
            AddCarsViewPager.setAdapter(new AddCarsViewPagerAdapter(this, bitmapArray));
            AddCarsViewPager.getAdapter().notifyDataSetChanged();
            AddCarsViewPager.setOffscreenPageLimit(bitmapArray.size() - 1);
            dotsCount = bitmapArray.size();
            drawPageSelectionIndicator();
            imgAddPhoto.setVisibility(View.INVISIBLE);
            tvAddPhotoText.setVisibility(View.INVISIBLE);
        }
        /*else
        {
            AddCarsViewPager.setVisibility(View.INVISIBLE);
            llDotsCount.removeAllViews();
            imgAddPhoto.setVisibility(View.VISIBLE);
            tvAddPhotoText.setVisibility(View.VISIBLE);
        }*/
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bm != null) {
            bitmapArray.add(0, bm);
            AddCarsViewPager.setAdapter(new AddCarsViewPagerAdapter(this, bitmapArray));
            AddCarsViewPager.getAdapter().notifyDataSetChanged();
            AddCarsViewPager.setOffscreenPageLimit(bitmapArray.size() - 1);
            dotsCount = bitmapArray.size();
            drawPageSelectionIndicator();
            imgAddPhoto.setVisibility(View.INVISIBLE);
            tvAddPhotoText.setVisibility(View.INVISIBLE);
        }
    }
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        } else {
            byte[] b = null;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                b = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return b;
        }
    }

    private void AlertDialogView(final String[] items, String title, final int position)
    {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                AddVehicleActivity.this, R.layout.alertdialog_custom_view, items);

        AlertDialog.Builder builder = new AlertDialog.Builder(AddVehicleActivity.this);
        builder.setTitle(title);
        TextView title_txt = new TextView(this);
        title_txt.setGravity(Gravity.CENTER);
        title_txt.setText(title);
        title_txt.setHeight(150);
        title_txt.setTypeface(fontBoldHelvetica);
        title_txt.setTextColor(ContextCompat.getColor(AddVehicleActivity.this, R.color.colorTextDark));
        title_txt.setTextSize(17);
        builder.setCustomTitle(title_txt);

        builder.setSingleChoiceItems(adapter, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(position == 1)
                            etMake.setText(items[item]);
                        if(position == 2)
                            etModel.setText(items[item]);
                        if(position == 3)
                            etSelectYear.setText(items[item]);
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_PICKER_ID:
//                return new DatePickerDialog(this, pickerListener, year, month,day);
//        }
//        return null;
//    }
//
//    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
//
//            year  = selectedYear;
//            month = selectedMonth;
//            day   = selectedDay;
//
//            etTagExpiration.setText(new StringBuilder().append(month + 1)
//                    .append("/").append(day).append("/").append(year).append(" "));
//
//        }
//    };

    public void setTagExpirationDate()
    {
        Calendar newCalendar = Calendar.getInstance();
        tagDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTagExpiration.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void setOnSubmitListener(int flag) {
        if(flag == 1)
            deleteVehicle();
    }


    /**
     * Created by Lakshmana on 16-09-2016.
     */
    public class AddCarsViewPagerAdapter  extends PagerAdapter {

        private Context ctx;
        ArrayList<Bitmap> objArrayList;
        LayoutInflater mLayoutInflater;

        public AddCarsViewPagerAdapter(Context context, ArrayList<Bitmap> objList) {
            this.ctx = context;
            objArrayList = objList;
            mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return objArrayList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


        @Override
        public Object instantiateItem(final ViewGroup collection, final int position) {
            View view = mLayoutInflater.inflate(R.layout.addcars_viewpager_row, null);

            ImageView imgCar = (ImageView) view.findViewById(R.id.add_car_image_row);
            ImageView imgClose = (ImageView) view.findViewById(R.id.AddVehicle_ImageCross_iv);

            if (objArrayList.size() > 0) {
                imgCar.setImageBitmap(objArrayList.get(position));
            } else
                imgCar.setImageResource(R.mipmap.placeholder);

            imgCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bitmapArray.size() < 5)
                        selectImage();
                    else
                        Toast.makeText(getApplicationContext(), "You can't add more than 5 Vehicle Images. Please delete image", Toast.LENGTH_SHORT).show();
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    collection.removeViewAt(position);
                    bitmapArray.remove(position);

                    if(bitmapArray.size()>0) {
                        AddCarsViewPager.setAdapter(new AddCarsViewPagerAdapter(AddVehicleActivity.this, bitmapArray));
                        AddCarsViewPager.getAdapter().notifyDataSetChanged();
                        AddCarsViewPager.setOffscreenPageLimit(bitmapArray.size() - 1);
                        dotsCount = bitmapArray.size();
                        drawPageSelectionIndicator();
                    }else {
                        AddCarsViewPager.setAdapter(null);
                        AddCarsViewPager.setVisibility(View.INVISIBLE);
                        llDotsCount.removeAllViews();
                        imgAddPhoto.setVisibility(View.VISIBLE);
                        tvAddPhotoText.setVisibility(View.VISIBLE);
                    }
                }
            });

            ((ViewPager) collection).addView(view);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            //((ViewPager) collection).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public void startUpdate(ViewGroup arg0) {
        }

        @Override
        public void finishUpdate(ViewGroup arg0) {
        }
    }


    public class GetImagesFromServer extends AsyncTask<ArrayList<String>, Void, Void>
    {
        @Override
        protected Void doInBackground(ArrayList<String> ... imageUrl) {
            // TODO Auto-generated method stub
            try {
                ArrayList<String> arrayList = imageUrl[0];
                for (int i = 0; i < arrayList.size(); i++) {
                    String strImg = arrayList.get(i);
                    URL url = new URL(strImg);
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    if(image != null)
                        bitmapArray.add(image);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(bitmapArray.size()>0) {
                AddCarsViewPager.setAdapter(new AddCarsViewPagerAdapter(AddVehicleActivity.this, bitmapArray));
                AddCarsViewPager.getAdapter().notifyDataSetChanged();
                AddCarsViewPager.setOffscreenPageLimit(bitmapArray.size() - 1);
                dotsCount = bitmapArray.size();
                drawPageSelectionIndicator();
            }else {
                AddCarsViewPager.setAdapter(null);
                AddCarsViewPager.setVisibility(View.INVISIBLE);
                llDotsCount.removeAllViews();
                imgAddPhoto.setVisibility(View.VISIBLE);
                tvAddPhotoText.setVisibility(View.VISIBLE);
            }
        }
    }

}
