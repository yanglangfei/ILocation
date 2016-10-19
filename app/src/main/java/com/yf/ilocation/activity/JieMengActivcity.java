package com.yf.ilocation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yf.ilocation.R;

/**
 * Created by Administrator on 2016/10/18.
 */

public class JieMengActivcity extends Activity implements View.OnClickListener {
    private ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_jm);
        initview();
    }

    private void initview() {
        iv_finish= (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
