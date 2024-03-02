package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Emploi_TempsBD  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amitEmploii.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "EmploiTemps";
    private static final String COLUMN_ID = "idEmploi";
    public static final String COLUMN_Text1 = "Matiere1";
    public static final String COLUMN_Text2 = "Matiere2";
    public static final String COLUMN_Text3 = "Matiere3";
    public static final String COLUMN_Text4 = "Matiere4";
    public static final String COLUMN_Text5 = "Matiere5";
    public static final String COLUMN_Text6 = "Matiere6";
    private static final String COLUMN_FILIERE_ID = "idFilieres";
    public static final String radioButton_ID = "id_s";
    private SQLiteDatabase db;

    public Emploi_TempsBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILIERE_ID + " INTEGER, " +
                COLUMN_Text1 + " TEXT, " +
                COLUMN_Text2 + " TEXT, " +
                COLUMN_Text3 + " TEXT, " +
                COLUMN_Text4 + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_FILIERE_ID + ") REFERENCES Filieres(idFilieres));";
        db.execSQL(createTableQuery);

        insererEmploiTemps(db,1, "JAVA", "J2ee", "droit", "sql");
        insererEmploiTemps(db,1, "RO", "LINUX", "doua", "reseau");
        insererEmploiTemps(db,3, "J2ee", "jhh", "droit", "securite");
        insererEmploiTemps(db,1, "JA", "2ee", "sece", "s");
        insererEmploiTemps(db,2, "JAV", "Jee", "oit", "s");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }
    private void insererEmploiTemps(SQLiteDatabase db, long filiereId, String matiere1, String matiere2, String matiere3, String matiere4) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILIERE_ID, filiereId);
        values.put(COLUMN_Text1, matiere1);
        values.put(COLUMN_Text2, matiere2);
        values.put(COLUMN_Text3, matiere3);
        values.put(COLUMN_Text4, matiere4);
        db.insert(TABLE_NAME, null, values);
    }
    public List<EmploiTempsClass> getEmploiData(Long filiereId) {
        List<EmploiTempsClass> emploiDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_FILIERE_ID + " = ?",
                new String[]{String.valueOf(filiereId)}, null, null, null);

        int columnIndex1 = cursor.getColumnIndex("Matiere1");
        int columnIndex2 = cursor.getColumnIndex("Matiere2");
        int columnIndex3 = cursor.getColumnIndex("Matiere3");
        int columnIndex4 = cursor.getColumnIndex("Matiere4");
        try {
            while (cursor != null && cursor.moveToNext()) {
                String mat1 = cursor.getString(columnIndex1);
                String mat2 = cursor.getString(columnIndex2);
                String mat3 = cursor.getString(columnIndex3);
                String mat4 = cursor.getString(columnIndex4);
                EmploiTempsClass question = new EmploiTempsClass(mat1, mat2, mat3, mat4);
                emploiDataList.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return emploiDataList;
    }
}
