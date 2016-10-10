package coxaxle.cox.automotive.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kishore on 9/27/2016.
 */

public class DealerInfo implements Parcelable{

    String name;
    String phone;
    String address;
    String email;
    String dealer_twiter_page_link;
    String dealer_fb_page_link;
    String owner_id;
    String main_contact_number;
    String sale_contact;
    String service_desk_contact;
    String collision_desk_contact;
    String dealer_logo;
    String manual;
    String privacy_policy;
    ArrayList<String> banner_image_urls;



    public DealerInfo(String name, String phone, String address, String email, String dealer_twiter_page_link, String dealer_fb_page_link, String owner_id, String main_contact_number, String sale_contact, String service_desk_contact, String collision_desk_contact, String dealer_logo, String manual, String privacy_policy, ArrayList<String> banner_image_urls) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.dealer_twiter_page_link = dealer_twiter_page_link;
        this.dealer_fb_page_link = dealer_fb_page_link;
        this.owner_id = owner_id;
        this.main_contact_number = main_contact_number;
        this.sale_contact = sale_contact;
        this.service_desk_contact = service_desk_contact;
        this.collision_desk_contact = collision_desk_contact;
        this.dealer_logo = dealer_logo;
        this.manual = manual;
        this.privacy_policy = privacy_policy;
        this.banner_image_urls = banner_image_urls;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDealer_twiter_page_link() {
        return dealer_twiter_page_link;
    }

    public void setDealer_twiter_page_link(String dealer_twiter_page_link) {
        this.dealer_twiter_page_link = dealer_twiter_page_link;
    }

    public String getDealer_fb_page_link() {
        return dealer_fb_page_link;
    }

    public void setDealer_fb_page_link(String dealer_fb_page_link) {
        this.dealer_fb_page_link = dealer_fb_page_link;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getMain_contact_number() {
        return main_contact_number;
    }

    public void setMain_contact_number(String main_contact_number) {
        this.main_contact_number = main_contact_number;
    }

    public String getSale_contact() {
        return sale_contact;
    }

    public void setSale_contact(String sale_contact) {
        this.sale_contact = sale_contact;
    }

    public String getService_desk_contact() {
        return service_desk_contact;
    }

    public void setService_desk_contact(String service_desk_contact) {
        this.service_desk_contact = service_desk_contact;
    }

    public String getCollision_desk_contact() {
        return collision_desk_contact;
    }

    public void setCollision_desk_contact(String collision_desk_contact) {
        this.collision_desk_contact = collision_desk_contact;
    }

    public String getDealer_logo() {
        return dealer_logo;
    }

    public void setDealer_logo(String dealer_logo) {
        this.dealer_logo = dealer_logo;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public ArrayList<String> getBanner_image_urls() {
        return banner_image_urls;
    }

    public void setBanner_image_urls(ArrayList<String> banner_image_urls) {
        this.banner_image_urls = banner_image_urls;
    }



    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(dealer_twiter_page_link);
        dest.writeString(dealer_fb_page_link);
        dest.writeString(owner_id);
        dest.writeString(main_contact_number);
        dest.writeString(sale_contact);
        dest.writeString(service_desk_contact);
        dest.writeString(collision_desk_contact);
        dest.writeString(dealer_logo);
        dest.writeString(manual);
        dest.writeString(privacy_policy);
        dest.writeList(banner_image_urls);
    }

    // Creator
    public static final Parcelable.Creator<DealerInfo> CREATOR
            = new Parcelable.Creator<DealerInfo> () {
        public DealerInfo createFromParcel(Parcel in) {
            return new DealerInfo(in);
        }

        public DealerInfo[] newArray(int size) {
            return new DealerInfo[size];
        }
    };






        public DealerInfo (Parcel in){


        this.name = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.email = in.readString();
        this.dealer_twiter_page_link = in.readString();
        this.dealer_fb_page_link = in.readString();
        this.owner_id = in.readString();
        this.main_contact_number = in.readString();
        this.sale_contact = in.readString();
        this.service_desk_contact = in.readString();
        this.collision_desk_contact = in.readString();
        this.dealer_logo = in.readString();
        this.manual = in.readString();
        this.privacy_policy = in.readString();
        this.banner_image_urls = in.readArrayList(DealerInfo.class.getClassLoader());


    }
}
