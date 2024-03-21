package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Attendance_List extends AppCompatActivity {
    private TableLayout tableLayout;
    private Attendance_list_BD attendanceData;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler();
    private Runnable mRunnable;
    private List<String> coloredUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        tableLayout = findViewById(R.id.tableLayout);
        attendanceData = new Attendance_list_BD(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        coloredUsers = new ArrayList<>();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                // Mettre à jour les couleurs des étudiants
                updateStudentColorsFromFirebase();
                // Répéter cette tâche toutes les 2 secondes
                handler.postDelayed(this, 1000);
            }
        };

        // Démarrer la mise à jour périodique
        handler.postDelayed(mRunnable, 1000);
        // Récupérer la filière sélectionnée depuis SharedPreferences
        String selectedFiliere = sharedPreferences.getString("SELECTED_FILIERE", "");


        if (!selectedFiliere.isEmpty()) {
            Toast.makeText(this, "Filière sélectionnée : " + selectedFiliere, Toast.LENGTH_SHORT).show();
            // Récupérer les données de présence depuis la base de données en fonction de la filière choisie par l'utilisateur
            List<Attendance_Class> attendanceList = attendanceData.getAllAttendanceForFiliere(selectedFiliere);
            // Afficher les données de présence dans le tableau
            displayAttendanceData(attendanceList);
        } else {
            Toast.makeText(this, "Filière non sélectionnée", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour mettre à jour les couleurs des étudiants dans la liste
    private void updateStudentColorsFromFirebase() {
        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");

        // Read data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                    String nomFirebase = studentSnapshot.getKey();
                    String apogeeFirebase = studentSnapshot.child("CodeApogee").getValue(String.class);

                    // Compare the Firebase data with the names in the list
                    for (int i = 1; i < tableLayout.getChildCount(); i++) {
                        TableRow row = (TableRow) tableLayout.getChildAt(i);
                        if (row != null && row.getChildCount() >= 2) {
                            TextView nomTextView = (TextView) row.getChildAt(0);
                            TextView apogeeTextView = (TextView) row.getChildAt(1);
                            if (nomTextView != null && apogeeTextView != null) {
                                String nomRow = nomTextView.getText().toString();
                                String apogeeRow = apogeeTextView.getText().toString();
                                if (nomRow.equals(nomFirebase) && apogeeRow.equals(apogeeFirebase)) {
                                    // Change the color of the text
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            nomTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                                            apogeeTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }




    private void displayAttendanceData(List<Attendance_Class> attendanceList) {
        // Ajouter les données de présence à chaque ligne du tableau
        for (Attendance_Class attendance : attendanceList) {
            addNewRow(attendance);
        }
    }

    // Méthode pour ajouter une nouvelle ligne au tableau
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

    // Méthode pour ajouter un TextView à une ligne du tableau
    private void addTextViewToRow(TableRow row, String text) {
        // Créer un nouveau TextView et l'ajouter à la ligne
        TextView textView = new TextView(this);
        textView.setText(text);

        // Définir la mise en forme et la mise en page des TextView au besoin
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(10, 10, 10, 10); // Marges pour l'espacement entre les colonnes
        textView.setLayoutParams(layoutParams);

        // Ajouter le TextView à la ligne
        row.addView(textView);
    }

}