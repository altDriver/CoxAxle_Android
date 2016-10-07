package coxaxle.cox.automotive.com.android.common;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kishore on 7/27/2016.
 */
public class Utility {

    private static Pattern pattern;
    private static Matcher matcher;
    public static final String PREFS_NAME = "CoxAxlePrefsFile";
    public static String STR_USERID = "UserID";
    public static String STR_TOKEN = "Token";

    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String FNAME_PATTERN = "[A-Z][a-zA-Z]*";

    private static final String LNAME_PATTERN = "[a-zA-z]+([ '-][a-zA-Z]+)*";
    private static final String passwordPattrn = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$";
    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */


    public static boolean emailValidate(String email) {

        if (email != null && email.trim().length() > 0) {

            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }

    }


    public static boolean fnameValidate(String name) {

        if (name != null && name.trim().length() > 0) {

            pattern = Pattern.compile(FNAME_PATTERN);
            matcher = pattern.matcher(name);
            return matcher.matches();
        } else {
            return false;
        }

    }

    public static boolean lnameValidate(String name) {

        if (name != null && name.trim().length() > 0) {

            pattern = Pattern.compile(LNAME_PATTERN);
            matcher = pattern.matcher(name);
            return matcher.matches();
        } else {
            return false;
        }

    }

    public static boolean passwordValidation(String password) {
        if (!TextUtils.isEmpty(password) && password != null ) {
            pattern = Pattern.compile(passwordPattrn);
            matcher = pattern.matcher(password);

            return true;
        } else
            return false;
    }

    public static boolean PhoneValidation(String phone) {
        if (phone != null && phone.length() >= 10) {

            return true;
        }
        return false;
    }

    public static boolean ZipcodeValidation(String zipcode) {
        if (zipcode != null && zipcode.length() >= 6) {

            return true;
        }
        return false;
    }

    public static boolean VinValidation(String vehicle_vin) {
        if (vehicle_vin != null && vehicle_vin.length() >= 16) {

            return true;
        }
        return false;
    }

    public static boolean VehicleYearValidation(String vehicle_year) {
        if (vehicle_year != null && vehicle_year.length() >= 4) {

            return true;
        }
        return false;
    }

    public static boolean CommonValidation(String common_name) {
        if (common_name != null && common_name.length() != 0) {

            return true;
        } else
            return false;
    }


}
