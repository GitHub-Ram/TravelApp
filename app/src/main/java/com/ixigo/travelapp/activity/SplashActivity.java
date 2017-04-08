package com.ixigo.travelapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ixigo.travelapp.R;
import com.ixigo.travelapp.utill.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.ixigo.travelapp.utill.Constants.USER_PREFERENCES;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    SharedPreferences sharedpreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        setContentView(R.layout.activity_splash);
        if(Utility.isLoggedIn(getApplicationContext())){
            Thread timer = new Thread(){
                public void run(){
                    try {
                        sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent("com.ixigo.travelapp.activity.STARTINGPOINT");
                        Log.d("gmail", "name" + sharedpreferences.getString("Name","NA") + " : " + sharedpreferences.getString("Email","NA"));
                        i.putExtra("Name", sharedpreferences.getString("Name","NA"));
                        i.putExtra("Email", sharedpreferences.getString("Email","NA"));
                        i.putExtra("Age", "");
                        i.putExtra("Phone", "");
                        i.putExtra("Photo", "");
                        startActivity(i);
                    }
                }

            };
            timer.start();
        }else{
            findViewById(R.id.linearLFB).setVisibility(View.VISIBLE);
            findViewById(R.id.linearLG).setVisibility(View.VISIBLE);
        }
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
                                    Intent i = new Intent("com.ixigo.travelapp.activity.STARTINGPOINT");
                                    i.putExtra("id",json.getString("id"));
                                    i.putExtra("Name", json.getString("name"));
                                    i.putExtra("Email", json.getString("email"));
                                    i.putExtra("Age", "");
                                    i.putExtra("Phone", "phone");
                                    i.putExtra("Photo", obj1.getString("url"));
                                    startActivity(i);
                                    Utility.setLogInState(SplashActivity.this, true);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("Name", json.getString("name"));
                                    editor.putString("Email", json.getString("email"));
                                    editor.putString("Gender", json.getString("gender"));
                                    editor.putString("Age", "");
                                    editor.putString("Phone", "");
                                    editor.putString("Photo", obj1.getString("url"));
                                    editor.commit();
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getResources().getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton gsignInButton = (SignInButton) findViewById(R.id.sign_in_gmail);
        gsignInButton.setSize(SignInButton.SIZE_STANDARD);
        gsignInButton.setScopes(gso.getScopeArray());
        findViewById(R.id.sign_in_gmail).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TAG", "handleSignInResult:" + (result==null));
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent i = new Intent("com.ixigo.travelapp.activity.STARTINGPOINT");
            Log.d("gmail", "name" + acct.getDisplayName() + " : " + acct.getPhotoUrl() + " : " + acct.getEmail());
            i.putExtra("Name", acct.getDisplayName());
            i.putExtra("Email", acct.getEmail());
            i.putExtra("Age", "");
            i.putExtra("Phone", "");
            i.putExtra("Photo", acct.getPhotoUrl());
            Utility.setLogInState(SplashActivity.this, true);
            startActivity(i);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("Name", acct.getDisplayName());
            editor.putString("Email", acct.getEmail());
            editor.putString("Age", "");
            editor.putString("Phone", "");
            if(acct.getPhotoUrl()!=null)
                editor.putString("Photo", acct.getPhotoUrl().toString());
            else{
                editor.putString("Photo", "NA");
            }
            editor.commit();
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_gmail:
                signIn();
                break;
        }
    }
}
