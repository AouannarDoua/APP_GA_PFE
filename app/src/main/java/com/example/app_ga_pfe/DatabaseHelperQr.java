package com.example.app_ga_pfe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperQr extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amitdb16.db";
    private static final int DATABASE_VERSION = 1;

    String createTableQuery;
    public DatabaseHelperQr(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableQuery = "CREATE TABLE qr_codes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "generated_text TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS qr_codes");
        onCreate(db);
    }
    public String getGeneratedTextFromDatabase(String scannedText) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"generated_text"};
        String selection = "generated_text=?";
        String[] selectionArgs = {scannedText};
        Cursor cursor = db.query("qr_codes", projection, selection, selectionArgs, null, null, null);

        String generatedText = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int COlomn1 = cursor.getColumnIndex("generated_text");
                generatedText = cursor.getString(COlomn1);
            }
            cursor.close();
        }
        db.close();
        return generatedText;
    }

}
