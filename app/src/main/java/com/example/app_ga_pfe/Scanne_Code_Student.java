package com.example.app_ga_pfe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class Scanne_Code_Student extends AppCompatActivity  {
    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanne_code_student);
        btn_scan=findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->
        {
            scanCode();
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume Up To Flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            // Récupérer le texte scanné depuis SharedPreferences
            String scannedText = result.getContents();
            String generatedText = "AbscenceEase";

            // Compare the scanned text with the generated text
            if (scannedText.equals(generatedText)) {
                // Si les textes correspondent, affichez un message de succès
                showResultDialog("Succès", "Le code QR scanné correspond au code QR généré.");
            } else {
                // Si les textes ne correspondent pas, affichez un message d'erreur
                showResultDialog("Erreur", "Le code QR scanné ne correspond pas au code QR généré.");
            }

            } else {
            Toast.makeText(Scanne_Code_Student.this, "Nom ou numéro d'apogée incorrect", Toast.LENGTH_SHORT).show();


        }

    });


    // Method to show the result dialog
    private void showResultDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Scanne_Code_Student.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

}