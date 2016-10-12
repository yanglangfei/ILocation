package com.yf.ilocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yf.ilocation.activity.Main;
import com.yf.ilocation.activity.MyRoound;
import com.yf.ilocation.activity.MyTaiJi;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             startActivity(new Intent(MainActivity.this, MyTaiJi.class));
            }
        },2000);
    }
}
