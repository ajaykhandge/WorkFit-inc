package com.example.workfitinc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseDetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView titleCategory;
    private TextView description;
    private ImageView imageResource;
     private TextView sets;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        title = findViewById(R.id.exe_dtls_title);
        titleCategory = findViewById(R.id.exe_dtls_category_title);
        description = findViewById(R.id.exe_dtls_description);
        imageResource = findViewById(R.id.exe_dtls_image);
        sets = findViewById(R.id.exe_dtls_sets);
        time = findViewById(R.id.exe_dtls_time);



        Intent intent = getIntent();
        Exercise exercise = intent.getParcelableExtra("exercise-details");
        setExerciseDetails(exercise);
    }

    public void setExerciseDetails(Exercise exer){

        title.setText(exer.getTitle());
        titleCategory.setText(exer.getTitleCategory());
        description.setText(exer.getDescription());
        imageResource.setImageResource(exer.getImageResource());
        sets.setText((String.valueOf(exer.getSets()))+" x ");
       time.setText("100");



    }
}