package com.example.dowgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    private QuizLogsViewModel mQuizLogsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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
    }

    public void return_home(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}