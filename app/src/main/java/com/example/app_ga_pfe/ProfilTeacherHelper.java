package com.example.app_ga_pfe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfilTeacherHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "profildbTeacher.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PROFILE = "profile";
    public static final String TABLE_LISTVIEW_ITEMS = "listview_items";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_LEADER_OF = "leader_of";
    public static final String COLUMN_GMAIL = "gmail";
    public static final String COLUMN_ITEM = "item";
    // Constructeur
    public ProfilTeacherHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_LEADER_OF + " TEXT,"
                + COLUMN_GMAIL + " TEXT"
                + ")";
        db.execSQL(CREATE_PROFILE_TABLE);

        // Ajoutez cette partie pour créer la table pour les éléments de ListView
        String CREATE_LISTVIEW_TABLE = "CREATE TABLE " + TABLE_LISTVIEW_ITEMS + "("
                + COLUMN_ITEM + " TEXT"
                + ")";
        db.execSQL(CREATE_LISTVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTVIEW_ITEMS); // Ajoutez cette ligne
        onCreate(db);
    }



}
