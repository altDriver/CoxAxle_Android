package coxaxle.cox.automotive.com.android.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.common.FontsOverride;

public class EditVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);
        FontsOverride fontsOverrideobj = new FontsOverride(getAssets(), "font/HelveticaNeue.ttf");
        fontsOverrideobj.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
    }
}
