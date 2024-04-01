package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DatabaseError;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class NotificationST extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_st);
        recyclerView = findViewById(R.id.notificationRecyclerView);
        notificationAdapter = new NotificationAdapter(this);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // Configurez votre RecyclerView avec un gestionnaire de disposition et l'adaptateur
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notificationAdapter);

        // Appelez une méthode pour charger les notifications depuis Firebase Realtime Database
        loadNotificationsFromFirebase();
    }

    private void loadNotificationsFromFirebase() {
        DatabaseReference notificationsRef = FirebaseDatabase.getInstance().getReference("notifications");
        notificationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notification> notifications = new ArrayList<>();
                String apogee = sharedPreferences.getString("APOGEE_UTILISATEUR", ""); // Récupérer le code Apogee de l'utilisateur
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notification = snapshot.getValue(Notification.class);
                    if (notification != null && notification.getCodeApogee().equals(apogee)) { // Filtrer par code Apogee
                        notifications.add(notification);
                    }
                }
                notificationAdapter.setNotifications(notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs de base de données
            }
        });
    }

}