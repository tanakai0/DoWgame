package com.example.dowgame;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class QuizLogsListAdapter extends ListAdapter<QuizLogs, QuizLogsViewHolder> {

    public QuizLogsListAdapter(@NonNull DiffUtil.ItemCallback<QuizLogs> diffCallback) {
        super(diffCallback);
    }

    @Override
    public QuizLogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return QuizLogsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(QuizLogsViewHolder holder, int position) {
        QuizLogs current = getItem(position);
        holder.bind(String.valueOf(holder.getLayoutPosition() + 1), current.getQuiz(), current.getAnswer(), current.getRightOrNot(), current.getCreatedOn());
    }
    
    static class QuizLogsDiff extends DiffUtil.ItemCallback<QuizLogs> {

        @Override
        public boolean areItemsTheSame(@NonNull QuizLogs oldItem, @NonNull QuizLogs newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull QuizLogs oldItem, @NonNull QuizLogs newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }

}
