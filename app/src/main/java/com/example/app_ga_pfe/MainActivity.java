package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextNom;
    EditText editTextApogee;
    Button buttonConnecter;
    private databasemain database;
    private infoStudentDB infodata;
    private filiereDataHelper dbHelper;
    private Spinner filiereSpinner;
    Switch faceIdSwitch;
    RadioGroup scheduleRadioGroup;
    boolean isFaceIdActivated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNom = findViewById(R.id.NP);
        editTextApogee = findViewById(R.id.CA);
        buttonConnecter = findViewById(R.id.con);
        filiereSpinner = findViewById(R.id.filiereSpinner);
        faceIdSwitch = findViewById(R.id.faceIdSwitch);
        scheduleRadioGroup = findViewById(R.id.scheduleRadioGroup);

        database = new databasemain(this);
        dbHelper = new filiereDataHelper(this);
        infodata = new infoStudentDB(this);

        chargerFilieres(); // Appel à la méthode pour charger les filières




        buttonConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editTextNom.getText().toString().trim();
                String apogee = editTextApogee.getText().toString().trim();

                // Vérifier si les informations saisies par l'utilisateur existent dans la base de données
                boolean utilisateurExiste = database.verificationDonnees(nom, apogee);

                if (utilisateurExiste) {
                    if (validateInputs()) {
                        int selectedFilierePosition = filiereSpinner.getSelectedItemPosition();
                        long selectedFiliereId = dbHelper.getFiliereId(selectedFilierePosition);
                        int selectedRadioButtonId = scheduleRadioGroup.getCheckedRadioButtonId();

                        String filiere = filiereSpinner.getSelectedItem().toString();
                        // Trouver la vue RadioButton correspondante à cet ID
                        View radioButton = scheduleRadioGroup.findViewById(selectedRadioButtonId);
                        String selectedRadioButtonText = "";

                        // Vérifier si le RadioButton est trouvé
                        if (radioButton != null && radioButton instanceof RadioButton) {
                            // Récupérer le texte de la vue RadioButton
                            selectedRadioButtonText = ((RadioButton) radioButton).getText().toString();
                        }



                        infodata.insererDonnees(selectedFilierePosition, selectedRadioButtonId, nom, apogee);
                        // Afficher un message de bienvenue
                        Toast.makeText(MainActivity.this, "Bienvenue " + nom, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FringerPrintFaceid.class);

                        intent.putExtra("idFilieres", selectedFiliereId);
                        intent.putExtra("isFaceIdActivated", isFaceIdActivated);
                        intent.putExtra("radiobutton_id", selectedRadioButtonId);
                        intent.putExtra("FULL_NAMES", nom);
                        intent.putExtra("Filiere Selectionnee", filiere);
                        intent.putExtra("Semester",selectedRadioButtonText);
                        goToProfil();
                        startActivity(intent);


                    }

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

        // Trouver l'index de "DUT - Finance,Comptabilité et Fiscalité (FCF)" dans la liste des filières
        int index = filieres.indexOf("DUT - Finance,Comptabilité et Fiscalité (FCF)");

        // Définir cet index comme l'élément sélectionné par défaut dans le Spinner
        filiereSpinner.setSelection(index);


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
    private boolean validateInputs() {
        isFaceIdActivated = faceIdSwitch.isChecked();
        if (!isFaceIdActivated) {
            Toast.makeText(MainActivity.this, "Veuillez activer Face ID & FingerPrint", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (scheduleRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MainActivity.this, "Veuillez sélectionner une option pour votre année", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void pageconnecter(View view) {
        startActivity(new Intent(MainActivity.this, choix_du_profil.class));
    }
    public void goToProfil() {
        String fullNameStudent = editTextNom.getText().toString();
        String filiere = filiereSpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, Profil_Student.class);
        // Récupérer l'ID du RadioButton sélectionné dans le RadioGroup
        int selectedRadioButtonId = scheduleRadioGroup.getCheckedRadioButtonId();

        // Trouver la vue RadioButton correspondante à cet ID
        View radioButton = scheduleRadioGroup.findViewById(selectedRadioButtonId);

        String selectedRadioButtonText = "";
        // Vérifier si le RadioButton est trouvé
        if (radioButton != null && radioButton instanceof RadioButton) {
            // Récupérer le texte de la vue RadioButton
            selectedRadioButtonText = ((RadioButton) radioButton).getText().toString();
        }
        intent.putExtra("FULL_NAMES", fullNameStudent);
        intent.putExtra("Filiere Selectionnee", filiere);
        intent.putExtra("Semester",selectedRadioButtonText);
        startActivity(intent);

    }



}

