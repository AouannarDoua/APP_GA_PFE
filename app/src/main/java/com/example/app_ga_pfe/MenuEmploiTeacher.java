package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuEmploiTeacher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_emploi_teacher);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = findViewById(R.id.my_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.my_fragment_container, new EmploiTempsTeacherfragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        sharedPreferences = getSharedPreferences("MyPrefs2", Context.MODE_PRIVATE);
        String fullName = sharedPreferences.getString("FULL_NAME", "");
        View headerView = navigationView.getHeaderView(0);
        ImageView imgPro = headerView.findViewById(R.id.imageView_profile1);
        TextView textViewName = headerView.findViewById(R.id.name1);
        TextView textViewgmail = headerView.findViewById(R.id.gmail11);
        textViewName.setText(fullName);
        // Utiliser une image par défaut pour le profil
        imgPro.setImageResource(R.drawable.img_1);



        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(fullName);


//      Ajoutez un écouteur de valeur à cette référence
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Vérifiez si les données existent
                if (dataSnapshot.exists()) {
                    // Récupérez les valeurs des champs Gmail et imag
                    String gmailFromFirebase = dataSnapshot.child("gmail").getValue(String.class);
                    String imageUriFromFirebase = dataSnapshot.child("profileImageUri").getValue(String.class);
                    textViewgmail.setText(gmailFromFirebase);
                    if(imageUriFromFirebase != null){
                        Uri imgUri = Uri.parse(imageUriFromFirebase);

                    }


                } else {
                    Toast.makeText(MenuEmploiTeacher.this, "No data found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs, si nécessaire
                Toast.makeText(MenuEmploiTeacher.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        imgPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page de profil
                Intent Profile = new Intent(MenuEmploiTeacher.this, profil_teacher.class);
                startActivity(Profile);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Intent searchIntent = new Intent(MenuEmploiTeacher.this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.my_fragment_container, new EmploiTempsTeacherfragment()).commit();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, Attendance_List.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, NotificationST.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, FingerPrintFaceidT.class));
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}