package com.example.mariajosemolina.news1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            getLoaderManager().initLoader(1, null, loaderCallbacks);

        } else {
            Intent intent = new Intent(MainActivity.this, ErrorMessage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("error","Error: No connection");
            intent.putExtra("detail", "Try again, when your device has connection");
            this.startActivity(intent);
        }
    }

    private LoaderManager.LoaderCallbacks<JSONArray> loaderCallbacks = new LoaderManager.LoaderCallbacks<JSONArray>() {
        @Override
        public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
            return new NewsLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
            if (data != null && data.length() != 0) {
                newsAdapter.swapData(data);
            } else {
                Intent intent = new Intent(MainActivity.this, ErrorMessage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("error","Error: Empty or null data");
                intent.putExtra("detail", "There was a problem with the retrieved data, try again.");
                startActivity(intent);
            }
        }
        @Override
        public void onLoaderReset(Loader<JSONArray> loader) {
            newsAdapter.swapData(new JSONArray(new ArrayList<String>()));
        }
    };
}
