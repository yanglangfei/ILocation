package com.yf.ilocation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/14.
 */

public class StringUtils {

    public  static  String getString(String s){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        return  s+sdf.format(new Date());
    }
}
