package com.yf.ilocation.fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yf.ilocation.R;
/**
 * Created by Administrator on 2016/10/13.
 */

public class F1 extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          view=inflater.inflate(R.layout.ui_control,null);
        initView();
        return view;
    }

    private void initView() {
        view.findViewById(R.id.btn_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //安装
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_PACKAGE_ADDED);
                intent.setData(Uri.fromParts("package","com.iknown.ylf.iknown",null));
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_uninstall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //卸载
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DELETE);
                intent.setData(Uri.fromParts("package","com.iknown.ylf.iknown",null));
                getActivity().startActivity(intent);

            }
        });

        view.findViewById(R.id.btn_air).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切换飞行模式
                ContentResolver cr = getActivity().getContentResolver();
                if(Settings.System.getString(cr,Settings.Global.AIRPLANE_MODE_ON).equals("0")){
                    Settings.System.putString(cr,Settings.Global.AIRPLANE_MODE_ON,"1");
                    Intent intent=new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                    getActivity().sendBroadcast(intent);
                    Toast.makeText(getActivity(), "开启飞行模式", Toast.LENGTH_SHORT).show();
                }else {
                    Settings.System.putString(cr,Settings.Global.AIRPLANE_MODE_ON,"0");
                    Intent intent=new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                    getActivity().sendBroadcast(intent);
                    Toast.makeText(getActivity(), "关闭飞行模式", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
