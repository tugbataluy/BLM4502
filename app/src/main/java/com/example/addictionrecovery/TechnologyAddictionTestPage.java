package com.example.addictionrecovery;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
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

public class TechnologyAddictionTestPage extends AppCompatActivity {

    public List<QuizModel> questionList;
    TextView tvQuestion, tvQuestionNo;
    RadioGroup radioGroup;
    RadioButton rb1, rb2, rb3, rb4;
    Button btnNext;
    int totalQuestions, qCounter = 0, score = 0;
    QuizModel currentQuestion;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_addiction_test_page);

        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.questionTitle);
        tvQuestionNo = findViewById(R.id.questionNum);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
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
                    Toast.makeText(TechnologyAddictionTestPage.this, "Lütfen seçim yapınız", Toast.LENGTH_SHORT).show();
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
                        score += 1;
                    } else if (selectedRadioButton == rb3) {
                        score += 2;
                    } else if (selectedRadioButton == rb4) {
                        score += 3;
                    }
                }
                // Sonraki soruyu göster
                showNextQuestion();
            }
        });
    }

    public void addQuestions(){
        // Soru 1
        String question1 = getResources().getString(R.string.question_1);
        String[] options1 = getResources().getStringArray(R.array.options_1);
        questionList.add(new QuizModel(question1, options1[0], options1[1], options1[2], options1[3]));

        // Soru 2
        String question2 = getResources().getString(R.string.question_2);
        String[] options2 = getResources().getStringArray(R.array.options_2);
        questionList.add(new QuizModel(question2, options2[0], options2[1], options2[2], options2[3]));

        String question3 = getResources().getString(R.string.question_3);
        String[] options3 = getResources().getStringArray(R.array.options_3);
        questionList.add(new QuizModel(question3, options3[0], options3[1], options3[2], options3[3]));

        String question4 = getResources().getString(R.string.question_4);
        String[] options4 = getResources().getStringArray(R.array.options_4);
        questionList.add(new QuizModel(question4, options4[0], options4[1], options4[2], options4[3]));

        String question5 = getResources().getString(R.string.question_5);
        String[] options5 = getResources().getStringArray(R.array.options_5);
        questionList.add(new QuizModel(question5, options5[0], options5[1], options5[2], options5[3]));

        String question6 = getResources().getString(R.string.question_6);
        String[] options6 = getResources().getStringArray(R.array.options_6);
        questionList.add(new QuizModel(question6, options6[0], options6[1], options6[2], options6[3]));
    }

    public void showNextQuestion(){
        radioGroup.clearCheck();

        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

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


