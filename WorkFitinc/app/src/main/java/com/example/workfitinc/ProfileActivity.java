package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView nextButton,prevButton;
    private EditText edtAge,edtWeight,edtHeight;
    private TextView txtAge,txtWeight,txtHeight;
    private FirebaseFirestore db;
    private FirebaseAuth mauth;
    private FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.txt_prev_step);
        viewPager = findViewById(R.id.profile_viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
        viewPager.setOffscreenPageLimit(3);

        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        edtAge = viewPager.findViewById(R.id.ediTextAge);
        edtHeight = viewPager.findViewById(R.id.ediTextHeight);
        edtWeight = viewPager.findViewById(R.id.ediTextWeight);

        txtAge = viewPager.findViewById(R.id.textViewAge);
        txtHeight = viewPager.findViewById(R.id.textViewHeight);
        txtWeight = viewPager.findViewById(R.id.textViewWeight);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                edtAge = viewPager.findViewById(R.id.ediTextAge);
                edtHeight = viewPager.findViewById(R.id.ediTextHeight);
                edtWeight = viewPager.findViewById(R.id.ediTextWeight);
                txtAge = viewPager.findViewById(R.id.textViewAge);
                txtHeight = viewPager.findViewById(R.id.textViewHeight);
                txtWeight = viewPager.findViewById(R.id.textViewWeight);


            }

            @Override
            public void onPageSelected(int position) {
                edtAge = viewPager.findViewById(R.id.ediTextAge);
                edtHeight = viewPager.findViewById(R.id.ediTextHeight);
                edtWeight = viewPager.findViewById(R.id.ediTextWeight);
                txtAge = viewPager.findViewById(R.id.textViewAge);
                txtHeight = viewPager.findViewById(R.id.textViewHeight);
                txtWeight = viewPager.findViewById(R.id.textViewWeight);

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                edtAge = viewPager.findViewById(R.id.ediTextAge);
                edtHeight = viewPager.findViewById(R.id.ediTextHeight);
                edtWeight = viewPager.findViewById(R.id.ediTextWeight);
                txtAge = viewPager.findViewById(R.id.textViewAge);
                txtHeight = viewPager.findViewById(R.id.textViewHeight);
                txtWeight = viewPager.findViewById(R.id.textViewWeight);


            }
        });

        getProfileData();




    }
    private void getProfileData(){
        Toast.makeText(getApplicationContext(),"In the fun",Toast.LENGTH_SHORT).show();
        if(muser!=null) {
            txtAge = viewPager.findViewById(R.id.textViewAge);
            txtHeight = viewPager.findViewById(R.id.textViewHeight);
            txtWeight = viewPager.findViewById(R.id.textViewWeight);

            db.collection("users").document(muser.getUid()).get().
                    addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ProfileInfo alreadyuser = documentSnapshot.toObject(ProfileInfo.class);
                            txtAge.setText(alreadyuser.getAge());
                            txtHeight.setText(alreadyuser.getHeight());
                            txtWeight.setText(alreadyuser.getWeight());


                        }
                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed to load
                        }
                    });
        }

    }
    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    public void nextpage(View view){
        viewPager.setCurrentItem(getItem(+1),true);
    }
    public void prevpage(View view){
        viewPager.setCurrentItem(getItem(-1),true);
    }
    public void closeViewpager(View view){
        finish();
        overridePendingTransition(0,0);   //disable the slider animation of android changing slides
    }

    public void uploadProfile(View view){
        edtAge = viewPager.findViewById(R.id.ediTextAge);
        edtHeight = viewPager.findViewById(R.id.ediTextHeight);
        edtWeight = viewPager.findViewById(R.id.ediTextWeight);

        if (muser != null) {
            String age = edtAge.getText().toString().trim();
            String height = edtHeight.getText().toString().trim();
            String weight = edtWeight.getText().toString().trim();

//            Toast.makeText(getApplicationContext(), edtAge.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), edtHeight.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), edtWeight.getText().toString(), Toast.LENGTH_SHORT).show();

            if(age.length()!=0 && height.length()!=0 && weight.length()!=0) {
                ProfileInfo profileInfo = new ProfileInfo(muser.getDisplayName(), muser.getEmail(), age, height, weight);
                db.collection("users").document(muser.getUid()).set(profileInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                closeViewpager(view);
                                Toast.makeText(getApplicationContext(), "Data Uploaded Success ..!", Toast.LENGTH_SHORT).show();


                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to upload data ..!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else{
                Snackbar.make(view,"Nothing to Change",Snackbar.LENGTH_SHORT).show();
            }

        }
        else{
            Snackbar.make(view,"LOGIN FIRST",Snackbar.LENGTH_SHORT).show();
        }
    }
}