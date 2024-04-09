package com.example.addictionrecovery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterPage extends AppCompatActivity {
    EditText userName, userEmail, userPassword, userPasswordRepeat;
    Button userRegister;
    ImageView goBackToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initialize();
    }

    public void initialize(){
        userName= (EditText) findViewById(R.id.user_name_edit);
        userEmail= (EditText) findViewById(R.id.password_edit);
        userPassword= (EditText) findViewById(R.id.password_edit);
        userPasswordRepeat= (EditText) findViewById(R.id.password_recap_edit);
        userRegister= (Button) findViewById(R.id.kayit_ol_register);
        goBackToLogin= (ImageView) findViewById(R.id.back_home_register);

        userRegister.setOnClickListener( new SetOnClickListenerRegister());
        goBackToLogin.setOnClickListener( new SetOnClickListenerRegister());
    }

    public class SetOnClickListenerRegister implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent= new Intent(RegisterPage.this,LoginPage.class);
            startActivity(intent);
        }
    }
}