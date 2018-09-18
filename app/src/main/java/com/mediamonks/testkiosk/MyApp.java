package com.mediamonks.testkiosk;

import android.app.Application;

import timber.log.Timber;

/**
 * Created on 18/09/2018.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
