package com.example.addictionrecovery;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import com.google.firebase.auth.FirebaseAuth;

public class GeneralAddictionMainPage extends AppCompatActivity {


    RelativeLayout relativeLayout;

    FirebaseAuth auth;
    NavigationView navigationView;
    ImageView navIcon,backIcon;
    //TextView homeIcon,questionIcon,videoIcon,helpIcon;
    BottomNavigationView bottomNavigationView;
    Toolbar tb;
    GridView symptomsView;
    GridView symptomsView2;

    GridView testView;

    String[] symptoms;


    String[] testTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_main_page);
        auth = FirebaseAuth.getInstance();
        relativeLayout=(RelativeLayout)findViewById(R.id.general_addiction_layout);
        symptoms=getResources().getStringArray(R.array.general_symptoms);

        testTitles=getResources().getStringArray(R.array.general_addiction_test_titles);

        // Semptomlar
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,R.layout.grid_item,symptoms);
        symptomsView=(GridView)findViewById(R.id.gridView);
        symptomsView.setAdapter(arrayAdapter);


        //Testler

        testView=(GridView)findViewById(R.id.gridView3);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this,R.layout.test_grid,testTitles);
        testView.setAdapter(arrayAdapter3);

        //Testlere tıklama yeteneği verme
        testView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tıklanan öğenin sırasını bastır
                System.out.println("Tıklanan öğenin sırası: " + position);
            }
        });


        backButtonInitialization();
        setToolbarTitle();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();
    }



    public void backButtonInitialization (){
        backIcon=(ImageView)findViewById(R.id.back_to_mainpage_icon);
        backIcon.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralAddictionMainPage.this, HomePage.class);
                startActivity(intent);
            }
        });
    }


    public void navBottomArrangements(){
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_id);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId= item.getItemId();
                switch (itemId){
                    case R.id.home_tab:
                        System.out.println("home");
                        Intent intent= new Intent(GeneralAddictionMainPage.this,HomePage.class);
                        startActivity(intent);
                        break;
                    case R.id.questions_tab:
                        System.out.println("questions");
                        Intent intent2= new Intent(GeneralAddictionMainPage.this,GeneralAddictionQuestionsPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.videos_tab:
                        System.out.println("videos");
                        Intent intent3= new Intent(GeneralAddictionMainPage.this,GeneralAddictionVideosPage.class);
                        startActivity(intent3);
                        break;
                    case R.id.support_tab:
                        System.out.println("support");
                        Intent intent4= new Intent(GeneralAddictionMainPage.this,GeneralAddictionSupportPage.class);
                        startActivity(intent4);
                        break;
                    default:

                        break;
                }

                return false;
            }
        });
    }



    public void setToolbarTitle(){
        tb=(Toolbar) findViewById(R.id.title_template);
        tb.setTitle("Bağımlılık");
    }
    private void showLogoutConfirmationDialog() {
        // AlertDialog oluştur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // İptal durumu, bir şey yapmaya gerek yok
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void drawerInitialization(){
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.INVISIBLE);
        navIcon=(ImageView) findViewById(R.id.navigation_icon);

        navIcon.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.INVISIBLE){
                    navigationView.setVisibility(View.VISIBLE);
                }
                else{
                    navigationView.setVisibility(View.INVISIBLE);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.kullanici_profili_option:
                        Intent intent= new Intent(GeneralAddictionMainPage.this,user_profile.class);
                        startActivity(intent);
                        break;

                    case R.id.neyi_amacliyoruz_option:
                        Intent intent1=new Intent (GeneralAddictionMainPage.this, Purpose.class);
                        startActivity(intent1);
                        break;
                    case R.id.geri_bildirim_option:
                        Intent intent3 = new Intent(GeneralAddictionMainPage.this, Feedback.class);
                        startActivity(intent3);
                        break;

                    case R.id.cikis_yap_option:
                        showLogoutConfirmationDialog();
                        break;
                    default:
                        return true;


                }
                return  false;

            }
        });

    }

    public void logout(){
        auth.signOut();

        // Giriş sayfasına yönlendir
        Intent intent = new Intent(GeneralAddictionMainPage.this, LoginPage.class);
        startActivity(intent);
        finish(); // Bu aktiviteyi kapat
    }

    public void relativeLayoutClickerEnable(){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.VISIBLE){
                    navigationView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}