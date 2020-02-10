package com.mahmoudshaaban.cortana.Model;

public class Users {

    private String Password , Phone , Username , image , address;

    public Users(){

    }

    public Users(String password, String phone, String username, String image, String address) {
        Password = password;
        Phone = phone;
        Username = username;
        this.image = image;
        this.address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
