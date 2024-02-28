package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextNom;
    EditText editTextApogee;
    Button buttonConnecter;
    private databasemain database;
    private filiereDataHelper dbHelper;
    private Spinner filiereSpinner;
    Switch faceIdSwitch;
    Switch fingerprintSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNom = findViewById(R.id.NP);
        editTextApogee = findViewById(R.id.CA);
        buttonConnecter = findViewById(R.id.con);
        filiereSpinner = findViewById(R.id.filiereSpinner);
        faceIdSwitch = findViewById(R.id.faceIdSwitch);
        fingerprintSwitch = findViewById(R.id.fingerprintSwitch);

        database = new databasemain(this);
        dbHelper = new filiereDataHelper(this);

        chargerFilieres(); // Appel à la méthode pour charger les filières

        buttonConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editTextNom.getText().toString().trim();
                String apogee = editTextApogee.getText().toString().trim();

                // Vérifier si les informations saisies par l'utilisateur existent dans la base de données
                boolean utilisateurExiste = database.verificationDonnees(nom, apogee);

                if (utilisateurExiste) {
                    int selectedFilierePosition = filiereSpinner.getSelectedItemPosition();
                    long selectedFiliereId = dbHelper.getFiliereId(selectedFilierePosition);
                    boolean isFaceIdActivated = faceIdSwitch.isChecked();
                    boolean isFingerprintActivated = fingerprintSwitch.isChecked();
                    // Afficher un message de bienvenue
                    Toast.makeText(MainActivity.this, "Bienvenue " + nom, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, FringerPrintFaceid.class);
                    intent.putExtra("idFilieres", selectedFiliereId);
                    intent.putExtra("isFaceIdActivated", isFaceIdActivated);
                    intent.putExtra("isFingerprintActivated", isFingerprintActivated);
                    startActivity(intent);


                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(MainActivity.this, "Nom ou numéro d'apogée incorrect", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    private void chargerFilieres() {
        List<String> filieres = dbHelper.getAllFilieres();

        // Créer un ArrayAdapter pour le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filieres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filiereSpinner.setAdapter(adapter);

        // Gérer la sélection d'une filière dans le Spinner
        filiereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filiereSelectionnee = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Filière sélectionnée : " + filiereSelectionnee, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void pageconnecter(View view) {
        startActivity(new Intent(MainActivity.this, choix_du_profil.class));
    }


}

