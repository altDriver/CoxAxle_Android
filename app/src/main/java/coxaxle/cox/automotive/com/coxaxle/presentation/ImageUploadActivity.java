package coxaxle.cox.automotive.com.coxaxle.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.model.Constants;

public class ImageUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private EditText editTextName;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL = Constants.ADD_VEHICLE_URL;

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        editTextName = (EditText) findViewById(R.id.editText);

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(ImageUploadActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ImageUploadActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
               /*
                params.put(KEY_NAME, name);*/

                //params.put(KEY_IMAGE, image);
                /*params.put("name", "Maruti Swift");
                params.put("uid", "21");
                params.put("dealer_id", "1");
                params.put("vin", "5J6RE3H74AL049448");
                params.put("vehicle_type", "Sedan");
                params.put("make", "Maruthi");
                params.put("model", "2010");
                params.put("color", "Silver");
                params.put("photo", image);
                params.put("waranty_from", "");
                params.put("waranty_to", "");
                params.put("extended_waranty_from", "");
                params.put("extended_waranty_to", "");
                params.put("kbb_price", "1000");
                params.put("mileage", "01516");
                params.put("manual", "");
                params.put("loan_amount", "200");
                params.put("emi", "50");
                params.put("interest", "9");
                params.put("loan_tenure", "6");*/

               /* params.put("name", "Maruti Swift");
                params.put(" vin", "5J6RE3H74AL049448");
                params.put("vehicle_type", "");
                params.put("make", "");
                params.put("year", "");
                params.put("model", "");
                params.put("color", "");
                params.put("style", "");
                params.put("trim", "");
                params.put("mileage", "");
                params.put("photo", "");*/

                params.put("name", "Audi A4");
                params.put("uid", "21");
                params.put("dealer_id", "2");
                params.put("photo", image);
                params.put("kbb_price", "100");
                params.put("insurance_document", "9");
                params.put("manual", "");
                params.put("vin", "6J6RE3H74AL049450");
                params.put("vehicle_type", "Volvo");
                params.put("make", "Audi");
                params.put("model", "Sub");
                params.put("year", "2015");
                params.put("color", "White");
                params.put("emi", "50");
                params.put("interest", "7");
                params.put("loan_amount", "100");
                params.put("loan_tenure", "3");
                params.put("waranty_from", "5");
                params.put("waranty_to", "10");
                params.put("extended_waranty_from", "3");
                params.put("extended_waranty_to", "8");
                params.put("trim", "vxi");
                params.put("style", "sedan");
                params.put("mileage", "54321");

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
            showFileChooser();
        }

        if (v == buttonUpload) {
            uploadImage();
        }
    }
}
