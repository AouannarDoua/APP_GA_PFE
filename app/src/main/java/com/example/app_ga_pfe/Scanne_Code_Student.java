package com.example.app_ga_pfe;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.google.firebase.database.ValueEventListener;


public class Scanne_Code_Student extends AppCompatActivity  {
    Button btn_scan;
    private VideoView videoView;
    private DatabaseReference qrCodesRef;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanne_code_student);
        btn_scan = findViewById(R.id.btn_scan);
        // Initialiser la référence à la base de données Firebase
        qrCodesRef = FirebaseDatabase.getInstance().getReference("Les Qr");
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        btn_scan.setOnClickListener(v -> scanCode());
        videoView =findViewById(R.id.video);
        // Chemin vers la vidéo dans le répertoire res/raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.scanback;
        // Convertir le chemin en URI
        Uri uri = Uri.parse(videoPath);
        // Définir l'URI de la vidéo pour la VideoView
        videoView.setVideoURI(uri);
        // Lancer la lecture de la vidéo en boucle
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

    }


    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume Up To Flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String scannedText = result.getContents();

            // Vérifie si le code QR scanné correspond à un code QR généré précédemment
            checkScannedCode(scannedText);
        }
    });

    private void checkScannedCode(String scannedText) {
        String nom = sharedPreferences.getString("NOM_UTILISATEUR", "");
        String apogee = sharedPreferences.getString("APOGEE_UTILISATEUR", "");
        qrCodesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String generatedCode = snapshot.getValue(String.class);
                    if (generatedCode != null && generatedCode.equals(scannedText)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    markAttendance(nom , apogee);
                    showResultDialog("Success", "The scanned QR code matches the generated QR code.");
                } else {
                    // Afficher un message d'erreur si le code QR scanné ne correspond à aucun code QR généré
                    showResultDialog("Error", "The scanned QR code does not match any generated QR code.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérer les erreurs de lecture de la base de données Firebase
                Toast.makeText(Scanne_Code_Student.this, "Failed to read QR codes from Firebase.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void markAttendance(String nom, String apogee) {
        DatabaseReference attendanceRef = FirebaseDatabase.getInstance().getReference("attendance");
        String key = attendanceRef.push().getKey(); // generate unique key for each attendance record

        // Save attendance record to Firebase
        attendanceRef.child(key).child("nom").setValue(nom);
        attendanceRef.child(key).child("apogee").setValue(apogee);
        attendanceRef.child(key).child("status").setValue("P"); // Mark as present
    }

    private void showResultDialog(String title, String message) {
        // Affiche un dialogue avec le résultat de la comparaison
        AlertDialog.Builder builder = new AlertDialog.Builder(Scanne_Code_Student.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }
}