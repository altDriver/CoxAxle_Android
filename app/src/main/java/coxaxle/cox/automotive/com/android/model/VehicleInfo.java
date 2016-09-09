package coxaxle.cox.automotive.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Srinivas on 8/31/2016.
 */
public class VehicleInfo implements Parcelable {
    public String id;
    public String date_entered;
    public String date_modified;
    public String deleted;
    public String name;
    public String user_id;
    public String dealer_id;
    public String vehicle_vin;
    public String vehicle_type;
    public String vehicle_make;
    public String vehicle_model;
    public String vehicle_year;
    public String vehicle_color;
    public String vehicle_mileage;
    public String vehicle_style;//hatch_bach/sedan/suv/..
    public String vehicle_trim;
    public String vehicle_tag_renewal_date;//0000-00-00
    public String vehicle_warranty_from; //0000-00-00
    public String vehicle_warranty_to; //0000-00-00
    public String vehicle_extended_warranty_from; //0000-00-00
    public String vehicle_extended_warranty_to; //0000-00-00
    public String kbb_price;
    public String vehicle_manual; //imge/file
    public String vehicle_loan_amount;//0
    public String vehicle_emi; //0
    public String vehicle_interest; //0
    public String vehicle_loan_tenure; //0
    public String vehicle_insurance_document; //image/file
    public String vehicle_extended_waranty_document;
    public String vehicle_insurance_expiration_date;
    public String vehicle_tag_expiration_date;
    public ArrayList<String> vehicle_image;
    int listPosition = 0;


