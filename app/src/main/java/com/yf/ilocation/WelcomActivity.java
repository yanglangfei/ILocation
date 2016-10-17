package com.yf.ilocation;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import com.yf.ilocation.activity.MainActivity;
import com.yf.ilocation.receiver.WatchService;
import com.yf.ilocation.service.A;
import com.yf.ilocation.service.B;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WelcomActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        IntentFilter filter=new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        WatchService service=new WatchService();
        registerReceiver(service,filter);

        Intent a=new Intent(WelcomActivity.this,A.class);
        startService(a);

        Intent b=new Intent(WelcomActivity.this,B.class);
        startService(b);
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomActivity.this, MainActivity.class));
            }
        },2000);
    }
}
