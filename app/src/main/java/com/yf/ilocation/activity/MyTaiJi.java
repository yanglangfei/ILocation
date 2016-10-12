package com.yf.ilocation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yf.ilocation.R;
import com.yf.ilocation.service.A;
import com.yf.ilocation.service.B;
import com.yf.ilocation.view.TaiJi;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MyTaiJi extends Activity {
    private TaiJi taiJi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taiji);
        taiJi= (TaiJi) findViewById(R.id.taiJi);
        Intent a=new Intent(MyTaiJi.this,A.class);
        startService(a);

        Intent b=new Intent(MyTaiJi.this,B.class);
        startService(b);


        final Handler handler = new Handler() {
            private float degrees = 0;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                taiJi.setRotate(degrees += 5);
                this.sendEmptyMessageDelayed(0, 80);
            }
        };

        handler.sendEmptyMessageDelayed(0, 20);
    }
}
