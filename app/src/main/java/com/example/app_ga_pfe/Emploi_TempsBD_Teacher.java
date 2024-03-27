package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Emploi_TempsBD_Teacher extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amitEmploiT.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "EmploiTempsTeacher";
    private static final String COLUMN_ID = "idEmploiTeacher";
    public static final String COLUMN_Text1 = "Matiere1";
    public static final String COLUMN_Text2 = "Matiere2";
    public static final String COLUMN_Text3 = "Matiere3";
    public static final String COLUMN_Text4 = "Matiere4";
    public static final String COLUMN_Text5 = "Matiere5";
    public static final String COLUMN_Text6 = "Matiere6";
    public static final String COLUMN_Text7 = "Matiere7";
    public static final String COLUMN_Text8 = "Matiere8";
    public static final String COLUMN_Text9 = "Matiere9";
    public static final String COLUMN_Text10 = "Matiere10";
    public static final String COLUMN_Text11 = "Matiere11";
    public static final String COLUMN_Text12 = "Matiere12";
    public static final String COLUMN_Text13 = "Matiere13";
    public static final String COLUMN_Text14 = "Matiere14";
    public static final String COLUMN_Text15 = "Matiere15";
    public static final String COLUMN_Text16 = "Matiere16";
    public static final String COLUMN_Text17 = "Matiere17";
    public static final String COLUMN_Text18 = "Matiere18";
    public static final String COLUMN_Text19 = "Matiere19";
    public static final String COLUMN_Text20 = "Matiere20";
    public static final String COLUMN_Text21 = "Matiere21";
    public static final String COLUMN_Text22 = "Matiere22";
    public static final String COLUMN_Text23 = "Matiere23";
    public static final String COLUMN_Text24 = "Matiere24";
    private static final String COLUMN_FILIERE_ID = "idFilieres";
    private SQLiteDatabase db;

    public  Emploi_TempsBD_Teacher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILIERE_ID + " TEXT, " +
                COLUMN_Text1 + " TEXT, " +
                COLUMN_Text2 + " TEXT, " +
                COLUMN_Text3 + " TEXT, " +
                COLUMN_Text4 + " TEXT, " +
                COLUMN_Text5 + " TEXT, " +
                COLUMN_Text6 + " TEXT, " +
                COLUMN_Text7 + " TEXT, " +
                COLUMN_Text8 + " TEXT, " +
                COLUMN_Text9 + " TEXT, " +
                COLUMN_Text10 + " TEXT, " +
                COLUMN_Text11 + " TEXT, " +
                COLUMN_Text12 + " TEXT, " +
                COLUMN_Text13 + " TEXT, " +
                COLUMN_Text14 + " TEXT, " +
                COLUMN_Text15 + " TEXT, " +
                COLUMN_Text16 + " TEXT, " +
                COLUMN_Text17 + " TEXT, " +
                COLUMN_Text18 + " TEXT, " +
                COLUMN_Text19 + " TEXT, " +
                COLUMN_Text20 + " TEXT, " +
                COLUMN_Text21 + " TEXT, " +
                COLUMN_Text22 + " TEXT, " +
                COLUMN_Text23 + " TEXT, " +
                COLUMN_Text24 + " TEXT );" ;
        db.execSQL(createTableQuery);
        insererEmploiTemps(db, "DAWM S1",  "droit C.S", "droit C.S", "Controle gestion", "activite org", "BD Oracle", "BD Oracle", "SQL*Plus", "SQL*Plus", "JAVA", "JAVA", "Controle gestion", "activite org", "BD Oracle", "BD Oracle", "SQL*Plus", "SQL*Plus", "PFE", "PFE", "", "", "PFE", "PFE", "", "");
        insererEmploiTemps(db, "AI S2",  "RO", "LINUX", "MYSQL", "reseau", "securite", "JAVA", "matiere7", "matiere8", "matiere9", "matiere10", "matiere11", "matiere12", "matiere13", "matiere14", "matiere15", "matiere16", "matiere17", "matiere18", "matiere19", "matiere20", "matiere21", "matiere22", "matiere23", "matiere24");
        insererEmploiTemps(db, "FCF S2", "J2ee", "JAVA", "droit", "securite", "matiere5", "matiere6", "matiere7", "matiere8", "matiere9", "matiere10", "matiere11", "matiere12", "matiere13", "matiere14", "matiere15", "matiere16", "matiere17", "matiere18", "matiere19", "matiere20", "matiere21", "matiere22", "matiere23", "matiere24");
        insererEmploiTemps(db, "DAWM S3", "JA", "2ee", "sece", "s", "matiere5", "matiere6", "matiere7", "matiere8", "matiere9", "matiere10", "matiere11", "matiere12", "matiere13", "matiere14", "matiere15", "matiere16", "matiere17", "matiere18", "matiere19", "matiere20", "matiere21", "matiere22", "matiere23", "matiere24");
        insererEmploiTemps(db, "DAWM S4", "JAV", "Jee", "oit", "s", "matiere5", "matiere6", "matiere7", "matiere8", "matiere9", "matiere10", "matiere11", "matiere12", "matiere13", "matiere14", "matiere15", "matiere16", "matiere17", "matiere18", "matiere19", "matiere20", "matiere21", "matiere22", "matiere23", "matiere24");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }

    private void insererEmploiTemps(SQLiteDatabase db, String filiereId, String matiere1, String matiere2, String matiere3, String matiere4,
                                    String matiere5, String matiere6, String matiere7, String matiere8, String matiere9, String matiere10,
                                    String matiere11, String matiere12, String matiere13, String matiere14, String matiere15, String matiere16,
                                    String matiere17, String matiere18, String matiere19, String matiere20, String matiere21, String matiere22,
                                    String matiere23, String matiere24) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILIERE_ID, filiereId);
        values.put(COLUMN_Text1, matiere1);
        values.put(COLUMN_Text2, matiere2);
        values.put(COLUMN_Text3, matiere3);
        values.put(COLUMN_Text4, matiere4);
        values.put(COLUMN_Text5, matiere5);
        values.put(COLUMN_Text6, matiere6);
        values.put(COLUMN_Text7, matiere7);
        values.put(COLUMN_Text8, matiere8);
        values.put(COLUMN_Text9, matiere9);
        values.put(COLUMN_Text10, matiere10);
        values.put(COLUMN_Text11, matiere11);
        values.put(COLUMN_Text12, matiere12);
        values.put(COLUMN_Text13, matiere13);
        values.put(COLUMN_Text14, matiere14);
        values.put(COLUMN_Text15, matiere15);
        values.put(COLUMN_Text16, matiere16);
        values.put(COLUMN_Text17, matiere17);
        values.put(COLUMN_Text18, matiere18);
        values.put(COLUMN_Text19, matiere19);
        values.put(COLUMN_Text20, matiere20);
        values.put(COLUMN_Text21, matiere21);
        values.put(COLUMN_Text22, matiere22);
        values.put(COLUMN_Text23, matiere23);
        values.put(COLUMN_Text24, matiere24);
        db.insert(TABLE_NAME, null, values);
    }
    public List<EmploiTempsClass> getEmploiData(String filiereId) {
        List<EmploiTempsClass> emploiDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_FILIERE_ID + " = ?",
                new String[]{filiereId}, null, null, null);


        int columnIndex1 = cursor.getColumnIndex(COLUMN_Text1);
        int columnIndex2 = cursor.getColumnIndex(COLUMN_Text2);
        int columnIndex3 = cursor.getColumnIndex(COLUMN_Text3);
        int columnIndex4 = cursor.getColumnIndex(COLUMN_Text4);
        int columnIndex5 = cursor.getColumnIndex(COLUMN_Text5);
        int columnIndex6 = cursor.getColumnIndex(COLUMN_Text6);
        int columnIndex7 = cursor.getColumnIndex(COLUMN_Text7);
        int columnIndex8 = cursor.getColumnIndex(COLUMN_Text8);
        int columnIndex9 = cursor.getColumnIndex(COLUMN_Text9);
        int columnIndex10 = cursor.getColumnIndex(COLUMN_Text10);
        int columnIndex11 = cursor.getColumnIndex(COLUMN_Text11);
        int columnIndex12 = cursor.getColumnIndex(COLUMN_Text12);
        int columnIndex13 = cursor.getColumnIndex(COLUMN_Text13);
        int columnIndex14 = cursor.getColumnIndex(COLUMN_Text14);
        int columnIndex15 = cursor.getColumnIndex(COLUMN_Text15);
        int columnIndex16 = cursor.getColumnIndex(COLUMN_Text16);
        int columnIndex17 = cursor.getColumnIndex(COLUMN_Text17);
        int columnIndex18 = cursor.getColumnIndex(COLUMN_Text18);
        int columnIndex19 = cursor.getColumnIndex(COLUMN_Text19);
        int columnIndex20 = cursor.getColumnIndex(COLUMN_Text20);
        int columnIndex21 = cursor.getColumnIndex(COLUMN_Text21);
        int columnIndex22 = cursor.getColumnIndex(COLUMN_Text22);
        int columnIndex23 = cursor.getColumnIndex(COLUMN_Text23);
        int columnIndex24 = cursor.getColumnIndex(COLUMN_Text24);
        try {
            while (cursor != null && cursor.moveToNext()) {
                String mat1 = cursor.getString(columnIndex1);
                String mat2 = cursor.getString(columnIndex2);
                String mat3 = cursor.getString(columnIndex3);
                String mat4 = cursor.getString(columnIndex4);
                String mat5 = cursor.getString(columnIndex5);
                String mat6 = cursor.getString(columnIndex6);
                String mat7 = cursor.getString(columnIndex7);
                String mat8 = cursor.getString(columnIndex8);
                String mat9 = cursor.getString(columnIndex9);
                String mat10 = cursor.getString(columnIndex10);
                String mat11 = cursor.getString(columnIndex11);
                String mat12 = cursor.getString(columnIndex12);
                String mat13 = cursor.getString(columnIndex13);
                String mat14 = cursor.getString(columnIndex14);
                String mat15 = cursor.getString(columnIndex15);
                String mat16 = cursor.getString(columnIndex16);
                String mat17 = cursor.getString(columnIndex17);
                String mat18 = cursor.getString(columnIndex18);
                String mat19 = cursor.getString(columnIndex19);
                String mat20 = cursor.getString(columnIndex20);
                String mat21 = cursor.getString(columnIndex21);
                String mat22 = cursor.getString(columnIndex22);
                String mat23 = cursor.getString(columnIndex23);
                String mat24 = cursor.getString(columnIndex24);
                EmploiTempsClass emploiData = new EmploiTempsClass(mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9, mat10,
                        mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19, mat20,
                        mat21, mat22, mat23, mat24);
                emploiDataList.add(emploiData);
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