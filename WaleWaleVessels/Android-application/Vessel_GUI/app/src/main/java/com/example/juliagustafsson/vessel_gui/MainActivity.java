package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static RESTServices.PortCDMServices.getActualPortData;
import static RESTServices.PortCDMServices.getStateDefinitions;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // private Button logout;
    private UserLocalStorage userLocalStore;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Current ETA: " + Send_ETA.newDate + "\n" + Send_ETA.newETA);

        // Set customized toolbar
        Toolbar customToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(customToolbar);
        getSupportActionBar().setTitle("Port CDM");
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, customToolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
       //         mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
       //         getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //        mToggle.setDrawerIndicatorEnabled(true);
       //     }
       // });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //logout = (Button) findViewById(R.id.logout);
        //logout.setOnClickListener(this);

        userLocalStore = new UserLocalStorage(this);


        // Calls method for fetching all the state definitions in PortCDM and saves them in a
        // static map for later use when sending PortCallMessages
        getStateDefinitions();

        // Calls method for fetching the data about portLocations for actual port.
        // Atm Port of Gothenburg - SEGOT. Specified in API-constants.
        getActualPortData();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //If someone is logged in access MainActivity page
        if (authenticate() == true) {
        displayVesselID();
        }
        //If noone is logged in access Login page
        else {
            startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
        }
    }

    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    public void displayVesselID() {
        User user = userLocalStore.getLoggedInUser();
        TextView textView = (TextView) findViewById(R.id.loggedIn);
        textView.setText("Active Vessel: " + user.vesselID);
    }

    public void viewPCM(View view) {
        Intent intent = new Intent(this, ViewPCM.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        // TODO Fixa källan till texten, dvs här ska ett PCM läsas is till ett textfält

        startActivity(intent);

//        HttpUrlConnectionPortCDM foo = new HttpUrlConnectionPortCDM();
        //String pcmStr = foo.xmlTester();
    }

    public void sendETA(View view) {
        Intent intent = new Intent(this, Send_ETA.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    public void sendLocationState(View view) {
        Intent intent = new Intent(this, SendLocationState.class); //skapar en ny instans av klassen SendLocationState som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    public void sendServiceState(View view) {
        Intent intent = new Intent(this, SendServiceState.class); //skapar en ny instans av klassen SendLocationState som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home_page:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_account:
                return true;
            case R.id.nav_settings:
                return true;
            case R.id.nav_logout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
