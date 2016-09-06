package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.Map;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;
import coxaxle.cox.automotive.com.android.common.UserSessionManager;
import coxaxle.cox.automotive.com.android.common.Utility;
import coxaxle.cox.automotive.com.android.model.Constants;

/**
 * Created by Lakshmana on 29-08-2016.
 */
public class AddVehicle2of4Activity extends Activity implements View.OnClickListener{
    //String strphotoInsurance64;
    Button btnSave;
    ImageView ivAddInsurancePhoto;
    EditText etInsuranceExpirationDate;
    private DatePickerDialog tagDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar calendar;
    private int year, month, day;
    HashMap<String,String> page1Values;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Typeface fontBoldHelvetica;
    LinearLayout llAddImages;
    ArrayList<Bitmap> bitmapArray;
    String strImages, strInsuranceExp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_vehicle2);
        fontBoldHelvetica = Typeface.createFromAsset(getAssets(), "font/helvetica-neue-bold.ttf");
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        page1Values = (HashMap<String,String>) getIntent().getExtras().get("Page1Values");
        loadViews();

        bitmapArray = new ArrayList<Bitmap>();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        setTagExpirationDate();
    }
    public void loadViews()
    {
        TextView tvInsuranceDetails = (TextView) findViewById(R.id.AddVehicle_InsuranceDetailsHeader_tv);
        btnSave = (Button) findViewById(R.id.AddVehicle_Save_button);
        etInsuranceExpirationDate = (EditText) findViewById(R.id.AddVehicle_InsuranceExpiration_et);
        ivAddInsurancePhoto = (ImageView) findViewById(R.id.AddVehicle_AddInsurancePhoto_iv);
        llAddImages = (LinearLayout) findViewById(R.id.AddVehicle_AddInsurancePhotos_ll);

        btnSave.setOnClickListener(this);
        ivAddInsurancePhoto.setOnClickListener(this);
        etInsuranceExpirationDate.setOnClickListener(this);

        etInsuranceExpirationDate.setTypeface(fontBoldHelvetica);
        tvInsuranceDetails.setTypeface(fontBoldHelvetica);
    }

    @Override
    public void onClick(View view)
    {
        if(view == btnSave)
        {
            strInsuranceExp = etInsuranceExpirationDate.getText().toString();
            Boolean valid_InsuranceDate = Utility.CommonValidation(strInsuranceExp);
            Boolean valid_insurance_photo;

            if(bitmapArray.size() > 0)
            {
                valid_insurance_photo = true;
                for(int i =0; i<bitmapArray.size() ; i++)
                {
                    Bitmap imgbitmap = bitmapArray.get(i);
                    byte[] photo1_byte = convertBitmapToByteArray(imgbitmap);
                    String strphotoInsurance64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
                    strImages = strphotoInsurance64 +",";
                }
            }
            else
                valid_insurance_photo = false;

            //Boolean valid_insurance_photo = Utility.CommonValidation(strphotoInsurance64);

            if (!valid_InsuranceDate) {
                Toast.makeText(AddVehicle2of4Activity.this, "Enter Insurance Expiration date", Toast.LENGTH_SHORT).show();
                etInsuranceExpirationDate.requestFocus();
            }else if (!valid_insurance_photo) {
                Toast.makeText(AddVehicle2of4Activity.this, "Select Insurance Photo", Toast.LENGTH_SHORT).show();
            }else {
                addVehicle();
            }
        }
        if( view == ivAddInsurancePhoto)
        {
            selectImage();
        }
        if(view == etInsuranceExpirationDate)
        {
            tagDatePickerDialog.show();
        }
    }
    private void addVehicle()
    {
        try
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_VEHICLE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("response Add Vehicle>>>",""+response);
                            Toast.makeText(AddVehicle2of4Activity.this,response,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddVehicle2of4Activity.this, HomeScreen.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error Add vehicle>>>",""+error);
                            Toast.makeText(AddVehicle2of4Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){

                    UserSessionManager obj = new UserSessionManager(AddVehicle2of4Activity.this);
                    String strUid = obj.getUserId();

                    Map<String,String> params = new HashMap<String, String>();
                    params.put("uid", strUid);
                    params.put("name", page1Values.get("vehicle_name"));
                    params.put("dealer_id", "2");
                    params.put("manual", "");
                    params.put("vin", page1Values.get("vehicle_vin"));
                    params.put("vehicle_type", page1Values.get("vehicle_type"));
                    params.put("make", page1Values.get("vehicle_make"));
                    params.put("model", page1Values.get("vehicle_model"));
                    params.put("year", page1Values.get("vehicle_year"));
                    params.put("tag_expiration_date",page1Values.get("vehicle_tagexpiration"));
                    params.put("color", "Red");
                    params.put("emi", "10000");
                    params.put("interest", "2000");
                    params.put("loan_amount", "100000");
                    params.put("loan_tenure", "36");
                    params.put("waranty_from", "2");
                    params.put("waranty_to", "3");
                    params.put("extended_waranty_from", "4");
                    params.put("extended_waranty_to", "5");
                    params.put("trim", "6");
                    params.put("style", "sports");
                    params.put("mileage", "20");
                    params.put("kbb_price","100");
                    params.put("expiration_date", strInsuranceExp );
                    params.put("photo",page1Values.get("vehicle_photo"));
                    params.put("insurance_card", strImages);
                    //params.put("insurance_expiration_date", "");
                    Log.v("params>>>",""+params);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddVehicle2of4Activity.this);
            requestQueue.add(stringRequest);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setTagExpirationDate()
    {
        Calendar newCalendar = Calendar.getInstance();
        tagDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etInsuranceExpirationDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddVehicle2of4Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= coxaxle.cox.automotive.com.android.common.Permissions.checkPermission(AddVehicle2of4Activity.this);

                if (items[item].equals("Take Photo")) {
                    //userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                   // userChoosenTask ="Choose from Library";
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


        //ivAddInsurancePhoto.setImageBitmap(thumbnail);
        addBitmapToLayout(thumbnail);
        // Convert bitmap to byte[]
        //byte[] photo1_byte = convertBitmapToByteArray(thumbnail);
        // convert byteArray to base64 String
        //strphotoInsurance64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
        //Log.i("photo1String1", "" + photo1String);

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

        //ivAddInsurancePhoto.setImageBitmap(bm);
        // Convert bitmap to byte[]
//        ImageView image = new ImageView(this);
//        image.setLayoutParams(new android.view.ViewGroup.LayoutParams(200,180));
//        image.setMaxHeight(45);
//        image.setMaxWidth(42);
//        image.setPadding(10,5,10,5);
//        image.setImageBitmap(bm);
//        // Adds the view to the layout
//        llAddImages.addView(image);

        addBitmapToLayout(bm);




        //byte[] photo1_byte = convertBitmapToByteArray(bm);
        // convert byteArray to base64 String
        //strphotoInsurance64 = Base64.encodeToString(photo1_byte, Base64.DEFAULT);
        //Log.i("photo1String1", "" + photo1String);

    }
    private void addBitmapToLayout(Bitmap bitmap) {
        LayoutInflater inflater = LayoutInflater.from(AddVehicle2of4Activity.this);
        final View layout = inflater.inflate(R.layout.insurance_images_custom_layout, null, true);
        ImageView iv = (ImageView) layout.findViewById(R.id.AddInsurance_ImageMain_iv);
        iv.setImageBitmap(bitmap);
        ImageView ivdelete = (ImageView) layout.findViewById(R.id.AddInsurance_ImageCross_iv);

        bitmapArray.add(bitmap);

        ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = ((ViewGroup) layout.getParent()).indexOfChild(layout);
                llAddImages.removeView(layout);
                bitmapArray.remove(index);
            }
        });
        llAddImages.addView(layout);
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
}
