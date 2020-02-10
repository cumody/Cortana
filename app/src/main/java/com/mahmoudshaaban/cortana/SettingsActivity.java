package com.mahmoudshaaban.cortana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mahmoudshaaban.cortana.Prevelant.Prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    CircleImageView profileimageview;
    private EditText fullnameedittext , userphoneedittext , addressedittext;
    private TextView profilechangetextbtn , closetextbtn , savetextbutton ;

    private StorageTask uploadtask;
    private Uri imageuri;
    private String myuri = "";
    private StorageReference storageprofilepicturereference;
    private String checker = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageprofilepicturereference = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileimageview = findViewById(R.id.settings_profile_image);
        fullnameedittext = findViewById(R.id.settings_full_name);
        // we use phone number to purshase in this number
        userphoneedittext = findViewById(R.id.settings_phone_number);
        addressedittext = findViewById(R.id.settings_address);
        profilechangetextbtn = findViewById(R.id.profile_image_change);
        closetextbtn = findViewById(R.id.close_settings_btn );
        savetextbutton = findViewById(R.id.update_Settings_btn);

        userInfroDisplay(profileimageview , fullnameedittext , userphoneedittext ,addressedittext);


        closetextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        savetextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")){

                    userInfoSaved();

                }
                else {

                    updateOnlyUserInfo();
                }
            }
        });

        profilechangetextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });









    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageuri = result.getUri();

            profileimageview.setImageURI(imageuri);
        }
        else {
            Toast.makeText(this, "Error: Try again", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void updateOnlyUserInfo() {



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        Map<String , Object> usermap = new HashMap<>();
        usermap.put("name",fullnameedittext.getText().toString());
        usermap.put("address",addressedittext);
        usermap.put("phoneorder",userphoneedittext);

        ref.child(Prevalent.currentOnlineU.getPhone()).updateChildren(usermap);



        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        finish();


    }


    private void userInfoSaved() {

        if (TextUtils.isEmpty(fullnameedittext.getText().toString())){
            Toast.makeText(this, "name is mandatory", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(addressedittext.getText().toString())){
            Toast.makeText(this, "Address is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userphoneedittext.getText().toString())){
            Toast.makeText(this, "Phone is mandatory", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadimage();
        }



    }

    private void uploadimage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait , while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageuri != null){
            final StorageReference fileRef = storageprofilepicturereference
                    .child(Prevalent.currentOnlineU.getPhone() + ".jpg");
            uploadtask = fileRef.putFile(imageuri);


            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloaduri = task.getResult();
                        myuri = downloaduri.toString();


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        Map<String , Object> usermap = new HashMap<>();
                        usermap.put("name",fullnameedittext.getText().toString());
                        usermap.put("address",addressedittext.getText().toString());
                        usermap.put("phoneorder",userphoneedittext.getText().toString());
                        usermap.put("image",myuri);

                        ref.child(Prevalent.currentOnlineU.getPhone()).updateChildren(usermap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SettingsActivity.this, "Error: eccured", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfroDisplay( final CircleImageView  profileimageview,final EditText fullnameedittext,final EditText userphoneedittext,final EditText addressedittext) {

        // to get the current user with a unique key which is ( phone number ) prevelant.user.getphone
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineU.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // if phonenumber exists
                if (dataSnapshot.exists()){
                    // we make second if statment because in model class user we don't have an image varriable
                    if (dataSnapshot.child("image").exists()){
                        // make sure you use the same name in the database child

                        String image = dataSnapshot.child("image").getValue().toString();
                        String name =  dataSnapshot.child("Username").getValue().toString();
                        String phone = dataSnapshot.child("Phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileimageview);
                        fullnameedittext.setText(name);
                        userphoneedittext.setText(phone);
                        addressedittext.setText(address);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
