package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class profil_teacher extends AppCompatActivity {
    TextView fullNameTxt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_teacher);
        fullNameTxt = findViewById(R.id.fullName);
        String fullName = getIntent().getStringExtra("FULL_NAME");
        fullNameTxt.setText(fullName);
    }
}