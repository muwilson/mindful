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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MINDFUL_DB";

    // Questions table name
    private static final String TABLE_QUESTIONS = "QUESTIONS";

    // Questions Table Columns names
    private static final String QUESTION_ID = "QUESTION_ID";
    private static final String QUESTION_TEXT = "QUESTION_TEXT";
    private static final String ANSWER_1 = "ANSWER_1";
    private static final String ANSWER_2 = "ANSWER_2";
    private static final String ANSWER_3 = "ANSWER_3";

    // Answers table name
    private static final String TABLE_ANSWERS = "ANSWERS";

    // Answers Table Columns names
    private static final String ANSWER_DATE = "ANSWER_DATE";
    //private static final String QUESTION_ID = "QUESTION_ID";
    private static final String RESPONSE = "RESPONSE";

    //Ratings table name
    private static final String TABLE_RATINGS = "RATINGS";

    //Ratings Table columns names
    private static final String RATING_DATE = "RATING_DATE";
    private static final String RATING = "RATING";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables, Questions
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Questions Table
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + " ( "
                + QUESTION_ID + " INTEGER PRIMARY KEY, "
                + QUESTION_TEXT + " TEXT, "
                + ANSWER_1 + " TEXT, "
                + ANSWER_2 + " TEXT, "
                + ANSWER_3 + " TEXT )";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        String POPULATE_QUESTIONS_TABLE = "INSERT INTO " + TABLE_QUESTIONS + " VALUES "
                + " (1, 'How stressed are you?', 'Very Stressed', 'Somewhat Stressed', 'Not Stressed'), "
                + " (2, 'How have you been eating?', 'Unhealthy', 'Somewhat Healthy', 'Healthy'), "
                + " (3, 'How did you sleep last night?', 'Poorly', 'Okay', 'Well'), "
                + " (4, 'How did work go today?', 'Poorly', 'Okay', 'Well'), "
                + " (5, 'How much have you exercised today?', 'Not at all', 'A little', 'A lot'), "
                + " (6, 'How many friends and family have you talked to today?', '0-2', '2-10', '10+') ";
        db.execSQL(POPULATE_QUESTIONS_TABLE);

        //Creating Ratings Table
        String CREATE_RATINGS_TABLE = "CREATE TABLE " + TABLE_RATINGS + " ( "
                + RATING_DATE + " INTEGER, "
                + RATING + " INTEGER )";
        db.execSQL(CREATE_RATINGS_TABLE);

        String POPULATE_RATINGS_TABLE = "INSERT INTO " + TABLE_RATINGS + " VALUES "
                + " (1488319200, 4), "
                + " (1488405600, 1), "
                + " (1488492000, 3), "
                + " (1488578400, 2), "
                + " (1488664800, 0), "
                + " (1488751200, 1), "
                + " (1488837600, 1), "
                + " (1488924000, 2)";
        db.execSQL(POPULATE_RATINGS_TABLE);

        //Creating Answers Table
        String CREATE_ANSWERS_TABLE = "CREATE TABLE " + TABLE_ANSWERS + " ( "
                + ANSWER_DATE + " INTEGER, "
                + QUESTION_ID + " INTEGER, "
                + RESPONSE + " INTEGER, "
                + " PRIMARY KEY ( " + ANSWER_DATE + " , " + QUESTION_ID + " ), "
                + " FOREIGN KEY ( " + QUESTION_ID + " ) REFERENCES " + TABLE_QUESTIONS + "(" + QUESTION_ID + " )) ";
        db.execSQL(CREATE_ANSWERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
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

    public void addRating(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RATING_DATE, answer.date);
        values.put(RATING, answer.response);
        // Inserting Row
        db.insert(TABLE_RATINGS, null, values);
        db.close(); // Closing database connection
    }

    public List<Rating> getLastRatings() {
        List<Rating> ratingList = new ArrayList<Rating>();
        // Select All Query
        String selectQuery = " SELECT * FROM (SELECT  * FROM " + TABLE_RATINGS
                + " ORDER BY " + RATING_DATE + " DESC "
                + " LIMIT 10) ORDER BY " + RATING_DATE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Rating rating = new Rating();
                rating.date = Long.parseLong(cursor.getString(0));
                rating.rating = Integer.parseInt(cursor.getString(1));

                ratingList.add(rating);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ratingList;

    }

    public boolean didRateToday() {

        long epochTime = 0;
        String selectQuery = "SELECT " + RATING_DATE + " FROM " + TABLE_RATINGS
                + " ORDER BY " + RATING_DATE + " DESC "
                + " LIMIT 1 ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                epochTime = Long.parseLong(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Date dateOfLast = new Date(epochTime * 1000);
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        return sdf.format(dateOfLast).equals(sdf.format(today));
    }

    public void addAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ANSWER_DATE, answer.date);
        values.put(QUESTION_ID, answer.q_id);
        values.put(RESPONSE, answer.response);
        // Inserting Row
        db.insert(TABLE_ANSWERS, null, values);
        db.close(); // Closing database connection
    }
}