package com.example.addictionrecovery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TechnologyAddictionTest2Page extends AppCompatActivity {

    public List<QuizModel> questionList;
    TextView tvQuestion, tvQuestionNo;
    RadioGroup radioGroup;
    RadioButton rb1, rb2;
    Button btnNext;
    int totalQuestions, qCounter = 0, score = 0;
    QuizModel currentQuestion;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_addiction_test2_page);

        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.questionTitle);
        tvQuestionNo = findViewById(R.id.questionNum);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        btnNext = findViewById(R.id.btnNext);

        addQuestions();
        totalQuestions = questionList.size();
        answers = new String[totalQuestions];
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                // Eğer bir seçenek seçilmediyse uyarı ver
                if (selectedRadioButtonId == -1) {
                    Toast.makeText(TechnologyAddictionTest2Page.this, "Lütfen seçim yapınız", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                // Seçilen seçeneği dize dizisine ekleyin
                if (selectedRadioButton != null) {
                    String selectedAnswer = selectedRadioButton.getText().toString();
                    answers[qCounter - 1] = selectedAnswer; // Soru indeksi 0'dan başladığı için qCounter - 1

                    // Skoru güncelle
                    if (selectedRadioButton == rb1) {
                        score += 0;
                    } else if (selectedRadioButton == rb2) {
                        score += 2;
                    }
                }
                // Sonraki soruyu göster
                showNextQuestion();
            }
        });
    }

    public void addQuestions(){
        // Soru 1
        String[] yesNoQuestions = getResources().getStringArray(R.array.yes_no_questions);
        String option1="Evet", option2="Hayır";

        String question1 = yesNoQuestions[0];
        questionList.add(new QuizModel(question1,option1,option2));

        String question2 = yesNoQuestions[1];
        questionList.add(new QuizModel(question2,option1,option2));

        String question3 = yesNoQuestions[2];
        questionList.add(new QuizModel(question3,option1,option2));

        String question4 = yesNoQuestions[3];
        questionList.add(new QuizModel(question4,option1,option2));

        String question5 = yesNoQuestions[4];
        questionList.add(new QuizModel(question5,option1,option2));

        String question6 = yesNoQuestions[5];
        questionList.add(new QuizModel(question6,option1,option2));

        String question7 = yesNoQuestions[6];
        questionList.add(new QuizModel(question7,option1,option2));

        String question8 = yesNoQuestions[7];
        questionList.add(new QuizModel(question8,option1,option2));

        String question9 = yesNoQuestions[8];
        questionList.add(new QuizModel(question9,option1,option2));
    }

    public void showNextQuestion(){
        radioGroup.clearCheck();

        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());

            qCounter++;
            tvQuestionNo.setText("Soru: "+qCounter+"/"+totalQuestions);

            if(qCounter==totalQuestions){btnNext.setText("Bitir");}

        } else {
            Log.d("Score", "Total score: " + score);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your Score");
            builder.setMessage("Total score: " + score);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dialog kapatıldığında yapılacak işlemler
                    finish(); // Activity'i kapat
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            //finish();
        }
    }
}


