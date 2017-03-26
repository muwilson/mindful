package com.example.myapplication.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class ViewingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public int count = 0;
    public int day_quality = 0;
    public int question_id = 0;
    public Set day_qualities;
    public Set questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner1Items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner2Items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // populate the sets
        String[] quality_array = getResources().getStringArray(R.array.spinner1Items);
//        ArrayAdapter<String> string_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quality_array);
//        ListView lv = (ListView)findViewById(R.id)
        day_qualities = new HashSet(Arrays.asList(quality_array));
        String[] question_array = getResources().getStringArray(R.array.spinner2Items);
//        string_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question_array);
//        for (int i = 0; i < string_adapter.getCount(); ++i) {
//            questions.add(string_adapter.getItem(i));
//        }
        questions = new HashSet(Arrays.asList(question_array));
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
//        Toast.makeText(parent.getContext(),
//                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString() + ", id: " + Long.toString(id),
//                Toast.LENGTH_SHORT).show();

        // get database
        DatabaseHandler db = new DatabaseHandler(this);

        if (day_qualities.contains(parent.getItemAtPosition(pos).toString())) {
            // changing quality of day
            day_quality = pos;
        } else {    // questions.contains(parent.getItemAtPosition(pos).toString()) == true
            // changing the question type
            question_id = pos;
        }

        ((TextView) findViewById(R.id.day_quality)).setText("On a " + getResources().getStringArray(R.array.day_qualities)[day_quality] + " day");
        List<Answer> answers = db.getAnswers(question_id, day_quality);
//        Integer response_frequency[] = new Integer[3];     // default initial value to zero
//        for (Answer answer : answers) {
//            ++response_frequency[answer.response];     // store the # of each response to this particular question
//        }
//            Arrays.sort(response_frequency, Collections.reverseOrder());    // sort
        String type[];
        if (question_id == 0) {
            type = getResources().getStringArray(R.array.stress_levels);
        } else if (question_id == 1) {
            type = getResources().getStringArray(R.array.diet_levels);
        } else if (question_id == 2) {
            type = getResources().getStringArray(R.array.sleep_quality);
        } else if (question_id == 3) {
            type = getResources().getStringArray(R.array.work_quality);
        } else if (question_id == 4) {
            type = getResources().getStringArray(R.array.exercise_levels);
        } else {    // question_id == 5
            type = getResources().getStringArray(R.array.friends_talked_to);
        }
//        ((TextView) findViewById(R.id.first_percentage)).setText(
//                Integer.toString((int)((double)response_frequency[0] / (double)answers.size()) * 100) + "% " + type[0]);
        Toast.makeText(parent.getContext(),
                "answers.size(): " + Integer.toString(answers.size()),
                Toast.LENGTH_SHORT).show();//        ((TextView) findViewById(R.id.second_percentage)).setText(
//                Integer.toString((int)((double)response_frequency[1] / (double)answers.size()) * 100) + "% " + type[1]);
//        ((TextView) findViewById(R.id.third_percentage)).setText(
//                Integer.toString((int)((double)response_frequency[2] / (double)answers.size()) * 100) + "% " + type[2]);

//        if (pos == 3) {
//            ((TextView) findViewById(R.id.day_quality)).setText("On a Good day");
//            ((TextView) findViewById(R.id.first_percentage)).setText("40% Not Stressed");
//            ((TextView) findViewById(R.id.second_percentage)).setText("40% Somewhat Stressed");
//            ((TextView) findViewById(R.id.third_percentage)).setText("20% Very Stressed");
//        } else if (pos == 0) {
//            ((TextView) findViewById(R.id.day_quality)).setText("On a Very Bad day");
//            ((TextView) findViewById(R.id.first_percentage)).setText("60% Very Stressed");
//            ((TextView) findViewById(R.id.second_percentage)).setText("30% Somewhat Stressed");
//            ((TextView) findViewById(R.id.third_percentage)).setText("10% Not Stressed");
//        } else if (pos == 4) {
//            ((TextView) findViewById(R.id.day_quality)).setText("On a Good day");
//            ((TextView) findViewById(R.id.first_percentage)).setText("50% A lot");
//            ((TextView) findViewById(R.id.second_percentage)).setText("30% A little");
//            ((TextView) findViewById(R.id.third_percentage)).setText("20% Not at all");
//        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}
