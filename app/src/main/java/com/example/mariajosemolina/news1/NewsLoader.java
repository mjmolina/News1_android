package com.example.mariajosemolina.news1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


import android.util.Log;

import java.net.URL;
public class NewsLoader extends AsyncTaskLoader<JSONArray>  {

    // Tag for the log messages
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    Context context;

    public NewsLoader(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public JSONArray loadInBackground() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        JSONArray results = null;

        try {
            URL url = new URL("https://content.guardianapis.com/search?&show-tags=contributor&api-key=6f8ccc6b-3d76-4733-aeea-74e431520ca9");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            StringBuffer buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            final String a = buffer.toString();
            JSONObject parentObject = new JSONObject(a);
            JSONObject response = parentObject.getJSONObject("response");
            results = response.getJSONArray("results");

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with making connection", e);
            // Starting a new activity to display a TextView with the error message
            // Note: I would ratter prefer a Dialog instead of a TextView
            // but this is a requirement on the Rubrick.
            Intent intent = new Intent(this.context, ErrorMessage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.context.startActivity(intent);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {

                Log.e(LOG_TAG, "Problem closing the buffer reader.", e);
            }
        }
        return results;

    }
}
