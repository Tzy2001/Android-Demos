package com.example.imagediscerndemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName SPUtils
 * @Author TZY
 * @Date 2024/1/30 20:45
 **/
public class SPUtils {//SharedPreferences工具类

    private static final String NAME="config";
    public static void putBoolean(String key, boolean value, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean(String key, boolean defValue, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public static void putString(String key, String value, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(String key, String defValue, Context context){
        if (context!=null){
            SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            return sp.getString(key,defValue);
        }
      return "";
    }

    public static void putInt(String key, int value, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    public static int getInt(String key, int defValue, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putLong(String key, long value, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key,value).commit();
    }
    public static long getLong(String key, long defValue, Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getLong(key,defValue);
    }

    public static void remove(String key,Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }


}
