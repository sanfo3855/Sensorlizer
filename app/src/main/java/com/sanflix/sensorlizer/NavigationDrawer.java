package com.sanflix.sensorlizer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;

import static android.content.ContentValues.TAG;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = null;
        setTitle("Sensors");
        fragmentClass = SensorDataVisualizer.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentClass = Settings.class;
            setTitle("Settings");
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_frame, fragment).commit();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.sensors_drawer) {
            fragmentClass = SensorDataVisualizer.class;
            setTitle("Sensors");
        } else if (id == R.id.plots_drawer) {
            fragmentClass = PlotVisualizer.class;
            setTitle("Plots");

            final File savedData = new File(getFilesDir(), "SensorData.csv");
            final Uri contentUri = FileProvider.getUriForFile(this, "com.sanflix.sensorlizer", savedData);

            ((FloatingActionButton) findViewById(R.id.fabBt)).setVisibility(View.VISIBLE);
            ((FloatingActionButton) findViewById(R.id.fabBt)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: opening share intent...");
                    Intent share = new Intent(Intent.ACTION_SEND);
                    Log.i(TAG, "onClick: "+contentUri.toString());
                    share.setData(contentUri);
                    share.setType("text/csv");
                    share.putExtra(Intent.EXTRA_TEXT, "Exported background-logged data from Sensorlizer App");
                    share.putExtra(Intent.EXTRA_SUBJECT, "SavedSensor.csv");
                    share.putExtra(Intent.EXTRA_STREAM, contentUri);
                    try {
                        startActivity(Intent.createChooser(share, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplication(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "onClick: opened share intent");

                }
            });
        } else if (id == R.id.settings_drawer || id == R.id.action_settings) {
            fragmentClass = Settings.class;
            setTitle("Settings");
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
