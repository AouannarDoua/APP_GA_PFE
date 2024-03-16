package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Attendance_List extends AppCompatActivity {
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        tableLayout = findViewById(R.id.tableLayout);

        // Appel à la méthode pour ajouter une nouvelle ligne exemple
        // Cette ligne peut être commentée car nous voulons ajouter les lignes depuis MainActivity
         ajouterNouvelleLigne("John Doe", "123456");
    }

    // Méthode pour ajouter une nouvelle ligne avec nom et numéro Apogée
    public void ajouterNouvelleLigne(String nom, String apogee) {
        // Créer une nouvelle ligne TableRow
        TableRow row = new TableRow(this);

        // Créer les TextView pour le nom et le numéro Apogée
        TextView nomTextView = creerTextView(nom);
        TextView apogeeTextView = creerTextView(apogee);

        // Ajouter les TextView à la ligne TableRow
        row.addView(nomTextView);
        row.addView(apogeeTextView);

        // Ajouter la ligne TableRow à la TableLayout
        tableLayout.addView(row);
    }

    // Méthode pour créer un TextView avec le texte donné
    private TextView creerTextView(String texte) {
        TextView textView = new TextView(this);
        textView.setText(texte);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        return textView;
    }
}