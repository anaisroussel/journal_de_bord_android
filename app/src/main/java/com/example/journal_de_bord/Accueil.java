package com.example.journal_de_bord;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class Accueil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment addDefiFragment;
    private Fragment accueilFragment;
    private Fragment mesDefisFragment;

    //FOR DATAS
    //Identify each fragment with a number
    private static final int FRAGMENT_ADD_DEFI = 0;
    private static final int FRAGMENT_ACCUEIL = 1;
    private static final int FRAGMENT_PARAMS = 2;

    public static Accueil newInstance() {
        return (new Accueil());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // On met le fragment Accueil par défaut
        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.activity_accueil_frame_layout,new AccueilFragment()).commit();
        }

        // Configure all views

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_accueil_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mesDefis:
                this.showDefisFragment();
                return true;
            case R.id.historique:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Show fragment after user clicked on a menu item
        switch (id){
            // Ajout de défi
            case R.id.activity_accueil_drawer_addDefi:
                this.showFragment(FRAGMENT_ADD_DEFI);
                break;
            // Accueil
            case R.id.activity_main_drawer_accueil:
                this.showFragment(FRAGMENT_ACCUEIL);
                break;
            case R.id.activity_main_drawer_settings:
                this.showFragment(FRAGMENT_PARAMS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // Show fragment according an Identifier

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_ADD_DEFI :
                this.showNewsFragment();
                break;
            case FRAGMENT_ACCUEIL:
                this.showAccueilFragment();
                break;
            case FRAGMENT_PARAMS:
                //this.showParamsFragment();
                break;
            default:
                break;
        }
    }

    // 4 - Create each fragment page and show it

    private void showNewsFragment(){
        if (this.addDefiFragment == null) this.addDefiFragment = AjoutDefi.newInstance();
        this.startTransactionFragment(this.addDefiFragment);
    }

    private void showAccueilFragment(){
        if (this.accueilFragment == null) this.accueilFragment = AccueilFragment.newInstance();
        this.startTransactionFragment(this.accueilFragment);
    }

    private void showDefisFragment(){
        // Etape 1 : on récupère l'instance de MesDefis
        this.mesDefisFragment = MesDefis.getInstanceMesDefis();
        System.out.println("Je suis dans le fragment accueil et j'ai ca comme Défis :"+ this.mesDefisFragment);
        // Etape 2 : on montre le Fragment correspondant à la class "MesDéfis"
        this.startTransactionFragment(this.mesDefisFragment);
    }


    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_accueil_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_accueil_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_accueil_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }
}
