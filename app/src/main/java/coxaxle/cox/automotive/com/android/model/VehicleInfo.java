package coxaxle.cox.automotive.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kishore on 7/29/2016.
 */
public class VehicleInfo implements Parcelable {

    String user_id;
    String vehicle_vin;
    String vehicle_type;
    String vehicle_make;
    String vehicle_model;
    String vehicle_year;
    String vehicle_color;

    String vehicle_photo1; //public /vehicles/17764.png
    String vehicle_photo2; //public /vehicles/17764.png
    String vehicle_photo3; //public /vehicles/17764.png

    String vehicle_warranty_from; //0000-00-00
    String vehicle_warranty_to; //0000-00-00
    String vehicle_extended_warranty_from; //0000-00-00
    String vehicle_extended_warranty_to; //0000-00-00
    String vehicle_manual; //imge/file
    String vehicle_loan_amount;//0
    String vehicle_emi; //0
    String vehicle_interest; //0
    String vehicle_loan_tenure; //0
    String vehicle_insurance_document; //image/file
    String vehicle_tag_renewal_date;//0000-00-00
    String vehicle_last_service_apt;
    String vehicle_next_service_apt; //0000-00-00
    String vehicle_trim;
    String vehicle_style;//hatch_bach/sedan/suv/..
    String vehicle_mileage;
    String vehicle_ownership_type;


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(vehicle_vin);
        dest.writeString(vehicle_type);
        dest.writeString(vehicle_make);
        dest.writeString(vehicle_model);
        dest.writeString(vehicle_year);
        dest.writeString(vehicle_color);
        dest.writeString(vehicle_photo1);
        dest.writeString(vehicle_photo2);
        dest.writeString(vehicle_photo3);
        dest.writeString(vehicle_warranty_from);
        dest.writeString(vehicle_warranty_to);
        dest.writeString(vehicle_extended_warranty_from);
        dest.writeString(vehicle_extended_warranty_to);
        dest.writeString(vehicle_manual);
        dest.writeString(vehicle_loan_amount);
        dest.writeString(vehicle_emi);
        dest.writeString(vehicle_interest);
        dest.writeString(vehicle_loan_tenure);
        dest.writeString(vehicle_insurance_document);
        dest.writeString(vehicle_tag_renewal_date);
        dest.writeString(vehicle_last_service_apt);
        dest.writeString(vehicle_next_service_apt);
        dest.writeString(vehicle_trim);
        dest.writeString(vehicle_style);
        dest.writeString(vehicle_mileage);
        dest.writeString(vehicle_ownership_type);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public VehicleInfo createFromParcel(Parcel in) {
            return new VehicleInfo(in);
        }

        public VehicleInfo[] newArray(int size) {
            return new VehicleInfo[size];
        }
    };

    // "De-parcel object
    public VehicleInfo(Parcel in) {

        user_id = in.readString();
        vehicle_vin = in.readString();
        vehicle_type = in.readString();
        vehicle_make = in.readString();
        vehicle_model = in.readString();
        vehicle_year = in.readString();
        vehicle_color = in.readString();

        vehicle_photo1 = in.readString();
        vehicle_photo2 = in.readString();
        vehicle_photo3 = in.readString();

        vehicle_warranty_from = in.readString();
        vehicle_warranty_to = in.readString();
        vehicle_extended_warranty_from = in.readString();
        vehicle_extended_warranty_to = in.readString();

        vehicle_manual = in.readString();
        vehicle_loan_amount = in.readString();
        vehicle_emi = in.readString();
        vehicle_interest = in.readString();
        vehicle_loan_tenure = in.readString();

        vehicle_insurance_document = in.readString();

        vehicle_tag_renewal_date = in.readString();
        vehicle_last_service_apt = in.readString();
        vehicle_next_service_apt = in.readString();

        vehicle_trim = in.readString();
        vehicle_style = in.readString();
        vehicle_mileage = in.readString();
        vehicle_ownership_type = in.readString();
    }

}
