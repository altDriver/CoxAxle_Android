package coxaxle.cox.automotive.com.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.model.NotificationInfo;

/**
 * Created by Srinivas B on 06-10-2016.
 */

public class NotificationAdapter extends ArrayAdapter<NotificationInfo> {

    Context mContext;
    int layoutResourceId;
    NotificationInfo data[] = null;

    public NotificationAdapter(Context mContext, int layoutResourceId, NotificationInfo[] data) {

        super(mContext, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	/*
		 * The convertView argument is essentially a "ScrapView" as described is Lucas post
		 * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
		 * It will have a non-null value when ListView is asking you recycle the row layout.
		 * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
    	 */
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        NotificationInfo objectItem = data[position];

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewtitle);
        TextView textViewTitleItem = (TextView) convertView.findViewById(R.id.textViewtitleItem);
        textViewTitle.setText(objectItem.Title);
        textViewTitleItem.setText(objectItem.Titleitem);

        return convertView;

    }

}
