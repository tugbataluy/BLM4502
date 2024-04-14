package com.example.addictionrecovery;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class GeneralAddictionQuestionAnswersPage extends AppCompatActivity {
    Toolbar tb;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView navIcon;
    TextView homeIcon,questionIcon,videoIcon, helpIcon, questionTitle,questionAnswer;
    RelativeLayout relativeLayout;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_addiction_question_answers);
        relativeLayout=findViewById(R.id.my_relative_layout);

        tb=(Toolbar) findViewById(R.id.title_template);
        tb.setTitle("Bağımlılık");

        Bundle extras = getIntent().getExtras();
        String question = extras.getString("QUESTION");
        String answer = extras.getString("ANSWER");


        System.out.println(question);
        System.out.println(answer);
        questionTitle=(TextView)findViewById(R.id.question_title);
        questionAnswer=(TextView) findViewById(R.id.answer_view);

        questionTitle.setText(question);
        questionAnswer.setText(answer);



        drawerInitialization();
        relativeLayoutClickerEnable();
        navBottomArrangements();
    }

    public void navBottomArrangements(){
        homeIcon=(TextView)findViewById(R.id.home_icon);
        questionIcon=(TextView) findViewById(R.id.questions_icon);
        videoIcon=(TextView) findViewById(R.id.videos_icon);
        helpIcon=(TextView) findViewById(R.id.help_icon);

        homeIcon.setOnClickListener(new GeneralAddictionQuestionAnswersPage.BottomBarListener());
        questionIcon.setOnClickListener(new GeneralAddictionQuestionAnswersPage.BottomBarListener());
        videoIcon.setOnClickListener(new GeneralAddictionQuestionAnswersPage.BottomBarListener());
        helpIcon.setOnClickListener(new GeneralAddictionQuestionAnswersPage.BottomBarListener());


    }
    public class BottomBarListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_icon:
                    Intent intent= new Intent(GeneralAddictionQuestionAnswersPage.this,GeneralAddictionMainPage.class);
                    startActivity(intent);
                    break;
                case R.id.questions_icon:
                    Intent intent2= new Intent(GeneralAddictionQuestionAnswersPage.this,GeneralAddictionQuestionsPage.class);
                    startActivity(intent2);
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
    public void drawerInitialization(){
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}