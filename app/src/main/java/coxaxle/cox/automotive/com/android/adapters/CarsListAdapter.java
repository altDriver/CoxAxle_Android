package coxaxle.cox.automotive.com.android.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;

public class CarsListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public CarsListAdapter(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.activity_cars_list_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_cars_list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.cars_list_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.carss_list_image);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}