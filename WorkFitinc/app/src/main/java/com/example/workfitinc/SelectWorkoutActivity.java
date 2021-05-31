package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SelectWorkoutActivity extends AppCompatActivity {

    LinearLayout sign_out_button,select_yoga,select_exercise;
    private ImageView select_workout_profile,custom_alert_signout_imageview;
    private TextView alert_name,alert_email,custom_signout_txt;
    private FirebaseAuth auth;
    HomeActivity home1;
    private BottomNavigationView bottomNavigationView;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);


        select_workout_profile = findViewById(R.id.select_workout_profile_icon);
        auth = FirebaseAuth.getInstance();
        home1 = new HomeActivity();
        bottomNavigationView = findViewById(R.id.bottom_navigation_select_workout);
        select_yoga = findViewById(R.id.select_yoga_workout);
        select_exercise = findViewById(R.id.select_exercise_workout);


        updateUI();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFDBF1"));

        //change ui accordinglu

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUI();

            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                home1.actionAfterNavigationSelected(item,getApplicationContext());
                return true;
            }
        });

        //profile icon
        select_workout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectWorkoutActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
                builder.setView(customLayout);


                builder.setNegativeButton("Close",null);
                AlertDialog alertDialog = builder.create();

                sign_out_button = customLayout.findViewById(R.id.custom_alert_signout_button);
                custom_signout_txt = customLayout.findViewById(R.id.custom_alert_signout_textview);
                custom_alert_signout_imageview = customLayout.findViewById(R.id.custom_alert_signout_imageview);

                sign_out_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(custom_signout_txt.getText().equals("Sign out WorkFit")) {
                            Snackbar.make(sign_out_button, "Signing out.Please wait ..!", Snackbar.LENGTH_SHORT).show();

                           FirebaseAuth.getInstance().signOut();
                           SignInWithGoogleActivity s1 = new SignInWithGoogleActivity();
                           s1.signOut();
                            updateUI(customLayout);

                        }
                        else{
                            Snackbar.make(sign_out_button, "Signing in.Please wait ..!", Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));


                        }

                    }
                });


                home1.updateAlertDialog(customLayout);
                alertDialog.show();
            }
        });
        //Update User Profile AlertDialog Ends


        select_yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"SELECT YOGA",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),YogaActivity.class));

            }
        });

        select_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"SELECT EXERCISE",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),ExceriseActivity.class));

            }
        });



    }
    public void updateUI(View view){
        updateUI();
        home1.updateAlertDialog(view);
    }

    public void updateUI(){
        select_workout_profile = findViewById(R.id.select_workout_profile_icon);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            Picasso.get().load(user.getPhotoUrl().toString()).into(select_workout_profile);

        }
        else{
            select_workout_profile.setImageResource(R.drawable.profile_ic);
        }
    }

}