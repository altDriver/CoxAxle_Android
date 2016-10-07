package coxaxle.cox.automotive.com.android.common;

/**
 * Created by Srinivas on 29-09-2016.
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;

public class MyCustomDialog2 extends DialogFragment {
    public onSubmitListener mListener;
    int mFlag, layout;
    Typeface fontNormalHelvetica,fontBoldHelvetica;
    Context context;

    public interface onSubmitListener {
        void setOnSubmitListener(int flag);
        void setOnSubmitListener2(int flag);
    }

    public void setDialog(int mlayout, Context ctx, int flag, String txt1, String txt2, String cancel)
    {
        layout = mlayout;
        mFlag = flag;
        context = ctx;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout); //R.layout.custom_dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        fontNormalHelvetica = Typeface.createFromAsset(context.getAssets(), "font/HelveticaNeue.ttf");
        fontBoldHelvetica = Typeface.createFromAsset(context.getAssets(), "font/helvetica-neue-bold.ttf");

        if(layout == R.layout.custom_dialog2)
        {
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView txtcamera = (TextView) dialog.findViewById(R.id.txt_camera);
            TextView txtgallery = (TextView) dialog.findViewById(R.id.txt_gallery);
            TextView txtcancel = (TextView) dialog.findViewById(R.id.txt_cancel);
            txtcamera.setTypeface(fontNormalHelvetica);
            txtgallery.setTypeface(fontNormalHelvetica);
            txtcancel.setTypeface(fontNormalHelvetica);
            txtcamera.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.setOnSubmitListener(mFlag);
                    dismiss();
                }
            });
            txtgallery.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.setOnSubmitListener2(mFlag);
                    dismiss();
                }
            });
            txtcancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        return dialog;
    }
}
