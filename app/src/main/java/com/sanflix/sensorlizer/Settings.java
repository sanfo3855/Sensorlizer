package com.sanflix.sensorlizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Switch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class Settings extends Fragment {

    private Switch switchSensorLogger;

    public Settings() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        switchSensorLogger = (Switch) v.findViewById(R.id.switchSensorLogger);

        final Activity a = getActivity();
        File fileConfig = new File(a.getFilesDir(), "config");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(fileConfig))));
            String line = null;
            while((line = in.readLine()) != null){
                if(line.startsWith("service")){
                    if(line.substring(8).equals("off")){
                        Log.i(TAG, "onCreate: service needs to be off");
                        switchSensorLogger.setChecked(false);
                    } else if(line.substring(8).equals("on")){
                        Log.i(TAG, "onCreate: service needs to be on");
                        switchSensorLogger.setChecked(true);
                    }
                }
            }
        } catch (Exception e){
            Log.i(TAG, "onCreate: fileConfig not found. Creating a default one");
            try{
                String defaultConfig = "service:off\n";
                fileConfig.createNewFile();
                FileOutputStream fos = new FileOutputStream(fileConfig);
                fos.write(defaultConfig.getBytes());
                fos.flush();
                fos.close();
                switchSensorLogger.setChecked(false);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        switchSensorLogger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchSensorLogger.isChecked()){
                    Log.i(TAG, "onClick: enable service");
                    Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: starting SensorLogger...");
                    AlarmManager scheduler = (AlarmManager) a.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(a.getApplicationContext(), SensorLogger.class );
                    PendingIntent scheduledIntent = PendingIntent.getService(a.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    scheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10*1000, scheduledIntent);
                    Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: started and scheduled SensorLogger");
                    updateConfig("service:on");
                } else {
                    Log.i(TAG, "onClick: disable service");
                    Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: stopping SensorLogger...");
                    AlarmManager scheduler = (AlarmManager) a.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(a,SensorLogger.class );
                    PendingIntent scheduledIntent = PendingIntent.getService(a.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    scheduler.cancel(scheduledIntent);
                    Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: stopped and scheduled SensorLogger");
                    updateConfig("service:off");
                }
            }
        });
        
        return v;
    }

    protected void updateConfig(String updated){
        File fileConfig = new File(getActivity().getFilesDir(), "config");
        try{
            fileConfig.createNewFile();
            FileOutputStream fos = new FileOutputStream(fileConfig);
            fos.write(updated.getBytes());
            fos.flush();
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
