package coxaxle.cox.automotive.com.android.common;

/**
 * Created by Lakshmana on 14-09-2016.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.adapters.NotificationAdapter;
import coxaxle.cox.automotive.com.android.model.NotificationInfo;

public class MyCustomDialog extends DialogFragment {
    TextView buttonOk, btnCancel;
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout); //R.layout.custom_dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        fontNormalHelvetica = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(context.getAssets(), "font/helvetica-neue-bold.ttf");
        if(layout == R.layout.custom_dialog) {
            txtTitle = (TextView) dialog.findViewById(R.id.textView_title);
            txtMessage = (TextView) dialog.findViewById(R.id.textView_Message);
            buttonOk = (TextView) dialog.findViewById(R.id.button_Ok);
            btnCancel = (TextView) dialog.findViewById(R.id.button_Cancel);

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
            TextView tryAgain = (TextView) dialog.findViewById(R.id.txt_TryAgain);
            tryAgain.setTypeface(fontNormalHelvetica);
            tryAgain.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }else if(layout == R.layout.notification_dialog)
        {
            NotificationInfo[] NotificationInfomData = new NotificationInfo[6];

            NotificationInfomData[0] = new NotificationInfo("Albert's Car", "Due for an oilchange 09/01/2016");
            NotificationInfomData[1] = new NotificationInfo("Michelle's Car", "Appointment Reminder 10/15/2016");
            NotificationInfomData[2] = new NotificationInfo("Adam's Car", "100,000 Mile Tune Up December");
            NotificationInfomData[3] = new NotificationInfo("Albert's Car", "Due for an oilchange 09/01/2016");
            NotificationInfomData[4] = new NotificationInfo("Michelle's Car", "Appointment Reminder 10/15/2016");
            NotificationInfomData[5] = new NotificationInfo("Adam's Car", "100,000 Mile Tune Up December");

            // our adapter instance
            NotificationAdapter adapter = new NotificationAdapter(getActivity(), R.layout.notification_list_row, NotificationInfomData);

            ListView listViewItems = (ListView) dialog.findViewById(R.id.dialoglist);
            listViewItems.setAdapter(adapter);
            listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                /*Context context = view.getContext();
                Toast.makeText(context, "Item Clicked ", Toast.LENGTH_SHORT).show();*/

                }
            });
            TextView dialogButton = (TextView) dialog.findViewById(R.id.notification_button_Ok);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        return dialog;
    }

}
