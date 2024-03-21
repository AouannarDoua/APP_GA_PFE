package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
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
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        chargerFilieres(); // Appel à la méthode pour charger les filières

      //  boolean isFirstLogin = checkFirstLogin();

        // if (isFirstLogin) {
            // Si c'est la première connexion, restez sur la page actuelle et configurez le bouton de connexion
            setupLogin();
       // } else {
            // Sinon, passez à la deuxième activité
          //  goToSecondActivity();
       // }
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
        Intent intent = new Intent(this,MenuEmploi.class);
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
   /*public void goToSecondActivity() {
        // Fonction pour passer à la deuxième activité
        Intent intent = new Intent(this, FringerPrintFaceid.class);
        startActivity(intent);
        finish(); // Terminer l'activité actuelle
    }*/

 /*   private boolean checkFirstLogin() {
        // Vérifier si c'est la première connexion en vérifiant les préférences partagées
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFirstLogin", true);
    }

    private void setFirstLogin() {
        // Mettre à jour les préférences partagées pour indiquer que l'utilisateur s'est connecté une fois
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstLogin", false);
        editor.apply();
    }*/

    private void setupLogin() {
        buttonConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editTextNom.getText().toString().trim();
                String apogee = editTextApogee.getText().toString().trim();


                // Vérifier si les informations saisies par l'utilisateur existent dans la base de données
                boolean utilisateurExiste = database.verificationDonnees(nom, apogee);

                if (utilisateurExiste) {
                    if (validateInputs()) {
                       // setFirstLogin();
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
                        writeDataToFirebase(nom, apogee);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        // Enregistrer les informations de l'utilisateur
                       // editor.putBoolean("isFaceIdActivated", isFaceIdActivated);
                        editor.putString("NOM_UTILISATEUR", nom);
                        editor.putString("APOGEE_UTILISATEUR", apogee);
                        editor.putBoolean("isStudent", true);
                        // Appliquer les modifications
                        editor.apply();

                        infodata.insererDonnees(selectedFilierePosition, selectedRadioButtonId, nom, apogee);
                        // Afficher un message de bienvenue
                        Toast.makeText(MainActivity.this, "Bienvenue " + nom, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,FringerPrintFaceid.class);

                        intent.putExtra("idFilieres", selectedFiliereId);
                        intent.putExtra("isFaceIdActivated", isFaceIdActivated);
                        intent.putExtra("radiobutton_id", selectedRadioButtonId);
                        intent.putExtra("FULL_NAMES", nom);
                        intent.putExtra("Filiere Selectionnee", filiere);
                        intent.putExtra("Semester", selectedRadioButtonText);
                        intent.putExtra("N_Apoogee", apogee);
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

    private void writeDataToFirebase(String nom, String code) {
        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");

        // Check if the student already exists in the database
        databaseReference.child(nom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // If the student does not exist, write the data to Firebase
                    databaseReference.child(nom).child("CodeApogee").setValue(code);
                    Toast.makeText(MainActivity.this, "Données enregistrées dans Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "L'étudiant existe déjà dans Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

}
