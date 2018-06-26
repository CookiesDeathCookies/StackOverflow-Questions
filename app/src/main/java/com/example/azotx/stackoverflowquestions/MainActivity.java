package com.example.azotx.stackoverflowquestions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azotx.stackoverflowquestions.Helpers.DateHelper;
import com.example.azotx.stackoverflowquestions.Helpers.NetworkHelper;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mErrorMessageTextView;
    ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.pb_loading);

        URL url = NetworkHelper.buildUrl(DateHelper.getRangeStart());
        if (url != null) {
           Log.d("xyu", url.toString());
        }
    }
}
