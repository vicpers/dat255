package com.example.juliagustafsson.vessel_gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;

import ServiceEntities.PortCallMessage;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // private Button logout;
    private User user = null;
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
        //Hårdkodat för att visa nuvarande hamn överst på hemskärmen.
        //TODO Skapa möjlighet att själv välja hamn
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
        } else { //If noone is logged in access Login page
            startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
        }

    }

    private boolean authenticate() {
        if(user != null)
            return true;
        try {
            user = new User(this, UserLocalStorage.getVessel());
        } catch (NullPointerException e){
            return false;
        }
        if(user != null)
            return true;
        return false;
    }

    public void displayVesselID() {
        //Displays vesselIMO in left menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        TextView textViewMenu = (TextView) v.findViewById(R.id.active_user);
        textViewMenu.setText(user.getVessel().getName());
        //Set image to the ships image
       /* ImageView ship = (ImageView) v.findViewById(R.id.imageView4);
        ship.setImageDrawable(LoadImageFromWebOperations(user.getVessel().getPhotoURL()));*/
    }

    public void viewPCM(View view) {
        Intent intent = new Intent(this, ViewPCM.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        // TODO Fixa källan till texten, dvs här ska ett PCM läsas is till ett textfält
        ArrayList<PortCallMessage> portCallList = user.getMessageBrokerMap().get("vessel").getQueue();

        ArrayList<String> stringList = new ArrayList<>();

        for(PortCallMessage pcm : portCallList){
            stringList.add(pcm.toXml());
        }
        stringList.add("hej");

        intent.putStringArrayListExtra("portcalls", stringList);//skicka med VesselID till nästa aktivitet

        startActivity(intent);

    }

    /*public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "Vessel Image");
            return d;
        } catch (Exception e) {
            return null;
        }
    }*/

    public void sendETA(View view) {
        Intent intent = new Intent(this, Send_ETA.class); //skapar en ny instans av klassen ViewPCM som initierar ett nytt blankt fönster
        intent.putExtra("vesselID", user.getVessel().getId());//skicka med VesselID till nästa aktivitet
        intent.putExtra("portCallIDID", user.getPortCallID());//skicka med VesselID till nästa aktivitet
        startActivity(intent);
    }

    public void checkStatus(View view) {
        Intent intent = new Intent(this, CheckStatus.class); //skapar en ny instans av klassen CheckStatus som initierar ett nytt blankt fönster
        startActivity(intent);
    }

    public void reportUpdate(View view) {
        Intent intent = new Intent(this, Report_Update.class); //skapar en ny instans av klassen Report_Update som initierar ett nytt blankt fönster
        intent.putExtra("vesselID", user.getVessel().getId());//skicka med VesselID till nästa aktivitet
        intent.putExtra("portCallID", user.getPortCallID());//skicka med portCallID till nästa aktivitet
        startActivity(intent);
    }

    public void statementsOfFacts(View view) {
        Intent intent = new Intent(this, StatementsOfFacts.class); //skapar en ny instans av klassen Report_Update som initierar ett nytt blankt fönster
        intent.putExtra("vesselID", user.getVessel().getId());//skicka med VesselID till nästa aktivitet
        intent.putExtra("portCallID", user.getPortCallID());//skicka med portCallID till nästa aktivitet
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
            case R.id.nav_checkStatus:
                return true;
            case R.id.nav_sendETA:
                Intent intent2 = new Intent(this, Send_ETA.class);
                startActivity(intent2);
                return true;
            case R.id.nav_reportUpdate:
                Intent intent3 = new Intent(this, Report_Update.class);
                startActivity(intent3);
                return true;
            case R.id.nav_settings:
                return true;
            case R.id.nav_logout:
                user.clearUser();
                user.interrupt();
                startActivity(new Intent(MainActivity.this, Vessel_Login.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
