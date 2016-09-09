package coxaxle.cox.automotive.com.android.common;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import coxaxle.cox.automotive.com.android.R;


/**
 * Created by Lakshmana on 09-09-2016.
 */
public class NoInternetDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nointernet_dialog, container, false);
        //getDialog().setTitle("Simple Dialog");
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, height);
        Button tryAgain = (Button) rootView.findViewById(R.id.button_TryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
