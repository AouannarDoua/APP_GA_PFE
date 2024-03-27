package com.example.app_ga_pfe;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.Query;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Attendance_List extends AppCompatActivity {
    private TableLayout tableLayout;
    private Attendance_list_BD attendanceData;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler();
    private Runnable mRunnable;
    private DatabaseReference attendanceRef;
    private List<String> coloredUsers;
    // Ajouter une variable pour suivre le nombre de scans par étudiant
    private Map<String, Integer> scanCounts = new HashMap<>();
    private String currentMonth;
    private boolean isEditing = false;

    private boolean isdelete = false;
    Button saveButton;
    Button deleteButton;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        tableLayout = findViewById(R.id.tableLayout);
        attendanceData = new Attendance_list_BD(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        coloredUsers = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        firebaseRef = FirebaseDatabase.getInstance().getReference("modifications");
        // Récupérer la filière sélectionnée depuis SharedPreferences
        TextView monthTextView = findViewById(R.id.monthTextView);
        String currentMonth = getCurrentMonth();
        monthTextView.setText(currentMonth);
        String selectedFiliere = sharedPreferences.getString("SELECTED_FILIERE", "");
        if (!selectedFiliere.isEmpty()) {
            Toast.makeText(this, "Filière sélectionnée : " + selectedFiliere, Toast.LENGTH_SHORT).show();
            // Récupérer les données de présence depuis la base de données en fonction de la filière choisie par l'utilisateur
            List<Attendance_Class> attendanceList = attendanceData.getAllAttendanceForFiliere(selectedFiliere);
            // Afficher les données de présence dans le tableau
            displayAttendanceData(attendanceList);
            updateStudentColorsFromFirebase();
            AddPresence();
            sendNotification();
            retrieveAndSetLocalDates();
        } else {
            Toast.makeText(this, "Filière non sélectionnée", Toast.LENGTH_SHORT).show();
        }

        saveButton = findViewById(R.id.saveButton);
        saveButton.setBackgroundTintList(getResources().getColorStateList(R.color.app_color));
        saveButton.setTextColor(getResources().getColor(R.color.white));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setBackgroundTintList(getResources().getColorStateList(R.color.app_color));
        deleteButton.setTextColor(getResources().getColor(R.color.white));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRow();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            toggleEditMode();
            return true;
        } else if (id == R.id.action_delete) {
            toggleRowSelection();
            toggleDeleteMode();
            return true;
        } else if (id == R.id.action_day) {
            showDaySelectionDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveChanges() {
        toggleEditMode();
        Toast.makeText(this, "Modifications enregistrées.", Toast.LENGTH_SHORT).show();
    }

    private void toggleEditMode() {
        isEditing = !isEditing;
        saveButton.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void toggleDeleteMode() {
        isdelete = !isdelete;
        deleteButton.setVisibility(isdelete ? View.VISIBLE : View.GONE);
    }

    private void toggleRowSelection() {
        // Activer la sélection de ligne ici
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            if (row != null) {
                toggleRowSelection(row);
            }
        }
    }
    private void toggleRowSelection(final TableRow row) {
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inversez la sélection de ligne et changez la couleur de fond en vert si elle est sélectionnée
                row.setBackgroundColor(row.isSelected() ? Color.TRANSPARENT : Color.GREEN);
                row.setSelected(!row.isSelected());
            }
        });

    }

    private void deleteSelectedRow() {
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            if (row != null && row.isSelected()) {
                TextView nomTextView = (TextView) row.getChildAt(0);
                TextView apogeeTextView = (TextView) row.getChildAt(1);
                String nom = nomTextView.getText().toString();
                String apogee = apogeeTextView.getText().toString();
                deleteRowFromDatabase(nom, apogee);
                tableLayout.removeView(row);
                break;
            }
        }
        toggleDeleteMode();
        Toast.makeText(this, "Ligne supprimée.", Toast.LENGTH_SHORT).show();
    }

    private void deleteRowFromDatabase(String nom, String apogee) {
        SQLiteDatabase db = attendanceData.getWritableDatabase();
        db.delete(Attendance_list_BD.TABLE_NAME, Attendance_list_BD.COLUMN_NOM_PRENOM + " = ? AND " + Attendance_list_BD.COLUMN_APOGEE + " = ?", new String[]{nom, apogee});
        db.close();
    }

    private String getCurrentMonth() {
        // Obtenez le mois actuel en utilisant la classe Calendar
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        // Convertissez le mois de son numéro (0-11) en nom de mois
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        return months[currentMonth];
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

                                    nomTextView.setTextColor(getResources().getColor(R.color.button_color));
                                    apogeeTextView.setTextColor(getResources().getColor(R.color.button_color));

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



    // Méthode pour ajouter la présence dans les colonnes des jours correspondants
    private void AddPresence() {
        attendanceRef = FirebaseDatabase.getInstance().getReference("attendance");
        attendanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot studentSnapshot : snapshot.getChildren()) {
                    String nom = studentSnapshot.child("nom").getValue(String.class);
                    String apogee = studentSnapshot.child("apogee").getValue(String.class);
                    String status = studentSnapshot.child("status").getValue(String.class);

                    // Obtenir le nombre de scans pour cet étudiant
                    int scanCount = scanCounts.containsKey(nom + apogee) ? scanCounts.get(nom + apogee) : 0;


                    for (int i = 1; i < tableLayout.getChildCount(); i++) {
                        TableRow row = (TableRow) tableLayout.getChildAt(i);
                        if (row != null && row.getChildCount() >= 2) {
                            TextView nomTextView = (TextView) row.getChildAt(0);
                            TextView apogeeTextView = (TextView) row.getChildAt(1);
                            if (nomTextView != null && apogeeTextView != null) {
                                String nomRow = nomTextView.getText().toString();
                                String apogeeRow = apogeeTextView.getText().toString();
                                if (nomRow.equals(nom) && apogeeRow.equals(apogee)) {
                                    // Trouver la bonne colonne pour stocker la présence "P"
                                    int columnIndex = Math.min(scanCount + 2, row.getChildCount() - 1);
                                    TextView dayTextView = (TextView) row.getChildAt(columnIndex);
                                    if (dayTextView != null) {
                                        dayTextView.setText("P"); // Remplacer "A" par "P" dans la colonne appropriée
                                        // Mettre à jour le nombre de scans pour cet étudiant
                                        scanCounts.put(nom + apogee, scanCount + 1);


                                    }
                                }
                            }
                        }
                    }
                    scanCount++; // Incrémenter le nombre de scans
                    // Mettre à jour la colonne du total de présences
                    updateTotalPresences(nom, apogee, scanCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Toast.makeText(Attendance_List.this, "Failed to read attendance data from Firebase.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Méthode pour mettre à jour le total de présences dans la dernière colonne
    private void updateTotalPresences(String nom, String apogee, int scanCount) {
        DatabaseReference professorRef = FirebaseDatabase.getInstance().getReference("students").child(apogee);
        professorRef.child("totalAttendance").setValue(scanCount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Mettre à jour le nombre total de présences dans la vue de tableau
                        for (int i = 1; i < tableLayout.getChildCount(); i++) {
                            TableRow row = (TableRow) tableLayout.getChildAt(i);
                            if (row != null && row.getChildCount() >= 2) {
                                TextView nomTextView = (TextView) row.getChildAt(0);
                                TextView apogeeTextView = (TextView) row.getChildAt(1);
                                if (nomTextView != null && apogeeTextView != null) {
                                    String nomRow = nomTextView.getText().toString();
                                    String apogeeRow = apogeeTextView.getText().toString();
                                    if (nomRow.equals(nom) && apogeeRow.equals(apogee)) {
                                        // Trouver la colonne pour le total de présences
                                        TextView totalTextView = (TextView) row.getChildAt(row.getChildCount() - 1);
                                        if (totalTextView != null) {
                                            totalTextView.setText(String.valueOf(scanCount));
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Gérer l'échec
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
        TableRow row = new TableRow(this);
        addTextViewToRow(row, attendance.getNomPrenom());
        addTextViewToRow(row, attendance.getApogee());

        for (int i = 0; i < 5; i++) {
            TextView textView = addTextViewToRow(row, "A");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleAttendance((TextView) v);
                }
            });
        }
        addTextViewToRow(row, "0");
        tableLayout.addView(row);
    }
    private void toggleAttendance(TextView textView) {
        if (isEditing) {
            try {
                String currentAttendance = textView.getText().toString();
                int adjustment = 0; // Ajustement du total des présences

                if (currentAttendance.equals("P")) {
                    // Supprimer les données de l'étudiant de la base de données Firebase
                    deleteAttendanceFromFirebase(textView);
                    textView.setText("A");
                    adjustment = -1; // Réduire le total des présences
                } else if (currentAttendance.equals("A")) {
                    // Ajouter les données de l'étudiant à la base de données Firebase
                    addAttendanceToFirebase(textView);
                    textView.setText("P");
                    adjustment = 1; // Augmenter le total des présences
                }

                // Obtenez le nom et l'apogée de l'étudiant
                TableRow row = (TableRow) textView.getParent();
                TextView nomTextView = (TextView) row.getChildAt(0);
                TextView apogeeTextView = (TextView) row.getChildAt(1);
                String nom = nomTextView.getText().toString();
                String apogee = apogeeTextView.getText().toString();

                // Mettre à jour le nombre total de présences dans la HashMap
                int scanCount = scanCounts.containsKey(nom + apogee) ? scanCounts.get(nom + apogee) : 0;
                scanCounts.put(nom + apogee, scanCount + adjustment);

                updateTotalPresences(nom, apogee, scanCount+adjustment);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Une erreur s'est produite lors de la modification de l'état de présence.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addAttendanceToFirebase(TextView textView) {
        // Récupérer le nom et l'apogée de l'étudiant
        TableRow row = (TableRow) textView.getParent();
        TextView nomTextView = (TextView) row.getChildAt(0);
        TextView apogeeTextView = (TextView) row.getChildAt(1);
        String nom = nomTextView.getText().toString();
        String apogee = apogeeTextView.getText().toString();

        // Construire la référence Firebase pour l'étudiant spécifique
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(apogee);

        // Obtenir le nombre total de présences actuel pour cet étudiant
        studentRef.child("totalAttendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentTotalAttendance = dataSnapshot.getValue(Integer.class);
                int newTotalAttendance = currentTotalAttendance; // Incrémenter le nombre total de présences

                // Mettre à jour le nombre total de présences pour cet étudiant dans Firebase
                studentRef.child("totalAttendance").setValue(newTotalAttendance)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Succès de la mise à jour
                                // Maintenant, ajoutez les données de présence de l'étudiant
                                DatabaseReference attendanceRef = FirebaseDatabase.getInstance().getReference("attendance").push();
                                attendanceRef.child("nom").setValue(nom);
                                attendanceRef.child("apogee").setValue(apogee);
                                attendanceRef.child("status").setValue("P"); // Définir le statut comme "P" (Présent)
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Échec de la mise à jour
                                // Gérer l'erreur
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void deleteAttendanceFromFirebase(TextView textView) {
        // Récupérer le nom et l'apogée de l'étudiant
        TableRow row = (TableRow) textView.getParent();
        TextView nomTextView = (TextView) row.getChildAt(0);
        TextView apogeeTextView = (TextView) row.getChildAt(1);
        String nom = nomTextView.getText().toString();
        String apogee = apogeeTextView.getText().toString();

        // Construire la référence Firebase pour l'étudiant spécifique
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(apogee);

        // Obtenir le nombre total de présences actuel pour cet étudiant
        studentRef.child("totalAttendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentTotalAttendance = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                int newTotalAttendance = currentTotalAttendance; // Décrémenter le nombre total de présences
                newTotalAttendance = Math.max(0, newTotalAttendance); // Assurez-vous que le total des présences ne devient pas négatif

                // Mettre à jour le nombre total de présences pour cet étudiant dans Firebase
                studentRef.child("totalAttendance").setValue(newTotalAttendance)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Succès de la mise à jour
                                // Maintenant, supprimez les données de présence de l'étudiant
                                DatabaseReference attendanceRef = FirebaseDatabase.getInstance().getReference("attendance");
                                attendanceRef.orderByChild("nom").equalTo(nom).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                            String childApogee = childSnapshot.child("apogee").getValue(String.class);
                                            if (childApogee != null && childApogee.equals(apogee)) {
                                                childSnapshot.getRef().removeValue(); // Supprimer les données de l'étudiant de la base de données Firebase
                                                break; // Sortir de la boucle après la suppression
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle errors
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Échec de la mise à jour
                                // Gérer l'erreur
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    // Méthode pour ajouter un TextView à une ligne du tableau
    private TextView addTextViewToRow(TableRow row, String text) {
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

        // Retourne le TextView créé
        return textView;
    }
    private void showDaySelectionDialog() {
        // Liste des jours à afficher dans le Spinner
        final String[] days = {"Jour 1", "Jour 2", "Jour 3", "Jour 4", "Jour 5"};

        // Créer une boîte de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogTheme);
        builder.setTitle("Sélectionner le jour à modifier");

        // Créer un Spinner pour sélectionner le jour
        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Ajouter le Spinner à la boîte de dialogue
        builder.setView(spinner);

        // Définir un bouton personnalisé "OK" avec la couleur app_color
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedDayIndex = spinner.getSelectedItemPosition();
                showDatePickerDialog(selectedDayIndex); // Afficher le DatePickerDialog avec l'index du jour sélectionné
            }
        });

        // Ajouter un bouton "Annuler" avec la couleur app_color
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fermer la boîte de dialogue si l'utilisateur clique sur Annuler
            }
        });

        // Créer la boîte de dialogue
        AlertDialog dialog = builder.create();

        // Afficher la boîte de dialogue
        dialog.show();

        // Personnaliser la couleur des boutons "OK" et "Annuler"
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        positiveButton.setTextColor(getResources().getColor(R.color.app_color));
        negativeButton.setTextColor(getResources().getColor(R.color.app_color));
    }
    // Méthode pour afficher le DatePickerDialog pour choisir la nouvelle date
    private void showDatePickerDialog(final int selectedDayIndex) {
        // Obtenir la date actuelle
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer le DatePickerDialog avec la personnalisation du style
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.CustomDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Mettre à jour le TextView correspondant avec la nouvelle date
                String newDate = dayOfMonth + "/" + (monthOfYear + 1);
                saveDateToFirebaseAndLocal(selectedDayIndex, newDate);  // Mettre à jour le jour sélectionné avec la nouvelle date
            }
        }, year, month, dayOfMonth);
        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // Personnaliser la couleur des boutons OK et Annuler
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.button_color));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.button_color));
            }
        });
        // Afficher le DatePickerDialog
        datePickerDialog.show();
    }
    private void updateSelectedDay(int selectedDayIndex, String newDate) {

        // Trouver le TableRow correspondant au contenu des jours dans le tableau
        TableRow daysRow = (TableRow) ((TableLayout) findViewById(R.id.tableLayout)).getChildAt(1);

        // Vérifier si le TableRow correspondant a été trouvé
        if (daysRow != null) {
            // Trouver le TextView correspondant au jour sélectionné dans le TableRow
            TextView selectedDayTextView = (TextView) daysRow.getChildAt(selectedDayIndex + 2); // +2 pour compenser les deux premières colonnes de Nom & Prénom et N° Apogée
            // Vérifier si le TextView correspondant a été trouvé
            if (selectedDayTextView != null) {
                // Mettre à jour le texte du TextView avec la nouvelle date
                selectedDayTextView.setText(newDate);
            }
        }}
    private void saveDateToFirebaseAndLocal(int selectedDayIndex, String newDate) {
        // Récupérer le nom du jour à partir de l'index
        String[] days = {"Jour 1", "Jour 2", "Jour 3", "Jour 4", "Jour 5"};
        String selectedDay = days[selectedDayIndex];

        // Construire la référence Firebase pour stocker la nouvelle date
        DatabaseReference dayRef = FirebaseDatabase.getInstance().getReference("jour").child(selectedDay);

        // Stocker la nouvelle date dans Firebase
        dayRef.setValue(newDate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Enregistrer la date localement à l'aide de SharedPreferences
                        saveDateToLocal(selectedDay, newDate);

                        Toast.makeText(Attendance_List.this, "Date mise à jour avec succès dans Firebase", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Attendance_List.this, "Échec de la mise à jour de la date dans Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
        updateSelectedDay(selectedDayIndex, newDate);
    }

    private void saveDateToLocal(String selectedDay, String newDate) {
        // Utiliser SharedPreferences pour enregistrer la date localement
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(selectedDay, newDate);
        editor.apply();
    }

    private void retrieveAndSetLocalDates() {
        // Récupérer les dates stockées localement à partir de SharedPreferences
        String[] days = {"Jour 1", "Jour 2", "Jour 3", "Jour 4", "Jour 5"};
        for (int i = 0; i < days.length; i++) {
            String selectedDay = days[i];
            String savedDate = sharedPreferences.getString(selectedDay, "");
            if (!savedDate.isEmpty()) {
                // Trouver le TableRow correspondant dans le tableau
                TableRow daysRow = (TableRow) ((TableLayout) findViewById(R.id.tableLayout)).getChildAt(1);
                if (daysRow != null) {
                    // Trouver le TextView correspondant au jour sélectionné dans le TableRow
                    TextView selectedDayTextView = (TextView) daysRow.getChildAt(i + 2); // +2 pour compenser les deux premières colonnes de Nom & Prénom et N° Apogée
                    if (selectedDayTextView != null) {
                        // Mettre à jour le texte du TextView avec la date récupérée
                        selectedDayTextView.setText(savedDate);
                    }
                }
            }
        }
    }
    public void goBack(View view) {
        startActivity(new Intent(Attendance_List.this, MenuEmploiTeacher.class));
    }

    void sendNotification() {
        // Récupérer tous les étudiants depuis Firebase Realtime Database
        DatabaseReference studentsRef = FirebaseDatabase.getInstance().getReference("students");
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Parcourir tous les étudiants
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    String apogee = studentSnapshot.getKey(); // Obtenir le code Apogée de l'étudiant
                    Integer totalAttendance = studentSnapshot.child("totalAttendance").getValue(Integer.class); // Récupérer le nombre total de présences de l'étudiant
                    if (totalAttendance != null && totalAttendance < 3) {
                        // Si le nombre total de présences est inférieur à 3, envoyer une notification à l'étudiant
                        sendNotificationToStudent(apogee);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérer les erreurs de base de données
            }
        });
    }

    void sendNotificationToStudent(String apogee) {
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(apogee);
        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fcmToken = dataSnapshot.child("FCMToken").getValue(String.class);

                if (fcmToken != null) {
                    Log.d(TAG, "FCM Token retrieved for student with code Apogee " + apogee + ": " + fcmToken);
                            try {
                                JSONObject jsonObject = new JSONObject();

                                JSONObject notificationObj = new JSONObject();
                                notificationObj.put("title", "Warning:");
                                notificationObj.put("body", "Your total absence is more than 3 !");

                                JSONObject dataObj = new JSONObject();
                                dataObj.put("CodeApogee", apogee);

                                jsonObject.put("notification", notificationObj);
                                jsonObject.put("data", dataObj);
                                jsonObject.put("to",  fcmToken);
                                callApi(jsonObject);
                        saveNotificationToFirebase(new Notification("Warning:", "Justify Your Absence", apogee));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "FCM Token not found for student with code Apogee " + apogee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }
        });
    }
    void saveNotificationToFirebase(Notification notification) {
        DatabaseReference notificationsRef = FirebaseDatabase.getInstance().getReference("notifications");
        String notificationId = notificationsRef.push().getKey();
        notificationsRef.child(notificationId).setValue(notification)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Notification enregistrée avec succès dans Firebase Realtime Database
                        // Mettre à jour le compteur de notifications pour cet étudiant
                        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(notification.getCodeApogee());
                        studentRef.child("notificationCount").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Integer count = dataSnapshot.getValue(Integer.class);
                                if (count == null) {
                                    count = 0;
                                }
                                count++; // Incrémenter le compteur
                                studentRef.child("notificationCount").setValue(count);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle database errors
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Échec de l'enregistrement de la notification dans Firebase Realtime Database
                    }
                });
    }
    void callApi(JSONObject jsonObject){
       MediaType JSON = MediaType.get("application/json");
       OkHttpClient client = new OkHttpClient();
       String url = "https://fcm.googleapis.com/fcm/send";
        RequestBody body = RequestBody.create(jsonObject.toString(),JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization","Bearer AAAAlOdASCo:APA91bE-yG2kc6b4eZBKznkQrLdGs7TRMfKLlAJY4vgENLL57YQn6348uk5fhVtbQD1yFQDgBRN2PSHZmryrlQKWAjfkMq7ug1AIeBzRXnzjul4X9dvBGWRmWxsdVz_5YGvE-P4G4CF7")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

}