package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Random;

public class QrGeneration extends AppCompatActivity {
    Button generate ;
    ImageView codeQR , scandes;
    static String generatedText ;
    TextView generatedCodeTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generation);
        generate = findViewById(R.id.generate);
        codeQR =findViewById(R.id.codeQr);
        scandes = findViewById(R.id.design);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scandes.setVisibility(View.VISIBLE);
                generateCode();
            }
        });
    }

    public void generateCode()  {
        generatedText = "AbscenceEase";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            // Encode le contenu du code QR en utilisant le format QR_CODE avec une taille de 300x300 pixels
            BitMatrix bitMatrix = multiFormatWriter.encode(generatedText , BarcodeFormat.QR_CODE ,300 , 300);
            // Crée une instance de BarcodeEncoder pour convertir le BitMatrix en un objet Bitmap
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder() ;
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            codeQR.setImageBitmap(bitmap);
        }catch(WriterException e){
            throw new RuntimeException();
        }

    }

    private String generateRandomText() {
        // Générer un texte aléatoire en utilisant des caractères alphanumériques
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10; // Longueur du texte aléatoire

        for (int i = 0; i < length; i++) {
            // Sélectionner un caractère aléatoire de la chaîne de caractères
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}