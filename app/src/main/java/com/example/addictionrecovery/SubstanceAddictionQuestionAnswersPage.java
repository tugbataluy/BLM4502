package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SubstanceAddictionQuestionAnswersPage extends AppCompatActivity {
    Toolbar tb;
    NavigationView navigationView;

    ImageView navIcon,backArrow;
    TextView homeIcon,questionIcon,videoIcon, helpIcon, questionTitle,questionAnswer;
    RelativeLayout relativeLayout;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substance_addiction_question_answers_page);
        relativeLayout=(RelativeLayout) findViewById(R.id.my_relative_layout);
        auth=FirebaseAuth.getInstance();


        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();
        goBack();
        setContents();

    }

    public void setContents(){
        Bundle extras= getIntent().getExtras();
        questionTitle=(TextView)findViewById(R.id.question_title);
        questionAnswer=(TextView)findViewById(R.id.answer_view);
        questionTitle.setText(extras.getString("QUESTION"));
        questionAnswer.setText(extras.getString("ANSWER"));

    }

    public void goBack(){
        backArrow=(ImageView) findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SubstanceAddictionQuestionAnswersPage.this,SubstanceAddictionQuestionsPage.class);
                startActivity(intent);
            }
        });
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
                        Intent intent= new Intent(SubstanceAddictionQuestionAnswersPage.this,user_profile.class);
                        startActivity(intent);
                        break;
                    case R.id.neyi_amacliyoruz_option:
                        Intent intent2= new Intent(SubstanceAddictionQuestionAnswersPage.this,Purpose.class);
                        startActivity(intent2);
                        break;
                    case R.id.geri_bildirim_option:
                        Intent intent3 = new Intent(SubstanceAddictionQuestionAnswersPage.this, Feedback.class);
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
        Intent intent= new Intent(SubstanceAddictionQuestionAnswersPage.this, LoginPage.class);
        startActivity(intent);
        finish();
    }
    public void navBottomArrangements(){
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new SubstanceAddictionQuestionAnswersPage.BottomBarListener());
        questionIcon.setOnClickListener(new SubstanceAddictionQuestionAnswersPage.BottomBarListener());
        videoIcon.setOnClickListener(new SubstanceAddictionQuestionAnswersPage.BottomBarListener());
        helpIcon.setOnClickListener(new SubstanceAddictionQuestionAnswersPage.BottomBarListener());


    }
    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(SubstanceAddictionQuestionAnswersPage.this,SubstanceAddictionMainPage.class);
                    startActivity(intent);

                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(SubstanceAddictionQuestionAnswersPage.this,SubstanceAddictionQuestionsPage.class);
                    startActivity(intent2);

                    break;
                case R.id.videos_icon:
                    Intent intent3= new Intent(SubstanceAddictionQuestionAnswersPage.this,SubstanceAddictionVideosPage.class);
                    startActivity(intent3);

                    break;
                case R.id.help_icon:
                    Intent intent4 = new Intent(SubstanceAddictionQuestionAnswersPage.this,SubstanceAddictionSupportPage.class);
                    startActivity(intent4);

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