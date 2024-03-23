package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Random;

public class QrGenerations extends AppCompatActivity {
    Button generate;
    ImageView codeQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generations);
        generate = findViewById(R.id.generate);
        codeQR = findViewById(R.id.codeQr);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCode();
            }
        });
    }

    public void generateCode() {
        String generatedText = generateRandomText();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Les Qr");

        // Check if the code already exists in the database
        databaseReference.child(generatedText).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // If the code does not exist, store the data in Firebase
                    databaseReference.push().setValue(generatedText);
                    Toast.makeText(QrGenerations.this, "Data stored in Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QrGenerations.this, "Code already exists in Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        // Generate QR Code
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(generatedText, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            codeQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRandomText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
