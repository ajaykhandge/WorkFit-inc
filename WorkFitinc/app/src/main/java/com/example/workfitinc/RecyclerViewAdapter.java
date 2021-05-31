package com.example.workfitinc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;

public  class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    private ArrayList<Exercise> arrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public RecyclerViewAdapter(ArrayList<Exercise> arrayList){
        super();
        this.arrayList = arrayList;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView titleCategory;
        private TextView description;
        private ImageView imageResource;
        private LinearLayout linearLayout;
        private TextView sets;
        private TextView time;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.exercise_title);
            imageResource = itemView.findViewById(R.id.exercise_image);
            titleCategory = itemView.findViewById(R.id.exercise_category);
            sets = itemView.findViewById(R.id.exercise_sets);
            time = itemView.findViewById(R.id.exercise_time);

            linearLayout = itemView.findViewById(R.id.linearlayout);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }

                    }

                }
            });




        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.exercise_card,parent,false);

        ViewHolder viewHolder = new ViewHolder(listItem,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Exercise exercise = arrayList.get(position);
        holder.title.setText(exercise.getTitle());
        holder.titleCategory.setText(exercise.getTitleCategory());
        holder.imageResource.setImageResource(exercise.getImageResource());
        holder.sets.setText(exercise.getSets()+" sets");
        holder.time.setText(exercise.getTime()+" mins");


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
