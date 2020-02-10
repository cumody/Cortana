    package com.mahmoudshaaban.cortana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahmoudshaaban.cortana.Model.Users;
import com.mahmoudshaaban.cortana.Prevelant.Prevalent;

import java.util.List;

import io.paperdb.Paper;

    public class Get_Started extends AppCompatActivity {

    ViewPager viewPager;
    List<Viewpagerlist> viewpagerlistList;
    Button login_in , register_button;
        ProgressDialog loadingdialog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_);

        // int view
        login_in = findViewById(R.id.button_login_In);
        register_button = findViewById(R.id.join_now_button);
        Paper.init(this);
        loadingdialog = new ProgressDialog( this);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(Get_Started.this,Register_Activity.class);
                startActivity(register);

            }
        });



        login_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Get_Started.this,Login_activity.class);
                startActivity(login);
            }
        });



        }
    }
