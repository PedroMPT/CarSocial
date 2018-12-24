package pt.ismai.pedro.needarideapp;

/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import pt.ismai.pedro.needarideapp.Model.Car;
//import com.parse.facebook.ParseFacebookUtils;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("6b0b6bd8cc1c684b6c13c4bc9fa6e641c735f8f5")
                .clientKey("cb4d8307117603dedf6992eaaa2125bb25953b80")
                .server("http://18.218.170.148:80/parse/")
                .build()
        );


        ParseUser.enableAutomaticUser();
        ParseObject.registerSubclass(Car.class);

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        //ParseFacebookUtils.initialize(this );

    }
}
