package coxaxle.cox.automotive.com.android.common;

/**
 * Created by Lakshmana on 14-09-2016.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;

public class MyCustomDialog extends DialogFragment {
    Button buttonOk, btnCancel;
    TextView txtTitle, txtMessage;
    public onSubmitListener mListener;
    int mFlag, layout;
    String strTitle, strMessage, strPositiveButton, strNegative;
    Typeface fontNormalHelvetica,fontBoldHelvetica;
    Context context;
    public interface onSubmitListener {
        void setOnSubmitListener(int flag);
    }

    public void  setDialog(int mlayout, Context ctx, int flag, String Title, String Message, String PositiveButton, String NegativeButton)
    {
        layout = mlayout;
        mFlag = flag;
        strTitle = Title;
        strMessage = Message;
        strPositiveButton = PositiveButton;
        strNegative = NegativeButton;
        context = ctx;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout); //R.layout.custom_dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        fontNormalHelvetica = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(context.getAssets(), "font/helvetica-neue-bold.ttf");
        if(layout == R.layout.custom_dialog) {
            txtTitle = (TextView) dialog.findViewById(R.id.textView_title);
            txtMessage = (TextView) dialog.findViewById(R.id.textView_Message);
            buttonOk = (Button) dialog.findViewById(R.id.button_Ok);
            btnCancel = (Button) dialog.findViewById(R.id.button_Cancel);

            if(strPositiveButton.length() == 0)
                buttonOk.setVisibility(View.GONE);
            if(strNegative.length() == 0)
                btnCancel.setVisibility(View.GONE);

            btnCancel.setText(strNegative);
            buttonOk.setText(strPositiveButton);
            txtTitle.setText(strTitle);
            txtMessage.setText(strMessage);
            buttonOk.setTypeface(fontNormalHelvetica);
            btnCancel.setTypeface(fontNormalHelvetica);
            txtMessage.setTypeface(fontNormalHelvetica);
            txtTitle.setTypeface(fontNormalHelvetica);

            buttonOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    mListener.setOnSubmitListener(mFlag);
                    dismiss();
                }
            });
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        else if(layout == R.layout.find_my_vin_dialog)
        {
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            TextView txtcantFind = (TextView) dialog.findViewById(R.id.findvindialog_Cantfindvin);
            TextView txtcommonplaces = (TextView) dialog.findViewById(R.id.findvindialog_herecommonplaces);
            TextView txtmostcommon = (TextView) dialog.findViewById(R.id.findvindialog_mostcommonplaces);
            txtcantFind.setTypeface(fontBoldHelvetica);
            txtcommonplaces.setTypeface(fontNormalHelvetica);
            txtmostcommon.setTypeface(fontNormalHelvetica);
            ImageView imgcancel = (ImageView) dialog.findViewById(R.id.findvindialog_cancel);
            imgcancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        else if(layout == R.layout.fragment_nointernet_dialog)
        {
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Button tryAgain = (Button) dialog.findViewById(R.id.button_TryAgain);
            tryAgain.setTypeface(fontNormalHelvetica);
            tryAgain.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        return dialog;
    }
}
