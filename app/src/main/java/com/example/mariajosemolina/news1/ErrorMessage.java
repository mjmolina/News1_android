package com.example.mariajosemolina.news1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ErrorMessage extends AppCompatActivity {
    TextView error, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error);

        Bundle params = getIntent().getExtras();
        if (params != null) {
            String errorMessage = params.getString("error");
            String detailMessage = params.getString("detail");

            error = findViewById(R.id.error);
            detail = findViewById(R.id.tryAgain);

            error.setText(errorMessage);
            detail.setText(detailMessage);
        }


    }

}
