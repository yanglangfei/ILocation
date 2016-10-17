package com.yf.ilocation.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.yf.ilocation.R;
import com.yf.ilocation.fragment.More;
import com.yf.ilocation.fragment.MyLocation;
import com.yf.ilocation.fragment.MyRound;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private  MyLocation location;
    private  MyRound round;
    private  More more;
    private RadioGroup group;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        initView();
    }

    private void initView() {
        group= (RadioGroup) findViewById(R.id.main_group);
        group.setOnCheckedChangeListener(this);
        fm=getSupportFragmentManager();
        if(group.getChildCount()>0){
            RadioButton rb= (RadioButton) group.getChildAt(0);
            rb.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentTransaction ft = fm.beginTransaction();
        switch (i){
            case  R.id.location:
                if(location==null){
                    location=new MyLocation();
                }
                ft.replace(R.id.fragment,location).commit();
                break;
            case R.id.round:
                if(round==null){
                    round=new MyRound();
                }
                ft.replace(R.id.fragment,round).commit();
                break;
            case R.id.more:
                if(more==null){
                    more=new More();
                }
                ft.replace(R.id.fragment,more).commit();
                break;
            default:
                break;
        }
    }
}
