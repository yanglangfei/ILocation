package com.yf.ilocation.service;
import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 *
 * 自动安装
 */

public class MyInstall extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        checkInstall(accessibilityEvent);
    }

    private void checkInstall(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo sort = accessibilityEvent.getSource();
        CharSequence pm = accessibilityEvent.getPackageName();
        switch (accessibilityEvent.getEventType()){
            case  AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                AccessibilityNodeInfo text=accessibilityEvent.getSource();
                CharSequence str = text.getText();
                Log.i("111","str:"+str);
                break;
            case  AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
        }


        Log.i("111","pm:"+pm);
        if(sort!=null){
            boolean isInstall=accessibilityEvent.getPackageName().equals("com.android.packageinstaller");
            if(isInstall){
                installApk(accessibilityEvent);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void installApk(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo root = getRootInActiveWindow();
        List<AccessibilityNodeInfo> nexts = root.findAccessibilityNodeInfosByText("下一步");
        click(nexts);
        List<AccessibilityNodeInfo> installs = root.findAccessibilityNodeInfosByText("安装");
        click(installs);
        List<AccessibilityNodeInfo> opens = root.findAccessibilityNodeInfosByText("打开");
        click(opens);
        //runInBackGround(accessibilityEvent);
    }

    private void runInBackGround(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.getSource().performAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    private void click(List<AccessibilityNodeInfo> nexts) {
      if(nexts!=null){
          for(AccessibilityNodeInfo next : nexts){
              if(next.isEnabled()&&next.isClickable()){
                  next.performAction(AccessibilityNodeInfo.ACTION_CLICK);
              }
          }
      }
    }

    @Override
    public void onInterrupt() {

    }

}
