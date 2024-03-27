package com.example.app_ga_pfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MenuEmploi extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ImageView imageViewProfile;


    ListView list ;
    String[] profiles ={"Lemkadem Fatima Zahraa" , "Aouannar Doua"};
    ArrayAdapter<String> arrayAdapter ;
    SearchView searchView ;
    private int notificationCount = 0; // Variable pour suivre le nombre de notifications
    private MenuItem notificationItem; // Référence à l'élément de menu de notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_emploi);

        // Initialisation de votre helper de base de données

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAMES");
        String filiere = getIntent().getStringExtra("Filiere Selectionnee");
        String selectedRadioButtonText = getIntent().getStringExtra("Semester");
        String gmail = intent.getStringExtra("Gmail");
        String apogee1 = getIntent().getStringExtra("N_Apoogee");
        long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
        EmploiTempsFragment emploiTempsFragment = new EmploiTempsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idFilieres", selectedFiliereId);
        bundle.putString("Semester", selectedRadioButtonText);
        emploiTempsFragment.setArguments(bundle);
        list = findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profiles);
        list.setAdapter(arrayAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String apogee = sharedPreferences.getString("APOGEE_UTILISATEUR", "");
        // Rendre la ListView invisible au démarrage de l'activité
        list.setVisibility(View.GONE);

        // Récupérer le SearchView depuis le menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ajouter le fragment à la vue
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emploiTempsFragment).commit();
        // Récupérer le NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Récupérer le HeaderView du NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Récupérer les TextView à l'intérieur du HeaderView
        TextView textViewName = headerView.findViewById(R.id.name);
        TextView textViewgmail = headerView.findViewById(R.id.gmail1);

        imageViewProfile = headerView.findViewById(R.id.imageView_profile);
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page de profil
                Intent Profile = new Intent(MenuEmploi.this, Profil_Student.class);
                Profile.putExtra("Filiere Selectionnee", filiere);
                Profile.putExtra("Semester", selectedRadioButtonText);
                Profile.putExtra("FULL_NAMES", fullName);
                startActivity(Profile);
            }
        });

        textViewgmail.setText(gmail);
        // Mettre à jour le TextView avec le nom de l'utilisateur
        textViewName.setText(fullName);

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

        }


    }

    private void writeDataToFirebase(String nom, String code) {
        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profils");

        // Check if the student already exists in the database
        databaseReference.child(nom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // If the student does not exist, write the data to Firebase
                    databaseReference.child(nom).child("CodeApogee").setValue(code);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.VISIBLE); // Rendre la liste visible lorsque le SearchView est cliqué
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                list.setVisibility(View.GONE); // Rendre la liste invisible lorsque le SearchView est fermé
                return false; // Retourne false pour indiquer que vous ne souhaitez pas consommer l'événement de fermeture
            }
        });

        MenuItem.OnActionExpandListener OnActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {

                return true;
            }
        };

        menu.findItem(R.id.search).setOnActionExpandListener(OnActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search Students here ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }

        });

        return true;


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MenuEmploi.class));
        } else if(id==R.id.nav_notification){
            startActivity(new Intent(this, NotificationST.class));
        }else if (id == R.id.program) {
            startActivity(new Intent(this, Details_filieres.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, FringerPrintFaceid.class));
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

    public void notification(View view) {
        startActivity(new Intent(this, NotificationST.class));
        updateNotificationBadge(0);
    }
    void fetchNotificationCountForStudent(String apogee) {
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(apogee);
        studentRef.child("notificationCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer count = dataSnapshot.getValue(Integer.class);
                if (count != null) {
                    updateNotificationBadge(count);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors
            }
        });
    }

    public void updateNotificationBadge(int count) {
        TextView badgeCounter = findViewById(R.id.notification_badge);
        if (count > 0) {
            badgeCounter.setVisibility(View.VISIBLE);
            badgeCounter.setText(String.valueOf(count));
        } else {
            badgeCounter.setVisibility(View.GONE);
        }
    }


}