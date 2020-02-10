package com.mahmoudshaaban.cortana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mahmoudshaaban.cortana.Model.Products;
import com.mahmoudshaaban.cortana.Prevelant.Prevalent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {


    private FloatingActionButton addtocartbtn;
    private ImageView productimage;
    private ElegantNumberButton numberButton;
    private Button Addtocartbutton;
    private TextView productprice , productDescription , productname;
    private String PRODUCTID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        PRODUCTID = getIntent().getStringExtra("pid");


        Addtocartbutton = findViewById(R.id.pd_add_to_cart_button);
        numberButton = findViewById(R.id.number_btn);
        productimage = findViewById(R.id.product_image_details);
        productprice = findViewById(R.id.product_price_details);
        productDescription = findViewById(R.id.product_describtion_details);
        productname = findViewById(R.id.product_name_details);

        getproductDetails(PRODUCTID);

        Addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addingtocartlist();
            }
        });


    }

    private void Addingtocartlist() {

        String savecurrenttime,savecurrentdata;

        Calendar calForDate =  Calendar.getInstance();


        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy ");
        savecurrentdata = currentdate.format(calForDate.getTime());


        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime  = currentdate.format(calForDate.getTime());


       final DatabaseReference cartlistRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String , Object> cartmap = new HashMap<>();
        cartmap.put("pid" , PRODUCTID);
        cartmap.put("pname" , productname.getText().toString());
        cartmap.put("price" , productprice.getText().toString() );
        cartmap.put("date" , savecurrentdata);
        cartmap.put("time" , savecurrenttime);
        cartmap.put("quantity" , numberButton.getNumber());
        cartmap.put("discount" , "");

        cartlistRef.child("User View").child(Prevalent.currentOnlineU.getPhone())
                .child("Products").child(PRODUCTID)
                .updateChildren(cartmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            cartlistRef.child("Admin View").child(Prevalent.currentOnlineU.getPhone())
                                    .child("Products").child(PRODUCTID)
                                    .updateChildren(cartmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProductDetailActivity.this, "Added to Cart List", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailActivity.this,HomeActivity.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });
                        }
                    }
                });




    }

    private void getproductDetails(String productid) {
        DatabaseReference productref = FirebaseDatabase.getInstance().getReference().child("Products");

        productref.child(PRODUCTID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    productname.setText(products.getPname());
                    productprice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productimage);
                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}
    }
