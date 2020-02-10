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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName , Description , Price , Pname , savecurrrentdata , savecurrenttime;
    private Button add_new_product;
    private ImageView Inputproduct_Image;
    private EditText Input_product_name , Input_product_description , Input_product_price;
    public static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productrandomkey , downloadimageurl;
    private StorageReference ProductImageref;
    private DatabaseReference productref;
    ProgressDialog loadingdialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        //int view

        loadingdialog = new ProgressDialog( this);
        Inputproduct_Image = findViewById(R.id.select_prodcut_image);
        add_new_product = findViewById(R.id.add_new_product);
        Input_product_name = findViewById(R.id.product_name);
        Input_product_description = findViewById(R.id.product_describtion);
        Input_product_price = findViewById(R.id.product_price);
        ProductImageref = FirebaseStorage.getInstance().getReference().child("Product Images");
        productref = FirebaseDatabase.getInstance().getReference().child("Products");




        CategoryName = getIntent().getExtras().get("category").toString();


        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        
        
        Inputproduct_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        // step number 2
        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });


    }



    // step number 1
    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick  && resultCode == RESULT_OK && data != null){

            ImageUri = data.getData();
            Inputproduct_Image.setImageURI(ImageUri);

        }

    }
    private void ValidateProductData() {

        Description = Input_product_description.getText().toString();
        Price = Input_product_price.getText().toString();
        Pname = Input_product_name.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(this, "Product Image is Mandatory", Toast.LENGTH_SHORT).show();
            
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please write product description .. ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please write product price ..  ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Please write product name ..  ", Toast.LENGTH_SHORT).show();

        }
        else{
            // step number 3
            StoreproductInformation();
        }






    }


    private void StoreproductInformation() {

        loadingdialog.setTitle("Add new Product ");
        loadingdialog.setMessage(" Dear admin , Please wait while we are adding new product  ");
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat  currendata = new SimpleDateFormat("MMM dd, yyyy");
        savecurrrentdata = currendata.format(calendar.getTime());

        SimpleDateFormat  currentime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currentime.format(calendar.getTime());

        // to combine the date and time to make it unique
        productrandomkey = savecurrrentdata + savecurrenttime;

        // to store the image into firebase storage
        // get lastpathsegment to get the image name (default name )
        final StorageReference filepath = ProductImageref.child(ImageUri.getLastPathSegment() + productrandomkey + ".jpg");
       final UploadTask uploadTask = filepath.putFile(ImageUri);

       uploadTask.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

               String message = e.toString();
               Toast.makeText(AdminAddNewProductActivity.this, message, Toast.LENGTH_SHORT).show();
               loadingdialog.dismiss();
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(AdminAddNewProductActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();

               // to get the imagelink if we need to show the image to the user or vice versa
               Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                   @Override
                   public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if (!task.isSuccessful()){
                          throw  task.getException();

                       }

                       // that method get the Uri not the link to get the link
                       downloadimageurl = filepath.getDownloadUrl().toString();
                       return filepath.getDownloadUrl();
                   }
               }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                   @Override

                    public void onComplete(@NonNull Task<Uri> task) {
                       if (task.isSuccessful()){

                           downloadimageurl = task.getResult().toString();

                           Toast.makeText(AdminAddNewProductActivity.this, " getting Product image Successfully ", Toast.LENGTH_SHORT).show();

                           // last step
                           saveproductinfotodatabase();

                       }


                   }
               });
           }
       });

    }

    private void saveproductinfotodatabase() {

        HashMap<String,Object> productmap = new HashMap<>();
        productmap.put("pid",productrandomkey);
        productmap.put("data",savecurrrentdata);
        productmap.put("time",savecurrenttime);
        productmap.put("description",Description);
        productmap.put("image",downloadimageurl);
        productmap.put("category",CategoryName);
        productmap.put("price",Price);
        productmap.put("pname",Pname);

        productref.child(productrandomkey).updateChildren(productmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            Intent intent = new Intent(AdminAddNewProductActivity.this, CategoryAdminActivity.class);
                            startActivity(intent);


                            loadingdialog.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully  ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingdialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }
}
