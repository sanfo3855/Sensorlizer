package com.sanflix.sensorlizer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    public TextView tvAccellerometerX;
    public TextView tvAccellerometerY;
    public TextView tvAccellerometerZ;
    public TextView tvAmbientTemperature;
    public TextView tvGravityX;
    public TextView tvGravityY;
    public TextView tvGravityZ;
    public TextView tvGyroscopeX;
    public TextView tvGyroscopeY;
    public TextView tvGyroscopeZ;
    public TextView tvLigth;
    public TextView tvLinearAccellerationX;
    public TextView tvLinearAccellerationY;
    public TextView tvLinearAccellerationZ;
    public TextView tvMagneticFieldX;
    public TextView tvMagneticFieldY;
    public TextView tvMagneticFieldZ;
    private String apice2 = "<sup><small>2</small></sup>";

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

        tvAccellerometerX = (TextView) findViewById(R.id.tvAccellerometerValueX);
        tvAccellerometerY = (TextView) findViewById(R.id.tvAccellerometerValueY);
        tvAccellerometerZ = (TextView) findViewById(R.id.tvAccellerometerValueZ);
        tvAmbientTemperature = (TextView) findViewById(R.id.tvAmbientTemperatureValue);
        tvGravityX = (TextView) findViewById(R.id.tvGravityValueX);
        tvGravityY = (TextView) findViewById(R.id.tvGravityValueY);
        tvGravityZ = (TextView) findViewById(R.id.tvGravityValueZ);

        tvGyroscopeX = (TextView) findViewById(R.id.tvGyroscopeValueX);
        tvGyroscopeY = (TextView) findViewById(R.id.tvGyroscopeValueY);
        tvGyroscopeZ = (TextView) findViewById(R.id.tvGyroscopeValueZ);
        tvLigth = (TextView) findViewById(R.id.tvLigthValue);
        tvLinearAccellerationX = (TextView) findViewById(R.id.tvLinearAccellerationValueX);
        tvLinearAccellerationY = (TextView) findViewById(R.id.tvLinearAccellerationValueY);
        tvLinearAccellerationZ = (TextView) findViewById(R.id.tvLinearAccellerationValueZ);
        tvMagneticFieldX = (TextView) findViewById(R.id.tvMagneticFieldValueX);
        tvMagneticFieldY = (TextView) findViewById(R.id.tvMagneticFieldValueY);
        tvMagneticFieldZ = (TextView) findViewById(R.id.tvMagneticFieldValueZ);
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
                tvAccellerometerX.setText(Html.fromHtml("X: " + accgx + "m/s"+apice2));
                tvAccellerometerY.setText(Html.fromHtml("Y: " + accgy + "m/s"+apice2));
                tvAccellerometerZ.setText(Html.fromHtml("Z: " + accgz + "m/s"+apice2));

            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                float temp = event.values[0];
                tvAmbientTemperature.setText("Temp: " + temp + " °C");
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                float gragx = event.values[0];
                float gragy = event.values[1];
                float gragz = event.values[2];
                tvGravityX.setText(Html.fromHtml("X: " + gragx + "m/s" + apice2));
                tvGravityY.setText(Html.fromHtml("Y: " + gragy + "m/s" + apice2));
                tvGravityZ.setText(Html.fromHtml("Z: " + gragz + "m/s" + apice2));

            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float gyrx = event.values[0];
                float gyry = event.values[1];
                float gyrz = event.values[2];
                tvGyroscopeX.setText("X: " + gyrx + "rad/s");
                tvGyroscopeY.setText("Y: " + gyry + "rad/s");
                tvGyroscopeZ.setText("Z: " + gyrz + "rad/s");
            } else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                float lax = event.values[0];
                float lay = event.values[1];
                float laz = event.values[2];
                tvLinearAccellerationX.setText(Html.fromHtml("X: " + lax + "m/s" + apice2));
                tvLinearAccellerationX.setText(Html.fromHtml("Y: " + lay + "m/s" + apice2));
                tvLinearAccellerationX.setText(Html.fromHtml("Z: " + laz + "m/s" + apice2));
            } else if(sensor.getType() == Sensor.TYPE_LIGHT){
                float ligth = event.values[0];
                tvLigth.setText("Light Level: " + ligth + "lx");
            } else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                float mfx = event.values[0];
                float mfy = event.values[1];
                float mfz = event.values[2];
                tvMagneticFieldX.setText("X: " + mfx + "μT");
                tvMagneticFieldY.setText("Y: " + mfy + "μT");
                tvMagneticFieldZ.setText("Z: " + mfz + "μT");
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
