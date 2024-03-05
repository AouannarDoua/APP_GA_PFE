package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuEmploi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_emploi);
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAMES");

        // Récupérer le NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Récupérer le HeaderView du NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Récupérer le TextView à l'intérieur du HeaderView
        TextView textViewName = headerView.findViewById(R.id.name);

        // Mettre à jour le TextView avec le nom de l'utilisateur
        textViewName.setText(fullName);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            // Si Emploi_Temps est une activité, démarrez-la ici
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmploiTempsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MenuEmploi.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, Profil_Student.class));
        } else if (id == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmploiTempsFragment()).commit();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, profil_teacher.class));
        } else if (id == R.id.nav_logout) {
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
