package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText TeacherName;
    EditText TeacherCode;
    Button Connect;
    private Techeardb techeardb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TeacherName = findViewById(R.id.name);
        TeacherCode = findViewById(R.id.codeTeacher);
        Connect = findViewById(R.id.connect);
        techeardb = new Techeardb(this);

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = TeacherName.getText().toString().trim();
                String code = TeacherCode.getText().toString().trim();
                boolean utilisateurExiste = techeardb.verificationDonnees(nom, code);

                if (utilisateurExiste) {
                    Toast.makeText(MainActivity2.this, "Bienvenue " + nom, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, choix_du_profil.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity2.this, "Nom ou numéro d'apogée incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void pageconnecter(View view) {
        startActivity(new Intent(MainActivity2.this, choix_du_profil.class));
    }
}