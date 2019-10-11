package com.llk.mytaobao.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by LG on 2018/11/29.
 */

public class ProApplication extends Application{
    private static Context mContext;
    private static ProApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static ProApplication getInstance() {
        return instance;
    }



    public static String HEADIMG = "";

    public static String BANNERIMG = "";

    public static synchronized ProApplication context() {
        return (ProApplication) mContext;
    }
}
