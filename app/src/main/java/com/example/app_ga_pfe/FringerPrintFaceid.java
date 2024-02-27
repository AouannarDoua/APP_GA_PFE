package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FringerPrintFaceid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean isFaceIdActivated = intent.getBooleanExtra("isFaceIdActivated", false);
        boolean isFingerprintActivated = intent.getBooleanExtra("isFingerprintActivated", false);

        // Utiliser les valeurs récupérées comme nécessaire
        if (isFaceIdActivated) {
            // Face ID est activé
            // Mettez en œuvre votre logique ici
        } else {
            // Face ID n'est pas activé
            // Mettez en œuvre votre logique ici
        }

        if (isFingerprintActivated) {
            // Fingerprint est activé
            // Mettez en œuvre votre logique ici
        } else {
            // Fingerprint n'est pas activé
            // Mettez en œuvre votre logique ici
        }
    }
    }
