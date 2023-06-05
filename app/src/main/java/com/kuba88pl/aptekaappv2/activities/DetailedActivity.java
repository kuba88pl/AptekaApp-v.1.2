package com.kuba88pl.aptekaappv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kuba88pl.aptekaappv2.R;
import com.kuba88pl.aptekaappv2.models.NewProductsModel;
import com.kuba88pl.aptekaappv2.models.PopularProductsModel;
import com.kuba88pl.aptekaappv2.models.ShowAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;

    Toolbar toolbar;

    int totalQuantity = 1;
    String quantityToString; // tutaj pobieram wartosc quantity z edittext
    int quantityToInt = 0;
    int totalPrice = 0;
    String totalPriceToString; //tutaj wynik finalny - to ma wyswietlac activity
    int totalPricetoInt; // na potrzeby dodawania cen lub przypisania wartosci do int totalPrice;
    String totalQuantityToString; // to ma wyswietlac activity
    int totalQuantityToInt;
    int priceToInt;
    String priceToString;

    //New Products
    NewProductsModel newProductsModel = null;

    //Popular Products
    PopularProductsModel popularProductsModel = null;

    //Show All
    ShowAllModel showAllModel = null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);



        //New Products
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

//            quantityToString = quantity.getText().toString();
//            quantityToInt = Integer.parseInt(quantityToString);
//
//            totalPriceToString = newProductsModel.getPrice();
//            totalPricetoInt = Integer.parseInt(totalPriceToString) * totalQuantity;
//            totalPriceToString = String.valueOf(totalPricetoInt);
//            totalPrice += totalPricetoInt; //totalprice

//            totalQuantityToString = String.valueOf(totalQuantity);
//            totalQuantityToInt = Integer.parseInt(totalQ
//            uantityToString);
//            totalQuantity = totalQuantityToInt;
            priceToString = price.getText().toString();
            priceToInt = Integer.parseInt(priceToString);
            quantityToString = quantity.getText().toString(); // wartosc pobrana z pola edittext
            quantityToInt = Integer.parseInt(quantityToString); // ta sama wartosc sparsowana na int
//            totalPriceToString = newProductsModel.getPrice(); // pobranie ceny produktu
//            totalPricetoInt = Integer.parseInt(totalPriceToString); // ta sama cena ale przerobiona do int
            priceToInt = priceToInt * quantityToInt;
            priceToString = String.valueOf(priceToInt);

            addItems.setClickable(true);

            addItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantityToInt++;
                    quantityToString = String.valueOf(quantityToInt);
                    totalQuantityToInt = Integer.parseInt(quantityToString);
                    totalQuantity = totalQuantityToInt;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            });

            removeItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (totalQuantity <= 0) {
                        Toast.makeText(DetailedActivity.this, "Mniej sie nie da", Toast.LENGTH_SHORT).show();
                    } else {
                        quantityToInt--;
                        quantityToString = String.valueOf(quantityToInt);
                        totalQuantityToInt = Integer.parseInt(quantityToString);
                        totalQuantity = totalQuantityToInt;
                        quantity.setText(String.valueOf(totalQuantity));

                    }
                }
            });


        }

        //Popular Products
        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPriceToString = popularProductsModel.getPrice();
            totalPricetoInt = Integer.parseInt(totalPriceToString) * totalQuantity;
            totalPriceToString = String.valueOf(totalPricetoInt);
            totalPrice += totalPricetoInt;

            totalQuantityToString = String.valueOf(totalQuantity);
            totalQuantityToInt = Integer.parseInt(totalQuantityToString);
            totalQuantity += totalQuantityToInt;

            addItems.setClickable(true);

            addItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalQuantity++;
                    totalQuantityToString = String.valueOf(totalQuantity);
                    totalQuantityToInt = Integer.parseInt(totalPriceToString);
                    totalQuantity = totalQuantityToInt;
                    quantity.setText(String.valueOf(totalQuantityToString));
                }
            });

            removeItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantityToInt--;
                    quantityToString = String.valueOf(quantityToInt);
                    quantityToInt = Integer.parseInt(totalPriceToString);
                    quantityToInt = totalQuantityToInt;
                    quantity.setText(totalQuantityToString);

                }
            });

        }

        //Show All Products
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPriceToString = showAllModel.getPrice();
            totalPricetoInt = Integer.parseInt(totalPriceToString) * totalQuantity;
            totalPriceToString = String.valueOf(totalPricetoInt);
            totalPrice += totalPricetoInt;

            totalQuantityToString = String.valueOf(totalQuantity);
            totalQuantityToInt = Integer.parseInt(totalQuantityToString);
            totalQuantity += totalQuantityToInt;

            addItems.setClickable(true);

            addItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalQuantity++;
                    totalQuantityToString = String.valueOf(totalQuantity);
                    totalQuantityToInt = Integer.parseInt(totalPriceToString);
                    totalQuantity = totalQuantityToInt;
                    quantity.setText(String.valueOf(totalQuantityToString));
                }
            });

            removeItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    totalQuantity--;
                    totalQuantityToString = String.valueOf(totalQuantity);
                    totalQuantityToInt = Integer.parseInt(totalPriceToString);
                    totalQuantity = totalQuantityToInt;
                    quantity.setText(totalQuantityToString);

                }
            });

        }

        //Buy Now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

                if (newProductsModel != null) {
                    intent.putExtra("item", newProductsModel);
                }
                if (popularProductsModel != null) {
                    intent.putExtra("item", popularProductsModel);
                }
                if (showAllModel != null) {
                    intent.putExtra("item", showAllModel);
                }
                startActivity(intent);

            }
        });

        //Add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM, dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("totalQuantity", totalQuantityToString);
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Produkt dodany do koszyka.", Toast.LENGTH_SHORT).show();
                        finish(); //turn off DetailedActivity after click "add to cart"
                    }
                });

    }
}




