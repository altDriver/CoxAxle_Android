package coxaxle.cox.automotive.com.android.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Lakshmana on 31-08-2016.
 */
public class CustomDialogFragment extends DialogFragment {
    static Activity objActivity = null;
    static Intent targetIntent = null;
    public CustomDialogFragment(){}
    public static CustomDialogFragment setDialog( String strTitle, String strMessage, String strPositiveButton, String strNegative, final Activity objActivity, final Intent targetIntent)
    {
        CustomDialogFragment obj = new CustomDialogFragment();
        obj.objActivity = objActivity;
        obj.targetIntent = targetIntent;
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", strTitle);
        args.putString("message", strMessage);
        args.putString("positive_button", strPositiveButton);
        args.putString("negative_button", strNegative);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int title = getArguments().getInt("title");
        setCancelable(false);
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("message"))
                .setPositiveButton(getArguments().getString("positive_button"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                if(targetIntent != null)
                                {
                                    objActivity.startActivity(targetIntent);
                                }
                                else
                                    dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(getArguments().getString("negative_button"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();

                            }
                        }
                )
                .create();
    }
}
