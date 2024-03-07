package com.example.app_ga_pfe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class profil_teacher extends AppCompatActivity {
    TextView fullNameTxt ;
    private filiereDataHelper dbHelper , profildb;
    private Spinner filiereSpinner;
    private ListView listview ;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView  profilImg ;

    Button Save ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_teacher);
        fullNameTxt = findViewById(R.id.fullName);
        filiereSpinner = findViewById(R.id.filSpinner);
        profilImg = findViewById(R.id.profil_img);
        dbHelper = new filiereDataHelper(this);
        listview = findViewById(R.id.listFil);
        Save = findViewById(R.id.save);
        arrayList = new ArrayList<>();
        adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(adapter);
        profildb = new filiereDataHelper(this);
        String fullName = getIntent().getStringExtra("FULL_NAME");
        fullNameTxt.setText(fullName);
        chargerFilieres();
        profilImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }
    private void chargerFilieres() {
        List<String> filieres = dbHelper.getAllFilieres();

        // Créer un ArrayAdapter pour le Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filieres);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filiereSpinner.setAdapter(adapter1);

        // Trouver l'index de "DUT - Finance,Comptabilité et Fiscalité (FCF)" dans la liste des filières
        int index = filieres.indexOf("DUT - Finance,Comptabilité et Fiscalité (FCF)");

        // Définir cet index comme l'élément sélectionné par défaut dans le Spinner
        filiereSpinner.setSelection(index);


        // Gérer la sélection d'une filière dans le Spinner
        filiereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filiereSelectionnee = parent.getItemAtPosition(position).toString();
                Toast.makeText(profil_teacher.this, "Filière sélectionnée : " + filiereSelectionnee, Toast.LENGTH_SHORT).show();
                arrayList.add(filiereSelectionnee);
                //for updating data
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
    public void pageconnecter(View view) {
        startActivity(new Intent(profil_teacher.this, MainActivity2.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI and set it to the ImageView
            Uri selectedImage = data.getData();
            profilImg.setImageURI(selectedImage);
        }
    }

}