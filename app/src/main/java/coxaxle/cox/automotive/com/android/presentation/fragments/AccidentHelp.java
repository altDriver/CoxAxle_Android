package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.presentation.HomeScreenActivity;

/**
 * Created by Srinivas B on 16-09-2016.
 */
public class AccidentHelp extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.accident_help_fragment,container,false);
        ((HomeScreenActivity) getActivity()).toolbar_title.setText("Accident Help");
        ((HomeScreenActivity) getActivity()).toolbar_icon.setVisibility(View.INVISIBLE);
        ((HomeScreenActivity) getActivity()).toolbar_notifications_count.setVisibility(View.INVISIBLE);

        return view;
    }
}