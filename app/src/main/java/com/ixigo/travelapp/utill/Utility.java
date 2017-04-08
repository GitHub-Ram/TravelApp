package com.ixigo.travelapp.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

/**
 * Created by F49558B on 4/8/2017.
 */

public class Utility {

    public static String getDeviceId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    public static boolean isLoggedIn(Context context){
        SharedPreferences lSharedPreferences = context.getSharedPreferences("LoginState",0);
        return  lSharedPreferences.getBoolean("islogin",false);

    }
    public static void setLogInState(Context context,boolean  isLogin){
        SharedPreferences lSharedPreferences = context.getSharedPreferences("LoginState",0);
        SharedPreferences.Editor     lEditor = lSharedPreferences.edit();
        lEditor.putBoolean("islogin", isLogin);
        lEditor.apply();

    }public static boolean isOneTimeSetUp(Context context){
        SharedPreferences lSharedPreferences = context.getSharedPreferences("LoginState",0);
        return  lSharedPreferences.getBoolean("isOts",false);

    }
    public static void setOneTimeSetUp(Context context,boolean  isLogin){
        SharedPreferences lSharedPreferences = context.getSharedPreferences("LoginState",0);
        SharedPreferences.Editor     lEditor = lSharedPreferences.edit();
        lEditor.putBoolean("isOts", isLogin);
        lEditor.apply();

    }
}
