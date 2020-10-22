package com.example.yeipos.model;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

