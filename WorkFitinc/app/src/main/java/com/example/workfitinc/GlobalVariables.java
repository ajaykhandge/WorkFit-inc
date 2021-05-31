package com.example.workfitinc;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class GlobalVariables extends Application {
public static  GoogleSignInClient mGooogleSignInClient;

    void setmGooogleSignInClient(GoogleSignInClient s1){
       mGooogleSignInClient = s1;
    }

    GoogleSignInClient getmGooogleSignInClient(){
        return mGooogleSignInClient;
    }
}
