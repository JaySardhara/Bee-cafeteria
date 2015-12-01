package com.cmpe272.beecafeteria.others;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cmpe272.beecafeteria.activities.LoginActivity;
import com.cmpe272.beecafeteria.services.GCMRegistrationIntentService;
import com.cmpe272.beecafeteria.services.GCMUnregistrationIntentService;

import java.util.HashMap;

/**
 * Created by Rushil on 11/20/2015.
 */
public class SessionManager {


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Beecafeteria";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    /**
     * Create login session
     * */
    public static void createLoginSession(Activity activity, String email){

        registerGCMForUser(activity);

        SharedPreferences.Editor editor = activity.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).edit();

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public static void checkLogin(Context context){
        // Check login status
        if(!isLoggedIn(context)){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public static HashMap<String, String> getUserDetails(Context context){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        //user.put(KEY_NAME, context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public static void logoutUser(Activity activity){

        unregisterGCMForUser(activity);

        SharedPreferences.Editor editor = activity.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).edit();
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        // After logout redirect user to Loing Activity
        Intent i = new Intent(activity, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        activity.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public static boolean isLoggedIn(Context context){
        return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).getBoolean(IS_LOGIN, false);
    }

    public static void registerGCMForUser(Activity activity){

        if (Utils.checkPlayServices(activity)) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(activity, GCMRegistrationIntentService.class);
            activity.startService(intent);
        }
    }

    public static void unregisterGCMForUser(Activity activity){

        if (Utils.checkPlayServices(activity)) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(activity, GCMUnregistrationIntentService.class);
            activity.startService(intent);
        }
    }
}
