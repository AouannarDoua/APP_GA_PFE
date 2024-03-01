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
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

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
        Intent intent = getIntent();
        boolean isFaceIdActivated = intent.getBooleanExtra("isFaceIdActivated", false);

        Toast.makeText(this, "Face ID activé : " + isFaceIdActivated, Toast.LENGTH_SHORT).show();

        // Utiliser les valeurs récupérées comme nécessaire
        if (isFaceIdActivated) {
            Toast.makeText(this, "Face ID est activé", Toast.LENGTH_SHORT).show();
            initializeBiometricPrompt();

        } else {
            // Face ID n'est pas activé
            Toast.makeText(this, "Face ID n'est pas activé", Toast.LENGTH_SHORT).show();
        }
    }
    private void initializeBiometricPrompt() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int authenticators = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;

        switch (biometricManager.canAuthenticate(authenticators)) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device doesn't have FingerPrint", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, authenticators);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(FringerPrintFaceid.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FringerPrintFaceid.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });



        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for app ABscentEasy")
                .setDescription("")
                .setSubtitle("Use Face ID OR ingerPrint To Login")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }




}