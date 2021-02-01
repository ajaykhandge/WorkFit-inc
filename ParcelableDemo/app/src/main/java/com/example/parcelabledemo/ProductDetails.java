package com.example.parcelabledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetails extends AppCompatActivity {
    private TextView title;
    private ImageView imageView;
    private TextView description;
    private TextView price;
    private TextView ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        title = findViewById(R.id.prod_title);
        description = findViewById(R.id.prod_subtitle);
        imageView = findViewById(R.id.prod_imageview);
        price = findViewById(R.id.prod_price);
        ratings = findViewById(R.id.prod_ratings);


        this.setTitle("Product Info");

        Intent intent = getIntent(); //
        Product details = intent.getParcelableExtra("product details"); //getParcelableExtra instead of getExtra
        this.setTitle(details.getTitle());
        setProdcutDetails(details);   //acessing the object


    }

    private void setProdcutDetails(Product product){
        title.setText(product.getTitle());
        description.setText(product.getDescription());
        imageView.setImageResource(product.getImageResource());
        price.setText(price.getText()+" $."+ product.getPrice());
        ratings.setText(ratings.getText()+" "+product.getRatings());


    }
}