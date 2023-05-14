package com.example.animalsound;

import android.app.Application;

public class App extends Application {
    private static App instance;
    private final Storage storage = new Storage();

    public static App getInstance() {
        return instance;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
