package com.mahmoudshaaban.cortana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button button;
    ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        Paper.init(this);
        loadingdialog = new ProgressDialog( this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Get_Started.class);
                startActivity(intent);
            }
        });


        String UserphoneKey = Paper.book().read(Prevalent.UserphoneKey);
        String UserpasswordKey = Paper.book().read(Prevalent.UserpasswordKey);

        if (UserphoneKey != "" && UserpasswordKey != ""){
            if (!TextUtils.isEmpty(UserphoneKey) && !TextUtils.isEmpty(UserpasswordKey)){
                Allowacess(UserphoneKey,UserpasswordKey);



                loadingdialog.setTitle("Already Logged In");
                loadingdialog.setMessage("Please wait");
                loadingdialog.setCanceledOnTouchOutside(false);
                loadingdialog.show();


            }


        }

    }

    private void Allowacess(final String login_phone_number, final String password_login) {

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();



        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(login_phone_number).exists()){

                    Users usersdata = dataSnapshot.child("Users").child(login_phone_number).getValue(Users.class);
                    if (usersdata.getPhone().equals(login_phone_number)){
                        if (usersdata.getPassword().equals(password_login)){
                            Toast.makeText(MainActivity.this, "Logged in successfully ... ", Toast.LENGTH_SHORT).show();
                            loadingdialog.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineU = usersdata;
                            startActivity(intent);


                        }


                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "This account number doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
