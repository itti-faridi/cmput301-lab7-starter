package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityNameText = findViewById(R.id.text_city_name);
        Button backButton = findViewById(R.id.button_back);

        // get the city name from the intent that started this activity
        Intent intent = getIntent();
        String cityName = intent.getStringExtra(EXTRA_CITY_NAME);

        // set text on screen
        cityNameText.setText(cityName);

        // back button goes back to the list screen
        backButton.setOnClickListener(v -> finish());
    }
}
