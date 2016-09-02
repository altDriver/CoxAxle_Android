package coxaxle.cox.automotive.com.coxaxle.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import coxaxle.cox.automotive.com.coxaxle.R;
import coxaxle.cox.automotive.com.coxaxle.adapters.CarsListAdapter;


public class CarsListActivity extends Activity {
    ListView list;
    String[] web = {
            "Vicky's Car",
            "Michelle Dorminey",
            "Adamthulla' Car",
            "Vicky's Car",
            "Michelle Dorminey",
            "Adamthulla' Car",
            "Michelle Dorminey"
    } ;
    Integer[] imageId = {
            R.mipmap.placeholder,
            R.mipmap.placeholder,
            R.mipmap.placeholder,
            R.mipmap.placeholder,
            R.mipmap.placeholder,
            R.mipmap.placeholder,
            R.mipmap.placeholder


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);


        CarsListAdapter adapter = new
                CarsListAdapter(CarsListActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CarsListActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });




    }

}