package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

    RelativeLayout homePageLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navIcon;
    TextView generalAddiction, substanceAddiction, techAddiction,alcoholAddiction;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //ana layout
        homePageLayout= (RelativeLayout)findViewById(R.id.homepage_layout);
        //sayfa butonları
        generalAddiction=(TextView)findViewById(R.id.general_addiction_btn);
        substanceAddiction=(TextView)findViewById(R.id.drug_addiction_btn);
        techAddiction=(TextView)findViewById(R.id.tech_addiction_btn);
        alcoholAddiction=(TextView)findViewById(R.id.alcohol_addiction_btn);

        drawerActions();
        homePageClickerEnable();

        generalAddiction.setOnClickListener( new PageButtonsListener());
        substanceAddiction.setOnClickListener( new PageButtonsListener());
        techAddiction.setOnClickListener(new PageButtonsListener());
        alcoholAddiction.setOnClickListener(new PageButtonsListener());



    }

    public  class PageButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.general_addiction_btn:
                    Intent intent= new Intent(HomePage.this, GeneralAddictionMainPage.class);
                    startActivity(intent);
                case R.id.drug_addiction_btn:
                    //pass
                case R.id.tech_addiction_btn:
                    //pass
                case R.id.alcohol_addiction_btn:
                    //pass
                default:
                    System.out.println("Nothing Clicked");


            }
        }
    }

    public void drawerActions(){
        // drawer menu ile alakalı atamalar
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setVisibility(View.INVISIBLE);
        drawerLayout= (DrawerLayout) findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

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
    }

    public void homePageClickerEnable(){
        homePageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility()==View.VISIBLE){
                    navigationView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}