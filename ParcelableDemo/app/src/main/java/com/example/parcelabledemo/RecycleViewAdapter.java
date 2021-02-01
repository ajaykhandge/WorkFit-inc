package com.example.parcelabledemo;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;

public  class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>
{

    private ArrayList<Product> arrayList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
       this.listener = listener;
    }


    public RecycleViewAdapter(ArrayList<Product> arrayList){
        super();
        this.arrayList = arrayList;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView description;
        private ImageView imageResource;
        private LinearLayout linearLayout;
        private Double price;
        private float ratings;

    public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        imageResource = itemView.findViewById(R.id.image_resource);
        description = itemView.findViewById(R.id.description);
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
        View listItem = layoutInflater.inflate(R.layout.list_row,parent,false);

        ViewHolder viewHolder = new ViewHolder(listItem,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.title.setText(product.getTitle());

        holder.description.setText(product.getDescription());
        holder.imageResource.setImageResource(product.getImageResource());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
