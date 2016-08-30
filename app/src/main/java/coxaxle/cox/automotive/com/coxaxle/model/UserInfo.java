package coxaxle.cox.automotive.com.coxaxle.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kishore on 7/29/2016.
 */

public class UserInfo implements Parcelable {


    int user_id;
    String user_first_name;
    String user_last_name;
    String user_password;
    String user_email;
    String user_phone;
    String dealer_code;
    int zip_code;
    String user_language;
    boolean is_guest_user;
    boolean remember_me;


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(user_id);
        dest.writeString(user_first_name);
        dest.writeString(user_last_name);
        dest.writeString(user_password);
        dest.writeString(user_email);
        dest.writeString(user_phone);
        dest.writeString(dealer_code);
        dest.writeInt(zip_code);
        dest.writeString(user_language);
        dest.writeString(is_guest_user + "");
        dest.writeString(remember_me + "");

    }


    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    // "De-parcel object
    public UserInfo(Parcel in) {
        /*name = in.readString();
        surname = in.readString();
        idx = in.readInt();*/

        user_id = in.readInt();
        user_first_name = in.readString();
        user_last_name = in.readString();
        user_password = in.readString();
        user_email = in.readString();
        user_phone = in.readString();
        dealer_code = in.readString();
        zip_code = in.readInt();
        user_language = in.readString();
    }


}
