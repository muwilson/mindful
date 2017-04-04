package com.example.myapplication.myapplication;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AddQuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_add_question);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean can_add_question = true;
                String insert_question = ((EditText) findViewById(R.id.insert_question)).getText().toString();
                String insert_negative_value = ((EditText) findViewById(R.id.insert_negative_value)).getText().toString();
                String insert_neutral_value = ((EditText) findViewById(R.id.insert_neutral_value)).getText().toString();
                String insert_positive_value = ((EditText) findViewById(R.id.insert_positive_value)).getText().toString();

                // if insert_question is default value or empty, don't add
                if (insert_question == "Insert Question Here" || insert_question.isEmpty() || insert_question == null) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid question text (non default, non empty).");
                }   // if insert_negative_value is default value or empty, don't add
                if (can_add_question && (insert_negative_value == "Insert Negative Value Here" ||
                        insert_negative_value.isEmpty() || insert_negative_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid negative value (non default, non empty).");
                }   // if insert_neutral_value is default value or empty, don't add
                if (can_add_question && (insert_neutral_value == "Insert Neutral Value Here" ||
                        insert_neutral_value.isEmpty() || insert_neutral_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid neutral value (non default, non empty).");
                }   // if insert_positive_value is default value or empty, don't add
                if (can_add_question && (insert_positive_value == "Insert Positive Value Here" ||
                        insert_positive_value.isEmpty() || insert_positive_value == null)) {
                    can_add_question = false;
                    ((TextView) findViewById(R.id.error_message)).setText("Please insert valid positive value (non default, non empty).");
                }
                if (can_add_question) { // if can still add question, attempt to add question into database
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    // if failed question insertion
                    if (!db.addQuestion(new Question(db.get_max_question_id() + 1, insert_question, insert_negative_value, insert_neutral_value, insert_positive_value))) {
                        ((TextView) findViewById(R.id.error_message)).setText("This question already exists in the database.");
                    } else {    // successful question insertion
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        try {
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            try {
                                Document doc = builder.parse(openFileInput("arrays.xml"));
                            } catch(FileNotFoundException e) {
                                Log.e("mindful", "filenotfoundexception", e);
                            } catch(IOException e) {
                                Log.e("mindful", "ioexception", e);
                            } catch(SAXException e) {
                                Log.e("mindful", "saxexception", e);
                            }
                        } catch(ParserConfigurationException e) {
                            Log.e("mindful", "parserconfigurationexception", e);
                        }
                    }
                }
            }
        });
    }
}
