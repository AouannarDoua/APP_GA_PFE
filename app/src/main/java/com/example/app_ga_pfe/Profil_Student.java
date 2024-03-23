package com.example.app_ga_pfe;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profil_Student extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    TextView fullNameSTxt ;
    TextView FiliereS , semester ;
    ImageView profilImg2;
    EditText editTextGmail;
    Button save ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_student);
        fullNameSTxt = findViewById(R.id.fullNameStudent);
        FiliereS = findViewById(R.id.program);
        semester = findViewById(R.id.semester);
        profilImg2=findViewById(R.id.profil_img2);
        String fullNameS = getIntent().getStringExtra("FULL_NAMES");
        fullNameSTxt.setText(fullNameS);
        String filiereSelectionne = getIntent().getStringExtra("Filiere Selectionnee");
        FiliereS.setText(filiereSelectionne);
        String selectedSemester = getIntent().getStringExtra("Semester");
        semester.setText(selectedSemester);
        editTextGmail=findViewById(R.id.gmailS);
        save= findViewById(R.id.Save);
        String gmail = editTextGmail.getText().toString().trim();
        Intent intent = new Intent(Profil_Student.this, MenuEmploi.class);
        intent.putExtra("Gmail", gmail);
        profilImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery2();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
                Toast.makeText(Profil_Student.this, "Profile  saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
        loadProfile();

    }
    public void openGallery2() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
    public void pageconnecter(View view) {
        startActivity(new Intent(Profil_Student.this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI and set it to the ImageView
            Uri selectedImage = data.getData();
            profilImg2.setImageURI(selectedImage);
        }
    }
    private void saveProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("student_profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gmail", editTextGmail.getText().toString());
        editor.apply();


        // Récupérer les autres informations du profil
        String fullName = fullNameSTxt.getText().toString();
        String filiere = FiliereS.getText().toString();
        String semesterText = semester.getText().toString();


    }
    private void loadProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("student_profile", Context.MODE_PRIVATE);
        String gmail = sharedPreferences.getString("gmail", "");
        editTextGmail.setText(gmail);
    }


}