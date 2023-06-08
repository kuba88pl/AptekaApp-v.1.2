package com.kuba88pl.aptekaappv2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kuba88pl.aptekaappv2.R;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //Toolbar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        String totalAmount = "0.0";
        totalAmount = getIntent().getStringExtra("totalAmount");

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);

        subTotal.setText(totalAmount + " PLN");
        total.setText(totalAmount + " PLN");


    }
}