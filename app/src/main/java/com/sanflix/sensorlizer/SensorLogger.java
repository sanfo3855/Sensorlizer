package com.sanflix.sensorlizer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class SensorLogger extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccellerometer;
    private Sensor mAmbientTemperature;
    private Sensor mGravity;
    private Sensor mGyroscope;
    private Sensor mLigth;
    private Sensor mLinearAccelleration;
    private Sensor mMagneticField;
    private Sensor mPressure;
    private Sensor mProximity;
    private Sensor mRelativeHumidity;
    private Sensor mRotationVector;
    private List<Sensor> SensorList;

    protected boolean checkAccellerometer;
    protected boolean checkAmbientTemperature;
    protected boolean checkGravity;
    protected boolean checkGyroscope;
    protected boolean checkLighth;
    protected boolean checkLinearAccelleration;
    protected boolean checkMagneticFielfd;
    protected boolean checkPressure;
    protected boolean checkProximity;
    protected boolean checkRelativeHumidity;
    protected boolean checkRotationVector;

    private ControlSensor cs;

    private class SensorEventLoggerTask extends AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... event) {
            String all = "";
            String timestamp = String.valueOf(event[0].timestamp);
            all += timestamp + ",";
            String sensorName = event[0].sensor.getStringType();
            Log.i("sensorName", sensorName);
            Log.i("timestamp", timestamp);
            int i=0;
            for(float value : event[0].values) {
                String sensorValues = String.valueOf(value);
                all += sensorValues + ",";
                Log.i("values"+(++i), sensorValues);
            }
            all += sensorName +"\n";

            File file = new File(getFilesDir(), "SensorData.csv");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));
                String readedFile = "";
                String line = "";
                while( (line = in.readLine()) != null){
                    readedFile += line + "\n";
                }
                readedFile += all;
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(readedFile.getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e){
                try{
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(all.getBytes());
                    fos.flush();
                    fos.close();
                } catch (IOException exCreate){
                    exCreate.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SensorLogger","com.sanflix.sensorlizer.SensorLogger: onStartCommand received ");
        Toast.makeText(getApplicationContext(),"Sensorlizer Background logging", Toast.LENGTH_SHORT).show();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccellerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLigth = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mLinearAccelleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mRelativeHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);


        cs = ControlSensor.getInstance();
        Log.i("SensorLogger","com.sanflix.sensorlizer.SensorLogger: created instance of ControlSensor");
        checkAccellerometer = false;
        checkAmbientTemperature = false;
        checkGravity = false;
        checkGyroscope = false;
        checkLighth = false;
        checkLinearAccelleration = false;
        checkMagneticFielfd = false;
        checkPressure = false;
        checkProximity = false;
        checkRelativeHumidity = false;
        checkRotationVector = false;

        SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor : SensorList){
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                checkAccellerometer = true;
                mSensorManager.registerListener(this, mAccellerometer, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                checkAmbientTemperature = true;
                mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                checkGravity = true;
                mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                checkGyroscope = true;
                mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                checkLinearAccelleration = true;
                mSensorManager.registerListener(this, mLigth, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                checkLighth = true;
                mSensorManager.registerListener(this, mLinearAccelleration, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                checkMagneticFielfd = true;
                mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                checkPressure = true;
                mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                checkRelativeHumidity = true;
                mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                checkProximity = true;
                mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                checkRotationVector = true;
                mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
                cs.addSensor(sensor.getStringType());
            }
        }

        Log.i("SensorLogger","com.sanflix.sensorlizer.SensorLogger: set available sensor in ControlSensor.list ");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mAccellerometer);

        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mAmbientTemperature);
        } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mGravity);
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mGyroscope);
        } else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mLinearAccelleration);
        } else if(sensor.getType() == Sensor.TYPE_LIGHT){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mLigth);
        } else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mMagneticField);
        } else if (sensor.getType() == Sensor.TYPE_PRESSURE){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mPressure);
        } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mRelativeHumidity);
        } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mProximity);
        } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            new SensorEventLoggerTask().execute(event);
            mSensorManager.unregisterListener(this, mRotationVector);
        }
        cs.removeSensor(sensor.getStringType());
        Log.i("SensorLogger","com.sanflix.sensorlizer.SensorLogger: removed "+sensor.getStringType());
        if(cs.getList().size()==0){
            Log.i("SensorLogger","com.sanflix.sensorlizer.SensorLogger: Stopping service... ");
            stopSelf();

        }
    }


}
