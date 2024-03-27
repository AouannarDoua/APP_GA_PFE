package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Attendance_list_BD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "listEtudiant.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Attendance";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM_PRENOM = "NomPrenom";
    public static final String COLUMN_APOGEE = "Apogee";
    private static final String COLUMN_JOUR_1 = "Jour1";
    private static final String COLUMN_JOUR_2 = "Jour2";
    private static final String COLUMN_JOUR_3 = "Jour3";
    private static final String COLUMN_JOUR_4 = "Jour4";
    private static final String COLUMN_JOUR_5 = "Jour5";
    private static final String COLUMN_FILIERE = "Filieres";

    public Attendance_list_BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILIERE + " TEXT, " +
                COLUMN_NOM_PRENOM + " TEXT, " +
                COLUMN_APOGEE + " TEXT, " +
                COLUMN_JOUR_1 + " TEXT DEFAULT '', " +
                COLUMN_JOUR_2 + " TEXT DEFAULT '', " +
                COLUMN_JOUR_3 + " TEXT DEFAULT '', " +
                COLUMN_JOUR_4 + " TEXT DEFAULT '', " +
                COLUMN_JOUR_5 + " TEXT DEFAULT '');";
        db.execSQL(createTableQuery);
        insertData(db, "DAWM S1", "ACHARKI MAHDI", "2215792");
        insertData(db, "DAWM S1", "AIT LAHMOUS", "2215824");
        insertData(db, "DAWM S1", "AKACHAR GHIZLAN", "2201292");
        insertData(db, "DAWM S1", "AKOUAOU MOSTAFA", "2215860");
        insertData(db, "DAWM S1", "AOUANNAR DOUA", "2218535");
        insertData(db, "DAWM S1", "AZOU ANASS", "2100176");
        insertData(db, "DAWM S1", "BARADI YOUSSEF", "2215832");
        insertData(db, "DAWM S1", "BELBACHIR WISSAL", "2112402");
        insertData(db, "DAWM S1", "BELLAI YOUSSEF", "2215796");
        insertData(db, "DAWM S1", "BERRADA IMANE", "2215796");
        insertData(db, "DAWM S1", "BORAK MOHAMED DYAE", "2215841)");
        insertData(db, "DAWM S1", "BOUTOB HASNAE", "2204659");
        insertData(db, "DAWM S1", "CHAFIK WIDAD", "2215856");
        insertData(db, "DAWM S1", "CHAHLAL AYOUB", "2215830");
        insertData(db, "DAWM S1", "CHIRAGE ABDERRAHMAN", "2215776");
        insertData(db, "DAWM S1", "EL ANSSARI MANAR", "2215851");
        insertData(db, "DAWM S1", "EL BOUTAYBI ILYASS", "2100626");
        insertData(db, "DAWM S1", "EL FADLI AHLAM", "2215778");
        insertData(db, "DAWM S1", "EL FANKARI MOHAMED", "2215773");
        insertData(db, "DAWM S1", "EL HAIDOURY RAJAE", "2215810");
        insertData(db, "DAWM S1", "EL MOSLIH SALMA", "2215853");
        insertData(db, "DAWM S1", "EL KAZDARI AMINE", "2215766");
        insertData(db, "DAWM S1", "EZZRIOULI MALAK", "2215829");
        insertData(db, "DAWM S1", "HABOUSSI NAJI", "2215857");
        insertData(db, "DAWM S1", "IDHMAD ANIR", "2215858");
        insertData(db, "DAWM S1", "KAOUKAB ZAKARIARE", "2215772");
        insertData(db, "DAWM S1", "LEMKADEM FATIMA ZAHRAA", "2215790");
        insertData(db, "DAWM S1", "MAKRANI MOHAMED", "2215826");
        insertData(db, "DAWM S1", "NACHIT TAHA", "2215828");
        insertData(db, "DAWM S1", "SADIK ABDELALI", "2215780");
        insertData(db, "DAWM S1", "ZIANE SOUHAYL", "2201446");
        insertData(db, "GBA S1", "ACHARKI MAHDI", "2215792");
        insertData(db, "GBA S1", "AIT LAHMOUS", "2215824");
        insertData(db, "GBA S1", "AKACHAR GHIZLAN", "2201292");
        insertData(db, "GBA S1", "AKOUAOU MOSTAFA", "2215860");
        insertData(db, "GBA S1", "AOUANNAR DOUA", "2218535");

    }

    public void insertData(SQLiteDatabase db, String filiere, String nomPrenom, String apogee) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILIERE, filiere);
        values.put(COLUMN_NOM_PRENOM, nomPrenom);
        values.put(COLUMN_APOGEE, apogee);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Attendance_Class> getAllAttendanceForFiliere(String filiere) {
        List<Attendance_Class> AttendanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_FILIERE + " = ?",
                new String[]{filiere}, null, null, null);

        int columnIndex1 = cursor.getColumnIndex(COLUMN_NOM_PRENOM);
        int columnIndex2 = cursor.getColumnIndex(COLUMN_APOGEE);
        try {
            while (cursor != null && cursor.moveToNext()) {
                String NomPre = cursor.getString(columnIndex1);
                String NApogee = cursor.getString(columnIndex2);
                Attendance_Class Att = new Attendance_Class(NomPre, NApogee);
                AttendanceList.add(Att);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return AttendanceList;
    }
}