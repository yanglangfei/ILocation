package com.yf.ilocation.activity;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yf.ilocation.R;

/**
 * Created by Administrator on 2016/10/12.
 *
 *    路线导航
 *
 */

public class MyPath extends Activity implements View.OnClickListener {
    private EditText fromEt;
    private  EditText toEt;
    private Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_path);
        initView();
    }

    private void initView() {
        fromEt= (EditText) findViewById(R.id.et_from);
        toEt= (EditText) findViewById(R.id.et_to);
        search= (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String from=fromEt.getText().toString();
        String to=toEt.getText().toString();
        if(from.trim().length()<=0){
            return;
        }
        if(to.trim().length()<=0){
            return;
        }
    }
}
