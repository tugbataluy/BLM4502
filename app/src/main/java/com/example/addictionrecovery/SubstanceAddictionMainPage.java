package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SubstanceAddictionMainPage extends AppCompatActivity {
    Toolbar tb;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substance_addiction_main_page);
        relativeLayout=(RelativeLayout) findViewById(R.id.substance_addiction_main_page_layout);
        auth=FirebaseAuth.getInstance();

        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();


    }


    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Madde Bağımlılığı");
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
                        break;
                    case R.id.neyi_amacliyoruz_option:
                        break;
                    case R.id.ayarlar_option:
                        break;
                    case R.id.cikis_yap_option:
                        logout();
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
        Intent intent= new Intent(SubstanceAddictionMainPage.this, LoginPage.class);
        startActivity(intent);
        finish();
    }
    public void navBottomArrangements(){
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new SubstanceAddictionMainPage.BottomBarListener());
        questionIcon.setOnClickListener(new SubstanceAddictionMainPage.BottomBarListener());
        videoIcon.setOnClickListener(new SubstanceAddictionMainPage.BottomBarListener());
        helpIcon.setOnClickListener(new SubstanceAddictionMainPage.BottomBarListener());


    }
    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(SubstanceAddictionMainPage.this,SubstanceAddictionMainPage.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(SubstanceAddictionMainPage.this,SubstanceAddictionQuestionsPage.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.videos_icon:
                    Intent intent3= new Intent(SubstanceAddictionMainPage.this,SubstanceAddictionVideosPage.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.help_icon:
                    Intent intent4= new Intent(SubstanceAddictionMainPage.this,SubstanceAddictionSupportPage.class);
                    startActivity(intent4);
                    finish();
                    break;
                default:
                    System.out.println("Any nav selected");
                    break;
            }
        }
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