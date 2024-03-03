package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Emploi_Temps extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi_temps);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);

        Intent intent = getIntent();
        long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
        int selectedRadioButtonId = intent.getIntExtra("radiobutton_id", -1);
        // Récupérer les données de la base de données en fonction des sélections de l'utilisateur
        getDataFromDatabase(selectedFiliereId,selectedRadioButtonId);

    }

    private void displayEmploiData(List<EmploiTempsClass> emploiDataList) {
        // Vérifier si la liste est vide
        if (emploiDataList != null && !emploiDataList.isEmpty()) {
            // Obtenir le premier objet EmploiTempsClass de la liste
            EmploiTempsClass emploiData = emploiDataList.get(0);
            // Mettre à jour les TextView avec les données récupérées
            t1.setText(emploiData.getMatiere1());
            t2.setText(emploiData.getMatiere2());
            t3.setText(emploiData.getMatiere3());
            t4.setText(emploiData.getMatiere4());
        }
    }

    private void getDataFromDatabase(long filiereId,int Radio) {
        Emploi_TempsBD emploiTempsBD = new Emploi_TempsBD(this);
        List<EmploiTempsClass> emploiDataList = emploiTempsBD.getEmploiData(filiereId,Radio);
        displayEmploiData(emploiDataList);
    }
}