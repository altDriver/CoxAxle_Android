package coxaxle.cox.automotive.com.android.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import coxaxle.cox.automotive.com.android.R;

/**
 * Created by Kishore on 10/4/2016.
 */

public class AddVehicleCompleteForm extends Activity {

    EditText vehicleNickName, vehicleVin, vehicleMake, vehicleModel, vehicleStyle, vehicleTrim, vehicleYear, vehicleColor, vehicleCurrentMileage, vehicleTagExpiration,
            vehicleWarrantyStart, vehicleWarrantyEnd, vehicleExtWarrantyStart, vehicleExtWarrantyEnd, vehicleInsurenceExpDate, vehicleInsuranceCost;

    //ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_complete_form);

        loadViews();

    }

    void loadViews() {
        vehicleNickName = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_nick_name_edt);
        vehicleVin = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_vin_edt);
        vehicleMake = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_make_edt);
        vehicleModel = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_model_edt);
        vehicleStyle = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_style_edt);
        vehicleTrim = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_trim_edt);
        vehicleYear = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_year_edt);
        vehicleColor = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_color_edt);
        vehicleCurrentMileage = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_mileage_edt);
        vehicleTagExpiration = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_tag_exp_edt);
        vehicleWarrantyStart = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_warranty_start_edt);

        vehicleWarrantyEnd = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_warranty_end_edt);
        vehicleExtWarrantyStart = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_ext_warranty_start_edt);
        vehicleExtWarrantyEnd = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_ext_warranty_end_edt);
        vehicleInsurenceExpDate = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_insurence_exp_date_edt);
        vehicleInsuranceCost = (EditText) findViewById(R.id.add_vehicle_complete_vehicle_insurence_cost_edt);


    }


}
