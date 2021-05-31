package com.example.workfitinc;

public class ProfileInfo {
    private String name,emailid,age,height,weight;
    public ProfileInfo(String name, String emailid, String age, String height, String weight) {
        this.name = name;
        this.emailid = emailid;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public ProfileInfo(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }



    public String getName() {
        return name;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }
}
