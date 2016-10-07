package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.presentation.HomeScreenActivity;

/**
 * Created by Srinivas B on 16-09-2016.
 */
public class ServicesFragment extends Fragment {
    View view;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.service_fragment,container,false);
        ((HomeScreenActivity) getActivity()).toolbar_title.setText("Services");
        ((HomeScreenActivity) getActivity()).toolbar_icon.setVisibility(View.INVISIBLE);
        ((HomeScreenActivity) getActivity()).toolbar_notifications_count.setVisibility(View.INVISIBLE);
            return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    }