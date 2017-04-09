package com.ixigo.travelapp.utill;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by F49558B on 4/9/2017.
 */

public interface OnTaskCompleted {
    void onTaskCompleted(JSONArray objecjJ, String response, String lPurpose);
    void onTaskError(String response, String lPurpose);
}