    /*
    * A constructor that initializes the Vehicles object
    */
    public VehicleInfo(String id, String date_entered, String date_modified, String deleted, String name, String user_id, String dealer_id, String vehicle_vin, String vehicle_type, String vehicle_make,
                        String vehicle_model, String vehicle_year, String vehicle_color, String vehicle_mileage, String vehicle_style,String vehicle_trim, String vehicle_tag_renewal_date, String vehicle_warranty_from, String vehicle_warranty_to,
                        String vehicle_extended_warranty_from, String vehicle_extended_warranty_to, String kbb_price, String vehicle_manual, String vehicle_loan_amount, String vehicle_emi, String vehicle_interest,
                        String vehicle_loan_tenure, String vehicle_insurance_document, String vehicle_extended_waranty_document, String vehicle_insurance_expiration_date,  String vehicle_tag_expiration_date, ArrayList<String> vehicle_image){
        super();
        this.user_id = user_id;
        this.date_entered = date_entered;
        this.date_modified = date_modified;
        this.deleted = deleted;
        this.name = name;
        this.id = id;
        this.dealer_id = dealer_id;
        this.kbb_price = kbb_price;
        this.vehicle_vin = vehicle_vin;
        this.vehicle_type = vehicle_type;
        this.vehicle_make = vehicle_make;
        this.vehicle_model = vehicle_model;
        this.vehicle_year = vehicle_year;
        this.vehicle_color = vehicle_color;
        this.vehicle_mileage = vehicle_mileage;
        this.vehicle_style = vehicle_style;
        this.vehicle_trim = vehicle_trim;
        this.vehicle_warranty_from = vehicle_warranty_from;
        this.vehicle_warranty_to = vehicle_warranty_to;
        this.vehicle_extended_warranty_from = vehicle_extended_warranty_from;
        this.vehicle_extended_warranty_to = vehicle_extended_warranty_to;
        this.vehicle_manual = vehicle_manual;
        this.vehicle_loan_amount = vehicle_loan_amount;
        this.vehicle_emi = vehicle_emi;
        this.vehicle_interest = vehicle_interest;
        this.vehicle_loan_tenure = vehicle_loan_tenure;
        this.vehicle_loan_amount = vehicle_loan_amount;
        this.vehicle_insurance_document = vehicle_insurance_document;
        this.vehicle_tag_renewal_date = vehicle_tag_renewal_date;
        this.vehicle_extended_waranty_document = vehicle_extended_waranty_document;
        this.vehicle_insurance_expiration_date = vehicle_insurance_expiration_date;
        this.vehicle_tag_expiration_date = vehicle_tag_expiration_date;
        this.vehicle_image = vehicle_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate_entered() {
        return date_entered;
    }

    public void setDate_entered(String date_entered) {
        this.date_entered = date_entered;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getVehicle_vin() {
        return vehicle_vin;
    }

    public void setVehicle_vin(String vehicle_vin) {
        this.vehicle_vin = vehicle_vin;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_year() {
        return vehicle_year;
    }

    public void setVehicle_year(String vehicle_year) {
        this.vehicle_year = vehicle_year;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = vehicle_color;
    }

    public String getVehicle_mileage() {
        return vehicle_mileage;
    }

    public void setVehicle_mileage(String vehicle_mileage) {
        this.vehicle_mileage = vehicle_mileage;
    }

    public String getVehicle_style() {
        return vehicle_style;
    }

    public void setVehicle_style(String vehicle_style) {
        this.vehicle_style = vehicle_style;
    }

    public String getVehicle_trim() {
        return vehicle_trim;
    }

    public void setVehicle_trim(String vehicle_trim) {
        this.vehicle_trim = vehicle_trim;
    }

    public String getVehicle_tag_renewal_date() {
        return vehicle_tag_renewal_date;
    }

    public void setVehicle_tag_renewal_date(String vehicle_tag_renewal_date) {
        this.vehicle_tag_renewal_date = vehicle_tag_renewal_date;
    }

    public String getVehicle_warranty_from() {
        return vehicle_warranty_from;
    }

    public void setVehicle_warranty_from(String vehicle_warranty_from) {
        this.vehicle_warranty_from = vehicle_warranty_from;
    }

    public String getVehicle_warranty_to() {
        return vehicle_warranty_to;
    }

    public void setVehicle_warranty_to(String vehicle_warranty_to) {
        this.vehicle_warranty_to = vehicle_warranty_to;
    }

    public String getVehicle_extended_warranty_from() {
        return vehicle_extended_warranty_from;
    }

    public void setVehicle_extended_warranty_from(String vehicle_extended_warranty_from) {
        this.vehicle_extended_warranty_from = vehicle_extended_warranty_from;
    }

    public String getVehicle_extended_warranty_to() {
        return vehicle_extended_warranty_to;
    }

    public void setVehicle_extended_warranty_to(String vehicle_extended_warranty_to) {
        this.vehicle_extended_warranty_to = vehicle_extended_warranty_to;
    }

    public String getKbb_price() {
        return kbb_price;
    }

    public void setKbb_price(String kbb_price) {
        this.kbb_price = kbb_price;
    }

    public String getVehicle_manual() {
        return vehicle_manual;
    }

    public void setVehicle_manual(String vehicle_manual) {
        this.vehicle_manual = vehicle_manual;
    }

    public String getVehicle_loan_amount() {
        return vehicle_loan_amount;
    }

    public void setVehicle_loan_amount(String vehicle_loan_amount) {
        this.vehicle_loan_amount = vehicle_loan_amount;
    }

    public String getVehicle_emi() {
        return vehicle_emi;
    }

    public void setVehicle_emi(String vehicle_emi) {
        this.vehicle_emi = vehicle_emi;
    }

    public String getVehicle_interest() {
        return vehicle_interest;
    }

    public void setVehicle_interest(String vehicle_interest) {
        this.vehicle_interest = vehicle_interest;
    }

    public String getVehicle_loan_tenure() {
        return vehicle_loan_tenure;
    }

    public void setVehicle_loan_tenure(String vehicle_loan_tenure) {
        this.vehicle_loan_tenure = vehicle_loan_tenure;
    }

    public String getVehicle_insurance_document() {
        return vehicle_insurance_document;
    }

    public void setVehicle_insurance_document(String vehicle_insurance_document) {
        this.vehicle_insurance_document = vehicle_insurance_document;
    }

    public String getVehicle_extended_waranty_document() {
        return vehicle_extended_waranty_document;
    }

    public void setVehicle_extended_waranty_document(String vehicle_extended_waranty_document) {
        this.vehicle_extended_waranty_document = vehicle_extended_waranty_document;
    }

    public String getVehicle_insurance_expiration_date() {
        return vehicle_insurance_expiration_date;
    }

    public void setVehicle_insurance_expiration_date(String vehicle_insurance_expiration_date) {
        this.vehicle_insurance_expiration_date = vehicle_insurance_expiration_date;
    }

    public String getVehicle_tag_expiration_date() {
        return vehicle_tag_expiration_date;
    }

    public void setVehicle_tag_expiration_date(String vehicle_tag_expiration_date) {
        this.vehicle_tag_expiration_date = vehicle_tag_expiration_date;
    }

    public ArrayList<String> getVehicle_image() {
        return vehicle_image;
    }

    public void setVehicle_image(ArrayList<String> vehicle_image) {
        this.vehicle_image = vehicle_image;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date_entered);
        dest.writeString(date_modified);
        dest.writeString(deleted);
        dest.writeString(name);
        dest.writeString(user_id);
        dest.writeString(dealer_id);
        dest.writeString(vehicle_vin);
        dest.writeString(vehicle_type);
        dest.writeString(vehicle_make);
        dest.writeString(vehicle_model);
        dest.writeString(vehicle_year);
        dest.writeString(vehicle_color);
        dest.writeString(vehicle_mileage);
        dest.writeString(vehicle_style);
        dest.writeString(vehicle_trim);
        dest.writeString(vehicle_tag_renewal_date);
        dest.writeString(vehicle_warranty_from);
        dest.writeString(vehicle_warranty_to);
        dest.writeString(vehicle_extended_warranty_from);
        dest.writeString(vehicle_extended_warranty_to);
        dest.writeString(kbb_price);
        dest.writeString(vehicle_manual);
        dest.writeString(vehicle_loan_amount);
        dest.writeString(vehicle_emi);
        dest.writeString(vehicle_interest);
        dest.writeString(vehicle_loan_tenure);
        dest.writeString(vehicle_insurance_document);
        dest.writeString(vehicle_extended_waranty_document);
        dest.writeString(vehicle_insurance_expiration_date);
        dest.writeString(vehicle_tag_expiration_date);
        dest.writeList(vehicle_image);
    }

    // Creator
    public static final Parcelable.Creator<VehicleInfo> CREATOR
            = new Parcelable.Creator<VehicleInfo> () {
        public VehicleInfo createFromParcel(Parcel in) {
            return new VehicleInfo(in);
        }

        public VehicleInfo[] newArray(int size) {
            return new VehicleInfo[size];
        }
    };

    // "De-parcel object
    public VehicleInfo(Parcel in) {
        this.id = in.readString();
        this.date_entered = in.readString();
        this.date_modified = in.readString();
        this.deleted = in.readString();
        this.name = in.readString();
        this.user_id = in.readString();
        this.dealer_id = in.readString();
        this.vehicle_vin = in.readString();
        this.vehicle_type = in.readString();
        this.vehicle_make = in.readString();
        this.vehicle_model = in.readString();
        this.vehicle_year = in.readString();
        vehicle_color = in.readString();
        this.vehicle_mileage = in.readString();
        this.vehicle_style = in.readString();
        this.vehicle_trim = in.readString();
        this.vehicle_tag_renewal_date = in.readString();
        this.vehicle_warranty_from = in.readString();
        this.vehicle_warranty_to = in.readString();
        this.vehicle_extended_warranty_from = in.readString();
        this.vehicle_extended_warranty_to = in.readString();
        this.kbb_price = in.readString();
        this.vehicle_manual = in.readString();
        this.vehicle_loan_amount = in.readString();
        this.vehicle_emi = in.readString();
        this.vehicle_interest = in.readString();
        this.vehicle_loan_tenure = in.readString();
        this.vehicle_insurance_document = in.readString();
        this.vehicle_extended_waranty_document = in.readString();
        this.vehicle_insurance_expiration_date = in.readString();
        this.vehicle_tag_expiration_date = in.readString();
        this.vehicle_image = in.readArrayList(VehicleInfo.class.getClassLoader());

    }
}
