package com.example.vizigo.Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.vizigo.Activities.LoginActivity;
import com.example.vizigo.ApplicationClass.ViziGoApp;

/**
 * Created by Arun S on 9/20/2019.
 */
public class PreferenceUtils {
    private static SharedPreferences preferences =
            PreferenceManager.getDefaultSharedPreferences(ViziGoApp.getContext());

    private static boolean loggedIn;
    private static String fullName;
    private static int roll_id;



    public static void loadPreferences() {
        loggedIn = preferences.getBoolean("loggedIn", false);
        fullName = preferences.getString("fullName", fullName);
        roll_id = preferences.getInt("roll_id", roll_id);

    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        PreferenceUtils.loggedIn = loggedIn;
        preferences.edit().putBoolean("loggedIn", loggedIn).apply();

    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        PreferenceUtils.fullName = fullName;
        preferences.edit().putString("fullName", fullName).apply();
    }

    public static int getRoll_id() {
        return roll_id;
    }

    public static void setRoll_id(int roll_id) {
        PreferenceUtils.roll_id = roll_id;
        preferences.edit().putInt("roll_id", roll_id).apply();

    }

    public static void clearPrefs() {
        preferences.edit().putBoolean("loggedIn", false).apply();
        preferences.edit().putString("fullName", "").apply();
        preferences.edit().putInt("roll_id", 0).apply();

        loadPreferences();
    }

    public static void clear() {
        preferences.edit().clear().apply();
    }

    public static void getPreferences() {
        preferences.getAll();
    }

    public static void logout() {
        clearPrefs();
        clear();
        Intent intent;
        intent = new Intent(ViziGoApp.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ViziGoApp.getContext().startActivity(intent);
    }
}
