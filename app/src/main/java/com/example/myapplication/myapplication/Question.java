package com.example.myapplication.myapplication;

/**
 * Created by muwilson, hasatahm, moeahmed on 4/7/2017.
 */

public class Question {
    public int question_id;
    public String question_text;
    public String answer1_text;
    public String answer2_text;
    public String answer3_text;
    public String spinner_tag;
    public int answer_chosen;


    public Question() {}

    public Question(int qid_in, String q_in, String a1_in, String a2_in, String a3_in, String spinner_tag_in) {
        this.question_id = qid_in;
        this.question_text = q_in;
        this.answer1_text = a1_in;
        this.answer2_text = a2_in;
        this.answer3_text = a3_in;
        this.spinner_tag = spinner_tag_in;
    }
}

