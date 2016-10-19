package com.yf.ilocation.aplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/10/10.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
