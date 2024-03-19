package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ResourceBundle;

public class Attendance_List extends AppCompatActivity {
    private TableLayout tableLayout;
    private Attendance_list_BD attendanceData;
    SharedPreferences sharedPreferences;
    private Handler handler = new Handler();
    private Handler mHandler = new Handler();
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        tableLayout = findViewById(R.id.tableLayout);
        attendanceData = new Attendance_list_BD(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Récupérer la filière sélectionnée depuis SharedPreferences
        String selectedFiliere = sharedPreferences.getString("SELECTED_FILIERE", "");
        String nomUtilisateur = sharedPreferences.getString("NOM_UTILISATEUR", "");
        String apogeeUtilisateur = sharedPreferences.getString("APOGEE_UTILISATEUR", "");
        boolean isStudent = sharedPreferences.getBoolean("isStudent", false);


        if (!selectedFiliere.isEmpty()) {
            Toast.makeText(this,"le nom d'utilisateur est :" +nomUtilisateur,Toast.LENGTH_SHORT).show();
            // Récupérer les données de présence depuis la base de données en fonction de la filière choisie par l'utilisateur
            List<Attendance_Class> attendanceList = attendanceData.getAllAttendanceForFiliere(selectedFiliere);

            // Afficher les données de présence dans le tableau
            displayAttendanceData(attendanceList);
            compareAndHighlightUser(nomUtilisateur, apogeeUtilisateur, attendanceList);
        } else {
            Toast.makeText(this, "Filière non sélectionnée", Toast.LENGTH_SHORT).show();
        }

    }


    public void compareAndHighlightUser(String nom, String apogee, List<Attendance_Class> attendanceList) {
        // Parcourir les enfants du TableLayout (lignes du tableau)
        for (int i = 1; i < tableLayout.getChildCount(); i++) { // Commencez à l'indice 1 pour sauter la première ligne (en-têtes)
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            if (row != null && row.getChildCount() >= 2) { // Assurez-vous que la ligne contient au moins deux enfants (nom et apogée)
                TextView nomTextView = (TextView) row.getChildAt(0); // Premier enfant : nom
                TextView apogeeTextView = (TextView) row.getChildAt(1); // Deuxième enfant : apogée
                if (nomTextView != null && apogeeTextView != null) {
                    String nomRow = nomTextView.getText().toString();
                    String apogeeRow = apogeeTextView.getText().toString();
                    if (nomRow.equals(nom) && apogeeRow.equals(apogee)) {
                        nomTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        apogeeTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        return; // Sortir de la boucle une fois la ligne trouvée
                    }
                }
            }
        }
    }


    private void displayAttendanceData(List<Attendance_Class> attendanceList) {

        // Ajouter les données de présence à chaque ligne du tableau
        for (Attendance_Class attendance : attendanceList) {
            addNewRow(attendance);
        }
    }

    private void addNewRow(Attendance_Class attendance) {
        // Créer une nouvelle ligne
        TableRow row = new TableRow(this);

        // Ajouter des TextViews avec les données de présence à la ligne
        addTextViewToRow(row, attendance.getNomPrenom());
        addTextViewToRow(row, attendance.getApogee());
        // Ajouter d'autres données de présence au besoin

        // Ajouter la ligne au tableau
        tableLayout.addView(row);
    }

    private void addTextViewToRow(TableRow row, String text) {
        // Créer un nouveau TextView et l'ajouter à la ligne
        TextView textView = new TextView(this);
        textView.setText(text);
        // Définir la mise en forme et la mise en page des TextView au besoin
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10); // Marges pour l'espacement entre les colonnes
        textView.setLayoutParams(layoutParams);
        row.addView(textView);
    }
}