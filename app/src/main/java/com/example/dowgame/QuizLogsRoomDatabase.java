package com.example.dowgame;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {QuizLogs.class}, version = 1, exportSchema = false)
public abstract class QuizLogsRoomDatabase extends RoomDatabase {

    // abstract "getter" method for QuizLogsDao
    public abstract QuizLogsDao quizLogsDao();

    // singleton
    private static volatile QuizLogsRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuizLogsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuizLogsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuizLogsRoomDatabase.class, "quiz_logs_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    /*
                    if you want to reset database version (but delete all past version data), then
                    use .fallbackToDestructiveMigration() like
                    Room.databaseBuilder(context.getApplicationContext(),
                            QuizLogsRoomDatabase.class, "quiz_logs_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    https://stackoverflow.com/questions/46516830/room-persistent-library-reset-version-to-1
                     */
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                QuizLogsDao dao = INSTANCE.quizLogsDao();
                dao.deleteAll();

                // If you want to start with more data, just add them.
                /*
                QuizLogs quizLogs = new QuizLogs("test", "test", "test", "test");
                dao.insert(quizLogs);
                */
            });
        }
    };
}
