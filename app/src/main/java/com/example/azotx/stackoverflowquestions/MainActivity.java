package com.example.azotx.stackoverflowquestions;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.azotx.stackoverflowquestions.Helpers.DateHelper;
import com.example.azotx.stackoverflowquestions.Helpers.NetworkHelper;
import com.example.azotx.stackoverflowquestions.Helpers.ParsingHelper;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Возможные состояния, в котором находится приложение
    enum stage {
        OK, LOADING, ERROR, NO_INTERNET;
    }

    private static stage mStage = stage.OK;
    private TextView mErrorMessageTextView;
    private TextView mNoInternetMessageTextView;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArray("data", mQuestionsAdapter.getData());
        super.onSaveInstanceState(outState);
    }

    private RecyclerView mQuestionsList;
    private QuestionsAdapter mQuestionsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                mQuestionsAdapter.setData(null);
                loadQuestions();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);
        mNoInternetMessageTextView = (TextView) findViewById(R.id.tv_no_internet_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mQuestionsList = (RecyclerView) findViewById(R.id.rv_questions_list);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mQuestionsList.setLayoutManager(layoutManager);
        mQuestionsList.setHasFixedSize(true);

        mQuestionsAdapter = new QuestionsAdapter();
        mQuestionsList.setAdapter(mQuestionsAdapter);

        if (savedInstanceState != null) {
            switch (mStage) {
                case OK: showData(); break;
                case LOADING: showLoading(); break;
                case ERROR: showError(); break;
                case NO_INTERNET: showNoInternet(); break;
            }

            mQuestionsAdapter.setData(savedInstanceState.getStringArray("data"));
        } else {
            loadQuestions();
        }
    }

    void loadQuestions() {
        showData();
        new FetchQuestionsTask().execute(DateHelper.getRangeStart());
    }

    void showData() {
        mStage = stage.OK;
        mErrorMessageTextView.setVisibility(TextView.INVISIBLE);
        mNoInternetMessageTextView.setVisibility(TextView.INVISIBLE);
        mQuestionsList.setVisibility(TextView.VISIBLE);
    }

    void showLoading() {
        mStage = stage.LOADING;
        mErrorMessageTextView.setVisibility(TextView.INVISIBLE);
        mQuestionsList.setVisibility(TextView.INVISIBLE);
        mNoInternetMessageTextView.setVisibility(TextView.INVISIBLE);
        mLoadingProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    void showError() {
        mStage = stage.ERROR;
        mErrorMessageTextView.setVisibility(TextView.VISIBLE);
        mNoInternetMessageTextView.setVisibility(TextView.INVISIBLE);
        mQuestionsList.setVisibility(TextView.INVISIBLE);
    }

    void showNoInternet() {
        mStage = stage.NO_INTERNET;
        mErrorMessageTextView.setVisibility(TextView.INVISIBLE);
        mNoInternetMessageTextView.setVisibility(TextView.VISIBLE);
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

            try {
                String response = NetworkHelper.getResponseFromHttpUrl(requestUrl);
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
