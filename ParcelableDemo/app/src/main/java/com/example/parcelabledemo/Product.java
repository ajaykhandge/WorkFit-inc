package com.example.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String title;

    private String description;
    private int imageResource;
    private Double price;
    private float ratings;

    public Product(String title,String description, int imageResource, Double price, float ratings) {     //creating the Product object
        this.title = title;

        this.description = description;
        this.imageResource = imageResource;
        this.price = price;
        this.ratings = ratings;
    }

    protected Product(Parcel in) {    //reads from the parcel...
        title = in.readString();
        description = in.readString();
        imageResource = in.readInt();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        ratings = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


//getters
    public String getTitle() {
        return title;
    }



    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Double getPrice() {
        return price;
    }

    public float getRatings() {
        return ratings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);

        parcel.writeString(description);
        parcel.writeInt(imageResource);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeFloat(ratings);
    }
}
