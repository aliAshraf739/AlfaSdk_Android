package com.example.alfasdk;

import android.content.Context;

import androidx.multidex.MultiDex;

public class Application extends android.app.Application {

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        Application.mAppContext = mAppContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
