package com.example.addictionrecovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class GeneralAddictionQuestionsPage extends AppCompatActivity {

    Toolbar tb;
    NavigationView navigationView;

    FirebaseAuth auth;
    RelativeLayout relativeLayout;

    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon;
    GridView questionsGrid;

    String [] questions,answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_addiction_questions_page);


        auth=FirebaseAuth.getInstance();
        relativeLayout=findViewById(R.id.general_addiction_questions_layout);



        toolBarArrangement();
        drawerInitialization();
        relativeLayoutClickerEnable();
        setQuestionsGrid();
        navBottomArrangements();
    }


    public void toolBarArrangement(){
        tb=(Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("Bağımlılık");
    }
    public void setQuestionsGrid(){
        answers=getResources().getStringArray(R.array.general_addiction_question_answers);
        questions=getResources().getStringArray(R.array.general_addiction_question_list);
        questionsGrid=(GridView) findViewById(R.id.general_addiction_questions_grid);
        ArrayAdapter<String> questionArrayAdapter= new ArrayAdapter<String>(this,R.layout.ga_test_page_grid,questions);
        questionsGrid.setAdapter(questionArrayAdapter);
        questionsGrid.setOnItemClickListener(new QuestionGridListener());
    }


    public void navBottomArrangements(){
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new GeneralAddictionQuestionsPage.BottomBarListener());
        questionIcon.setOnClickListener(new GeneralAddictionQuestionsPage.BottomBarListener());
        videoIcon.setOnClickListener(new GeneralAddictionQuestionsPage.BottomBarListener());
        helpIcon.setOnClickListener(new GeneralAddictionQuestionsPage.BottomBarListener());


    }

    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(GeneralAddictionQuestionsPage.this,GeneralAddictionMainPage.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(GeneralAddictionQuestionsPage.this,GeneralAddictionQuestionsPage.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.videos_icon:
                    Intent intent3= new Intent(GeneralAddictionQuestionsPage.this,GeneralAddictionVideosPage.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.help_icon:
                    Intent intent4= new Intent(GeneralAddictionQuestionsPage.this,GeneralAddictionSupportPage.class);
                    startActivity(intent4);
                    finish();
                    break;
                default:
                    System.out.println("Any nav selected");
                    break;
            }
        }
    }

    public class QuestionGridListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent( GeneralAddictionQuestionsPage.this,GeneralAddictionQuestionAnswersPage.class);
            Bundle extras = new Bundle();
            extras.putString("QUESTION",questions[position]);
            extras.putString("ANSWER",answers[position]);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    public void showQuestionAnswer(int position){
        Intent intent = new Intent( GeneralAddictionQuestionsPage.this,GeneralAddictionQuestionAnswersPage.class);
        intent.putExtra("questionTitle",questions[position]);
        intent.putExtra("questionAnswer",answers[position]);
        startActivity(intent);
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
        Intent intent= new Intent(GeneralAddictionQuestionsPage.this, LoginPage.class);
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