package com.kuba88pl.aptekaappv2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kuba88pl.aptekaappv2.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, password;

    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        boolean isFistTime = sharedPreferences.getBoolean("firstTime", true);

        if (isFistTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signup(View view) {

        auth = FirebaseAuth.getInstance();

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Podaj nazwę użytkownika!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Podaj adres e-mail!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Podaj hasło!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6 ){
            Toast.makeText(this, "Hasło powinno składać się z conajmniej 6 znaków!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegistrationActivity.this, "Pomyślnie utoworzono konto!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                }
                                else {
                                    Toast.makeText(RegistrationActivity.this, "Błąd przy zakładaniu konta. Spróbuj ponownie." +task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }
}