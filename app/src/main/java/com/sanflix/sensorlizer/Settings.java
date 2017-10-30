package com.sanflix.sensorlizer;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class Settings extends Fragment {

    private Switch switchSensorLogger;
    private Button updateButton;
    private EditText frequency;
    private int f;
    public Settings() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        switchSensorLogger = (Switch) v.findViewById(R.id.switchSensorLogger);
        updateButton = (Button) v.findViewById((R.id.updateButton));
        frequency = (EditText) v.findViewById(R.id.frequency);
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
                } else if(line.startsWith("update")){
                    f=Integer.valueOf(line.substring(7));
                    frequency.setText(line.substring(7));
                }
            }
        } catch (Exception e){
            Log.i(TAG, "onCreate: fileConfig not found. Creating a default one");
            try{
                String defaultConfig = "service:off\nupdate:10\n";
                fileConfig.createNewFile();
                FileOutputStream fos = new FileOutputStream(fileConfig);
                fos.write(defaultConfig.getBytes());
                fos.flush();
                fos.close();
                switchSensorLogger.setChecked(false);
                frequency.setText("10");
                f=10;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        switchSensorLogger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchSensorLogger.isChecked()){
                    Log.i(TAG, "onClick: enable service");
                    startService(f);
                    updateConfig();
                } else {
                    Log.i(TAG, "onClick: disable service");
                    stopService();
                    updateConfig();
                }
            }
        });

        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String text = frequency.getText().toString();
                    if(switchSensorLogger.isChecked()) {
                        stopService();
                        startService(Integer.valueOf(text));
                    }
                    updateConfig();
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"You must enter a value", Toast.LENGTH_LONG).show();
                }
            }
        });


        
        return v;
    }

    protected void updateConfig(){
        View v = getView();
        switchSensorLogger = (Switch) v.findViewById(R.id.switchSensorLogger);
        String service;
        if(switchSensorLogger.isChecked()){
            service = "service:on\n";
        } else {
            service = "service:off\n";
        }
        String text = frequency.getText().toString();
        String update = "update:"+text+"\n";
        String updated = service+update;
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

    public void startService(int freq){
        Activity a = this.getActivity();
        Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: starting SensorLogger...");
        AlarmManager scheduler = (AlarmManager) a.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(a.getApplicationContext(), SensorLogger.class );
        PendingIntent scheduledIntent = PendingIntent.getService(a.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        scheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis() + freq*1000, freq*1000, scheduledIntent);
        Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: started and scheduled SensorLogger");
    }

    public void stopService(){
        Activity a = this.getActivity();
        Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: stopping SensorLogger...");
        AlarmManager scheduler = (AlarmManager) a.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(a,SensorLogger.class );
        PendingIntent scheduledIntent = PendingIntent.getService(a.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        scheduler.cancel(scheduledIntent);
        Log.i("SensorDataVisualizer", "com.sanflix.sensorlizer.SensorDataVisualizer: stopped and scheduled SensorLogger");
    }
}
