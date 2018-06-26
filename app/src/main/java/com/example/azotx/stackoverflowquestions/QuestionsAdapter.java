package com.example.azotx.stackoverflowquestions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuestionsAdapter
        extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    String[] data;

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView mQuestionTitleTextView;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            mQuestionTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.question_item, viewGroup, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        questionViewHolder.mQuestionTitleTextView.setText(data[i]);
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (data == null ? 0 : data.length);
    }
}
