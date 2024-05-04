package com.example.app_ga_pfe;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.Manifest;



public class Profil_Student extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    TextView fullNameSTxt ;
    TextView FiliereS , semester ;
    ImageView profilImg2;
    EditText editTextGmail;
    Button save ;
    StorageReference storageReference;
    private Uri selectedImageUri;
    ProgressDialog progressDialog;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    SharedPreferences sharedPreferences ,  sharedPreferences2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_student);
        fullNameSTxt = findViewById(R.id.fullNameStudent);
        FiliereS = findViewById(R.id.program);
        semester = findViewById(R.id.semester);
        profilImg2=findViewById(R.id.profil_img2);
        save= findViewById(R.id.Save);
        editTextGmail = findViewById(R.id.gmailS);


        sharedPreferences = getSharedPreferences("MyPrefs1", MODE_PRIVATE);
        String fullNameS = sharedPreferences.getString("FULL_NAME", "");
        String filiereSelectionne = sharedPreferences.getString("Filiere", "");
        String selectedSemester = sharedPreferences.getString("SEMESTER", "");
        fullNameSTxt.setText(fullNameS);
        FiliereS.setText(filiereSelectionne);
        semester.setText(selectedSemester);

        String imageUriKey = fullNameS + "_IMAGE_URI";


        String imageUriString = sharedPreferences.getString(imageUriKey, null);
        if (imageUriString != null) {
            selectedImageUri = Uri.parse(imageUriString);
            profilImg2.setImageURI(selectedImageUri);
        }

        // Récupérer le nom de l'utilisateur transmis via l'Intent

        sharedPreferences2 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String full = sharedPreferences2.getString("nameS", "");
        Toast.makeText(Profil_Student.this, "Profile" + full, Toast.LENGTH_SHORT).show();




        profilImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery2();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = editTextGmail.getText().toString().trim();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();

                if (!fullNameS.isEmpty()) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(fullNameS);
                    userRef.child("name").setValue(fullNameS);
                    userRef.child("Gmail").setValue(gmail);
                    userRef.child("imag").setValue(imageUriString);
                    userRef.child("program").setValue(filiereSelectionne);
                    userRef.child("semester").setValue(selectedSemester);
                    if (selectedImageUri != null) {
                        oploadImage(fullNameS);
                    }
                    Toast.makeText(Profil_Student.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Profil_Student.this, "Please enter a valid gmail", Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(Profil_Student.this, MenuEmploi.class);
                startActivity(intent1);
            }
        });
    }

    private void oploadImage(String fullNameS) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading file...");
        progressDialog.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("image/"+fileName);

        storageReference.putFile(selectedImageUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageView imgProfil = findViewById(R.id.profil_img2);
                        imgProfil.setImageURI(null);
                        Toast.makeText(Profil_Student.this,"Successfully uploaded",Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(Profil_Student.this,"Failed to upload",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedImageUri = data.getData();
            profilImg2.setImageURI(selectedImageUri);

            String fullNameS = sharedPreferences.getString("FULL_NAME", "");
            String imageUriKey = fullNameS + "_IMAGE_URI";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(imageUriKey, selectedImageUri.toString());
            editor.apply();
        }
    }

    public void openGallery2() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery2();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void pageconnecter(View view) {
        startActivity(new Intent(Profil_Student.this, MainActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        String gmail = editTextGmail.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GMAIL", gmail);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs1", MODE_PRIVATE);
        String gmail = sharedPreferences.getString("GMAIL", "");
        editTextGmail.setText(gmail);
    }
}