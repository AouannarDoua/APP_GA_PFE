package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class CHoix_Filiere_Semestre extends AppCompatActivity {
    ListView listView;
    ListFiliereBD listFiliereBD;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_filiere_semestre);
        listView = findViewById(R.id.list_view);
        listFiliereBD = new ListFiliereBD(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Récupérer le numéro d'apogée de l'utilisateur depuis SharedPreferences
        String apogee = sharedPreferences.getString("APOGEE", "");

        if (!apogee.isEmpty()) {
            afficherListeFilieres(apogee);
        } else {
            Toast.makeText(this, "Numéro d'apogée non valide", Toast.LENGTH_SHORT).show();
        }
    }

    private void afficherListeFilieres(String apogee) {
        // Récupérer la liste des filières pour l'utilisateur
        List<String> filieres = listFiliereBD.getFilieresUtilisateur(apogee);

        // Vérifier si des filières ont été trouvées
        if (filieres.isEmpty()) {
            Toast.makeText(this, "Aucune filière trouvée pour cet utilisateur", Toast.LENGTH_SHORT).show();
        } else {
            // Créer un adaptateur pour afficher la liste des filières dans le ListView
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filieres);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                // Récupérer la filière sélectionnée
                String selectedFiliere = filieres.get(position);

                // Enregistrer la filière sélectionnée dans SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SELECTED_FILIERE", selectedFiliere);
                editor.apply();

                // Naviguer vers l'activité Attendance_List
                Intent intent = new Intent(CHoix_Filiere_Semestre.this, Attendance_List.class);
                startActivity(intent);
            });
        }
    }
}