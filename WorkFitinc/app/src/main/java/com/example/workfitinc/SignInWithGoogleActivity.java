package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInWithGoogleActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private FirebaseAuth mauth;
    private static final int GC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_google);

        //Sign with Google

          gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GlobalVariables g1 = new GlobalVariables();
        g1.setmGooogleSignInClient(mGoogleSignInClient);

        mauth = FirebaseAuth.getInstance();

        SignInWithGoogle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();

        if(user!=null)
        updateUI(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account =  task.getResult(ApiException.class);
                Log.d(TAG,"firebasewithGoogle"+account.getId());
                firebasewithGoogle(account.getIdToken());

            } catch (ApiException e) {
                e.printStackTrace();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebasewithGoogle(String token){
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mauth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }

    private void SignInWithGoogle(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,GC_SIGN_IN);
    }
    private void updateUI(FirebaseUser user)
    {
       // Toast.makeText(getApplicationContext(),user.getEmail().toString(),Toast.LENGTH_LONG).show();
        if (user != null)
        {
            Toast.makeText(getApplicationContext(),user.getEmail().toString(),Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        }
        else
        {
             Toast.makeText(getApplicationContext(),"User is Null",Toast.LENGTH_LONG).show();


        }


    }

    public void signOut(){

        new GlobalVariables().getmGooogleSignInClient().signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(getApplicationContext(),"Google User Logout",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}