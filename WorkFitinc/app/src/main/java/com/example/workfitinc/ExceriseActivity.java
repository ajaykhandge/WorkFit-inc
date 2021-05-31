package com.example.workfitinc;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExceriseActivity extends AppCompatActivity {
    private ImageView exercise_profile;
    private ArrayList<Exercise> arrayList;
    LinearLayout sign_out_button;
    private SelectWorkoutActivity select_workout;
    private  HomeActivity home1;
    private ImageView select_workout_profile,custom_alert_signout_imageview;
    private TextView alert_name,alert_email,custom_signout_txt,txt_user_label;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excerise);
        select_workout = new SelectWorkoutActivity();
        home1 = new HomeActivity();
        auth  = FirebaseAuth.getInstance();
        exercise_profile = findViewById(R.id.exercise_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        txt_user_label= findViewById(R.id.textExceriseUserLabel);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFDBF1"));
        updateUI();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUI();
            }
        };

        exercise_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExceriseActivity.this);
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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                home1.actionAfterNavigationSelected(item, getApplicationContext());
                return true;
            }
        });


        //set up the exercise page
        createExerciseList();
        setUpRecyclerView();

    }

        public void updateUI(){
            exercise_profile = findViewById(R.id.exercise_profile);

            auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if(user!=null){
                txt_user_label.setText(user.getDisplayName().toString());
                Picasso.get().load(user.getPhotoUrl().toString()).into(exercise_profile);

            }
            else{
                exercise_profile.setImageResource(R.drawable.profile_ic);
            }
        }

    public void updateUI(View view){
        updateUI();
        home1.updateAlertDialog(view);
    }

    private void createExerciseList(){
        arrayList  = new ArrayList<>();
        arrayList.add(new Exercise("It Exercise","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,10,20));

        arrayList.add(new Exercise("Side Situps","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,5,15));

        arrayList.add(new Exercise("Side Situps","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,5,15));
        arrayList.add(new Exercise("Side Situps","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,5,15));
        arrayList.add(new Exercise("Side Situps","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,5,15));
        arrayList.add(new Exercise("Side Situps","Daily Routine","This will only holde the description about the exercise you are" +
                " doing and how will beifts to your body .So read this desciption very carefully anf enjoy doing the exercise while allowing" +
                "calories to burn.",R.drawable.yoga_girl,5,15));


    }

    private void setUpRecyclerView(){
        recyclerView = findViewById(R.id.exercise_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(),ExerciseDetailsActivity.class);
                intent.putExtra("exercise-details",arrayList.get(position));
                startActivity(intent);
            }
        });

    }
    }
