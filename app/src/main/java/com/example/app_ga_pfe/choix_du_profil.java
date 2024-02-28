package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class choix_du_profil extends AppCompatActivity {
    ImageView teacher ;
    ImageView student ;
    ImageView admin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_du_profil);
        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);
        admin=findViewById((R.id.admin));

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choix_du_profil.this , MainActivity2.class);
                startActivity(intent);
            }
        });

        student.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choix_du_profil.this,MainActivity.class);
                startActivity(intent);
            }
        }));

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choix_du_profil.this , MainActivity.class);
                startActivity(intent);
            }
        });


    }
}