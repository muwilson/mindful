package com.example.myapplication.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.myapplication.Question;
import com.example.myapplication.myapplication.QuestionairreActivity;
import com.example.myapplication.myapplication.Rating;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mindful_db";

    // Questions table name
    private static final String TABLE_QUESTIONS = "questions";

    // Questions Table Columns names
    private static final String QUESTION_ID = "q_id";
    private static final String QUESTION_TEXT = "text";
    private static final String ANSWER_1 = "answer_1";
    private static final String ANSWER_2 = "answer_2";
    private static final String ANSWER_3 = "answer_3";

    // Answers table name
    private static final String TABLE_ANSWERS = "answers";

    // Answers Table Columns names
    private static final String ANSWER_DATE = "answer_date";
    //private static final String QUESTION_ID = "q_id";
    private static final String RESPONSE = "response";

    //Ratings table name
    private static final String TABLE_RATINGS = "ratings";

    //Ratings Table columns names
    private static final String RATING_DATE = "rating_date";
    private static final String RATING = "rating";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables, Questions
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Questions Table
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + " ( "
                + QUESTION_ID + " INTEGER PRIMARY KEY, " + QUESTION_TEXT + " TEXT, "
                + ANSWER_1 + " TEXT, " + ANSWER_2 + " TEXT, " + ANSWER_3 + " TEXT " + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // filling Questions Table
        addQuestion(new Question(1, "How stressed are you?", "Very Stressed", "Somewhat Stressed", "Not Stressed"));
        addQuestion(new Question(2, "How have you been eating?", "Unhealthy", "Somewhat Healthy", "Healthy"));
        addQuestion(new Question(3, "How did you sleep last night?", "Poorly", "Okay", "Well"));
        /*
        // Creating Answers Table
        String CREATE_ANSWERS_TABLE = "CREATE TABLE answers ( "
                + "answer_date INTEGER, "
                + "q_id INTEGER, "
                + "response INTEGER,"
                + "PRIMARY KEY (answer_date, q_id),"
                + "FOREIGN KEY (qid) references questions.q_id)";
        db.execSQL(CREATE_ANSWERS_TABLE);

        //creating Ratings Table
        String CREATE_RATINGS_TABLE = "CREATE TABLE ratings ( " +
                "rating_date INTEGER PRIMARY KEY, rating INTEGER)";
        db.execSQL(CREATE_RATINGS_TABLE);
        */
        Log.d("We", "got here");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);

        // Create tables again
        onCreate(db);
    }

    // Create, Read, Update, Delete operations
    private void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_ID, question.question_id);
        values.put(QUESTION_TEXT, question.question_text);
        values.put(ANSWER_1, question.answer1_text);
        values.put(ANSWER_2, question.answer2_text);
        values.put(ANSWER_3, question.answer3_text);

        // Inserting Row
        db.insert(TABLE_QUESTIONS, null, values);
        db.close(); // Closing database connection
    }

    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.question_id = Integer.parseInt(cursor.getString(0));
                question.question_text = cursor.getString(1);
                question.answer1_text = cursor.getString(2);
                question.answer2_text = cursor.getString(3);
                question.answer3_text = cursor.getString(4);
                // Adding contact to list
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return questionList;
    }

    public void addRating(Rating rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RATING_DATE, rating.date);
        values.put(RATING, rating.rating);
        // Inserting Row
        db.insert(TABLE_RATINGS, null, values);
        db.close(); // Closing database connection
    }

    public void addAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ANSWER_DATE, answer.date);
        values.put(RESPONSE, answer.response);
        // Inserting Row
        db.insert(TABLE_ANSWERS, null, values);
        db.close(); // Closing database connection
    }
}