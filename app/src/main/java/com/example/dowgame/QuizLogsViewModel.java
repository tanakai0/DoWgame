package com.example.dowgame;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuizLogsViewModel extends AndroidViewModel {

    private QuizLogsRepository mRepository;

    private final LiveData<List<QuizLogs>> mAllQuizLogs;

    public QuizLogsViewModel (Application application) {
        super(application);
        mRepository = new QuizLogsRepository(application);
        mAllQuizLogs = mRepository.getAllQuizLogs();
    }

    LiveData<List<QuizLogs>> getAllQuizLogs() { return mAllQuizLogs; }

    public void insert(QuizLogs quiz_log) { mRepository.insert(quiz_log); }

    public void insertWithMaximumLimit(QuizLogs quiz_log, int maximumLimit) {
        mRepository.insert_with_maximum_limit(quiz_log, maximumLimit);
    }
}