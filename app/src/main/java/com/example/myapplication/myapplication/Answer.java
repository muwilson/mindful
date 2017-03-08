package com.example.myapplication.myapplication;

/**
 * Created by Nazneen on 3/7/2017.
 */

public class Answer {
    public long date;
    public int q_id;
    public int response;

    public Answer(long date_in, int qid_in, int response_in) {
        this.date = date_in;
        this.q_id = qid_in;
        this.response = response_in;
    }
}
