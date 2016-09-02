package coxaxle.cox.automotive.com.coxaxle.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import coxaxle.cox.automotive.com.coxaxle.presentation.LoginActivity;

/**
 * Created by Kishore on 7/21/2016.
 */
public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "CoxAxleUserPref";

    // All Shared Preferences Keys
    // private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_FIRSTNAME = "first_name";
    public static final String KEY_LASTNAME = "last_name";
    public static final String KEY_PHONENUMBER = "phone";
    public static final String KEY_USERID = "user_id";
    public static final String KEY_EMAIL = "email";

    public static final String REMEMBER_ME = "rememberMe";

    String FCM_TOKEN_ID = "fcmTokenId";


    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);

        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String userId, String email, boolean rememberMe){

        // Storing login value as TRUE
        editor.putBoolean(REMEMBER_ME, false);

        editor.putString(KEY_USERID, userId);

        if(rememberMe) {


            // Storing email in pref
            editor.putString(KEY_EMAIL, email);

            editor.putBoolean(REMEMBER_ME, rememberMe);
        }
        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isRememberMeChecked()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Save User data in prefs file
     * */
    public void saveUserDetailsPref(HashMap<String, String> mapDetails)
    {
        editor.putString(KEY_USERID, mapDetails.get(KEY_USERID));
        editor.putString(KEY_FIRSTNAME, mapDetails.get(KEY_FIRSTNAME));
        editor.putString(KEY_LASTNAME, mapDetails.get(KEY_LASTNAME));
        editor.putString(KEY_EMAIL, mapDetails.get(KEY_EMAIL));
        editor.putString(KEY_PHONENUMBER, mapDetails.get(KEY_PHONENUMBER));

        editor.commit();
    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_PHONENUMBER, pref.getString(KEY_PHONENUMBER, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isRememberMeChecked(){

        return pref.getBoolean(REMEMBER_ME, false);
    }

    public String getUserId(){

        String UserId = pref.getString(KEY_USERID, null);

        return UserId;

    }

    public String getEmailId(){

        String UserId = pref.getString(KEY_EMAIL, null);

        return UserId;

    }

    public void StoreFCMTokenId(String fcmTokenID){

        editor.putString(FCM_TOKEN_ID, fcmTokenID);

    }

}