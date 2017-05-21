package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


/**
 * Main Activity for the application. It provides a menu for the user and displays some information
 * of the active Vessel. From here the Vessel Captain can launch various Activities or change
 * VesselID if required.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // private Button logout;
    private User user;
    private ArrayAdapter<String> mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private boolean firstTimeInMainActivity = true;
    private Handler handler;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView4);
        //TODO Hårdkodat för att visa nuvarande hamn överst på hemskärmen?
        textView.setText("Current Port: SEGOT");

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
        new UserLocalStorage(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //If someone is logged in access MainActivity page
        if (authenticate()) {
            displayVesselID();
            displayVesselImage();
        } else { //If noone is logged in access Login page
            startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
        }

    }

    /**
     * @return Controls that the activity has a user.
     */
    private boolean authenticate() {
        if(user != null)
            return true;
        try {
            this.user = new User(this, UserLocalStorage.getVessel());
            return true;
        } catch (NullPointerException e){
            return false;
        }
    }

    /**
     * Displays vesselIMO in left menu
     */
    public void displayVesselID() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        TextView textViewMenu = (TextView) v.findViewById(R.id.active_user);
        textViewMenu.setText(user.getVessel().getName());
    }

    /**
     * Displays image of Vessel in the header
     */
    public void displayVesselImage(){
        ImageView ship = (ImageView) findViewById(R.id.vesselImage);
        ship.setImageDrawable(LoadImageFromWebOperations(user.getVessel().getPhotoURL()));
    }

    /**
     * @param url Image-url
     * @return Drawable of image
     */
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.wtf("Vessel-image failed to load", e.toString());
            return null;
        }
    }

    /**
     * Launches the activity to View ETAs
     * @param view
     */
    public void viewPCM(View view) {
        //TODO döpa om?
        Intent intent = new Intent(this, ViewPCM.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        startActivity(intent);

    }

    /**
     * Launches the activity to send ETAs
     * @param view
     */
    public void sendETA(View view) {
        Intent intent = new Intent(this, Send_ETA.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    /**
     * Launches the activity to check statuses
     * @param view
     */
    public void checkStatus(View view) {
        Intent intent = new Intent(this, CheckStatus.class); //skapar en ny instans av klassen CheckStatus som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    /**
     * Launches the activity to report updates
     * @param view
     */
    public void reportUpdate(View view) {
        Intent intent = new Intent(this, Report_Update.class); //skapar en ny instans av klassen Report_Update som initierar ett nytt blankt fönster
        intent.putExtra("vesselID", user.getVessel().getId());//skicka med VesselID till nästa aktivitet
        intent.putExtra("portCallID", user.getPortCallID());//skicka med portCallID till nästa aktivitet
        startActivity(intent);
    }

    /**
     * Launches the activity to view statement of facts.
     * @param view
     */
    public void statementsOfFacts(View view) {
        Intent intent = new Intent(this, StatementsOfFacts.class); //skapar en ny instans av klassen Report_Update som initierar ett nytt blankt fönster
        intent.putExtra("vesselID", user.getVessel().getId());//skicka med VesselID till nästa aktivitet
        intent.putExtra("portCallID", user.getPortCallID());//skicka med portCallID till nästa aktivitet
        startActivity(intent);
    }

    /**
     * Launches the corresponding activity based on the choice from the menu
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home_page:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_checkStatus:
                Intent intent4 = new Intent(this, CheckStatus.class);
                startActivity(intent4);
                return true;
            case R.id.nav_sendETA:
                Intent intent2 = new Intent(this, Send_ETA.class);
                startActivity(intent2);
                return true;
            case R.id.nav_reportUpdate:
                Intent intent3 = new Intent(this, Report_Update.class);
                startActivity(intent3);
                return true;

            case R.id.nav_viewETA:
                Intent intent5 = new Intent(this, ViewPCM.class);
                startActivity(intent5);
                return true;

            case R.id.nav_statementsOfFacts:
                Intent intent6 = new Intent(this, StatementsOfFacts.class);
                startActivity(intent6);
                return true;

            case R.id.nav_logout:
                user.clearUser();
                user.interrupt();
                try {
                    Log.e("Logout - PCID", UserLocalStorage.getPortCallID());
                } catch (Exception e){
                    Log.e("Logout - ExcPCID", e.toString());
                }
                try {
                    Log.e("Logout - Map", UserLocalStorage.getMessageBrokerMap().toString());
                } catch (Exception e){
                    Log.e("Logout - ExcMap", e.toString());
                }
                try {
                    Log.e("Logout - vessel", UserLocalStorage.getVessel().toString());
                } catch (Exception e){
                    Log.e("Logout - ExcVessel", e.toString());
                }

                startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
