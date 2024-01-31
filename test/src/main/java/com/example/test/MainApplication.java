package com.example.test;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

/**
 * @ClassName MainApplication
 * @Author TZY
 * @Date 2023/11/7 15:56
 **/
public class MainApplication extends Application {
    private final static String TAG="MainApplication";
    private static MainApplication mApp;
    public HashMap<String,String> infoMap=new HashMap<>();
    public static MainApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        mApp=this;
    }
}
