package com.example.azotx.stackoverflowquestions;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.azotx.stackoverflowquestions.Helpers.DateHelper;
import com.example.azotx.stackoverflowquestions.Helpers.NetworkHelper;
import com.example.azotx.stackoverflowquestions.Helpers.ParsingHelper;

import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingProgressBar;

    private RecyclerView mQuestionsList;
    private QuestionsAdapter mQuestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mQuestionsList = (RecyclerView) findViewById(R.id.rv_questions_list);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mQuestionsList.setLayoutManager(layoutManager);
        mQuestionsList.setHasFixedSize(true);

        mQuestionsAdapter = new QuestionsAdapter();
        mQuestionsList.setAdapter(mQuestionsAdapter);

        loadQuestions();
    }

    void loadQuestions() {
        showData();
        new FetchQuestionsTask().execute(DateHelper.getRangeStart());
    }

    void showLoading() {
        mErrorMessageTextView.setVisibility(TextView.INVISIBLE);
        mQuestionsList.setVisibility(TextView.INVISIBLE);
        mLoadingProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    void showData() {
        mErrorMessageTextView.setVisibility(TextView.INVISIBLE);
        mQuestionsList.setVisibility(TextView.VISIBLE);
    }

    void showError() {
        mErrorMessageTextView.setVisibility(TextView.VISIBLE);
        mQuestionsList.setVisibility(TextView.INVISIBLE);
    }

    class FetchQuestionsTask extends AsyncTask<Long, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected String[] doInBackground(Long... times) {
            long startTime = times[0];

            URL requestUrl = NetworkHelper.buildUrl(startTime);
            Log.d("xyu", requestUrl.toString());
            try {
                String response = NetworkHelper.getResponseFromHttpUrl(requestUrl);
                Log.d("response size", String.valueOf(response.length()));
                String[] data = ParsingHelper.getQuestionsTitlesFromJson(response);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] data) {
            mLoadingProgressBar.setVisibility(ProgressBar.INVISIBLE);

            if (data != null) {
                showData();
                mQuestionsAdapter.setData(data);
            } else {
                showError();
            }
        }
    }
}
