package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class filiereDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "filieres.db";
    private static final int DATABASE_VERSION = 1;
    String createTableQuery;
    private Context mContext;
    public filiereDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableQuery = "CREATE TABLE Filieres (" +
                "idFilieres INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT)";
        db.execSQL(createTableQuery);

        ajouterFiliere(db, "DUT - Finance,Comptabilité et Fiscalité (FCF)");
        ajouterFiliere(db, "DUT - Gestion des Banques et Assurances (GBA)");
        ajouterFiliere(db, "DUT - Web Marketing (WM)");
        ajouterFiliere(db, "DUT- ENVIRONNEMENT ET SCIENCES DE L’EAU (ESE)");
        ajouterFiliere(db, "DUT- Développeur d’Applications Web & Mobile (DAWM)");
        ajouterFiliere(db, "DUT - Génie civil (GC)");
        ajouterFiliere(db, "DUT - Intelligence Artificielle et Ingénierie des Données (IAID)");
        ajouterFiliere(db, "");

    }

    public void ajouterFiliere(SQLiteDatabase db, String nomFiliere) {
        ContentValues values = new ContentValues();
        values.put("nom", nomFiliere);
        db.insert("Filieres", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Filieres");
        onCreate(db);
    }
    public List<String> getAllFilieres() {
        List<String> filieres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Filieres", null);

        if (cursor.moveToFirst()) {
            int columnIndex3 = cursor.getColumnIndex("nom");
            do {
                String nomFiliere = cursor.getString(columnIndex3);
                filieres.add(nomFiliere);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return filieres;
    }
    public long getFiliereId(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        long filiereId = -1;

        Cursor cursor = db.rawQuery("SELECT idFilieres FROM Filieres LIMIT 1 OFFSET " + position, null);
        if (cursor.moveToFirst()) {
            int columnIndex2 = cursor.getColumnIndex("idFilieres");
            filiereId = cursor.getLong(columnIndex2);
        }

        cursor.close();
        db.close();
        return filiereId;
    }
}


