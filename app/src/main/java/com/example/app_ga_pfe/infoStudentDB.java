package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class infoStudentDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "infoStudent.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "UserData";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FILIERE_ID = "filiere_id";
    private static final String COLUMN_RADIOBUTTON_ID = "radiobutton_id";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_APOGEE = "apogee";

    public infoStudentDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table UserData
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILIERE_ID + " INTEGER, " +
                COLUMN_RADIOBUTTON_ID + " INTEGER, " +
                COLUMN_NOM + " TEXT, " +
                COLUMN_APOGEE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression de la table si elle existe déjà
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Méthode pour insérer les données dans la base de données
    public long insererDonnees(int filiereId, int nomRadioButton, String nom, String apogee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILIERE_ID, filiereId);
        values.put(COLUMN_RADIOBUTTON_ID, nomRadioButton);
        values.put(COLUMN_NOM, nom);
        values.put(COLUMN_APOGEE, apogee);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
}

