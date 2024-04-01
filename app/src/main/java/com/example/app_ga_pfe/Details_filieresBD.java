package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Details_filieresBD  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "amitdbdf2.db";
    private static final String TABLE_FILIERES = "filiere_details";
    private static final String COLUMN_ID = "id";
    private static final String Title = "title";
    private static final String COLUMN_DETAILS1 = "details1";
    private static final String COLUMN_DETAILS2 = "details2";
    private static final String S1 = "s1";
    private static final String S2 = "s2";
    private static final String S3 = "s3";
    private static final String S4 = "s4";
    public  Details_filieresBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_FILIERES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + Title + " TEXT,"
                + COLUMN_DETAILS1 + " TEXT,"
                + COLUMN_DETAILS2  + " TEXT,"
                + S1 + " TEXT,"
                + S2  + " TEXT,"
                + S3 + " TEXT,"
                + S4 + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILIERES);
        onCreate(db);
    }

    public void addFiliereDetails(int filiereId,String titl, String details1, String details2,String s1,String s2,String s3,String s4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, filiereId);
        values.put(Title, titl);
        values.put(COLUMN_DETAILS1, details1);
        values.put(COLUMN_DETAILS2 , details2);
        values.put(S1, s1);
        values.put(S2, s2);
        values.put(S3, s3);
        values.put(S4, s4);
        db.insert(TABLE_FILIERES, null, values);
        db.close();
    }

    public  Details_filieresClass getFiliereDetailsById(Long filiereId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FILIERES, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(filiereId)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        int columnIndex0= cursor.getColumnIndex(Title);
        int columnIndex1= cursor.getColumnIndex(COLUMN_DETAILS1);
        int columnIndex2 = cursor.getColumnIndex(COLUMN_DETAILS2);
        int columnIndex3= cursor.getColumnIndex(S1);
        int columnIndex4 = cursor.getColumnIndex(S2);
        int columnIndex5= cursor.getColumnIndex(S3);
        int columnIndex6 = cursor.getColumnIndex(S4);

        String title=cursor.getString(columnIndex0);
        String details1 = cursor.getString(columnIndex1);
        String details2 = cursor.getString(columnIndex2);
        String s1 = cursor.getString(columnIndex3);
        String s2 = cursor.getString(columnIndex4);
        String s3 = cursor.getString(columnIndex5);
        String s4 = cursor.getString(columnIndex6);

        cursor.close();
        return new Details_filieresClass(title,details1, details2, s1,s2,s3,s4);
    }

    public List<Details_filieresClass> getAllFiliereDetails() {
        List<Details_filieresClass> filiereDetailsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FILIERES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int columnIndex0= cursor.getColumnIndex(Title);
            int columnIndex1= cursor.getColumnIndex(COLUMN_DETAILS1);
            int columnIndex2 = cursor.getColumnIndex(COLUMN_DETAILS2);
            int columnIndex3= cursor.getColumnIndex(S1);
            int columnIndex4 = cursor.getColumnIndex(S2);
            int columnIndex5= cursor.getColumnIndex(S3);
            int columnIndex6 = cursor.getColumnIndex(S4);

            do {
                String title=cursor.getString(columnIndex0);
                String details1 = cursor.getString(columnIndex1);
                String details2 = cursor.getString(columnIndex2);
                String s1 = cursor.getString(columnIndex3);
                String s2 = cursor.getString(columnIndex4);
                String s3 = cursor.getString(columnIndex5);
                String s4 = cursor.getString(columnIndex6);
                Details_filieresClass filiereDetails = new Details_filieresClass(title,details1, details2, s1,s2,s3,s4);
                filiereDetailsList.add(filiereDetails);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return filiereDetailsList;
    }
}