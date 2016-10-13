package com.yf.ilocation;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yf.ilocation.activity.Main;
import com.yf.ilocation.activity.MyMain;
import com.yf.ilocation.activity.MyRoound;
import com.yf.ilocation.activity.MyTaiJi;
import com.yf.ilocation.receiver.WatchService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        IntentFilter filter=new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        WatchService service=new WatchService();
        registerReceiver(service,filter);
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             startActivity(new Intent(MainActivity.this, MyMain.class));
            }
        },2000);
    }
}
