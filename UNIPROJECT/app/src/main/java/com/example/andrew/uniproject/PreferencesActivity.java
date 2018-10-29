package com.example.andrew.uniproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Display the fragment as the main content
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new AppPreferencesFragment()).commit();
    }
}
