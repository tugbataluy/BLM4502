package com.example.addictionrecovery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button loginButton, registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        initialize();

    }

    public void initialize(){
        userEmail= (EditText) findViewById(R.id.eposta_edit);
        userPassword= (EditText) findViewById(R.id.password_edit);
        loginButton= (Button) findViewById(R.id.giris_yap);
        registerButton= (Button) findViewById(R.id.kayit_ol);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // next page
                Intent intent =new Intent( LoginPage.this, WelcomePage.class);
                startActivity(intent);
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }



}