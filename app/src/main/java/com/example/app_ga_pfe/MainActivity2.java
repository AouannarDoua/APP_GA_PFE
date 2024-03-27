package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.FractionPrecision;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText TeacherName;
    EditText TeacherCode;
    Button Connect;
    private Techeardb techeardb;
    Switch faceIdSwitch;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TeacherName = findViewById(R.id.name);
        TeacherCode = findViewById(R.id.codeTeacher);
        Connect = findViewById(R.id.connect);
        techeardb = new Techeardb(this);
        faceIdSwitch = findViewById(R.id.faceIdSwitch);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = TeacherName.getText().toString().trim();
                String code = TeacherCode.getText().toString().trim();
                boolean isFaceIdActivated = faceIdSwitch.isChecked();
                boolean utilisateurExiste = techeardb.verificationDonnees(nom, code);
                if (!isFaceIdActivated) {
                    Toast.makeText(MainActivity2.this, "Veuillez activer Face ID & FingerPrint", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (utilisateurExiste) {
                    Toast.makeText(MainActivity2.this, "Bienvenue " + nom, Toast.LENGTH_SHORT).show();
                    // Stocker le numéro d'apogée dans SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("APOGEE", code);
                    editor.apply();
                    Intent intent = new Intent(MainActivity2.this, FingerPrintFaceidT.class);
                    intent.putExtra("isFaceIdActivated", isFaceIdActivated);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity2.this, "Nom ou numéro d'apogée incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void goToProfil() {
        String fullNameTeacher = TeacherName.getText().toString();


        // Enregistrer les données dans SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FULL_NAME", fullNameTeacher);
        editor.apply();

        // Démarrer l'activité Profil_teacher
        Intent intent = new Intent(MainActivity2.this, profil_teacher.class);
        startActivity(intent);

    }



}