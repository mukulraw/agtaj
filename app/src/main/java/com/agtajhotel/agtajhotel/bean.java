package com.agtajhotel.agtajhotel;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class bean extends Application {

    String BASE_URL = "http://agtajhotel.com/Restaurent/";

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

    }
}
