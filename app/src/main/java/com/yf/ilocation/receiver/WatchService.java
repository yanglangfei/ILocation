package com.yf.ilocation.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yf.ilocation.service.A;
import com.yf.ilocation.service.B;

/**
 * Created by Administrator on 2016/10/12.
 */

public class WatchService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent a=new Intent(context, A.class);
            context.startService(a);
            Intent b=new Intent(context,B.class);
            context.startService(b);
            Toast.makeText(context, "开机 ....", Toast.LENGTH_SHORT).show();
        }

    }
}
