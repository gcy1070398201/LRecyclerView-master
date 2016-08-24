package com.example.mytext;

import android.app.Application;

import com.example.mytext.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CTXD-24 on 2016/8/18.
 */

public class App extends Application {
    private static App sInstance;
    Api mApi;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        mApi = new Retrofit.Builder()
                .baseUrl("http://192.168.0.22:8085/ext/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
    }
    public static App getInstance(){
        return sInstance;
    }
    public Api getApi(){
        return mApi;
    }

}
