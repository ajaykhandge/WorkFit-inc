package com.example.workfitinc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    LinearLayout SignupBtn, GLoginBtn, ELoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignupBtn = findViewById(R.id.SignupBtn);
        GLoginBtn = findViewById(R.id.GLoginBtn);
        ELoginBtn = findViewById(R.id.ELoginBtn);

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextAct();
            }
        });

        GLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignInWithGoogleActivity.class));
            }
        });

        ELoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EmailRegistrationActivity.class));

            }
        });
    }

    private void toNextAct() {
        startActivity(new Intent(getApplicationContext(), SignupActivity.class));
    }
}