package com.yf.ilocation.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.yf.ilocation.receiver.WatchService;

/**
 * Created by Administrator on 2016/10/12.
 */

public class B extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter=new IntentFilter("com.yf.watch");
        WatchService service=new WatchService(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                int state=intent.getIntExtra("state",-1);
                if(state==0){
                    Intent A=new Intent(context,A.class);
                    context.startService(A);
                    Toast.makeText(context, "restart A", Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerReceiver(service,filter);
        Log.i("111","service B start....");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("111","B start command...");
        return START_REDELIVER_INTENT;

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("111"," B start....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent();
        intent.setAction("com.yf.watch");
        intent.putExtra("state",1);
        sendBroadcast(intent);
        Log.i("111","service B destory...");
    }
}
