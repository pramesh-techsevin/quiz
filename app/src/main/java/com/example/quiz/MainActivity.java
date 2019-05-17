package com.example.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    Button playgame;
    private TextView textViewhighscore;
    private TextView textViewhighscoretime;
    private int highscore;
    private String timescore;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    public static final String KEY_HIGHSCORE_TIME = "00:00:00";
    private static final int REQUEST_CODE_QUIZ = 1;
    public String TEST = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewhighscore=findViewById(R.id.high_score);
        textViewhighscoretime= findViewById(R.id.high_score_time);
        loadHighScore();


        playgame= (Button) findViewById(R.id.btnplaygame);
        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();

            }
        });
    }

    private void startQuiz(){
        Intent intent = new Intent(MainActivity.this,QuizActivity.class);
        startActivityForResult(intent,REQUEST_CODE_QUIZ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                String timesscore = (String) data.getStringExtra(QuizActivity.EXTRA_SCORE_TIME);
                if (score > highscore) {
                    updateHighscore(score,timesscore);
                }else{
                    updateHighscore(highscore,timesscore);
                }
            }
        }
    }


    public void loadHighScore() {

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        timescore = prefs.getString(KEY_HIGHSCORE_TIME,"0");
        textViewhighscore.setText("Your HighScore : " + highscore);
        textViewhighscoretime.setText("Last Match Time :" + timescore);

    }
    public void updateHighscore(int highscoreNew,String highscoretimeNew){
        highscore=highscoreNew;
        timescore=highscoretimeNew;
        textViewhighscore.setText("Your HighScore :" +highscore);
        textViewhighscoretime.setText("Last Match Time :"+timescore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.putString(KEY_HIGHSCORE_TIME,timescore);
        editor.apply();




    }
}
