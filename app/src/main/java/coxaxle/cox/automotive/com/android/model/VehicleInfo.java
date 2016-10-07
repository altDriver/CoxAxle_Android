package coxaxle.cox.automotive.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Srinivas on 8/31/2016.
 */
public class VehicleInfo implements Parcelable {



    /*public String id;
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
    */
    public String id;//": "1",
    public String name;//": "car1",
    public String vin;//": "BDNFBFH876JHFBFN",
    public String vehicle_type;//": "New",
    public String make;//": "Mrecedes",
    public String model;//": "Santa Fe Sports SaFeX",
    public String year;//": "2011",
    public String color;//": "Silver",
    public String mileage;//": "1234",
    public String style;//": "sedan",
    public String trim;//": "vxi",
    public String waranty_from;//": "2015-01-10",
    public String waranty_to;//": "2016-12-31",
    public String extended_waranty_from;//": "0000-00-00",
    public String extended_waranty_to;//": "0000-00-00",
    public String manual;//": ""
    public String photo;
    public String extended_waranty_document;
    public ArrayList<String> insurance_document;

    public String getTag_expiration_date() {
        return tag_expiration_date;
    }

    public void setTag_expiration_date(String tag_expiration_date) {
        this.tag_expiration_date = tag_expiration_date;
    }

    public String getInsurence_expiration_date() {
        return insurence_expiration_date;
    }

    public void setInsurence_expiration_date(String insurence_expiration_date) {
        this.insurence_expiration_date = insurence_expiration_date;
    }

    public String tag_expiration_date;
    public String insurence_expiration_date;
    //public ArrayList<String> extended_waranty_document;
    //public ArrayList<String> vehicle_image;

    public String getKbb_price() {
        return kbb_price;
    }

    public void setKbb_price(String kbb_price) {
        this.kbb_price = kbb_price;
    }

    public String kbb_price;

    public VehicleInfo(){

    }
    public VehicleInfo(String id, String name, String vin, String vehicle_type, String make, String model,
                       String year, String color, String mileage, String style, String trim,
                       String waranty_from, String waranty_to, String extended_waranty_from,
                       String extended_waranty_to, String manual, ArrayList<String> insurance_document,
                       String extended_waranty_document, String vehicle_image ,String kbb_price,
                        String tag_expiration_date,
                        String insurence_expiration_date) {
        this.id = id;
        this.name = name;
        this.vin = vin;
        this.vehicle_type = vehicle_type;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.style = style;
        this.trim = trim;
        this.waranty_from = waranty_from;
        this.waranty_to = waranty_to;
        this.extended_waranty_from = extended_waranty_from;
        this.extended_waranty_to = extended_waranty_to;
        this.manual = manual;
        this.insurance_document = insurance_document;
        this.extended_waranty_document = extended_waranty_document;
        this.photo = vehicle_image;
        this.kbb_price = kbb_price;
        this.tag_expiration_date = tag_expiration_date;
        this.insurence_expiration_date = insurence_expiration_date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getWaranty_from() {
        return waranty_from;
    }

    public void setWaranty_from(String waranty_from) {
        this.waranty_from = waranty_from;
    }

    public String getWaranty_to() {
        return waranty_to;
    }

    public void setWaranty_to(String waranty_to) {
        this.waranty_to = waranty_to;
    }

    public String getExtended_waranty_from() {
        return extended_waranty_from;
    }

    public void setExtended_waranty_from(String extended_waranty_from) {
        this.extended_waranty_from = extended_waranty_from;
    }

    public String getExtended_waranty_to() {
        return extended_waranty_to;
    }

    public void setExtended_waranty_to(String extended_waranty_to) {
        this.extended_waranty_to = extended_waranty_to;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public ArrayList<String> getInsurance_document() {
        return insurance_document;
    }

    public void setInsurance_document(ArrayList<String> insurance_document) {
        this.insurance_document = insurance_document;
    }

    public String getExtended_waranty_document() {
        return extended_waranty_document;
    }

    public void setExtended_waranty_document(String extended_waranty_document) {
        this.extended_waranty_document = extended_waranty_document;
    }

    public String getVehicle_image() {
        return photo;
    }

    public void setVehicle_image(String vehicle_image) {
        this.photo = vehicle_image;
    }

    public static Creator<VehicleInfo> getCREATOR() {
        return CREATOR;
    }

    /*
    * A constructor that initializes the Vehicles object
    */
   /* public VehicleInfo(String id, String date_entered, String date_modified, String deleted, String name, String user_id, String dealer_id, String vehicle_vin, String vehicle_type, String vehicle_make,
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

    public VehicleInfo(String name, String dealer_code, String phone, String address, String email,  String dealer_twiter_page_link,
                       String dealer_fb_page_link, String main_contact_number, String sale_contact, String service_desk_contact,
                       String collision_desk_contact, String dealer_logo, String date_modified, ArrayList<String> banner_image){
        super();
        this.name = name;
        this.date_entered = phone;
        this.date_modified = address;
        this.deleted = email;
        this.id = dealer_twiter_page_link;
        this.user_id = dealer_fb_page_link;
        this.dealer_id = dealer_code;
        this.kbb_price = main_contact_number;
        this.vehicle_vin = sale_contact;
        this.vehicle_type = service_desk_contact;
        this.vehicle_make = collision_desk_contact;
        this.vehicle_model = dealer_logo;
        this.vehicle_year = date_modified;
        this.vehicle_image = banner_image;
    }*/


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(vin);
        dest.writeString(vehicle_type);
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(color);
        dest.writeString(mileage);
        dest.writeString(style);
        dest.writeString(trim);
        dest.writeString(waranty_from);
        dest.writeString(waranty_to);
        dest.writeString(extended_waranty_from);
        dest.writeString(extended_waranty_to);
        dest.writeString(manual);
        dest.writeString(photo);//
        dest.writeString(extended_waranty_document);//
        dest.writeList(insurance_document);
        dest.writeString(tag_expiration_date);
        dest.writeString(insurence_expiration_date);
        //dest.writeList(extended_waranty_document);
        //dest.writeList(vehicle_image);
    }

    // Creator
    public static final Creator<VehicleInfo> CREATOR
            = new Creator<VehicleInfo>() {
        public VehicleInfo createFromParcel(Parcel in) {
            return new VehicleInfo(in);
        }

        public VehicleInfo[] newArray(int size) {
            return new VehicleInfo[size];
        }
    };

    // "De-parcel object
    public VehicleInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        vin = in.readString();
        vehicle_type = in.readString();
        make = in.readString();
        model = in.readString();
        year = in.readString();
        color = in.readString();
        mileage = in.readString();
        style = in.readString();
        trim = in.readString();
        waranty_from = in.readString();
        waranty_to = in.readString();
        extended_waranty_from = in.readString();
        extended_waranty_to = in.readString();
        manual = in.readString();
        photo = in.readString();
        extended_waranty_document = in.readString();
        insurance_document = in.readArrayList(VehicleInfo.class.getClassLoader());
        tag_expiration_date = in.readString();
        insurence_expiration_date = in.readString();

        //extended_waranty_document = in.readArrayList(VehicleInfo.class.getClassLoader());
        //vehicle_image = in.readArrayList(VehicleInfo.class.getClassLoader());

        /*this.date_entered = in.readString();
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
        this.vehicle_tag_expiration_date = in.readString();*/

    }
}
