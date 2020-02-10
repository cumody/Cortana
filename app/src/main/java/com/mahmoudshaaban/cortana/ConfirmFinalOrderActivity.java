package com.mahmoudshaaban.cortana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameedittext, phoneedittetxt, addressedittext, cityedittext;
    private Button confirmorderbtn;

    private String totalamout = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalamout = getIntent().getStringExtra("Total Price");

        nameedittext = findViewById(R.id.shipment_name);
        phoneedittetxt = findViewById(R.id.shipment_number);
        addressedittext = findViewById(R.id.shipment_address);
        cityedittext = findViewById(R.id.shipment_city);
        confirmorderbtn = findViewById(R.id.confirm_final_order);
    }
}
