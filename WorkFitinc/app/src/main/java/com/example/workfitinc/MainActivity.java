package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;



public class MainActivity extends AppCompatActivity {
    private TextView txtUserGreetings;
    private ImageView imgUserImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtUserGreetings = findViewById(R.id.user_greetings);
        imgUserImage = findViewById(R.id.img_user_image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){
            //update the ui if accordingl
            Toast.makeText(getApplicationContext(),user.getDisplayName().toString(),Toast.LENGTH_LONG).show();

            updateUI(user);
        }
        else{
            Toast.makeText(getApplicationContext(),"User is Null",Toast.LENGTH_SHORT).show();
        }

        imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getApplicationContext(),imgUserImage);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu_profile:
                                Toast.makeText(getApplicationContext(),"Profile Clicked",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_logout:
                                signOutUser();
                                break;
                            case R.id.menu_login:
                                new Intent(getApplicationContext(),LoginActivity.class);
                                break;


                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.main_menu);
                menu.show();


            }
        });







    }

    private void signOutUser(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"User Sign Out Successfully ",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
    private void updateUI(FirebaseUser user)  {

        if (user !=null) {
            txtUserGreetings.setText("Hey " + user.getDisplayName() + "..!");
           Picasso.get().load(user.getPhotoUrl().toString()).into(imgUserImage);


        }



    }




}