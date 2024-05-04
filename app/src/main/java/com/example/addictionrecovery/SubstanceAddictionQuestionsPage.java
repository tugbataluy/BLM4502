package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SubstanceAddictionQuestionsPage extends AppCompatActivity {

    Toolbar tb;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    BottomNavigationView bottomNavigationView;

    String [] questions,answers;

    GridView questionsGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substance_addiction_questions_page);
        relativeLayout=(RelativeLayout) findViewById(R.id.substance_addiction_questions_page_layout);
        auth=FirebaseAuth.getInstance();


        setQuestionsGrid();
        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();


    }

    public void setQuestionsGrid(){
        questionsGrid= (GridView) findViewById(R.id.substance_addiction_questions_grid);
        questions= getResources().getStringArray(R.array.subs_addiction_questions);
        answers=getResources().getStringArray(R.array.subs_addiction_question_answers);

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this,R.layout.subs_addiction_question_grid,questions);
        questionsGrid.setAdapter(arrayAdapter);
        questionsGrid.setOnItemClickListener(new QuestionGridListener());


    }

    public class  QuestionGridListener implements  AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent( SubstanceAddictionQuestionsPage.this,SubstanceAddictionQuestionAnswersPage.class);
            Bundle extras = new Bundle();
            extras.putString("QUESTION",questions[position]);
            extras.putString("ANSWER",answers[position]);
            intent.putExtras(extras);
            startActivity(intent);
        }
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
                       Intent intent= new Intent(SubstanceAddictionQuestionsPage.this,user_profile.class);
                       startActivity(intent);
                        break;
                    case R.id.neyi_amacliyoruz_option:
                        Intent intent2= new Intent(SubstanceAddictionQuestionsPage.this,Purpose.class);
                        startActivity(intent2);
                        break;
                    case R.id.geri_bildirim_option:
                        Intent intent3 = new Intent(SubstanceAddictionQuestionsPage.this, Feedback.class);
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

    public void logout(){
        auth.signOut();
        Intent intent= new Intent(SubstanceAddictionQuestionsPage.this, LoginPage.class);
        startActivity(intent);
        finish();
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
                        Intent intent= new Intent(SubstanceAddictionQuestionsPage.this,SubstanceAddictionMainPage.class);
                        startActivity(intent);
                        break;
                    case R.id.questions_tab:
                        System.out.println("questions");
                        Intent intent2= new Intent(SubstanceAddictionQuestionsPage.this,SubstanceAddictionQuestionsPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.videos_tab:
                        System.out.println("videos");
                        Intent intent3= new Intent(SubstanceAddictionQuestionsPage.this,SubstanceAddictionVideosPage.class);
                        startActivity(intent3);
                        break;
                    case R.id.support_tab:
                        System.out.println("support");
                        Intent intent4= new Intent(SubstanceAddictionQuestionsPage.this,SubstanceAddictionSupportPage.class);
                        startActivity(intent4);
                        break;
                    default:

                        break;
                }

                return false;
            }
        });
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
    public void onBackPressed() {
        // Yeni aktiviteye geçiş yapmak için Intent oluştur
        super.onBackPressed();
        Intent intent = new Intent(this, SubstanceAddictionMainPage.class);
        // Yeni aktiviteyi başlat
        startActivity(intent);
        // Mevcut aktiviteyi sonlandır
        finish();
    }
}