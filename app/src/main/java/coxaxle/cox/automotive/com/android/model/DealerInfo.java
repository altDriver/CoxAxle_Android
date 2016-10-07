package coxaxle.cox.automotive.com.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kishore on 9/27/2016.
 */

public class DealerInfo implements Parcelable{

    String name;//: "Sabitha",
     //: "(276) 343-7669",
    String email;//: "sabitha@vensaiinc.com",
    String dealer_twiter_page_link;//: "",
    String dealer_fb_page_link;
    /*"mon_from//": "8AM",
    "mon_to": "10PM",
    "tue_from": "8AM",
    "tue_to": "10PM",
    "thu_from": "8AM",
    "thu_to": "10PM",
    "fri_from": "8AM",
    "fri_to": "10PM",
    "sat_from": "8AM",
    "sat_to": "10PM",
    "sun_from": "8AM",
    "sun_to": "10PM",*/
    String main_contact_number;//": "(888) 888-8888",
    String sale_contact;//": "(888) 888-8888",
    String service_desk_contact;//": "(555) 555-5555",
    String collision_desk_contact;//": "(999) 999-9999",
    String dealer_logo;//": "http://192.168.8.101/ecommerce_crm/coxaxle/public/dealers_logo/dealers_thumb/Test111_30758_Penguins.jpg",




    ArrayList<String> banner_image_urls;//":

    public HashMap<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(HashMap<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    HashMap<String,String> phoneNumbers;


    public DealerInfo(String name, String email, String dealer_twiter_page_link, String dealer_fb_page_link, String main_contact_number, String sale_contact, String service_desk_contact, String collision_desk_contact, String dealer_logo, ArrayList<String> banner_image_urls,  HashMap<String,String> phoneNumbers) {
        this.name = name;

        this.email = email;
        this.dealer_twiter_page_link = dealer_twiter_page_link;
        this.dealer_fb_page_link = dealer_fb_page_link;
        this.main_contact_number = main_contact_number;
        this.sale_contact = sale_contact;
        this.service_desk_contact = service_desk_contact;
        this.collision_desk_contact = collision_desk_contact;
        this.dealer_logo = dealer_logo;
        this.banner_image_urls = banner_image_urls;
        this.phoneNumbers = phoneNumbers;
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
        this.email =in.readString();
        this.dealer_twiter_page_link = in.readString();
        this.dealer_fb_page_link = in.readString();
        this.main_contact_number = in.readString();
        this.sale_contact = in.readString();
        this.service_desk_contact = in.readString();
        this.collision_desk_contact = in.readString();
        this.dealer_logo = in.readString();
        this.banner_image_urls = banner_image_urls;


    }
}
