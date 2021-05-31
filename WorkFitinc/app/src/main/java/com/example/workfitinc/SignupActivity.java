package com.example.workfitinc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SignupActivity extends AppCompatActivity {
    private LinearLayout glogin_btn,elogin_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        glogin_btn = findViewById(R.id.GLoginBtn);
        elogin_btn = findViewById(R.id.ELoginBtn);

        glogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignInWithGoogleActivity.class));
            }
        });

        elogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EmailRegistrationActivity.class));
            }
        });
    }
}