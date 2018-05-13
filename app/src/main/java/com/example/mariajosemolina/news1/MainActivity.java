package com.example.mariajosemolina.news1;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import org.json.JSONArray;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView newsItems;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsItems = findViewById(R.id.newsItems);

        newsAdapter = new NewsAdapter(this);
        newsItems.setAdapter(newsAdapter);
        getLoaderManager().initLoader(1, null, loaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<JSONArray> loaderCallbacks = new LoaderManager.LoaderCallbacks<JSONArray>() {
        @Override
        public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
            return new NewsLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
            newsAdapter.swapData(data);

        }
        @Override
        public void onLoaderReset(Loader<JSONArray> loader) {
            newsAdapter.swapData(new JSONArray(new ArrayList<String>()));
        }
    };
}
