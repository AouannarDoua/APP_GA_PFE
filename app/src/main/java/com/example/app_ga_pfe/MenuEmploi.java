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


        Intent intent = getIntent();
        String selectedRadioButtonText = getIntent().getStringExtra("Semester");
        long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
        EmploiTempsFragment emploiTempsFragment = new EmploiTempsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("idFilieres", selectedFiliereId);
        bundle.putString("Semester", selectedRadioButtonText);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs1", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("FULL_NAME", "");
        String gmail = sharedPreferences.getString("GMAIL", "");
        String imageUri = sharedPreferences.getString("IMAGE_URI", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewName = headerView.findViewById(R.id.name);
        TextView textViewgmail = headerView.findViewById(R.id.gmail1);
        ImageView imgPro = headerView.findViewById(R.id.imageView_profile);
        textViewName.setText(fullName);


        // Utiliser une image par défaut pour le profil
        imgPro.setImageResource(R.drawable.img_1);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(fullName);

// Ajoutez un écouteur de valeur à cette référence
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Vérifiez si les données existent
                if (dataSnapshot.exists()) {
                    // Récupérez les valeurs des champs Gmail et imag
                    String gmailFromFirebase = dataSnapshot.child("Gmail").getValue(String.class);
                    String imageUriFromFirebase = dataSnapshot.child("imag").getValue(String.class);
                    textViewgmail.setText(gmailFromFirebase);

                } else {
                    // Les données n'existent pas
                    Toast.makeText(MenuEmploi.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gestion des erreurs, si nécessaire
                Toast.makeText(MenuEmploi.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        emploiTempsFragment.setArguments(bundle);
        list = findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profiles);
        list.setAdapter(arrayAdapter);

        // Rendre la ListView invisible au démarrage de l'activité
        list.setVisibility(View.GONE);

        // Ajouter le fragment à la vue
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emploiTempsFragment).commit();

        // Définir le contenu de la vue avec les données du profil


        imgPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers la page de profil
                Intent Profile = new Intent(MenuEmploi.this, Profil_Student.class);
                startActivity(Profile);
            }
        });



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