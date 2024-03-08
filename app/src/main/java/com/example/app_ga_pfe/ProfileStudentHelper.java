package com.example.app_ga_pfe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProfileStudentHelper extends SQLiteOpenHelper {
    public ProfileStudentHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

        public static final String DATABASE_NAME = "profil_database";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_PROFILE = "profile";
        public static final String COLUMN_FULL_NAME = "full_name";
        public static final String COLUMN_SEMESTER = "semester";
        public static final String COLUMN_GMAIL = "gmail";
        // Constructor


        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                    + COLUMN_FULL_NAME + " TEXT,"
                    + COLUMN_SEMESTER + " TEXT,"
                    + COLUMN_GMAIL + " TEXT"
                    + ")";
            db.execSQL(CREATE_PROFILE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            onCreate(db);
        }
    }


