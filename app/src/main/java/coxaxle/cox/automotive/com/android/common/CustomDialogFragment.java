package coxaxle.cox.automotive.com.android.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Lakshmana on 31-08-2016.
 */
public class CustomDialogFragment extends DialogFragment {
    public static CustomDialogFragment setDialog(int flag, String strTitle, String strMessage, String strPositiveButton, String strNegative)
    {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", strTitle);
        args.putString("message", strMessage);
        args.putString("positive_button", strPositiveButton);
        args.putString("negative_button", strNegative);
        args.putInt("flag", flag);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int title = getArguments().getInt("title");
        final int flag = getArguments().getInt("flag");
        setCancelable(false);
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("message"))
                .setPositiveButton(getArguments().getString("positive_button"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                               /* if(flag == 1)
                                   // ((HomeScreen) getActivity()).doPositiveClickMaps();
                                if(flag == 2)
                                   // ((HomeScreen)getActivity()).doPositiveClickCall();
                                if(flag == 3)
                                   // ((HomeScreen)getActivity()).doPositiveClickMail();*/

                            }
                        }
                )
                .setNegativeButton(getArguments().getString("negative_button"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                if(flag == 1)
//                                    ((HomeScreen)getActivity()).doNegativeClick();
                                dialog.dismiss();

                            }
                        }
                )
                .create();

    }
}
