package coxaxle.cox.automotive.com.android.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import coxaxle.cox.automotive.com.android.AxleApplication;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.RecentSearchAdapter;

public class RecentSearchActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_search);

       final ArrayList<HashMap<String, String>> recentSerches = AxleApplication.recentSerches ;

        list=(ListView)findViewById(R.id.recent_search_list);
        //Collections.reverse(recentSerches);
        RecentSearchAdapter adapter = new RecentSearchAdapter(RecentSearchActivity.this, recentSerches);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);//sets the adapter for listView

        //perform listView item click event
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                HashMap<String, String> searched_hashmap = new HashMap<String, String>();
                searched_hashmap = recentSerches.get(position);
                Intent intent = new Intent(RecentSearchActivity.this, DealerInventorySearchResultsActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("SearchValues", searched_hashmap);
                extras.putInt("Flag", 1);
                intent.putExtras(extras);
                startActivity(intent);
                finish();

            }
        });

    }

}
