package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Techeardb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amitdb3.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "Teachers";
    static final String COLUMN_NAME = "nom";
    static final String COLUMN_CODE = "CodeTeach";

    public  Techeardb(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CODE + " TEXT)";
        db.execSQL(createTableQuery);
        insertData(db, "alami nabil","1234");
        insertData(db, "fissaoui mohamed","5678");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(SQLiteDatabase db, String nom, String Code) {
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_NAME, nom);
        Values.put(COLUMN_CODE, Code);
        long result = db.insert(TABLE_NAME, null, Values);
        return result != -1; // Retourne true si l'insertion a réussi, sinon false
    }

    public boolean verificationDonnees(String nom, String apogee) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_CODE + " = ?";
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

