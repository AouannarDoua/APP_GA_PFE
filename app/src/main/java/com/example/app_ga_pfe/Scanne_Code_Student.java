package com.example.app_ga_pfe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

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
            String scannedText = result.getContents();
            // Compare the scanned text with the generated text
            if (scannedText.equals(QrGeneration.generatedText)) {
                // If the texts match, show a success message
                showResultDialog("Success", "The scanned QR code matches the generated QR code.");
            } else {
                // If the texts do not match, show an error message
                showResultDialog("Error", "The scanned QR code does not match the generated QR code.");
            }
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