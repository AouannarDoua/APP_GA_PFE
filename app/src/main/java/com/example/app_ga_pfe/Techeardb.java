package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Techeardb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "Teachers";
    private  String COLUMN_NAME = "NomPrenom";
    private  String COLUMN_CODE = "CodeTeach";
    public Techeardb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CODE + " TEXT)";
        db.execSQL(createTableQuery);
        insertData("alami nabil" , "1234");
        insertData("fissaoui mohamed","5678");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String NOM, String CODE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(COLUMN_NAME, NOM);
        Values.put(COLUMN_CODE, CODE);
        long result = db.insert(TABLE_NAME, null, Values);
        db.close(); // Fermez la base de données après l'insertion
        return result != -1;
    }

    public boolean verificationDonnees(String nomPrenom, String codeTeach) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME + " = ? AND " + COLUMN_CODE + " = ?";
        String[] selectionArgs = {nomPrenom, codeTeach};

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
        db.close(); // Fermez la base de données après avoir fini de l'utiliser

        return userExists;
    }}