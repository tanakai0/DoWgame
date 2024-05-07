package com.example.dowgame;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_logs")
public class QuizLogs {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "created_on")
    @NonNull
    private String createdOn;

    @ColumnInfo
    @NonNull
    private String quiz;

    @ColumnInfo
    @NonNull
    private String answer;

    @ColumnInfo(name = "right_or_not")
    private String rightOrNot;

    // room can use incomplete constructor
    public QuizLogs(String createdOn, String quiz, String answer, String rightOrNot) {
        this.createdOn = createdOn;
        this.quiz = quiz;
        this.answer = answer;
        this.rightOrNot = rightOrNot;
    }


    // setter and getter
    public void setCreatedOn(String createdOn) { this.createdOn = createdOn; }
    public String getCreatedOn() { return this.createdOn; }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }
    public String getQuiz(){
        return this.quiz;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setRightOrNot(String rightOrNot) { this.rightOrNot = rightOrNot; }
    public String getRightOrNot() { return this.rightOrNot; }
}
