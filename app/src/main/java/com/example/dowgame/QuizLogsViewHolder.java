package com.example.dowgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class QuizLogsViewHolder extends RecyclerView.ViewHolder {
    private final TextView quizLogsLayoutRowNumber;
    private final TextView quizLogsItemQuiz;
    private final TextView quizLogsItemAnswer;
    private final TextView quizLogsItemRightOrNot;
    private final TextView quizLogsItemCreatedOn;

    private QuizLogsViewHolder(View itemView) {
        super(itemView);
        quizLogsLayoutRowNumber = itemView.findViewById(R.id.rowNumber);
        quizLogsItemQuiz = itemView.findViewById(R.id.quiz);
        quizLogsItemAnswer = itemView.findViewById(R.id.answer);
        quizLogsItemRightOrNot = itemView.findViewById(R.id.right_or_not);
        quizLogsItemCreatedOn = itemView.findViewById(R.id.created_on);
    }

    public void bind(String rowNumber, String quiz, String answer, String right_or_not, String created_on) {
        quizLogsLayoutRowNumber.setText(rowNumber);
        quizLogsItemQuiz.setText(quiz);
        quizLogsItemAnswer.setText(answer);
        quizLogsItemRightOrNot.setText(right_or_not);
        quizLogsItemCreatedOn.setText(created_on);
    }

    static QuizLogsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new QuizLogsViewHolder(view);
    }
}
