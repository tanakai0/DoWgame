package com.example.dowgame;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class QuizLogsRepository {

    private QuizLogsDao mQuizLogsDao;
    private LiveData<List<QuizLogs>> mAllQuizLogs;


    // Note that in order to unit test the Repository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    QuizLogsRepository(Application application) {
        QuizLogsRoomDatabase db = QuizLogsRoomDatabase.getDatabase(application);
        mQuizLogsDao = db.quizLogsDao();
        mAllQuizLogs = mQuizLogsDao.getDESCOrderedLogs();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<QuizLogs>> getAllQuizLogs() {
        return mAllQuizLogs;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(QuizLogs quiz_log) {
        QuizLogsRoomDatabase.databaseWriteExecutor.execute(() -> {
            mQuizLogsDao.insert(quiz_log);
        });
    }

    void insert_with_maximum_limit(QuizLogs quiz_log, int maximumLimit) {
        QuizLogsRoomDatabase.databaseWriteExecutor.execute(() -> {
            int rowCount = mQuizLogsDao.getRowCount();
            if (rowCount >= maximumLimit){
                mQuizLogsDao.deleteOldData(maximumLimit - 1);
            }
            mQuizLogsDao.insert(quiz_log);
        });
    }

    int getRowCount() {
        return mQuizLogsDao.getRowCount();
    }

}
