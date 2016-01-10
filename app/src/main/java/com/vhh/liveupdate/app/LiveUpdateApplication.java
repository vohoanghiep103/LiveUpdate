package com.vhh.liveupdate.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class LiveUpdateApplication extends Application {

    public LiveUpdateApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "7emQMEAitSEAeDKDq9rIBSipnjDcwoC3nst2n60Z", "JkSsl1ATivF9QPTyatjSGVmdxMJsixbnsK2iTssL");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
