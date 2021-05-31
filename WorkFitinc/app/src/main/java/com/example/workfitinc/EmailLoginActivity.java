package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLoginActivity extends AppCompatActivity {
    private TextView txtSignupNow,btnLogin;
    private EditText edtEmailAddress,edtPassword;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        txtSignupNow = findViewById(R.id.txt_signup_now);
        btnLogin = findViewById(R.id.txt_email_login_now);
        edtEmailAddress = findViewById(R.id.edt_login_emailaddress);
        edtPassword = findViewById(R.id.edt_login_password);
        mauth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"CLICKED",Toast.LENGTH_SHORT).show();
                String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                boolean email_valid=false,pass_valid=false;

                 String emailaddress = edtEmailAddress.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();


                    if(emailaddress.length()!=0){
                        if(!emailaddress.matches(regex)){
                            Snackbar.make(btnLogin,"Enter Valid Email..!",Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            email_valid=true;
                            if(password.length()!=0){
                                pass_valid = true;
                            }
                            else
                                Snackbar.make(btnLogin,"Password cannot be Empty",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Snackbar.make(btnLogin,"Email cannot be Empty..!",Snackbar.LENGTH_SHORT).show();
                    }

                    if(email_valid && pass_valid){
                        signInWithEmailAndPassword(emailaddress,password);
                    }





            }
        });




        txtSignupNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EmailRegistrationActivity.class));
            }
        });
    }

    private void signInWithEmailAndPassword(String emailaddress, String password) {
        mauth.signInWithEmailAndPassword(emailaddress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Snackbar.make(btnLogin,"Login Successfull!!",Snackbar.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
            else{
                Snackbar.make(btnLogin, "Failed to SignIn..!", Snackbar.LENGTH_INDEFINITE).setAction("TRY AGAIN", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         edtEmailAddress.setText("");
                        edtPassword.setText("");
                    }
                }).show();

            }
            }
        });
    }
}