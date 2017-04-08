package com.ixigo.travelapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.ixigo.travelapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        setContentView(R.layout.activity_splash);
        LoginButton fb =(LoginButton)findViewById(R.id.fb);
        fb.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {
                                String text = json.getString("email");
                                try {

                                    JSONObject obj = new JSONObject(json.getString("picture"));
                                    JSONObject obj1 = new JSONObject(obj.getString("data"));

                                    Log.d("My App", json.toString() + "next:" + obj1.getString("url"));
                                    Intent i=new Intent(SplashActivity.this,LetMeKnowActivity.class);
                                    i.putExtra("id",json.getString("id"));
                                    i.putExtra("Name", json.getString("name"));
                                    i.putExtra("Email", json.getString("email"));
                                    i.putExtra("Age", "");
                                    i.putExtra("Phone", "phone");
                                    i.putExtra("Photo", obj1.getString("url"));
                                    startActivity(i);
                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
                                }

                                Log.d("email", text+" name:"+json.getString("name")+" picture:"+json.getString("picture"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Please login to proceed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(),"Error connecting Facebook",Toast.LENGTH_SHORT).show();
            }
        });
//        Thread timer = new Thread(){
//            public void run(){
//                try {
//                    sleep(3000);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }finally {
//                    Intent openstartingpoint = new Intent("com.ixigo.travelapp.activity.STARTINGPOINT");
//                    startActivity(openstartingpoint);
//                }
//            }
//
//        };
        //timer.start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
