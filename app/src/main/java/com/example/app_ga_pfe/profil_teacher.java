package com.example.app_ga_pfe;

import static android.opengl.ETC1.decodeImage;
import static android.opengl.ETC1.encodeImage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class profil_teacher extends AppCompatActivity {
    TextView fullNameTxt ;
    private filiereDataHelper dbHelper  ;
    private ProfilTeacherHelper profilHelper;

    private Spinner filiereSpinner;
    private ListView listview ;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView  profilImg ;
    EditText gmailTxt,leaderOfTxt ;
    Button Save ;
    ListView list ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_teacher);
        fullNameTxt = findViewById(R.id.fullName);
        filiereSpinner = findViewById(R.id.filSpinner);
        profilImg = findViewById(R.id.profil_img);
        dbHelper = new filiereDataHelper(this);
        profilHelper = new ProfilTeacherHelper(this);
        listview = findViewById(R.id.listFil);
        gmailTxt = findViewById(R.id.gmail);
        leaderOfTxt = findViewById(R.id.leaderOf);
        Save = findViewById(R.id.save);
        arrayList = new ArrayList<>();
        adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(adapter);
        String fullName = getIntent().getStringExtra("FULL_NAME");
        fullNameTxt.setText(fullName);
        chargerFilieres();
        profilImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
                saveListViewItems();
                Toast.makeText(profil_teacher.this, "Profile and ListView items saved successfully", Toast.LENGTH_SHORT).show();

            }
        });
        loadProfile();
        loadListViewItems();

    }




    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    private void chargerFilieres() {
        List<String> filieres = dbHelper.getAllFilieres();

        // Créer un ArrayAdapter pour le Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filieres);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filiereSpinner.setAdapter(adapter1);

        // Trouver l'index de "DUT - Finance,Comptabilité et Fiscalité (FCF)" dans la liste des filières
        int index = filieres.indexOf("");

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
    private void saveProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fullName", fullNameTxt.getText().toString());
        editor.putString("leaderOf", leaderOfTxt.getText().toString());
        editor.putString("gmail", gmailTxt.getText().toString());
        // Enregistrer l'image de profil dans les préférences partagées
        BitmapDrawable drawable = (BitmapDrawable) profilImg.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String encodedImage = encodeImage(bitmap);
        editor.putString("profileImage", encodedImage);
        editor.apply();
    }
    private String encodeImage(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void loadProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName", "");
        String leaderOf = sharedPreferences.getString("leaderOf", "");
        String gmail = sharedPreferences.getString("gmail", "");
        fullNameTxt.setText(fullName);
        leaderOfTxt.setText(leaderOf);
        gmailTxt.setText(gmail);

        // Charger et afficher l'image de profil
        String encodedImage = sharedPreferences.getString("profileImage", "");
        if (!encodedImage.isEmpty()) {
            Bitmap bitmap = decodeImage(encodedImage);
            profilImg.setImageBitmap(bitmap);
        }
    }
    private Bitmap decodeImage(String encodedImage) {
        byte[] decodedByteArray = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    private void saveListViewItems() {
        SQLiteDatabase db = profilHelper.getWritableDatabase();
        db.delete(ProfilTeacherHelper.TABLE_LISTVIEW_ITEMS, null, null);
        for (String item : arrayList) {
            ContentValues values = new ContentValues();
            values.put(ProfilTeacherHelper.COLUMN_ITEM, item);
            db.insert(ProfilTeacherHelper.TABLE_LISTVIEW_ITEMS, null, values);
        }
    }
    private void loadListViewItems() {
        SQLiteDatabase db =profilHelper.getReadableDatabase();
        Cursor cursor = db.query(ProfilTeacherHelper.TABLE_LISTVIEW_ITEMS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int itemIndex = cursor.getColumnIndex(ProfilTeacherHelper.COLUMN_ITEM);
            do {
                String item = cursor.getString(itemIndex);
                arrayList.add(item);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
            cursor.close();
        }
    }

}