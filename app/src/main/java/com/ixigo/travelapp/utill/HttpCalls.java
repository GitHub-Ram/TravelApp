package com.ixigo.travelapp.utill;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by F49558B on 4/9/2017.
 */

public class HttpCalls {
    private OnTaskCompleted mOnTaskCompleted;
    private Context mContext;
    public HttpCalls(OnTaskCompleted mOnTaskCompleted, Context mContext) {
        this.mOnTaskCompleted = mOnTaskCompleted;
        this.mContext = mContext;
    }
    public void getRequestForJsonArray(int lMethod, String lUrl, final String lPurpose) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(lMethod, lUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("responseRaw",response.toString());
                mOnTaskCompleted.onTaskCompleted(response,response.toString(),lPurpose);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseErrorRaw",error.toString());
                mOnTaskCompleted.onTaskError("err",lPurpose);
            }
        });
        queue.add(jsObjRequest);
    }

    public void getRequestForJsonObject(int lMethod, String lUrl, final String lPurpose) {
        Log.i("Request",lUrl);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(lMethod, lUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("responseRaw",response.toString());
                if (true) {
                    JSONObject lJSONObject = null;
                    try {
                        lJSONObject = new JSONObject(response.toString());
                        mOnTaskCompleted.onTaskCompleted(null,response.toString(),lPurpose);
                        if(lJSONObject.getString("response_code").equals("200")){
                            mOnTaskCompleted.onTaskCompleted(null,response.toString(),lPurpose);
                        }else if(lJSONObject.getString("response_code").equals("500")){
                            mOnTaskCompleted.onTaskError("server_error",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("201")){
                            mOnTaskCompleted.onTaskError("login_failed",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("202")){
                            mOnTaskCompleted.onTaskError("missing_param",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("204")){
                            mOnTaskCompleted.onTaskError("invalid_infomation",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("205")){
                            mOnTaskCompleted.onTaskError("validation_failed",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("206")){
                            mOnTaskCompleted.onTaskError("saving_failed",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("207")){
                            mOnTaskCompleted.onTaskError("saving_failed",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("208")){
                            mOnTaskCompleted.onTaskError("app_version_missing",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("209")){
                            mOnTaskCompleted.onTaskError("permission_denied",lPurpose);
                        }
                        else if(lJSONObject.getString("response_code").equals("212")){
                            mOnTaskCompleted.onTaskError("updation_failed",lPurpose);
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responseError",error.toString());
                mOnTaskCompleted.onTaskError("err",lPurpose);
            }
        });
        queue.add(jsObjRequest);
    }
}
