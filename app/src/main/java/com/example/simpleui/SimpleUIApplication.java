package com.example.simpleui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by ggm on 10/19/15.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始Parse
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, "x3CL9BmV2TTCs6lBCxgbJ7rm4Fautlg8dWXvADrb",
//                "0O7uIE3iLyNprXUzhq7dAwayPrsRHBm1rTfVFQGX");
        Parse.initialize(this, "c84HUPYMQnlEDlsUvv3xtlOj8XpH94Q9Nkaoqt7S",
                "V8iuLIp995ZO5A28uQwPh8FzZZEFmc67jx8Iblnr");


    }
}
