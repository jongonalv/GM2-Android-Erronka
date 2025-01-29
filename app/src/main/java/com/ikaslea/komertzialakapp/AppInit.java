package com.ikaslea.komertzialakapp;

import android.app.Application;

import com.ikaslea.komertzialakapp.utils.DBManager;

public class AppInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.inizializatu(this);
    }
}
