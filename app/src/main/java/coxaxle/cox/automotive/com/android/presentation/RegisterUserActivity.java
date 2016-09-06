package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.Permissions;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;


public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaration of EditText variable names
    private AutoCompleteTextView firstname;
    private AutoCompleteTextView lastname;
    private AutoCompleteTextView password;
    private AutoCompleteTextView email;
    private AutoCompleteTextView phone;
    private AutoCompleteTextView zip_code;
    private AutoCompleteTextView vehicle_vin;
    private AutoCompleteTextView vehicle_type;
    private AutoCompleteTextView vehicle_make;
    private AutoCompleteTextView vehicle_model;
    private AutoCompleteTextView vehicle_year;
    private AutoCompleteTextView vehicle_color;
    private AutoCompleteTextView warranty_from;
    private AutoCompleteTextView warranty_to;
    private AutoCompleteTextView extended_warranty_from;
    private AutoCompleteTextView extended_warranty_to;
    private AutoCompleteTextView loan_amount;
    private AutoCompleteTextView emi;
    private AutoCompleteTextView interest;
    private AutoCompleteTextView loan_tenure;
    private AutoCompleteTextView renewal_date;
    private AutoCompleteTextView trim;
    private AutoCompleteTextView style;
    private AutoCompleteTextView mileage;
    private AutoCompleteTextView ownership_type;
    //Declaration of Button variable names
    private Button vehicle_photo1;
    private Button vehicle_photo2;
    private Button vehicle_photo3;
    private Button manual;
    private Button insurance_document;
    private Button sign_up;
    //Declaration of ImageView variable names
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private ImageView manualimg;
    private ImageView insuranceimg;
    private ImageView warrenty_from_calender_pic;
    private ImageView warrenty_to_calender_pic;
    private ImageView extnd_warrenty_from_calender_pic;
    private ImageView extnd_warrenty_to_calender_pic;
    private ImageView renual_iv;
    //Declaration of Calender variable names
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog extndfromDatePickerDialog;
    private DatePickerDialog extndtoDatePickerDialog;
    private DatePickerDialog renualDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    // String declations for Registration variables
    String first_name_str;
    String last_name_str;
    String password_str;
    String email_str;
    String phone_str;
    String user_type_str = "user";
    String vehicle_type_str;
    String dealercode_str = "KH001";
    String dl_expiry_date_str = "5";
    String zipcode_str;
    String userlanguage_str = "EN";
    String dongle_name_str = "WIFI";
    String vehicle_vin_str;
    String pswd_base64;
    String vehicle_make_str;
    String vehicle_model_str;
    String vehicle_year_str;
    String vehicle_color_str;
    String vehicle_emi_str;
    String vehicle_interest_str;
    String vehicle_loan_tenure_str;
    String vehicle_loan_amount_str;
    String vehicle_warranty_from_str;
    String vehicle_warranty_to_str;
    String vehicle_extended_warranty_from_str;
    String vehicle_extended_warranty_to_str;
    String renewal_date_str;
    String vehicle_trim_str;
    String vehicle_style_str;
    String vehicle_mileage_str;
    String vehicle_ownership_type_str;
    private String photo1String;
    private String photo2String;
    private String photo3String;
    String manual_String;
    String insurance_String;

    //declations for Camera & Gallery  Identification
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Declaration of AutoCompleteTextView values
        firstname = (AutoCompleteTextView) findViewById(R.id.user_firstname);
        lastname = (AutoCompleteTextView) findViewById(R.id.user_lastname);
        password = (AutoCompleteTextView) findViewById(R.id.user_password);
        email = (AutoCompleteTextView) findViewById(R.id.user_email);
        phone = (AutoCompleteTextView) findViewById(R.id.user_phonenumber);
        zip_code = (AutoCompleteTextView) findViewById(R.id.user_zipcode);
        vehicle_vin = (AutoCompleteTextView) findViewById(R.id.user_vehicle_vin);
        vehicle_type = (AutoCompleteTextView) findViewById(R.id.user_vehicle_type);
        vehicle_make = (AutoCompleteTextView) findViewById(R.id.user_vehicle_make);
        vehicle_model = (AutoCompleteTextView) findViewById(R.id.user_vehicle_model);
        vehicle_year = (AutoCompleteTextView) findViewById(R.id.user_vehicle_year);
        vehicle_color = (AutoCompleteTextView) findViewById(R.id.user_vehicle_color);
        warranty_from = (AutoCompleteTextView) findViewById(R.id.user_vehicle_warrentyfrom);
        warranty_to = (AutoCompleteTextView) findViewById(R.id.user_vehicle_warrentyto);
        extended_warranty_from = (AutoCompleteTextView) findViewById(R.id.user_vehicle_extended_warrentyfrom);
        extended_warranty_to = (AutoCompleteTextView) findViewById(R.id.user_vehicle_extended_warrentyto);
        loan_amount = (AutoCompleteTextView) findViewById(R.id.user_vehicle_loanamount);
        emi = (AutoCompleteTextView) findViewById(R.id.user_vehicle_emi);
        interest = (AutoCompleteTextView) findViewById(R.id.user_vehicle_interest);
        loan_tenure = (AutoCompleteTextView) findViewById(R.id.user_vehicle_loantenure);
        renewal_date = (AutoCompleteTextView) findViewById(R.id.user_vehicle_renewal_date);
        trim = (AutoCompleteTextView) findViewById(R.id.user_vehicle_trim);
        style = (AutoCompleteTextView) findViewById(R.id.user_vehicle_style);
        mileage = (AutoCompleteTextView) findViewById(R.id.user_vehicle_mileage);
        ownership_type = (AutoCompleteTextView) findViewById(R.id.user_vehicle_ownership_type);
        //Declaration of Button values
        vehicle_photo1 = (Button) findViewById(R.id.user_vehicle_imagebutton1);
        vehicle_photo2 = (Button) findViewById(R.id.user_vehicle_imagebutton2);
        vehicle_photo3 = (Button) findViewById(R.id.user_vehicle_imagebutton3);
        manual = (Button) findViewById(R.id.user_manual_button);
        insurance_document = (Button) findViewById(R.id.user_insurence_document);
        sign_up = (Button) findViewById(R.id.usersign_up_button);
        //Declaration of ImageView values
        photo1 = (ImageView) findViewById(R.id.user_vehicle_image1);
        photo2 = (ImageView) findViewById(R.id.user_vehicle_image2);
        photo3 = (ImageView) findViewById(R.id.user_vehicle_image3);
        manualimg = (ImageView) findViewById(R.id.user_manual_img);
        insuranceimg = (ImageView) findViewById(R.id.user_insurence_img);
        warrenty_from_calender_pic = (ImageView) findViewById(R.id.user_warrenty_from_calender_pic);
        warrenty_to_calender_pic = (ImageView) findViewById(R.id.user_warrenty_to_calender_pic);
        extnd_warrenty_from_calender_pic = (ImageView) findViewById(R.id.user_extended_warrenty_from_calender_pic);
        extnd_warrenty_to_calender_pic = (ImageView) findViewById(R.id.user_extended_warrenty_to_calender_pic);
        renual_iv = (ImageView) findViewById(R.id.user_renewal_date_img);

        //Declaration of Calendar values
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

        //button click listener for vehicle_photo1
        vehicle_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = "photo1";
                selectImage();
            }
        });
        //button click listener for vehicle_photo2
        vehicle_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = "photo2";
                selectImage();
            }
        });
        //button click listener for vehicle_photo3
        vehicle_photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = "photo3";
                selectImage();
            }
        });
        //button click listener for vehicle_manual
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = "photo_manual";
                selectImage();
            }
        });
        //button click listener show file chooser
        insurance_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = "photo_insurance";
                selectImage();
            }
        });
        //button click listener for User_Signup
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name_str = firstname.getText().toString().trim();
                last_name_str  = lastname.getText().toString().trim();
                password_str = password.getText().toString().trim();
                Boolean valid_password = Utility.passwordValidation(password_str);

                // Declaration of Encrypt Password
                try {
                    byte[] data = password.getText().toString().getBytes("UTF-8");
                    password_str = Base64.encodeToString(data, Base64.DEFAULT);
                    Log.v("pswd_base64>>",""+pswd_base64);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                email_str = email.getText().toString().trim();
                phone_str = phone.getText().toString().trim();
                zipcode_str = zip_code.getText().toString().trim();
                vehicle_vin_str = vehicle_vin.getText().toString().trim();
                vehicle_type_str = vehicle_type.getText().toString().trim();
                vehicle_make_str = vehicle_make.getText().toString().trim();
                vehicle_model_str = vehicle_model.getText().toString().trim();
                vehicle_year_str = vehicle_year.getText().toString().trim();
                vehicle_color_str = vehicle_color.getText().toString().trim();
                vehicle_emi_str = emi.getText().toString().trim();
                vehicle_interest_str = interest.getText().toString().trim();
                vehicle_loan_tenure_str = loan_tenure.getText().toString().trim();
                vehicle_loan_amount_str = loan_amount.getText().toString().trim();
                vehicle_warranty_from_str = warranty_from.getText().toString().trim();
                vehicle_warranty_to_str = warranty_to.getText().toString().trim();
                vehicle_extended_warranty_from_str = extended_warranty_from.getText().toString().trim();
                vehicle_extended_warranty_to_str = extended_warranty_to.getText().toString().trim();
                renewal_date_str = renewal_date.getText().toString().trim();
                vehicle_trim_str = trim.getText().toString().trim();
                vehicle_style_str = style.getText().toString().trim();
                vehicle_mileage_str = mileage.getText().toString().trim();
                vehicle_ownership_type_str = ownership_type.getText().toString().trim();

                //Validations for all Registration Fields
                Boolean valid_firstname = Utility.CommonValidation(first_name_str);
                Boolean valid_lastname = Utility.CommonValidation(last_name_str);
                Boolean valid_email = Utility.emailValidate(email_str);

                Boolean valid_phonenumber = Utility.PhoneValidation(phone_str);
                Boolean valid_zipcode = Utility.ZipcodeValidation(zipcode_str);
                Boolean valid_vin = Utility.VinValidation(vehicle_vin_str);
                Boolean valid_vehicle_type = Utility.CommonValidation(vehicle_type_str);
                Boolean valid_vehicle_make = Utility.CommonValidation(vehicle_make_str);
                Boolean valid_vehicle_model = Utility.CommonValidation(vehicle_model_str);
                Boolean valid_vehicle_year = Utility.VehicleYearValidation(vehicle_year_str);
                Boolean valid_vehicle_color = Utility.CommonValidation(vehicle_color_str);
                Boolean valid_vehicle_loan_amount = Utility.CommonValidation(vehicle_loan_amount_str);
                Boolean valid_vehicle_emi = Utility.CommonValidation(vehicle_emi_str);
                Boolean valid_vehicle_interest = Utility.CommonValidation(vehicle_interest_str);
                Boolean valid_vehicle_loan_tenure = Utility.CommonValidation(vehicle_loan_tenure_str);
                Boolean valid_vehicle_trim = Utility.CommonValidation(vehicle_trim_str);
                Boolean valid_vehicle_style = Utility.CommonValidation(vehicle_style_str);
                Boolean valid_vehicle_mileage = Utility.CommonValidation(vehicle_mileage_str);
                Boolean valid_vehicle_ownership_type = Utility.CommonValidation(vehicle_ownership_type_str);

                if (!valid_firstname){
                    Toast.makeText(RegisterUserActivity.this, "Enter valid First Name", Toast.LENGTH_SHORT).show();
                    firstname.requestFocus();
                }else if (!valid_lastname){
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Last Name", Toast.LENGTH_SHORT).show();
                    lastname.requestFocus();
                }else if (!valid_password) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else if (!valid_email){
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (!valid_phonenumber) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else if (!valid_zipcode) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Zipcode", Toast.LENGTH_SHORT).show();
                    zip_code.requestFocus();
                }else if (!valid_vin) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle_IN", Toast.LENGTH_SHORT).show();
                    vehicle_vin.requestFocus();
                }else if (!valid_vehicle_type) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Type", Toast.LENGTH_SHORT).show();
                    vehicle_type.requestFocus();
                }else if (!valid_vehicle_make) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Make", Toast.LENGTH_SHORT).show();
                    vehicle_make.requestFocus();
                }else if (!valid_vehicle_model) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Model", Toast.LENGTH_SHORT).show();
                    vehicle_model.requestFocus();
                }else if (!valid_vehicle_year) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Year", Toast.LENGTH_SHORT).show();
                    vehicle_year.requestFocus();
                }else if (!valid_vehicle_color) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Color", Toast.LENGTH_SHORT).show();
                    vehicle_color.requestFocus();
                }else if (!valid_vehicle_loan_amount) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Loan Amount", Toast.LENGTH_SHORT).show();
                    loan_amount.requestFocus();
                }else if (!valid_vehicle_emi) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle EMI", Toast.LENGTH_SHORT).show();
                    emi.requestFocus();
                }else if (!valid_vehicle_interest) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Interest", Toast.LENGTH_SHORT).show();
                    interest.requestFocus();
                }else if (!valid_vehicle_loan_tenure) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Loan Tenure", Toast.LENGTH_SHORT).show();
                    loan_tenure.requestFocus();
                }else if (!valid_vehicle_trim) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Trim", Toast.LENGTH_SHORT).show();
                    trim.requestFocus();
                }else if (!valid_vehicle_style) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Style", Toast.LENGTH_SHORT).show();
                    style.requestFocus();
                }else if (!valid_vehicle_mileage) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Milage", Toast.LENGTH_SHORT).show();
                    mileage.requestFocus();
                }else if (!valid_vehicle_ownership_type) {
                    Toast.makeText(RegisterUserActivity.this, "Enter valid Vehicle Ownership Type", Toast.LENGTH_SHORT).show();
                    ownership_type.requestFocus();
                }else{
                    SendUserRegDetails();
                }
            }
        });
    }//end of Oncreate

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Permissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterUserActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Permissions.checkPermission(RegisterUserActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
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
    // getting back result from child activities
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

        if(image.equals("photo1")) {
            photo1.setImageBitmap(thumbnail);
            // Convert bitmap to byte[]
            byte[] photo1_byte = convertBitmapToByteArray(thumbnail);
            // convert byteArray to base64 String
            photo1String = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
            //Log.i("photo1String1", "" + photo1String);
        }else if(image.equals("photo2")){
            photo2.setImageBitmap(thumbnail);
            byte[] photo2_byte = convertBitmapToByteArray(thumbnail);
            photo2String = Base64.encodeToString(photo2_byte,Base64.DEFAULT);
        }else if(image.equals("photo3")){
            photo3.setImageBitmap(thumbnail);
            byte[] photo3_byte = convertBitmapToByteArray(thumbnail);
            photo3String = Base64.encodeToString(photo3_byte,Base64.DEFAULT);
        }else if(image.equals("photo_manual")){
            manualimg.setImageBitmap(thumbnail);
            byte[] photo_manual_byte = convertBitmapToByteArray(thumbnail);
            manual_String = Base64.encodeToString(photo_manual_byte,Base64.DEFAULT);
        }else if(image.equals("photo_insurance")){
            insuranceimg.setImageBitmap(thumbnail);
            byte[] photo_insurance_byte = convertBitmapToByteArray(thumbnail);
            insurance_String = Base64.encodeToString(photo_insurance_byte,Base64.DEFAULT);
        }
    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(image.equals("photo1")) {
            photo1.setImageBitmap(bm);
            // Convert bitmap to byte[]
            byte[] photo1_byte = convertBitmapToByteArray(bm);
            // convert byteArray to base64 String
            photo1String = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
            //Log.i("photo1String1", "" + photo1String);
        }else if(image.equals("photo2")){
            photo2.setImageBitmap(bm);
            byte[] photo2_byte = convertBitmapToByteArray(bm);
            photo2String = Base64.encodeToString(photo2_byte,Base64.DEFAULT);
        }else if(image.equals("photo3")){
            photo3.setImageBitmap(bm);
            byte[] photo3_byte = convertBitmapToByteArray(bm);
            photo3String = Base64.encodeToString(photo3_byte,Base64.DEFAULT);
        }else if(image.equals("photo_manual")){
            manualimg.setImageBitmap(bm);
            byte[] photo_manual_byte = convertBitmapToByteArray(bm);
            manual_String = Base64.encodeToString(photo_manual_byte,Base64.DEFAULT);
        }else if(image.equals("photo_insurance")){
            insuranceimg.setImageBitmap(bm);
            byte[] photo_insurance_byte = convertBitmapToByteArray(bm);
            insurance_String = Base64.encodeToString(photo_insurance_byte,Base64.DEFAULT);
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

    //Declaration of Calender Dialog
    private void setDateTimeField() {
        warrenty_from_calender_pic.setOnClickListener(RegisterUserActivity.this);
        warrenty_to_calender_pic.setOnClickListener(RegisterUserActivity.this);
        extnd_warrenty_from_calender_pic.setOnClickListener(RegisterUserActivity.this);
        extnd_warrenty_to_calender_pic.setOnClickListener(RegisterUserActivity.this);
        renual_iv.setOnClickListener(RegisterUserActivity.this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                warranty_from.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                warranty_to.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        extndfromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                extended_warranty_from.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        extndtoDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                extended_warranty_to.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        renualDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                renewal_date.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    //OnclickListeners for DatePickerImages
    public void onClick(View view) {
        if(view == warrenty_from_calender_pic) {
            fromDatePickerDialog.show();
        } else if(view == warrenty_to_calender_pic) {
            toDatePickerDialog.show();
        }else if(view == extnd_warrenty_from_calender_pic) {
            extndfromDatePickerDialog.show();
        }else if(view == extnd_warrenty_to_calender_pic) {
            extndtoDatePickerDialog.show();
        }else if(view == renual_iv) {
            renualDatePickerDialog.show();
        }
    }

    // Sending Details of User Registration
    private void SendUserRegDetails(){
        final ProgressDialog dialog = ProgressDialog.show(RegisterUserActivity.this, "",
                "Sending Data,   Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Dismiss the progress dialog
                        dialog.dismiss();
                        JsonparseNavigateScreen(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Dismiss the progress dialog
                        dialog.dismiss();
                        Toast.makeText(RegisterUserActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.v("error>>>",""+error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("first_name",first_name_str);
                params.put("last_name",last_name_str);
                params.put("password",password_str);
                params.put("email", email_str);
                params.put("phone", phone_str);
                params.put("dealer_code", dealercode_str);
                params.put("dl_expiry_date", dl_expiry_date_str);
                params.put("zip_code", zipcode_str);
                params.put("language", userlanguage_str);
                params.put("tag_renewal_date", renewal_date_str);
                params.put("vin", vehicle_vin_str);
                params.put("photo", photo1String);
                params.put("dongle_name", dongle_name_str);
                params.put("user_type", user_type_str);
                params.put("vehicle_type", vehicle_type_str);
                params.put("make", vehicle_make_str);
                params.put("model", vehicle_model_str);
                params.put("year", vehicle_year_str);
                params.put("color", vehicle_color_str);
                params.put("emi", vehicle_emi_str);
                params.put("interest", vehicle_interest_str);
                params.put("loan_amount", vehicle_loan_amount_str);
                params.put("loan_tenure", vehicle_loan_tenure_str);
                params.put("waranty_from", vehicle_warranty_from_str);
                params.put("waranty_to", vehicle_warranty_to_str);
                params.put("extended_waranty_from", vehicle_extended_warranty_from_str);
                params.put("extended_waranty_to", vehicle_extended_warranty_to_str);
                params.put("trim", vehicle_trim_str);
                params.put("style", vehicle_style_str);
                params.put("mileage", vehicle_mileage_str);
                params.put("hypothecate", vehicle_ownership_type_str);
                params.put("manual", manual_String);
                params.put("insurance_document", insurance_String);
                Log.v("params>>>",""+params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterUserActivity.this);
        requestQueue.add(stringRequest);

    }// End of SendUserRegDetails

    // JsonParse method for navigating screen
    void JsonparseNavigateScreen(String jsonObj) {
        boolean isSuccess = false;
        try {
            JSONObject mainObject = new JSONObject(jsonObj);
            String strStatus = mainObject.getString("status");
            String responceMessage = mainObject.getString("message");
            if (strStatus.equals("True")) {
                JSONObject jsonData = mainObject.getJSONObject("response");
                JSONArray jsonArr = jsonData.getJSONArray("data");

                JSONObject jsonobj = jsonArr.getJSONObject(0);
                //String strUid = jsonobj.getString("uid");

                HashMap<String, String> userDetails = new HashMap<String, String>();
                userDetails.put(UserSessionManager.KEY_FIRSTNAME, jsonobj.getString("first_name"));
                userDetails.put(UserSessionManager.KEY_LASTNAME, jsonobj.getString("last_name"));
                userDetails.put(UserSessionManager.KEY_EMAIL, jsonobj.getString("email"));
                userDetails.put(UserSessionManager.KEY_PHONENUMBER, jsonobj.getString("phone"));
                userDetails.put(UserSessionManager.KEY_USERID, jsonobj.getString("uid"));
                UserSessionManager objManager = new UserSessionManager(this);
                objManager.saveUserDetailsPref(userDetails);

            }
            isSuccess = Boolean.parseBoolean(mainObject.getString("status").toLowerCase());
            Log.v("response reg_token>>>",""+jsonObj);
            if (isSuccess) {

                Intent intent = new Intent(RegisterUserActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterUserActivity.this, responceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}