package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasemain extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "students";
    static final String COLUMN_NAME = "nom";
    static final String COLUMN_APOGEE = "apogee";

    public databasemain(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_APOGEE + " TEXT)";
        db.execSQL(createTableQuery);
        insertData(db, "AOUANNAR DOUA","2218535");
        insertData(db, "ACHARKI MAHDI","2215792");
        insertData(db, "AIT LAHMOUS","2215824");
        insertData(db, "AZOU ANASS","2100176");
        insertData(db, "BELBACHIR WISSAL","2112402");
        insertData(db, "BERRADA IMANE","2215796");
        insertData(db, "CHAHLAL AYOUB","2215830");
        insertData(db, "ELFADLI AHLAM","2215778");
        insertData(db, "ELMOSLIH SALMA","2215853");
        insertData(db, "IDHMAD ANIR","2215858");
        insertData(db, "LEMKADEM FATIMA ZAHRAA","2215790");
        insertData(db, "MAKRANI MOHAMED","2215826");
        insertData(db, "NACHIT TAHA","2215828");
        insertData(db, "SADIK ABDELALI","2215780");
        insertData(db, "ZIANE SOUHAYL","2201446");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(SQLiteDatabase db, String nom, String apogee) {
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_NAME, nom);
        Values.put(COLUMN_APOGEE, apogee);
        long result = db.insert(TABLE_NAME, null, Values);
        return result != -1; // Retourne true si l'insertion a réussi, sinon false
    }

    public boolean verificationDonnees(String nom, String apogee) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_APOGEE + " = ?";
        String[] selectionArgs = {nom, apogee};

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor != null && cursor.moveToFirst();

        // Fermer le curseur et la base de données
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userExists;
    }
}

