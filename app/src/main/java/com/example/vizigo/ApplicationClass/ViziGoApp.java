package com.example.vizigo.ApplicationClass;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.example.vizigo.Utils.PreferenceUtils;

public class ViziGoApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        PreferenceUtils.loadPreferences();



    }

    public static Context getContext() {
        return context;
    }
}
