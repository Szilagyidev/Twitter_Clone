package com.example.faketwitter;

public class FeedModel {
    String Email,Name,Text,imageurl;

    FeedModel(){

    }

    public FeedModel(String email, String name, String text, String imageurl) {
        Email = email;
        Name = name;
        Text = text;
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
