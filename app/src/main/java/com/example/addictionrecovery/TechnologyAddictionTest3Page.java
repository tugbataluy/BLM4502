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

public class TechnologyAddictionTest3Page extends AppCompatActivity {

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
                    Toast.makeText(TechnologyAddictionTest3Page.this, "Lütfen seçim yapınız", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                // Seçilen seçeneği dize dizisine ekleyin
                if (selectedRadioButton != null) {
                    String selectedAnswer = selectedRadioButton.getText().toString();
                    answers[qCounter - 1] = selectedAnswer; // Soru indeksi 0'dan başladığı için qCounter - 1

                    // Skoru güncelle
                    if (selectedRadioButton == rb1) {
                        if (currentQuestion.hasFourOptions()) {
                            score += 0;
                        } else {
                            score += 0; // İki seçenekli ise skora 0 ekle
                        }
                    } else if (selectedRadioButton == rb2) {
                        if (currentQuestion.hasFourOptions()) {
                            score += 1;
                        } else {
                            score += 2; // İki seçenekli ise skora 2 ekle
                        }
                    } else if (selectedRadioButton == rb3 && currentQuestion.hasFourOptions()) {
                        score += 2;
                    } else if (selectedRadioButton == rb4 && currentQuestion.hasFourOptions()) {
                        score += 3;
                    }
                }
                // Sonraki soruyu göster
                showNextQuestion();
            }
        });
    }

    public void addQuestions(){

        String[] yesNoQuestions = getResources().getStringArray(R.array.yes_no_questions);
        String option1="Evet", option2="Hayır";

        String question1 = getResources().getString(R.string.question_7);
        String[] options7 = getResources().getStringArray(R.array.options_7);
        questionList.add(new QuizModel(question1, options7[0], options7[1], options7[2], options7[3],true));

        String question5 = yesNoQuestions[9];
        questionList.add(new QuizModel(question5,option1,option2,false));

        String question2 = getResources().getString(R.string.question_8);
        String[] options8 = getResources().getStringArray(R.array.options_8);
        questionList.add(new QuizModel(question2, options8[0], options8[1], options8[2], options8[3],true));

        String question7 = yesNoQuestions[11];
        questionList.add(new QuizModel(question7,option1,option2,false));

        String question3 = getResources().getString(R.string.question_9);
        String[] options9 = getResources().getStringArray(R.array.options_9);
        questionList.add(new QuizModel(question3, options9[0], options9[1], options9[2], options9[3],true));

        String question4 = getResources().getString(R.string.question_10);
        String[] options10 = getResources().getStringArray(R.array.options_10);
        questionList.add(new QuizModel(question4, options10[0], options10[1], options10[2], options10[3],true));

        String question6 = yesNoQuestions[10];
        questionList.add(new QuizModel(question6,option1,option2,false));
    }

    public void showNextQuestion(){
        radioGroup.clearCheck();

        if (qCounter < totalQuestions) {
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());

            // QuizModel'deki seçenek sayısına göre radio buttonları ayarla
            if (currentQuestion.hasFourOptions()) {
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.VISIBLE);
                rb4.setVisibility(View.VISIBLE);

                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
            } else {
                // İki seçenekli ise sadece iki radio button gösterilsin
                rb1.setVisibility(View.VISIBLE);
                rb2.setVisibility(View.VISIBLE);
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);

                rb1.setText(currentQuestion.getOption1());
                rb2.setText(currentQuestion.getOption2());
            }

            qCounter++;
            tvQuestionNo.setText("Soru: " + qCounter + "/" + totalQuestions);

            if (qCounter == totalQuestions) {
                btnNext.setText("Bitir");
            }

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


