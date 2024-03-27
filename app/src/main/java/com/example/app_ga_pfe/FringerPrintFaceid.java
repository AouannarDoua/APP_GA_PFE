package com.example.app_ga_pfe;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class FringerPrintFaceid extends AppCompatActivity {
    private static final int REQUEST_CODE = 101010;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout mMainLayout;
    private Executor executor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fringer_print_faceid);
        mMainLayout = findViewById(R.id.FringerFace);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
       // boolean isFaceIdActivated = sharedPreferences.getBoolean("isFaceIdActivated", false);

        // Utiliser les valeurs récupérées comme nécessaire
        //if (isFaceIdActivated) {
            initializeBiometricPrompt();
        // } else {
            // Si le Face ID n'est pas activé, afficher un message à l'utilisateur
         //   Toast.makeText(this, "Veuillez activer le Face ID ou l'empreinte digitale", Toast.LENGTH_SHORT).show();
       // }

    }

    private void initializeBiometricPrompt() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int authenticators = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;

        switch (biometricManager.canAuthenticate(authenticators)) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Votre appareil ne prend pas en charge l'authentification biométrique", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "L'authentification biométrique n'est pas disponible actuellement", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // L'utilisateur n'a pas enregistré de données biométriques, lui demander de le faire
                showEnrollmentDialog();
                break;
        }

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(FringerPrintFaceid.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Erreur d'authentification : " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Authentification réussie, passer à la page suivante
                goToNextPage();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentification échouée", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentification biométrique pour l'application ABscentEasy")
                .setDescription("")
                .setSubtitle("Utilisez le Face ID ou l'empreinte digitale pour vous connecter")
                .setNegativeButtonText("Utiliser le mot de passe du compte")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void showEnrollmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aucune empreinte digitale ou visage enregistré");
        builder.setMessage("Voulez-vous enregistrer une empreinte digitale ou un visage maintenant ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Rediriger l'utilisateur vers les paramètres pour l'enregistrement des empreintes digitales ou du visage
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si l'utilisateur choisit de ne pas enregistrer, simplement afficher un message et fermer l'activité
                Toast.makeText(getApplicationContext(), "Vous pouvez activer le Face ID ou l'empreinte digitale ultérieurement", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    private void derigevers(){
        if(getIntent().getExtras() != null) {
            // Depuis la notification
            String userId = getIntent().getExtras().getString("CodeApogee");
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("students").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Passer à MainActivity (ou votre activité principale) sans animation
                        Intent mainIntent = new Intent(FringerPrintFaceid.this, NotificationST.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(mainIntent);

                        // Terminer l'activité actuelle
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Gérer les erreurs
                }
            });
        }

    }


    private void goToNextPage() {
        // Récupérer les données nécessaires transmises depuis l'activité précédente
        Intent intent = getIntent();
        long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
        int selectedRadioButtonId = intent.getIntExtra("radiobutton_id", -1);
        String fullNameS = getIntent().getStringExtra("FULL_NAMES");
        String filiere = getIntent().getStringExtra("Filiere Selectionnee");
        String selectedRadioButtonText = getIntent().getStringExtra("Semester");
        String apogee = getIntent().getStringExtra("N_Apoogee");

        // Passer à l'activité Emploi_Temps
        Intent emploiTempsIntent = new Intent(FringerPrintFaceid.this, MenuEmploi.class);
        derigevers();
        emploiTempsIntent.putExtra("idFilieres", selectedFiliereId);
        emploiTempsIntent.putExtra("radiobutton_id", selectedRadioButtonId);
        emploiTempsIntent.putExtra("FULL_NAMES", fullNameS);
        emploiTempsIntent.putExtra("Filiere Selectionnee", filiere);
        emploiTempsIntent.putExtra("Semester", selectedRadioButtonText);
        emploiTempsIntent.putExtra("N_Apoogee", apogee);
        startActivity(emploiTempsIntent);
        finish();
    }

}