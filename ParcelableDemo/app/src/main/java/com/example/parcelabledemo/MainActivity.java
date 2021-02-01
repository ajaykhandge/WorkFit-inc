package com.example.parcelabledemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private Button startButton;
private ArrayList<Product> arrayList;
private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createProductList();
        setupRecycleView();



    }

    private void setupRecycleView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewAdapter adapter = new RecycleViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                intent.putExtra("product details", arrayList.get(position));   //passing the  object to ProductDetails Activity.
                startActivity(intent);

            }
        });


    }
    private void createProductList(){
        arrayList  = new ArrayList<>();
        arrayList.add(new Product("Nike Pro Fit","Stylish women's wear from exclusive pro-fit collection",R.drawable.nike1,20.05D,4.5F));
        arrayList.add(new Product("Nike Sky_Blue Hoodie","Limited Edition Bisexual  Nike Sky Blue Hoodie with exclusive demands ",R.drawable.nike2,23.05D,2.5F));
        arrayList.add(new Product("Nike Sweatshirt","Men's Slim Fit Gym Sweat-Shirt with exlusive discounts.",R.drawable.nike3,23.05D,4.5F));
        arrayList.add(new Product("Nike Soccers Sword","Nike new Stylish Soccer collection product.Handpicked and Verfied.",R.drawable.nike4,23.05D,3.9F));
        arrayList.add(new Product("Nike Messi Collection","Messi's Collection for Scoccer Shoes out on demand with exlusive discount price",R.drawable.nike5,23.05D,4.5F));
        arrayList.add(new Product("Nike Pro College Bag","5.5 kg College Bag with extra space for Laptop and Sports Acessories.",R.drawable.nike6,23.05D,4.1F));
        arrayList.add(new Product("Nike Compact Travels","Compact and Stylish Travel bag both for Men and Women",R.drawable.nike7,23.05D,3.5F));

    }
}