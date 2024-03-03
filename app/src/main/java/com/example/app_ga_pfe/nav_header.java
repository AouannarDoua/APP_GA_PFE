package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class nav_header extends AppCompatActivity {
    TextView fullNameSTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_header);
        fullNameSTxt=findViewById(R.id.name);
        String fullNameS = getIntent().getStringExtra("FULL_NAMES");
        fullNameSTxt.setText(fullNameS);
    }
}