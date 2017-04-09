package com.ixigo.travelapp.utill;

import com.android.volley.Request;

/**
 * Created by F49558B on 4/8/2017.
 */

public class Constants {
    public static String USER_PREFERENCES ="USER_PREFERENCES";
    public static String AUTO_COMPLETE_CITY = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=";
    public static String CITY_RESOURCE (String id,String items,int skip,int limit){
        String url = "http://build2.ixigo.com/api/v3/namedentities/city/";
        String url2 = "/categories?apiKey=ixicode!2$&type=";
        String url3 = "&skip=";
        String url4 = "&limit=";
        url = url +id+url2+items+url3+skip+url4+limit;
        //url = "http://build2.ixigo.com/api/v3/namedentities/city/503b2a70e4b032e338f0ee67/categories?apiKey=ixicode!2$&type=Hotel&skip=1&limit=10";
        return url;
    }
    public static int GET_METHOD    = Request.Method.GET;
    public static int POST_METHOD   = Request.Method.POST;
}
