package com.kuba88pl.aptekaappv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kuba88pl.aptekaappv2.R;
import com.kuba88pl.aptekaappv2.adapters.AddressAdapter;
import com.kuba88pl.aptekaappv2.models.AddressModel;
import com.kuba88pl.aptekaappv2.models.MyCartModel;
import com.kuba88pl.aptekaappv2.models.NewProductsModel;
import com.kuba88pl.aptekaappv2.models.PopularProductsModel;
import com.kuba88pl.aptekaappv2.models.ShowAllModel;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SellectedAddress {

    Button addAddres;
    RecyclerView recyclerView;
    TextView addressAdd;

    private List <AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button addAddressBtn, paymentBtn;
    Toolbar toolbar;
    String mAddress = "";

    String newProductsAmount;
    int newProductsAmountToInt;
    String popularProductsAmount;
    int popularProductsAmountToInt;
    String showAllProducts;
    int showAllProductsToInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Object obj = getIntent().getSerializableExtra("item");

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        addAddres = findViewById(R.id.add_address_btn);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelList, this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                AddressModel addressModel = doc.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });


        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, PaymentActivity.class));

                String amount = "0";

                int totalAmount = 0;

                if (obj instanceof NewProductsModel) {
                    NewProductsModel newProductsModel = (NewProductsModel) obj;
                    newProductsAmount = newProductsModel.getPrice();
                    newProductsAmountToInt = Integer.valueOf(newProductsAmount);
                }
                if (obj instanceof PopularProductsModel) {
                    PopularProductsModel popularProductsModel  = (PopularProductsModel) obj;
                    popularProductsAmount = popularProductsModel.getPrice();
                    popularProductsAmountToInt = Integer.valueOf(popularProductsAmount);
                }
                if (obj instanceof ShowAllModel) {
                    ShowAllModel showAllModel = (ShowAllModel) obj;
                    showAllProducts = showAllModel.getPrice();
                    showAllProductsToInt = Integer.valueOf(showAllProducts);
                }
                Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                totalAmount = newProductsAmountToInt + popularProductsAmountToInt + showAllProductsToInt;
                amount = String.valueOf(totalAmount);
                intent.putExtra("totalAmount", amount);
                startActivity(intent);
            }
        });

        addAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });

    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}