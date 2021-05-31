package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EmailRegistrationActivity extends AppCompatActivity {

    private TextView txtLoginNow,btnSubmit;
    private ImageView capture_photo;
    private EditText edtFullname,edtEmailAddress,edtPassword;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_registration);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#3F51B5"));


        //find and access the id
        txtLoginNow = findViewById(R.id.txt_login_now);
        edtFullname = findViewById(R.id.edt_fullname);
        edtEmailAddress = findViewById(R.id.edt_emailaddress);
        edtPassword = findViewById(R.id.edt_password);
        btnSubmit = findViewById(R.id.btn_submit);
        capture_photo = findViewById(R.id.capture_photo);
        mAuth = FirebaseAuth.getInstance();



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"CLICKED",Toast.LENGTH_SHORT).show();
                String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                boolean email_valid=false,pass_valid=false,name_valid=false;

                String fullname = edtFullname.getText().toString().trim();
                String emailaddress = edtEmailAddress.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(fullname.length()!=0){
                    name_valid =true;
                    if(emailaddress.length()!=0){
                        if(!emailaddress.matches(regex)){
                            Snackbar.make(btnSubmit,"Enter Valid Email..!",Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            email_valid=true;
                            if(password.length()!=0){
                                if(password.length()>5){
                                    pass_valid=true;
                                }
                                else{
                                    Snackbar.make(btnSubmit,"Password should be atleast 5 characters",Snackbar.LENGTH_SHORT).show();
                                }


                            }
                            else
                                Snackbar.make(btnSubmit,"Password cannot be Empty",Snackbar.LENGTH_SHORT).show();


                        }
                    }
                    else
                    {
                        Snackbar.make(btnSubmit,"Email cannot be Empty..!",Snackbar.LENGTH_SHORT).show();
                    }

                }
                else{
                    Snackbar.make(btnSubmit,"Name cannot be Empty..!",Snackbar.LENGTH_SHORT).show();

                }



                if(name_valid && email_valid && pass_valid) {
                    registerEmailAndPassword(fullname,emailaddress,password);
               }
               else{
                  Snackbar.make(btnSubmit,"Enter the correct details...!",Snackbar.LENGTH_SHORT).show();

             }



             }
        });
        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
            }
        });
        capture_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    private void registerEmailAndPassword(String fullname,String emailaddress, String password) {

        mAuth.createUserWithEmailAndPassword(emailaddress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                            setDisplayName(fullname)
                            .setPhotoUri(Uri.parse("android:resource://com.example.workfitinc/drawable-v24/profile_potter.png"))
                            .build();
                    currentUser.updateProfile(profileChangeRequest)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Snackbar.make(btnSubmit,"User Register Successfully",Snackbar.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(),currentUser.getDisplayName().toString(),Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(getApplicationContext(),EmailLoginActivity.class));

                                    }
                                    else{
                                        Snackbar.make(btnSubmit, "Failed to Register..!", Snackbar.LENGTH_INDEFINITE).setAction("TRY AGAIN", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                edtFullname.setText("");
                                                edtEmailAddress.setText("");
                                                edtPassword.setText("");


                                            }
                                        }).show();

                                    }
                                }
                            });


                }
                else{
                    Snackbar.make(btnSubmit, "Failed to Register..!", Snackbar.LENGTH_INDEFINITE).setAction("TRY AGAIN", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edtFullname.setText("");
                            edtEmailAddress.setText("");
                            edtPassword.setText("");


                        }
                    }).show();


                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        capture_photo.setImageBitmap(bitmap);

    }
}



