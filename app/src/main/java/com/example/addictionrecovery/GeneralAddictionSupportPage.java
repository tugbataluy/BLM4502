package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GeneralAddictionSupportPage extends AppCompatActivity {
    Toolbar tb, bottom;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon;
    ListView listView;
    String [] supportContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_support_page);
        auth=FirebaseAuth.getInstance();
        relativeLayout=(RelativeLayout) findViewById(R.id.general_addiction_support_layout);


        showSupportList();

        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();



    }

    public void showSupportList(){
        supportContent= getResources().getStringArray(R.array.addiction_support_organizations);


        listView=(ListView) findViewById(R.id.ngo_list_view);
        ArrayAdapter<String>listAdapter= new ArrayAdapter<String>(GeneralAddictionSupportPage.this,R.layout.general_addiction_support_list_item,supportContent);
        listView.setAdapter(listAdapter);
    }


    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Bağımlılık");
    }

    public void navBottomArrangements(){
        bottom= (Toolbar) findViewById(R.id.bottom_temp);
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new GeneralAddictionSupportPage.BottomBarListener());
        questionIcon.setOnClickListener(new GeneralAddictionSupportPage.BottomBarListener());
        videoIcon.setOnClickListener(new GeneralAddictionSupportPage.BottomBarListener());
        helpIcon.setOnClickListener(new GeneralAddictionSupportPage.BottomBarListener());


    }

    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(GeneralAddictionSupportPage.this,GeneralAddictionMainPage.class);
                    startActivity(intent);

                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(GeneralAddictionSupportPage.this,GeneralAddictionQuestionsPage.class);
                    startActivity(intent2);

                    break;
                case R.id.videos_icon:
                    Intent intent3= new Intent(GeneralAddictionSupportPage.this,GeneralAddictionVideosPage.class);
                    startActivity(intent3);

                    break;
                case R.id.help_icon:
                    Intent intent4 = new Intent(GeneralAddictionSupportPage.this,GeneralAddictionSupportPage.class);
                    startActivity(intent4);

                    break;
                default:
                    System.out.println("Any nav selected");
                    break;
            }
        }


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
        Intent intent= new Intent(GeneralAddictionSupportPage.this, LoginPage.class);
        startActivity(intent);
        finish();
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