package com.mahmoudshaaban.cortana;

public class Viewpagerlist {
   private int image;
   private String name;

    public Viewpagerlist(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Viewpagerlist(int image, String name) {
        this.image = image;
        this.name = name;
    }
}

