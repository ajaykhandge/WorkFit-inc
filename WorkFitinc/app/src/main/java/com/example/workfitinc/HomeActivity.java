package com.example.workfitinc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workfitinc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;


public class HomeActivity extends AppCompatActivity {

    LinearLayout linearLayout,sign_out_button,water_drinking,edt_drink_water,btn_change_drink_water;
    private ImageView home_scedule_button,alert_image,custom_alert_signout_imageview;
    private BottomNavigationView bottomNavigationView;
    private   AlertDialog.Builder builder;
    private FirebaseAuth auth;
    private TextView alert_name,alert_email,custom_signout_txt;
    private EditText drink_water_change_edt;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;






    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linearLayout = findViewById(R.id.linearLayout);
        home_scedule_button = findViewById(R.id.home_scedule_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        auth = FirebaseAuth.getInstance();
          alert_image = findViewById(R.id.custom_alert_imageview);
          alert_name = findViewById(R.id.custom_alert_email);
          alert_email = findViewById(R.id.custom_alert_email);
          water_drinking  = findViewById(R.id.water_drinking_remainder);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                actionAfterNavigationSelected(item,getApplicationContext());
                return true;
            }
        });


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#3B0065"));

        home_scedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
                        if(custom_signout_txt.getText().toString().equals("Sign out WorkFit")) {
                            Snackbar.make(sign_out_button, "Signing out...!", Snackbar.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            SignInWithGoogleActivity s1 = new SignInWithGoogleActivity();
                            s1.signOut();
                            updateAlertDialog(customLayout);
                        }
                        else{
                            Snackbar.make(sign_out_button,custom_signout_txt.getText().toString() , Snackbar.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }
                    }
                });

                updateAlertDialog(customLayout);
                alertDialog.show();
            }
        });


        water_drinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_water_drinking, null);
                builder.setView(customLayout);


                builder.setNegativeButton("Close",null);
                AlertDialog alertDialog = builder.create();

                edt_drink_water = customLayout.findViewById(R.id.edt_drink_water);
                btn_change_drink_water = customLayout.findViewById(R.id.btn_change_drink_water);
                drink_water_change_edt = customLayout.findViewById(R.id.drink_water_change_edt);

                btn_change_drink_water.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newtime =drink_water_change_edt.getText().toString().trim();
                        if(newtime.length()!=0){
                            Snackbar.make(btn_change_drink_water,"Time Updated Successfully..!",Snackbar.LENGTH_SHORT).show();
                            scheduleNotification(getNotification( "5 second remainder" ) , 5000 ) ;

                            alertDialog.cancel();
                        }
                        else{
                            Snackbar.make(btn_change_drink_water,"Time not Specified",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });


                alertDialog.show();

            }
        });


    }

    public void actionAfterNavigationSelected(MenuItem item, Context context) {
        switch (item.getItemId()){
            case R.id.bottom_statitics:
                Toast.makeText(context," HomeActivity",Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,HomeActivity.class));
                break;
            case R.id.bottom_settings:
                Toast.makeText(context," ExceriseActivity",Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,ExceriseActivity.class));
                break;
            case R.id.bottom_excerise:
                Toast.makeText(context,"SelectWorkoutActivity ",Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,SelectWorkoutActivity.class));
                break;
            case R.id.bottom_nutrition:
                Toast.makeText(context,"Nutrition ",Toast.LENGTH_SHORT).show();
                break;

            case R.id.bottom_profile:
                Toast.makeText(context,"ProfileActivity ",Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,ProfileActivity.class));

                break;
        }
    }

    public  void updateAlertDialog(View view){
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        alert_email = view.findViewById(R.id.custom_alert_email);
        alert_image = view.findViewById(R.id.custom_alert_imageview);
        alert_name = view.findViewById(R.id.custom_alert_name);
        sign_out_button = view.findViewById(R.id.custom_alert_signout_button);
        custom_signout_txt = view.findViewById(R.id.custom_alert_signout_textview);
        custom_alert_signout_imageview = view.findViewById(R.id.custom_alert_signout_imageview);


        if(user!=null) {

            alert_name.setText(user.getDisplayName());
            alert_email.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl().toString()).into(alert_image);
            custom_signout_txt.setText("Sign out WorkFit");
            custom_alert_signout_imageview.setBackgroundResource(R.drawable.logout_alert);

        }
        else{

            alert_name.setText("I am WorkFit User");
            alert_email.setText("user@worfit.inc");
            alert_image.setImageResource(R.drawable.profile_ic);
            custom_signout_txt.setText("Sign into WorkFit");
            custom_alert_signout_imageview.setBackgroundResource(R.drawable.icon_login);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you really want to exit the App?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Close app
            }
        });
        builder.setNegativeButton("No",null);
        builder.setIcon(R.drawable.google_ic);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Working Drinking Remainder" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. man_drinks_water ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
}