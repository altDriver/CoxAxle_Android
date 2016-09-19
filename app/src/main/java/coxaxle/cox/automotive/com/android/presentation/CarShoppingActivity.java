package coxaxle.cox.automotive.com.android.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import coxaxle.cox.automotive.com.android.R;

public class CarShoppingActivity extends AppCompatActivity {


    private ImageView calculatorsLearnMoreIv, vehicleValuesLearnMoreIv;
    private RelativeLayout calculatorsHeaderLayout, calculatorsSubLayout,
            vehicleValuesHeaderLayout, vehicleValuesSubLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_shopping);
        getSupportActionBar().hide();

        loadViews();


    }

    void loadViews() {
        calculatorsHeaderLayout = (RelativeLayout) findViewById(R.id.car_shopping_calculators_header_layout);
        calculatorsSubLayout = (RelativeLayout) findViewById(R.id.car_shopping_calculators_sub_layout);
        vehicleValuesHeaderLayout = (RelativeLayout) findViewById(R.id.car_shopping_vehicle_values_header_layout);
        vehicleValuesSubLayout = (RelativeLayout) findViewById(R.id.car_shopping_vehicle_values_sub_layout);
        calculatorsLearnMoreIv = (ImageView) findViewById(R.id.car_shopping_calculators_iv);
        vehicleValuesLearnMoreIv = (ImageView) findViewById(R.id.car_shopping_vehicle_values_iv);

        calculatorsSubLayout.setVisibility(View.GONE);
        vehicleValuesSubLayout.setVisibility(View.GONE);


        calculatorsHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorsSubLayout.getVisibility() == View.VISIBLE) {

                    calculatorsSubLayout.setVisibility(View.GONE);
                    calculatorsLearnMoreIv.setRotation(0);


                } else {
                    calculatorsSubLayout.setVisibility(View.VISIBLE);
                    calculatorsLearnMoreIv.setRotation(90);

                }
            }
        });

        vehicleValuesHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicleValuesSubLayout.getVisibility() == View.VISIBLE) {
                    vehicleValuesSubLayout.setVisibility(View.GONE);
                    vehicleValuesLearnMoreIv.setRotation(0);
                } else {
                    vehicleValuesSubLayout.setVisibility(View.VISIBLE);
                    vehicleValuesLearnMoreIv.setRotation(90);
                }
            }
        });

    }
}
