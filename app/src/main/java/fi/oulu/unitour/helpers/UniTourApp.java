package fi.oulu.unitour.helpers;

import android.app.Application;

import com.firebase.client.Firebase;

public class UniTourApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
