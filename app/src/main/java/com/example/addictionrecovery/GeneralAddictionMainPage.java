package com.example.addictionrecovery;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

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



import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import com.google.firebase.auth.FirebaseAuth;

public class GeneralAddictionMainPage extends AppCompatActivity {


    RelativeLayout relativeLayout;

    FirebaseAuth auth;
    NavigationView navigationView;
    ImageView navIcon,backIcon;
    TextView homeIcon,questionIcon,videoIcon,helpIcon;
    Toolbar tb;
    GridView symptomsView;
    GridView symptomsView2;

    GridView testView;

    String[] symptoms;
    String[] symptoms2;

    String[] testTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_main_page);
        auth = FirebaseAuth.getInstance();
        relativeLayout=(RelativeLayout)findViewById(R.id.general_addiction_layout);
        symptoms=getResources().getStringArray(R.array.general_symptoms);
        symptoms2= Arrays.copyOfRange(symptoms,3,6);
        testTitles=getResources().getStringArray(R.array.general_addiction_test_titles);

        // Birinci parça
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,R.layout.grid_item,symptoms);
        symptomsView=(GridView)findViewById(R.id.gridView);
        symptomsView.setAdapter(arrayAdapter);
       //2.parça
        symptomsView2=(GridView) findViewById(R.id.gridView2);
        ArrayAdapter<String> arrayAdapter2= new ArrayAdapter<>(this,R.layout.grid_item,symptoms2);
        symptomsView2.setAdapter(arrayAdapter2);

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
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new BottomBarListener());
        questionIcon.setOnClickListener(new BottomBarListener());
        videoIcon.setOnClickListener(new BottomBarListener());
        helpIcon.setOnClickListener(new BottomBarListener());


    }

    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
               case R.id.home_icon:
                   Intent intent= new Intent(GeneralAddictionMainPage.this,GeneralAddictionMainPage.class);
                   startActivity(intent);
                   finish();
                   break;
               case R.id.questions_icon:
                    Intent intent2= new Intent(GeneralAddictionMainPage.this,GeneralAddictionQuestionsPage.class);
                    startActivity(intent2);
                    finish();
                    break;
               case R.id.videos_icon:
                   //pass
                   break;
               case R.id.help_icon:
                   //pass
                   break;
               default:
                    System.out.println("Any nav selected");
                   break;
            }
        }
    }

    public void setToolbarTitle(){
        tb=(Toolbar) findViewById(R.id.title_template);
        tb.setTitle("Bağımlılık");
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