package com.mahmoudshaaban.cortana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CategoryAdminActivity extends AppCompatActivity {

    private ImageView tShirts , sportsthsirts , femaledresses , sweathres ;
    private ImageView glasses , hatscaps , walletbagspurses , shoes;
    private ImageView headphones , laptops , mobiles , watch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);

        tShirts = findViewById(R.id.tshirts);
        sportsthsirts = findViewById(R.id.referee);
        femaledresses = findViewById(R.id.basketballshirt);
        sweathres = findViewById(R.id.colorsshirt);
        glasses = findViewById(R.id.glasses);
        hatscaps = findViewById(R.id.highheels);
        walletbagspurses = findViewById(R.id.chef);
        shoes = findViewById(R.id.shoppingbag);
        headphones = findViewById(R.id.imageView6);
        watch =findViewById(R.id.watch);
        laptops =findViewById(R.id.laptop);
        mobiles =findViewById(R.id.mobile1);






        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","tShirts ");
                startActivity(intent);
            }
        });

        sportsthsirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sports tShirts ");
                startActivity(intent);
            }
        });
        femaledresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });
        sweathres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sweathers ");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Glasses ");
                startActivity(intent);
            }
        });
        hatscaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });
        walletbagspurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Wallet Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","HeadPhones HandFree");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Watchs");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryAdminActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });



    }
}
