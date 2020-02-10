package com.mahmoudshaaban.cortana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahmoudshaaban.cortana.Model.Users;
import com.mahmoudshaaban.cortana.Prevelant.Prevalent;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class Login_activity extends AppCompatActivity {

    Button login_button;
    EditText phonenumber,password;
    ProgressDialog loadingdialog;
    CheckBox checkBoxRememberme;
    TextView adminlink,notAdminlink;

    private String parentDbname = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);


        // int values
        login_button = findViewById(R.id.login_button);
        phonenumber = findViewById(R.id.login_phonenumber);
        password = findViewById(R.id.login_password);
        loadingdialog = new ProgressDialog( this);
        checkBoxRememberme = findViewById(R.id.remember_me);
        adminlink = findViewById(R.id.admin_panel_link);
        notAdminlink = findViewById(R.id.not_admin_panel_link);

        Paper.init(this);




        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginuser();

            }
        });

        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.setText("Login Admin");
                adminlink.setVisibility(View.INVISIBLE);
                notAdminlink.setVisibility(View.VISIBLE);
                parentDbname = "Admins";
            }
        });
        notAdminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.setText("Login");
                adminlink.setVisibility(View.VISIBLE);
                notAdminlink.setVisibility(View.INVISIBLE);
                parentDbname = "Users";
            }
        });




    }

    private void Loginuser() {



        String login_phone_number = phonenumber.getText().toString();
        String password_login = password.getText().toString();

        if (TextUtils.isEmpty(password_login)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(login_phone_number)){
            Toast.makeText(this, "Please write your phonenumber", Toast.LENGTH_SHORT).show();

        }
        else {
            loadingdialog.setTitle("Login Account");
            loadingdialog.setMessage("Please wait , while we are checking the credentials ");
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();


            AllowAccessToAccount(login_phone_number,password_login);
        }

    }

    private void AllowAccessToAccount(final String login_phone_number, final String password_login) {

        if (checkBoxRememberme.isChecked()){
            Paper.book().write(Prevalent.UserphoneKey,login_phone_number);
            Paper.book().write(Prevalent.UserpasswordKey,password_login);

        }

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbname).child(login_phone_number).exists()){

                    Users usersdata = dataSnapshot.child(parentDbname).child(login_phone_number).getValue(Users.class);
                    if (usersdata.getPhone().equals(login_phone_number)){
                        if (usersdata.getPassword().equals(password_login)){
                            if (parentDbname.equals("Admins")){
                                Toast.makeText(Login_activity.this, " Welocme Admin , you're  Logged in successfully ... ", Toast.LENGTH_SHORT).show();
                                loadingdialog.dismiss();

                                Intent intent = new Intent(Login_activity.this, CategoryAdminActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbname.equals("Users")){
                                Toast.makeText(Login_activity.this, "Logged in successfully ... ", Toast.LENGTH_SHORT).show();
                                loadingdialog.dismiss();

                                Intent intent = new Intent(Login_activity.this, HomeActivity.class);
                                Prevalent.currentOnlineU = usersdata;

                                startActivity(intent);
                            }


                        }


                    }
                    
                }
                else {
                    Toast.makeText(Login_activity.this, "This account number doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
