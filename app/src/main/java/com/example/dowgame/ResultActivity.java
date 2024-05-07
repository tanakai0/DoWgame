package com.example.dowgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView result_score, result_time;
    private ListView list_view;
    // private String[] quiz_list = {"quiz_1", "quiz_2", "quiz_3"};
    // private final String[] quiz_choices = {"日", "月", "火", "水", "木", "金", "土"};
    private QuizLogsViewModel mQuizLogsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_score = findViewById(R.id.result_score);
        result_time = findViewById(R.id.result_time);
        int right_answer_count = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);
        int quiz_limit = getIntent().getIntExtra("QUIZ_LIMIT", 0);
        long right_answer_time = getIntent().getLongExtra("RIGHT_ANSWER_TIME", 0);
        result_score.setText(getString(R.string.result_score, right_answer_count, quiz_limit));
        if (right_answer_count > 0) {
            result_time.setText(getString(R.string.result_time, right_answer_time / right_answer_count / 1000.0f));
        }
        else{
            result_time.setText(R.string.result_time_exception);
        }

        RecyclerView recyclerView = findViewById(R.id.quiz_list);
        final QuizLogsListAdapter adapter = new QuizLogsListAdapter(new QuizLogsListAdapter.QuizLogsDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        // https://github.com/googlecodelabs/android-room-with-a-view/issues/150
        mQuizLogsViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(QuizLogsViewModel.class);
        mQuizLogsViewModel.getAllQuizLogs().observe(this, quizLogs -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(quizLogs);
        });
        /*
        // want to use observe in ViewModel
        // quiz_list[i] = [year, month(1~12), day, DoW(0:Sunday, 1:Monday, ..., 6: Saturday)]
        // simple_list_item_1 is an ID of layout file which was already defined.
        list_view = findViewById(R.id.quiz_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,quiz_list);
        list_view.setAdapter(arrayAdapter);
         */
    }

    public void return_home(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // can't use back button in quiz
    @Override
    public void onBackPressed() {
    }
}