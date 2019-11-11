package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quiz.Question.*;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_TABLE = "CREATE TABLE " +

                Question.QuestionsTable.TABLE_NAME + " ( " +
                Question.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Question.QuestionsTable.COLUMN_QUESTION + " TEXT , " +
                Question.QuestionsTable.COLUMN_OPTION1 + " TEXT , " +
                Question.QuestionsTable.COLUMN_OPTION2 + " TEXT , " +
                Question.QuestionsTable.COLUMN_OPTION3 + " TEXT , " +
                Question.QuestionsTable.COLUMN_OPTION4 + " TEXT , " +
                Question.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER " + " ) ";
        db.execSQL(SQL_CREATE_TABLE);
        fillTableData();


        final String SQL_TABLE_TIMESCORE = " CREATE TABLE quiz_lesstime_score (ID INTEGER PRIMARY KEY " +
                " AUTOINCREMENT , TimeOfScore Varchar(15) ) ";
        db.execSQL(SQL_TABLE_TIMESCORE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Question.QuestionsTable.TABLE_NAME);
        onCreate(db);

    }


    public long fillTimeTableData(String Time) {

        // Question t1 = new Question(Time);
        return addTime(Time);

    }

    public void fillTableData() {
        Question q1 = new Question("Find the sum of 111 + 222 + 333", "700", "666", "10", "100", 2);
        addQuestion(q1);
        Question q2 = new Question("Subtract 457 from 832", "375", "57", "376", "970", 1);
        addQuestion(q2);
        Question q3 = new Question("50 times 5 is equal to", "2500", "505", "500", "None of these", 4);
        addQuestion(q3);
        Question q4 = new Question(" 90 ÷ 10", "9", "10", "20", "30", 1);
        addQuestion(q4);
        Question q5 = new Question("Simplify: 26 + 32 - 12", "0", "32", "56", "46", 4);
        addQuestion(q5);
        Question q6 = new Question("Find the product of 72 × 3", " 216", "7230", "106", "372", 1);
        addQuestion(q6);
        Question q7 = new Question("200 – (96 ÷ 4)", "105", "176", "26", "16", 2);
        addQuestion(q7);
        Question q8 = new Question("3 + 6 x (5 + 4) ÷ 3 - 7", "11", "16", "14", "15", 3);
        addQuestion(q8);


    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    private long addTime(String time) {
        ContentValues cv = new ContentValues();
        cv.put("TimeOfScore", time);
        return db.insert("quiz_lesstime_score", null, cv);
    }


    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);


        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getAllTime() {

        ArrayList<Question> timescore = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c1 = db.rawQuery("SELECT TimeOfScore  FROM quiz_lesstime_score Order By TimeOfScore DESC", null);
        if (c1.moveToFirst()) {
            do {
                Question time = new Question();
                time.setTimeScore(c1.getString(c1.getColumnIndex("TimeOfScore")));
                timescore.add(time);
            } while (c1.moveToNext());
        }
        c1.close();
        return timescore;
    }


}
