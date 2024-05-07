package com.example.dowgame;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuizLogsDao {
    @Query("SELECT * FROM quiz_logs")
    List<QuizLogs> getAllLogs();

    @Insert
    void insert(QuizLogs quiz_log);

    @Query("DELETE FROM quiz_logs")
    void deleteAll();

    @Query("SELECT * FROM quiz_logs ORDER BY created_on DESC")
    LiveData<List<QuizLogs>> getDESCOrderedLogs();

    /*
    @Query("SELECT * FROM quiz_logs ORDER BY createdOn DESC LIMIT :topN")
    LiveData<List<QuizLogs>> getDESCOrderedLogsLimitN(int topN);
     */

    @Query("SELECT COUNT(id) FROM quiz_logs")
    int getRowCount();

    @Query("DELETE from quiz_logs WHERE id IN (SELECT id FROM quiz_logs ORDER BY created_on DESC LIMIT -1 OFFSET :maximumLimit)")
    void deleteOldData(int maximumLimit);
}
