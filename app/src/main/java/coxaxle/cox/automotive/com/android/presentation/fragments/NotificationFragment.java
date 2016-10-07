package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.android.R;

public class NotificationFragment extends Fragment {
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notification,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

}
