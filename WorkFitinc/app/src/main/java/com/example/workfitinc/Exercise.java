package com.example.workfitinc;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String title;
    private String titleCategory;
    private String description;
    private int imageResource;
    private int sets;
    private int time;

    //creates the arraylist for the pictures and images


    public Exercise(String title, String titleCategory, String description, int imageResource, int sets, int time) {
        this.title = title;
        this.titleCategory = titleCategory;
        this.description = description;
        this.imageResource = imageResource;
        this.sets = sets;
        this.time = time;
    }

    protected Exercise(Parcel in) {    //reads from the parcel...
        title = in.readString();
        titleCategory = in.readString();
        description = in.readString();
        imageResource = in.readInt();
        sets = in.readInt();
        time = in.readInt();

    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };


    //getters
    public String getTitle() {
        return title;
    }
    public String getTitleCategory(){
        return titleCategory;}

    public String getDescription() {
        return description;
    }
    public int getImageResource() {
        return imageResource;
    }
    public int getSets() {
        return sets;
    }
    public int getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(titleCategory);
        parcel.writeString(description);
        parcel.writeInt(imageResource);
        parcel.writeInt(sets);
        parcel.writeInt(time);
    }
}
