package com.example.azotx.stackoverflowquestions.Helpers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.unbescape.html.HtmlEscape;

public final class ParsingHelper {
    static final String ERROR_ID_NAME = "error_id";
    static final String ERROR_MESSAGE_NAME = "error_message";

    static final String QUESTIONS_ARRAY_NAME = "items";
    static final String QUESTION_TITLE_NAME = "title";


    public static String[] getQuestionsTitlesFromJson(String jsonData) throws JSONException {
        String[] parsedData = null;

        JSONObject root = new JSONObject(jsonData);

        if (root.has(ERROR_ID_NAME)) {
            return null;
        }

        JSONArray questions = root.getJSONArray(QUESTIONS_ARRAY_NAME);
        parsedData = new String[questions.length()];

        for (int i = 0; i < questions.length(); ++i) {
            String encodedTitle = questions.getJSONObject(i).getString(QUESTION_TITLE_NAME);
            parsedData[i] = HtmlEscape.unescapeHtml(encodedTitle);
        }

        return parsedData;
    }
}
