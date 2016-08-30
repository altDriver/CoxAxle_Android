package coxaxle.cox.automotive.com.coxaxle.presentation;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.common.Utility;
/**
 * Created by Lakshman on 24-08-2016.
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnNext;
    LinearLayout llNew, llUsed, llCPO;
    TextView tvNew, tvUsed, tvCPO, tvAddPhotoText;
    ImageView imgAddPhoto, imgAddPhotoMain;
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
    private SimpleDateFormat dateFormatter;
    String strphotob64, strType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_vehicle);

        loadViews();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        setTagExpirationDate();

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1980; i--) {
            years.add(Integer.toString(i));
        }
        yearArray = new String[years.size()];
        yearArray = years.toArray(yearArray);
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

    @Override
    public void onClick(View view) {
        if (view == llNew) {
            strType = "New";
            tvNew.setTypeface(null, Typeface.BOLD);
            tvUsed.setTypeface(null, Typeface.NORMAL);
            tvCPO.setTypeface(null, Typeface.NORMAL);
        }
        if (view == llUsed) {
            strType = "Used";
            tvNew.setTypeface(null, Typeface.NORMAL);
            tvUsed.setTypeface(null, Typeface.BOLD);
            tvCPO.setTypeface(null, Typeface.NORMAL);
        }
        if (view == llCPO) {
            strType = "CPO";
            tvNew.setTypeface(null, Typeface.NORMAL);
            tvUsed.setTypeface(null, Typeface.NORMAL);
            tvCPO.setTypeface(null, Typeface.BOLD);
        }
        if (view == imgAddPhoto) {
            selectImage();
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
            Boolean valid_vehicle_photo = Utility.CommonValidation(strphotob64);

            if (!valid_vin) {
                Toast.makeText(AddVehicleActivity.this, "Enter 17 digit VIN", Toast.LENGTH_SHORT).show();
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
                HashMap<String,String> page1Values = new HashMap<String, String>();
                page1Values.put("vehicle_name",strname);
                page1Values.put("vehicle_vin",strVIN);
                page1Values.put("vehicle_make",strMake);
                page1Values.put("vehicle_model",strModel);
                page1Values.put("vehicle_year",strYear);
                page1Values.put("vehicle_miles",strMiles);
                page1Values.put("vehicle_tagexpiration",strTagExp);
                page1Values.put("vehicle_type",strType);
                page1Values.put("vehicle_photo", strphotob64);

                Intent intent = new Intent(AddVehicleActivity.this, AddVehicle2of4Activity.class);
                intent.putExtra("Page1Values", page1Values);
                startActivity(intent);
            }
        }
    }
    public void loadViews()
    {
        //Typeface fontStyle = Typeface.createFromAsset(getAssets(), "font/HelveticaNeue.ttf");
        //Typeface fontStyleBold = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");

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
        imgAddPhotoMain = (ImageView) findViewById(R.id.AddVehicle_AddImageMain_iv);

        llNew = (LinearLayout) findViewById(R.id.AddVehicle_New_ll);
        llUsed = (LinearLayout) findViewById(R.id.AddVehicle_Used_ll);
        llCPO = (LinearLayout) findViewById(R.id.AddVehicle_CPO_ll);
        llNew.setOnClickListener(this);
        llUsed.setOnClickListener(this);
        llCPO.setOnClickListener(this);
        imgAddPhoto.setOnClickListener(this);
        btnNext.setOnClickListener(this);

//        tvNew.setTypeface(fontStyle);
//        tvUsed.setTypeface(fontStyle);
//        tvCPO.setTypeface(fontStyle);
//        tvAddPhotoText.setTypeface(fontStyle);
//        etSelectYear.setTypeface(fontStyleBold);
//        etVehicleName.setTypeface(fontStyleBold);
//        etVin.setTypeface(fontStyleBold);
//        etMake.setTypeface(fontStyleBold);
//        etModel.setTypeface(fontStyleBold);
//        etMilesDriven.setTypeface(fontStyleBold);
//        etTagExpiration.setTypeface(fontStyleBold);
//        btnNext.setTypeface(fontStyleBold);
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddVehicleActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= coxaxle.cox.automotive.com.coxaxle.common.Permissions.checkPermission(AddVehicleActivity.this);

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
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

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


        imgAddPhotoMain.setImageBitmap(thumbnail);
        imgAddPhoto.setImageBitmap(null);
        tvAddPhotoText.setText(null);
        // Convert bitmap to byte[]
        byte[] photo1_byte = convertBitmapToByteArray(thumbnail);
        // convert byteArray to base64 String
        strphotob64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgAddPhotoMain.setImageBitmap(bm);
        imgAddPhoto.setImageBitmap(null);
        tvAddPhotoText.setText(null);
        // Convert bitmap to byte[]
        byte[] photo1_byte = convertBitmapToByteArray(bm);
        // convert byteArray to base64 String
        strphotob64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);

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
        title_txt.setTypeface(null, Typeface.BOLD);
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

}
