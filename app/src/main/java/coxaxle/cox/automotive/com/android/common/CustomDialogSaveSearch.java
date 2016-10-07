package coxaxle.cox.automotive.com.android.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;

/**
 * Created by Lakshmana on 27-09-2016.
 */
public class CustomDialogSaveSearch extends DialogFragment{
    public onSubmitListener mListener;
    //String strText;
    Typeface fontNormalHelvetica;
    //Context context;
    public interface onSubmitListener {
        void setOnSubmitListener(String strSearchText);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.save_search_dialog); //R.layout.custom_dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        fontNormalHelvetica = Typeface.createFromAsset(getActivity().getAssets(), "font/HelveticaNeue.ttf");
        //Window window = dialog.getWindow();
        // window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView txtHeader = (TextView) dialog.findViewById(R.id.savesearchdialog_title);
        txtHeader.setTypeface(fontNormalHelvetica);
        final EditText etSaveSearchName = (EditText) dialog.findViewById(R.id.saveSearch_dialog_et);

        Button cancel = (Button) dialog.findViewById(R.id.SaveSearch_dialog_Cancelbutton);
        Button save = (Button) dialog.findViewById(R.id.SaveSearch_dialog_Savebutton);
        save.setTypeface(fontNormalHelvetica);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strName = etSaveSearchName.getText().toString();
                if(!(strName == null) && (strName.length()!=0))
                {
                    dismiss();
                    mListener.setOnSubmitListener(strName);
                }
            }
        });
        cancel.setTypeface(fontNormalHelvetica);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    return dialog;

    }
}
