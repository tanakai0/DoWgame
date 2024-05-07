package com.example.dowgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.time.LocalDateTime;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class Game extends AppCompatActivity {

    private int databaseMaximumLimit = 100;
    private TextView quiz_number;
    private TextView quiz_text;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;
    private Button answerBtn5;
    private Button answerBtn6;
    private Button answerBtn7;

    private String right_answer;
    private int right_answer_count = 0;
    private int quiz_count = 1;
    private long temp_time = 0;
    private long right_answer_time = 0;
    private int total_quiz_num = 5; // adapt in onCreate (default:5)

    private final String calendar_type = "proleptic";

    private final String[] quiz_choices = {"日", "月", "火", "水", "木", "金", "土"};
    ArrayList<ArrayList<Integer>> quiz_list = new ArrayList<>();
    // quiz_list[i] = [year, month (1~12), day, DoW (0:Sunday, 1:Monday, ..., 6: Saturday)]

    // Sound
    private SoundPlayer soundPlayer;

    // range of date
    private final Calendar start_date = Calendar.getInstance();
    private final Calendar end_date = Calendar.getInstance();
    private int start_year = 1;
    private int start_month = 1;
    private int start_day = 1;
    private int end_year = 10000;
    private int end_month = 12;
    private int end_day = 31;

    private QuizLogsViewModel mQuizLogsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        total_quiz_num = getIntent().getIntExtra("TOTAL_QUIZ_NUM", 5);

        soundPlayer = new SoundPlayer(this);

        quiz_number = findViewById(R.id.quiz_number);
        quiz_text = findViewById(R.id.quiz_text);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);
        answerBtn5 = findViewById(R.id.answerBtn5);
        answerBtn6 = findViewById(R.id.answerBtn6);
        answerBtn7 = findViewById(R.id.answerBtn7);

        answerBtn1.setText(quiz_choices[0]);
        answerBtn2.setText(quiz_choices[1]);
        answerBtn3.setText(quiz_choices[2]);
        answerBtn4.setText(quiz_choices[3]);
        answerBtn5.setText(quiz_choices[4]);
        answerBtn6.setText(quiz_choices[5]);
        answerBtn7.setText(quiz_choices[6]);

        set_date(start_date, start_year, start_month, start_day);
        set_date(end_date, end_year, end_month, end_day);

        // Get a new or existing ViewModel from the ViewModelProvider.
        // https://github.com/googlecodelabs/android-room-with-a-view/issues/150
        mQuizLogsViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(QuizLogsViewModel.class);

        show_next_quiz();

    }

    private void set_date(Calendar date, int year, int month, int day) {
        // create random Calendar between start_date and end_date
        date.set(year, month ,day);
    }

    public void show_next_quiz() {
        // create and show next quiz

        quiz_number.setText(getString(R.string.quiz_number, quiz_count, total_quiz_num));
        boolean is_new = false;
        //Calendar random_date = Calendar.getInstance();
        GregorianCalendar random_date = create_random_date(start_date, end_date, calendar_type);
        int random_date_year = random_date.get(Calendar.YEAR);
        int random_date_month = random_date.get(Calendar.MONTH) + 1;
        int random_date_date = random_date.get(Calendar.DATE);
        int random_date_dow = random_date.get(Calendar.DAY_OF_WEEK) - 1;
        if (quiz_list.size() > 0) {
            while (!is_new) {
                random_date = create_random_date(start_date, end_date, calendar_type);
                random_date_year = random_date.get(Calendar.YEAR);
                random_date_month = random_date.get(Calendar.MONTH) + 1;
                random_date_date = random_date.get(Calendar.DATE);
                random_date_dow = random_date.get(Calendar.DAY_OF_WEEK) - 1;
                for (int i = 0; i < quiz_list.size(); i++) {
                    if ((quiz_list.get(i).get(0) == random_date_year) && (quiz_list.get(i).get(1) == random_date_month)
                            && (quiz_list.get(i).get(2) == random_date_date)) {
                        break;
                    }
                    if (i == quiz_list.size() - 1) {
                        is_new = true;
                    }
                }
            }
        }
        ArrayList<Integer> tmp_array = new ArrayList<>();
        tmp_array.add(random_date_year);
        tmp_array.add(random_date_month);
        tmp_array.add(random_date_date);
        tmp_array.add(random_date_dow);
        quiz_list.add(tmp_array);
        right_answer = quiz_choices[random_date_dow];
        quiz_text.setText(getString(R.string.quiz_text, create_quiz_text(random_date)));
        temp_time = System.currentTimeMillis();
    }

    private GregorianCalendar create_random_date(Calendar start_date, Calendar end_date, String calendar_type) {
        // create random Calendar between start_date and end_date
        // input:
        // start_date : start date of the range
        // end_date : end_date of the range
        // calendar_type : "proleptic" means proleptic Gregorian calendar

        GregorianCalendar random_date = new GregorianCalendar();
        // Calendar random_date = Calendar.getInstance();

        long date_diff = (end_date.getTimeInMillis() - start_date.getTimeInMillis())
                / (1000 * 60 * 60 * 24);

        if (calendar_type == "proleptic"){
            random_date.setGregorianChange(new Date(Long.MIN_VALUE));
        }
        else if (calendar_type == "julian"){
            random_date.setGregorianChange(new Date(Long.MAX_VALUE));
        }
        random_date.clear();
        set_date(random_date, start_year, start_month, start_day);
        random_date.add(random_date.DATE, new Random().nextInt((int)date_diff + 1));

        return random_date;
    }

    public static String create_quiz_text(Calendar date) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return String.format("%d/%d/%d", year, month, day);
        //return sdf.format(date.getTime());
    }

    public static String createTimingToAnswer(Calendar date) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return String.format("%d-%d-%d", year, month, day);
        //return sdf.format(date.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void check_answer(View view) {

        long temp_temp_time = System.currentTimeMillis();
        Button answer_btn = findViewById(view.getId());
        String btn_text = answer_btn.getText().toString();
        boolean right = true; // If the answer is right, boolean right = true.
        String alert_title;

        if (btn_text.equals(right_answer)) {
            right_answer_time = right_answer_time + (temp_temp_time - temp_time);
            right = true;
            alert_title = "〇";
            right_answer_count++;
            soundPlayer.play_right_sound();
            // Toast toast = Toast.makeText(this, alert_title, Toast.LENGTH_SHORT);
            // toast.show();
        } else {
            right = false;
            alert_title = "×";
            soundPlayer.play_wrong_sound();
            // Toast toast = Toast.makeText(this, right_answer, Toast.LENGTH_SHORT);
            // toast.show();
        }

        // save quiz for database
        Calendar TimingToAnswer = Calendar.getInstance();
        QuizLogs quizLogs = new QuizLogs(LocalDateTime.now().toString(), quiz_text.getText().toString(), right_answer, alert_title);
        // mQuizLogsViewModel.insert(quizLogs);
        mQuizLogsViewModel.insertWithMaximumLimit(quizLogs, databaseMaximumLimit);
        /*
        // make dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // 結果画面へ移動
                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
         */

        if (quiz_count == total_quiz_num) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("RIGHT_ANSWER_COUNT", right_answer_count);
            intent.putExtra("RIGHT_ANSWER_TIME", right_answer_time);
            intent.putExtra("QUIZ_LIMIT", total_quiz_num);
            startActivity(intent);
        } else {
            quiz_count++;
            show_next_quiz();
        }

    }

    public void return_home(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // can't use back button in quiz
    @Override
    public void onBackPressed() {
    }

}
