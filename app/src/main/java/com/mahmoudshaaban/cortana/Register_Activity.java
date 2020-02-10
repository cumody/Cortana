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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {

    Button registerbutton;
    EditText username,password,phonenumber;
    ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        // int views
        registerbutton = findViewById(R.id.register_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phonenumber = findViewById(R.id.phonenumber);
        loadingdialog = new ProgressDialog( this);



        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createaccount();



            }
        });



    }

    private void createaccount() {

        final String name_input = username.getText().toString();
        final String password_input = password.getText().toString();
        final String phonenumber_input = phonenumber.getText().toString();


        if (TextUtils.isEmpty(name_input)){
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(password_input)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(phonenumber_input)){
            Toast.makeText(this, "Please write your phonenumber", Toast.LENGTH_SHORT).show();

        }
        else {

            loadingdialog.setTitle("Create Account");
            loadingdialog.setMessage("Please wait , while we are checking the credentials ");
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();

            validatephonenumber(name_input,password_input,phonenumber_input);
        }

    }

    private void validatephonenumber(final String name_input, final String password_input, final String phonenumber_input) {

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("Users").child(phonenumber_input).exists())
                {
                    HashMap<String,Object> userdatamap = new HashMap<>();
                    userdatamap.put("Phone" , phonenumber_input);
                    userdatamap.put("Username" , name_input);
                    userdatamap.put("Password" , password_input);

                    Rootref.child("Users").child(phonenumber_input).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register_Activity.this, "Congratulations , your account has been created ", Toast.LENGTH_SHORT).show();
                                        loadingdialog.dismiss();

                                        Intent intent = new Intent(Register_Activity.this, Login_activity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingdialog.dismiss();
                                        Toast.makeText(Register_Activity.this, "Network error : Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
                else {
                    Toast.makeText(Register_Activity.this, "This " + phonenumber_input + " Already exits", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                    Toast.makeText(Register_Activity.this, "Please use another phone number", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register_Activity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
