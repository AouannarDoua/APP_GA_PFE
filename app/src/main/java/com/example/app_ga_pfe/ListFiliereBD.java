package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListFiliereBD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ListFiliere.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ListFiliere";
    private static final String COLUMN_ID = "idListFilieres";
    private static final String COLUMN_APOGEE = "apogee";
    private static final String COLUMN_FILIERE = "Filieres";

    public ListFiliereBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APOGEE + " TEXT," +
                COLUMN_FILIERE + " TEXT)";
        db.execSQL(createTableQuery);
        ajouterFiliere(1234, "DAWM S1", db);
        ajouterFiliere(1234, "AI S2", db);
        ajouterFiliere(1234, "FCF S2", db);
        ajouterFiliere(1234, "GBA S1", db);
        ajouterFiliere(5678, "FCF S3", db);
        ajouterFiliere(5678, "GBA S1", db);
        ajouterFiliere(5678, "DAWM S1", db);
    }

    public void ajouterFiliere(int userId, String filiere, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_APOGEE, userId);
        values.put(COLUMN_FILIERE, filiere);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<String> getFilieresUtilisateur(String apogee) {
        List<String> filieres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_APOGEE + " = ?",
                new String[]{apogee}, null, null, null);

        int columnIndex1 = cursor.getColumnIndex(COLUMN_FILIERE);
        try {
            while (cursor != null && cursor.moveToNext()) {
                String filiere = cursor.getString(columnIndex1);
                filieres.add(filiere);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return filieres;
    }
}