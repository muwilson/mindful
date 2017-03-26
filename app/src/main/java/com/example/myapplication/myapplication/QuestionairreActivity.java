package com.example.myapplication.myapplication;


import android.app.ActionBar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionairreActivity extends AppCompatActivity {

    private List<Question> questions;
    public int indexOfQuestion = 0;
    public int numQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        DatabaseHandler db = new DatabaseHandler(this);
        questions = db.getQuestions();

        questions.add(0, new Question(0, "How are you feeling today?", "Very Bad", "Meh", "Very Good"));
        this.numQuestions = questions.size();

        setContentView(R.layout.activity_questionairre);
        ((SeekBar) findViewById(R.id.seekBar1)).setMax(4);
        ((SeekBar) findViewById(R.id.seekBar1)).setProgress(2);

        //handling back
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexOfQuestion == 0) QuestionairreActivity.super.onBackPressed();
                else {
                    --indexOfQuestion;
                    ((TextView) findViewById(R.id.textView2)).setText(questions.get(indexOfQuestion).question_text);
                    ((TextView) findViewById(R.id.textView3)).setText(questions.get(indexOfQuestion).answer1_text);
                    ((TextView) findViewById(R.id.textView4)).setText(questions.get(indexOfQuestion).answer3_text);
                    ((TextView) findViewById(R.id.textView5)).setText(questions.get(indexOfQuestion).answer2_text);
                }
                if (indexOfQuestion == 0) {
                    ((SeekBar) findViewById(R.id.seekBar1)).setMax(4);
                    ((SeekBar) findViewById(R.id.seekBar1)).setProgress(2);
                    findViewById(R.id.imageButton3).setEnabled(false);
                    findViewById(R.id.imageButton4).setEnabled(true);
                } else {
                    ((SeekBar) findViewById(R.id.seekBar1)).setMax(2);
                    ((SeekBar) findViewById(R.id.seekBar1)).setProgress(1);
                    findViewById(R.id.imageButton3).setEnabled(true);
                    findViewById(R.id.imageButton4).setEnabled(true);
                }
            }
        });

        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                // save data locally here
                questions.get(indexOfQuestion).answer_chosen = ((SeekBar) findViewById(R.id.seekBar1)).getProgress();
                if (indexOfQuestion == numQuestions - 1) {
                    Date d = new Date();
                    long timestamp = d.getTime() / 1000;
                    boolean first = true;
                    // need to make answers
                    for (Question q : questions) {
                        Answer answer = new Answer(timestamp, q.question_id, q.answer_chosen);
                        if (first) {    // first question is the Rating of the day
                            db.addRating(answer);
                            first = false;
                        } else {        // remaining questions are Answers
                            db.addAnswer(answer);
                        }
                    }
                    // commit to db here
                    Toast.makeText(getApplicationContext(), "You're finished! Nice job!", Toast.LENGTH_SHORT).show();
                    QuestionairreActivity.super.onBackPressed();
                } else {
                    ++indexOfQuestion;
                    ((SeekBar) findViewById(R.id.seekBar1)).setMax(2);
                    ((SeekBar) findViewById(R.id.seekBar1)).setProgress(1);
                    ((TextView) findViewById(R.id.textView2)).setText(questions.get(indexOfQuestion).question_text);
                    ((TextView) findViewById(R.id.textView3)).setText(questions.get(indexOfQuestion).answer1_text);
                    ((TextView) findViewById(R.id.textView4)).setText(questions.get(indexOfQuestion).answer3_text);
                    ((TextView) findViewById(R.id.textView5)).setText(questions.get(indexOfQuestion).answer2_text);
                }
                findViewById(R.id.imageButton4).setEnabled(true);
                findViewById(R.id.imageButton3).setEnabled(true);
            }
        });
    }
}