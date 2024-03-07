package com.example.app_ga_pfe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Profildb extends SQLiteOpenHelper {
    private static final String NOM_BASE_DE_DONNEES = "profildb.db";
    private static final int VERSION_BASE_DE_DONNEES = 1;
    private static final String TABLE_NAME = "teacher_profiles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_LEADER_OF = "leader_of";
    private static final String COLUMN_GMAIL = "gmail";
    private static final String COLUMN_PROGRAMS_TAUGHT = "programs_taught";

    public Profildb(Context context) {
        super(context, NOM_BASE_DE_DONNEES, null, VERSION_BASE_DE_DONNEES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_LEADER_OF + " TEXT,"
                + COLUMN_GMAIL + " TEXT,"
                + COLUMN_PROGRAMS_TAUGHT + " TEXT)";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettez à jour la structure de la base de données si nécessaire
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Méthode pour enregistrer un profil d'enseignant dans la base de données
    public long saveTeacherProfile(String fullName, String leaderOf, String gmail, String programsTaught) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_LEADER_OF, leaderOf);
        values.put(COLUMN_GMAIL, gmail);
        values.put(COLUMN_PROGRAMS_TAUGHT, programsTaught);
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }
}
