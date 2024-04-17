package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {
    EditText userName, userEmail, userPassword, userPasswordRepeat;
    Button userRegister;
    ImageView goBackToLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    /*@Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        initialize();
    }

    public void initialize(){
        mAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        userName= (EditText) findViewById(R.id.user_name_edit);
        userEmail= (EditText) findViewById(R.id.email_edit);
        userPassword= (EditText) findViewById(R.id.password_edit);
        userPasswordRepeat= (EditText) findViewById(R.id.password_recap_edit);

        userRegister= (Button) findViewById(R.id.kayit_ol_register);
        goBackToLogin= (ImageView) findViewById(R.id.back_home_register);

        userRegister.setOnClickListener( new SetOnClickListenerRegister());
        goBackToLogin.setOnClickListener( new SetOnClickListenerRegister1());
    }

    public class SetOnClickListenerRegister implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            String fulName,email,password,passRep;
            fulName=String.valueOf(userName.getText());
            email=String.valueOf(userEmail.getText());
            password=String.valueOf(userPassword.getText());
            passRep=String.valueOf(userPasswordRepeat.getText());

            if(TextUtils.isEmpty(fulName)){
                Toast.makeText(RegisterPage.this,"Enter fullname",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if(TextUtils.isEmpty(email)){
                Toast.makeText(RegisterPage.this,"Enter email",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(RegisterPage.this,"Enter password",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            if(TextUtils.isEmpty(passRep)){
                Toast.makeText(RegisterPage.this,"Repeat password",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // If sign in success, display a message to the user.
                                Toast.makeText(RegisterPage.this, "Account created.",
                                        Toast.LENGTH_SHORT).show();
                                Log.e("RegisterPage", "Kullanıcı kaydı başarılı: " );
                                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterPage.this, "Authentication failed.Email is used" ,
                                        Toast.LENGTH_SHORT).show();
                                Log.e("RegisterPage", "Kullanıcı kaydı başarısız oldu: " );
                            }
                        }
                    });
        }
    }
    public class SetOnClickListenerRegister1 implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(getApplicationContext(),LoginPage.class);
            startActivity(intent);
        }
    }
}