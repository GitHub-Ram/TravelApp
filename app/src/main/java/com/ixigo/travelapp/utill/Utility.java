package com.ixigo.travelapp.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

/**
 * Created by F49558B on 4/8/2017.
 */

public class Utility {

    public static String Current_trip = "CURRENT_TRP";
    public static String Past_trip = "PAST_TRP";
    public static String  Destination = "DESTINATION",From = "FROM" ,goDate = "GO_DATE", returnDate = "RETURN_DATE" ,tripName = "TRIP_NAME";

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
    }

    public static void setCurrentTrip(Context context,String  destination,String from,String GoDate,String ReturnDate,String TripName){
        SharedPreferences lSharedPreferences = context.getSharedPreferences(Current_trip,0);
        SharedPreferences.Editor     lEditor = lSharedPreferences.edit();
        lEditor.putString(Destination, destination);
        lEditor.putString(From, from);
        lEditor.putString(goDate, GoDate);
        lEditor.putString(returnDate, ReturnDate);
        lEditor.putString(tripName, TripName);
        lEditor.apply();
    }

    public static TripInfo getCurrentTrip(Context context){
        SharedPreferences lSharedPreferences = context.getSharedPreferences(Current_trip,0);
        TripInfo ti = new TripInfo();
        ti.setDestination(lSharedPreferences.getString(Destination, "NA"));
        ti.setSource(lSharedPreferences.getString(From, "NA"));
        ti.setFromDate(lSharedPreferences.getString(goDate, "NA"));
        ti.setToDate(lSharedPreferences.getString(returnDate, "NA"));
        ti.setTripName(lSharedPreferences.getString(tripName, "NA"));
        return  ti;
    }

    public static void setPastTrip(Context context,String  destination,String from,String GoDate,String ReturnDate,String TripName){
        SharedPreferences lSharedPreferences = context.getSharedPreferences(Past_trip,0);
        SharedPreferences.Editor     lEditor = lSharedPreferences.edit();
        lEditor.putString(Destination, destination);
        lEditor.putString(From, from);
        lEditor.putString(goDate, GoDate);
        lEditor.putString(returnDate, ReturnDate);
        lEditor.putString(tripName, TripName);
        lEditor.apply();
    }

    public static TripInfo getPastTrip(Context context){
        SharedPreferences lSharedPreferences = context.getSharedPreferences(Past_trip,0);
        TripInfo ti = new TripInfo();
        ti.setDestination(lSharedPreferences.getString(Destination, "NA"));
        ti.setSource(lSharedPreferences.getString(From, "NA"));
        ti.setFromDate(lSharedPreferences.getString(goDate, "NA"));
        ti.setToDate(lSharedPreferences.getString(returnDate, "NA"));
        ti.setTripName(lSharedPreferences.getString(tripName, "NA"));
        return  ti;
    }



    public static boolean isOneTimeSetUp(Context context){
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
