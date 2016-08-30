package coxaxle.cox.automotive.com.coxaxle.model;

/**
 * Created by Kishore on 8/1/2016.
 */
public class Constants {


    // User Registration
    public static final String USER_REGISTRATION_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/register";

    // Login
    public static final String LOGIN_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer";

    //Features / user permissions list
    public static final String FEATURES_LIST_URL= "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer/featuretabs";


    // change Password
    public static final String CHANGE_PASSWORD_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer/updatepwd/id";

    //Forgot Password type1(autogenerated Password will be sent to mail)
//    public static final String FORGOT_PASSWORD_AUTOGENERATED = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer/forgotpassword";

    // Forgot Password type2(link will be sent to user for reset password)
    public static final String FORGOT_PASSWORD_RESET_LINK = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer/resetpassword";

    //change language
    public static final String CHANGE_LANGUAGE = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/customer/language/id";

    //Add Vehicle
    public static final String ADD_VEHICLE_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicle/create";

    //Vehicle Info
    public static final String VEHICLE_INFO_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicle/view";

    //Edit Vehicle
    public static final String EDIT_VEHICLE = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicle/id";

    //Delete Vehicle
    public static final String DELETE_VEHICLE = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicle/id";

    //Garage Info
    public static final String GARAGE_INFO_URL = "http://192.168.8.101/ecommerce_crm/coxaxle_api/public/vehicle";

    public static final String GET_ACCOUNT_INFO ="http://192.168.8.101/ecommerce_crm/coxaxle_api/public/user/list";

    public static final String UPDATE_ACCOUNT_INFO ="http://192.168.8.101/ecommerce_crm/coxaxle_api/public/user/update";
}