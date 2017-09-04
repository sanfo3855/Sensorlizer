package com.sanflix.sensorlizer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccellerometer;
    private Sensor mAmbientTemperature;
    private Sensor mGravity;
    private Sensor mGyroscope;
    private Sensor mLigth;
    private Sensor mLinearAccelleration;
    private Sensor mMagneticField;
    private Sensor mOrientation;
    private Sensor mPressure;
    private Sensor mProximity;
    private Sensor mRelativeHumidity;
    private Sensor mRotationVector;
    private Sensor mTemperature;

    private List<String> SensorList;
    public TextView tvAccellerometer;
    public TextView tvAmbientTemperature;
    public TextView tvGravity;
    public TextView tvGyroscope;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccellerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAmbientTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLigth = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mLinearAccelleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        tvAccellerometer = (TextView) findViewById(R.id.tvAccellerometerValue);
        tvAmbientTemperature = (TextView) findViewById(R.id.tvAmbientTemperatureValue);
        tvGravity = (TextView) findViewById(R.id.tvGravityValue);
        tvGyroscope = (TextView) findViewById(R.id.tvGyroscopeValue);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float accgx = event.values[0];
                float accgy = event.values[1];
                float accgz = event.values[2];
                tvAccellerometer.setText("GX: " + accgx + " GY: " + accgy + "GZ: " + accgz);
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                float temp = event.values[0];
                tvAmbientTemperature.setText("Temp: " + temp);
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                float gragx = event.values[0];
                float gragy = event.values[1];
                float gragz = event.values[2];
                tvGravity.setText("GX: " + gragx + " GY: " + gragy + "GZ: " + gragz);
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float gyrx = event.values[0];
                float gyry = event.values[1];
                float gyrz = event.values[2];
                tvGyroscope.setText("GX: " + gyrx + " GY: " + gyry + "GZ: " + gyrz);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccellerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLigth, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLinearAccelleration, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
