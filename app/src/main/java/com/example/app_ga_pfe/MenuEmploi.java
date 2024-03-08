package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuEmploi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    ImageView imageViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_emploi);
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAMES");
        String filiere = getIntent().getStringExtra("Filiere Selectionnee");
        String selectedRadioButtonText = getIntent().getStringExtra("Semester");
        String gmail = intent.getStringExtra("Gmail");
        long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
        int selectedRadioButtonId = intent.getIntExtra("radiobutton_id", -1);
        EmploiTempsFragment emploiTempsFragment = new EmploiTempsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idFilieres", selectedFiliereId);
        bundle.putString("Semester", selectedRadioButtonText);
        emploiTempsFragment.setArguments(bundle);

// Ajouter le fragment à la vue
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emploiTempsFragment).commit();



        // Récupérer le NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Récupérer le HeaderView du NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Récupérer le TextView à l'intérieur du HeaderView
        TextView textViewName = headerView.findViewById(R.id.name);
        TextView textViewgmail = headerView.findViewById(R.id.gmail1);
        imageViewProfile = headerView.findViewById(R.id.imageView_profile);imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page de profil
                Intent Profile = new Intent(MenuEmploi.this, profil_teacher.class);
                Profile.putExtra("Filiere Selectionnee", filiere);
                Profile.putExtra("Semester", selectedRadioButtonText);
                Profile.putExtra("FULL_NAMES", fullName);
                startActivity(Profile);
            }
        });

        textViewgmail.setText(gmail);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Récupérer l'élément de recherche du menu
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Configurer un écouteur de recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Action à effectuer lors de la soumission de la recherche
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Action à effectuer lorsque le texte de recherche change
                // Vous pouvez utiliser newText pour filtrer les données
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
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
    public void notification(MenuItem item) {
        startActivity(new Intent(MenuEmploi.this, choix_du_profil.class));
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
