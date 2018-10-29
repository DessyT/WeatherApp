package com.example.andrew.uniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Assign button
        Button next = findViewById(R.id.btnEnter);
        //Add button listener
        findViewById(R.id.btnEnter).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Open main activity when button is pressed
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
    }
}
