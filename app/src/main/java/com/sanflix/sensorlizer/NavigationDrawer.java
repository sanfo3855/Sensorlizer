package com.sanflix.sensorlizer;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int READ_REQUEST_CODE = 42;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=43;
    public FloatingActionButton fabShare;
    public FloatingActionButton fabImport;
    public Bundle args;
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_navigation_drawer);
        fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabImport = (FloatingActionButton) findViewById(R.id.fabImport);
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

        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
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
            fabImport.setVisibility(View.GONE);
            fabShare.setVisibility(View.GONE);
        } else if (id == R.id.plots_drawer) {
            fragmentClass = PlotVisualizer.class;
            setTitle("Logged-Data Plots");
            args = new Bundle();
            args.putString("FileDir","");
            args.putString("FileName","SensorData.csv");

            final File savedData = new File(getFilesDir(), "SensorData.csv");
            final Uri contentUri = FileProvider.getUriForFile(this, "com.sanflix.sensorlizer", savedData);
            fabImport.setVisibility(View.GONE);
            fabShare.setVisibility(View.VISIBLE);
            fabShare.setOnClickListener(new OnClickListener() {
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
                        startActivity(Intent.createChooser(share, "Share..."));
                        Log.i(TAG, "onClick: opened share intent");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplication(), "Cannot found a sharing app", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else if (id == R.id.settings_drawer || id == R.id.action_settings) {
            fragmentClass = Settings.class;
            setTitle("Settings");
            fabImport.setVisibility(View.GONE);
            fabShare.setVisibility(View.GONE);
        } else if(id == R.id.imported_drawer){
            fragmentClass = SelectImported.class;
            setTitle("Imported-Data Plots");

            fabShare.setVisibility(View.GONE);
            fabImport.setVisibility(View.VISIBLE);
            fabImport.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/csv");
                    startActivityForResult(Intent.createChooser(intent, "Open with ..."), READ_REQUEST_CODE);
                }
            });
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.base_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                File readed = new File(uri.getPath());
                try{
                    AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(uri,"r");

                    //BufferedReader in = new BufferedReader(new FileReader(readed));
                    BufferedReader in = new BufferedReader(new InputStreamReader(afd.createInputStream()));
                    String line;
                    String importedString = "";
                    boolean check = true;
                    while ((line = in.readLine()) != null) {
                        String[] arr = line.split(",");
                        importedString += line + "\n";
                        if(!arr[arr.length-1].startsWith("android.sensor")){
                            Toast.makeText(getApplication(),"Data format wrong", Toast.LENGTH_SHORT).show();
                            check = false;
                        }
                    }
                    if(check){
                        File importedPath = new File(getFilesDir()+File.separator+"imported");
                        if(!importedPath.exists()) {
                            importedPath.mkdirs();
                        }
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy'_'HH:mm:ss");
                        String date = df.format(Calendar.getInstance().getTime());
                        File imported = new File(importedPath, date + " imported.csv");
                        imported.createNewFile();
                        FileOutputStream fos = new FileOutputStream(imported);
                        fos.write(importedString.getBytes());
                        fos.flush();
                        fos.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
