package coxaxle.cox.automotive.com.android.model;

/**
 * Created by Kishore on 8/1/2016.
 */
public class Constants {

    static String base_uat_url = //"http://192.168.8.101/ecommerce_crm/coxaxle_api/public/";
            "http://192.168.8.101/ecommerce_crm/uatcoxapi/public/";
    // User Registration
    public static final String USER_REGISTRATION_URL = base_uat_url+"register";

    // Login
    public static final String LOGIN_URL = base_uat_url+"customer";

    //Features / user permissions list
    public static final String FEATURES_LIST_URL = base_uat_url+"customer/featuretabs";

    // change Password
    public static final String CHANGE_PASSWORD_URL = base_uat_url+"customer/updatepwd/id";

    // Forgot Password type2(link will be sent to user for reset password)
    public static final String FORGOT_PASSWORD_RESET_LINK = base_uat_url+"customer/resetpassword";

    //change language
    public static final String CHANGE_LANGUAGE = base_uat_url+"customer/language/id";

    //Add Vehicle
    public static final String ADD_VEHICLE_URL = base_uat_url+"vehicle/create";

    //Vehicle Info
    public static final String VEHICLE_INFO_URL = base_uat_url+"vehicle/view";

    //Edit Vehicle
    public static final String EDIT_VEHICLE_URL = base_uat_url+"vehicle/update";

    //Delete Vehicle
    public static final String DELETE_VEHICLE_URL = base_uat_url+"vehicle/delete";

    //Garage Info
    public static final String GARAGE_INFO_URL = base_uat_url+"vehicle/list";

    public static final String GET_ACCOUNT_INFO = base_uat_url+"user/list";

    public static final String UPDATE_ACCOUNT_INFO = base_uat_url+"user/update";

    public static final String XTIME_INFO_URL = base_uat_url+"xtime";

    public static final String INVENTORY_SEARCH_FILTERS_URL = base_uat_url+"searchfilters";

    //Logout(post)
    public static final String LOG_OUT_URL = base_uat_url+"customer/logout";

    public static final String DEALER_INFO_URL = base_uat_url+"dealers/dealersinfo";

    public static final String DEALER_INVENTORY_SEARCH_ON_AUTOTRADER_URL = base_uat_url+"listing";

    public static final String DEALER_INVENTORY_SET_FAVORITE_URL = base_uat_url+"savefavourite";

    public static final String DEALER_INVENTORY_SAVE_SEARCH_URL = base_uat_url+"savesearch";

    public static final String DEALER_INVENTORY_SAVED_SEARCH_URL = base_uat_url+"savedsearch";

    public static final String DEALER_INVENTORY_FAVOURITE_LIST_URL = base_uat_url+"favouritelist";

    public static final String DEALER_INVENTORY_SAVED_SEARCH_DELETE_URL = base_uat_url+"removesearch";

    public static final String VEHICLE_MAKE_MODEL_YEAR_EDMUND_URL = "http://api.edmunds.com/api/vehicle/v2/makes?fmt=json&api_key=kmez685mgdnhav6rbwfbq3xn"; //&state=new&view=full

    public static final String VEHICLE_STYLE_TRIM_EDMUND_URL = "https://api.edmunds.com/api/vehicle/v2/lexus/rx350/2011/styles?fmt=json&api_key=kmez685mgdnhav6rbwfbq3xn"; //&view=full



    public static final String DEALER_CODE = "1000";
}
