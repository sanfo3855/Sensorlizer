package com.sanflix.sensorlizer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;
import java.io.*;
import java.util.Map;
import java.util.Set;


public class MainActivity extends Activity implements SensorEventListener {
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
    private Sensor mOrientation;
    private List<Sensor> SensorList;

    public TextView tvAccellerometerX;
    public TextView tvAccellerometerY;
    public TextView tvAccellerometerZ;
    public CheckBox chkAccelerometer;
    public TextView tvAmbientTemperature;
    public CheckBox chkAmbientTemperature;
    public TextView tvGravityX;
    public TextView tvGravityY;
    public TextView tvGravityZ;
    public CheckBox chkGravity;
    public TextView tvGyroscopeX;
    public TextView tvGyroscopeY;
    public TextView tvGyroscopeZ;
    public CheckBox chkGyroscope;
    public TextView tvLigth;
    public CheckBox chkLigth;
    public TextView tvLinearAccellerationX;
    public TextView tvLinearAccellerationY;
    public TextView tvLinearAccellerationZ;
    public CheckBox chkLinearAccelleration;
    public TextView tvMagneticFieldX;
    public TextView tvMagneticFieldY;
    public TextView tvMagneticFieldZ;
    public CheckBox chkMagneticField;
    public TextView tvPressure;
    public CheckBox chkPressure;
    public TextView tvProximity;
    public CheckBox chkProximity;
    public TextView tvRelativeHumidity;
    public CheckBox chkRelativeHumidity;
    public TextView tvRotationVectorX;
    public CheckBox chkRotationVector;
    public TextView tvOrientationAzimuth;
    public TextView tvOrientationRoll;
    public TextView tvOrientationPitch;
    public CheckBox chkOrientation;

    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    private String apice2 = "<sup><small>2</small></sup>";

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_data);

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

        tvAccellerometerX = (TextView) findViewById(R.id.tvAccellerometerValueX);
        tvAccellerometerY = (TextView) findViewById(R.id.tvAccellerometerValueY);
        tvAccellerometerZ = (TextView) findViewById(R.id.tvAccellerometerValueZ);
        chkAccelerometer = (CheckBox) findViewById(R.id.chkAccellerometer);
        tvAmbientTemperature = (TextView) findViewById(R.id.tvAmbientTemperatureValue);
        chkAmbientTemperature = (CheckBox) findViewById(R.id.chkAmbientTemperature);
        tvGravityX = (TextView) findViewById(R.id.tvGravityValueX);
        tvGravityY = (TextView) findViewById(R.id.tvGravityValueY);
        tvGravityZ = (TextView) findViewById(R.id.tvGravityValueZ);
        chkGravity = (CheckBox) findViewById(R.id.chkGravity);
        tvGyroscopeX = (TextView) findViewById(R.id.tvGyroscopeValueX);
        tvGyroscopeY = (TextView) findViewById(R.id.tvGyroscopeValueY);
        tvGyroscopeZ = (TextView) findViewById(R.id.tvGyroscopeValueZ);
        chkGyroscope = (CheckBox) findViewById(R.id.chkGyroscope);
        tvLigth = (TextView) findViewById(R.id.tvLigthValue);
        chkLigth = (CheckBox) findViewById(R.id.chkLigth);
        tvLinearAccellerationX = (TextView) findViewById(R.id.tvLinearAccellerationValueX);
        tvLinearAccellerationY = (TextView) findViewById(R.id.tvLinearAccellerationValueY);
        tvLinearAccellerationZ = (TextView) findViewById(R.id.tvLinearAccellerationValueZ);
        chkLinearAccelleration = (CheckBox) findViewById(R.id.chkLinearAccelleration);
        tvMagneticFieldX = (TextView) findViewById(R.id.tvMagneticFieldValueX);
        tvMagneticFieldY = (TextView) findViewById(R.id.tvMagneticFieldValueY);
        tvMagneticFieldZ = (TextView) findViewById(R.id.tvMagneticFieldValueZ);
        chkMagneticField = (CheckBox) findViewById(R.id.chkMagneticField);
        tvPressure = (TextView) findViewById(R.id.tvPressureValue);
        chkPressure = (CheckBox) findViewById(R.id.chkPressure);
        tvRelativeHumidity = (TextView) findViewById(R.id.tvRelativeHumidityValue);
        chkRelativeHumidity = (CheckBox) findViewById(R.id.chkRelativeHumidity);
        tvProximity = (TextView) findViewById(R.id.tvProximityValue);
        chkProximity = (CheckBox) findViewById(R.id.chkProximity);
        tvRotationVectorX = (TextView) findViewById(R.id.tvRotationVectorValueX);
        chkRotationVector = (CheckBox) findViewById(R.id.chkRotationVector);
        tvOrientationAzimuth = (TextView) findViewById(R.id.tvOrientationAzimuth);
        tvOrientationRoll = (TextView) findViewById(R.id.tvOrientationRoll);
        tvOrientationPitch = (TextView) findViewById(R.id.tvOrientationPitch);
        chkOrientation = (CheckBox) findViewById(R.id.chkOrientation);

        File file = new File(getFilesDir(), "savedSensor.csv");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));
            String line;
            while( (line = in.readLine()) != null){
                if(line.equals("Accellerometer;1;")){
                    chkAccelerometer.setChecked(true);
                } else if(line.equals("Gravity;1;")){
                    chkGravity.setChecked(true);
                } else if(line.equals("Gyroscope;1;")){
                    chkGyroscope.setChecked(true);
                } else if(line.equals("LinearAccelleration;1;")){
                    chkLinearAccelleration.setChecked(true);
                } else if(line.equals("RotationVector;1;")){
                    chkRotationVector.setChecked(true);
                } else if(line.equals("AmbientTemperature;1;")){
                    chkAmbientTemperature.setChecked(true);
                } else if(line.equals("Ligth;1;")){
                    chkLigth.setChecked(true);
                } else if(line.equals("Pressure;1;")){
                    chkPressure.setChecked(true);
                } else if(line.equals("RelativeHumidity;1;")){
                    chkRelativeHumidity.setChecked(true);
                } else if(line.equals("MagneticField;1;")){
                    chkMagneticField.setChecked(true);
                } else if(line.equals("Proximity;1;")){
                    chkProximity.setChecked(true);
                } else if(line.equals("Orientation;1;")){
                    chkOrientation.setChecked(true);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("LOAD DEFAULT CHECKED");
            SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
            for (Sensor sensor : SensorList) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    chkAccelerometer.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    chkAmbientTemperature.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                    chkGravity.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    chkGyroscope.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    chkLinearAccelleration.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                    chkLigth.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    chkMagneticField.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    chkPressure.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                    chkRelativeHumidity.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                    chkProximity.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                    chkRotationVector.setChecked(true);
                } else if (sensor.getType() == Sensor.TYPE_ORIENTATION){
                    chkOrientation.setChecked(true);
                }
                updateSavedSensor(null);
            }
        }

        /*SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : SensorList) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                chkAccelerometer.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                chkAmbientTemperature.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                chkGravity.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                chkGyroscope.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                chkLinearAccelleration.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                chkLigth.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                chkMagneticField.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                chkPressure.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                chkRelativeHumidity.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                chkProximity.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                chkRotationVector.setChecked(true);
            } else if (sensor.getType() == Sensor.TYPE_ORIENTATION){
                chkOrientation.setChecked(true);
            }
            updateSavedSensor(null);
        }*/
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mAccelerometerReading, 0, mAccelerometerReading.length);
                updateOrientationAngles();
                float accgx = event.values[0];
                float accgy = event.values[1];
                float accgz = event.values[2];
                tvAccellerometerX.setText(Html.fromHtml("X: " + accgx + " m/s"+apice2));
                tvAccellerometerY.setText(Html.fromHtml("Y: " + accgy + " m/s"+apice2));
                tvAccellerometerZ.setText(Html.fromHtml("Z: " + accgz + " m/s"+apice2));
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                float temp = event.values[0];
                tvAmbientTemperature.setText("Temp: " + temp + " °C");
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                float gragx = event.values[0];
                float gragy = event.values[1];
                float gragz = event.values[2];
                tvGravityX.setText(Html.fromHtml("X: " + gragx + " m/s" + apice2));
                tvGravityY.setText(Html.fromHtml("Y: " + gragy + " m/s" + apice2));
                tvGravityZ.setText(Html.fromHtml("Z: " + gragz + " m/s" + apice2));
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float gyrx = event.values[0];
                float gyry = event.values[1];
                float gyrz = event.values[2];
                tvGyroscopeX.setText("X: " + gyrx + " rad/s");
                tvGyroscopeY.setText("Y: " + gyry + " rad/s");
                tvGyroscopeZ.setText("Z: " + gyrz + " rad/s");
            } else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                float lax = event.values[0];
                float lay = event.values[1];
                float laz = event.values[2];
                tvLinearAccellerationX.setText(Html.fromHtml("X: " + lax + " m/s" + apice2));
                tvLinearAccellerationY.setText(Html.fromHtml("Y: " + lay + " m/s" + apice2));
                tvLinearAccellerationZ.setText(Html.fromHtml("Z: " + laz + " m/s" + apice2));
            } else if(sensor.getType() == Sensor.TYPE_LIGHT){
                float ligth = event.values[0];
                tvLigth.setText("Value: " + ligth + " lx");
            } else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.length);
                updateOrientationAngles();
                float mfx = event.values[0];
                float mfy = event.values[1];
                float mfz = event.values[2];
                tvMagneticFieldX.setText("X: " + mfx + " μT");
                tvMagneticFieldY.setText("Y: " + mfy + " μT");
                tvMagneticFieldZ.setText("Z: " + mfz + " μT");
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE){
                float pressure = event.values[0];
                tvPressure.setText("Value:" + pressure + " hPa");
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                float relHum = event.values[0];
                tvRelativeHumidity.setText("Value: " + relHum + "%");
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                float prox = event.values[0];
                tvProximity.setText("Value: " + prox + " cm");
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                float rot = event.values[3];
                tvRotationVectorX.setText("Value: " + rot);
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
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRelativeHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void showhide(View v){
        TableLayout tbl = null;
        TextView tv = null;
        if(v.getId() == R.id.tvEnviromentalSensor) {
            tbl = (TableLayout) findViewById(R.id.enviromentalSensorGroup);
            tv = (TextView) findViewById(R.id.tvEnviromentalSensor);
        } else if(v.getId() == R.id.tvPositionSensor) {
            tbl = (TableLayout) findViewById(R.id.positionSensorGroup);
            tv = (TextView) findViewById(R.id.tvPositionSensor);
        } else if(v.getId() == R.id.tvMotionSensors){
            tbl = (TableLayout) findViewById(R.id.motionSensorGroup);
            tv = (TextView) findViewById(R.id.tvMotionSensors);
        }

        if(tbl.getVisibility() == View.VISIBLE){
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
            tbl.setVisibility(View.GONE);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
            tbl.setVisibility(View.VISIBLE);
        }
    }

    public void updateOrientationAngles() {
        mSensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);
        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
        tvOrientationAzimuth.setText("Azimuth: " + mOrientationAngles[0] + " °");
        tvOrientationPitch.setText("Pitch: " + mOrientationAngles[1] + " °");
        tvOrientationRoll.setText("Roll: " + mOrientationAngles[2] + " °");
    }

    public void updateSavedSensor(View v){
        File file = new File(getFilesDir(), "savedSensor.csv");
        String listCheck = "";
        if(chkAccelerometer.isChecked()){
            listCheck += "Accellerometer;1;\n";
        } else {
            listCheck += "Accellerometer;0;\n";
        }
        if(chkGravity.isChecked()){
            listCheck += "Gravity;1;\n";
        } else {
            listCheck += "Gravity;0;\n";
        }
        if(chkGyroscope.isChecked()){
            listCheck += "Gyroscope;1;\n";
        } else {
            listCheck += "Gyroscope;0;\n";
        }
        if(chkLinearAccelleration.isChecked()){
            listCheck += "LinearAccelleration;1;\n";
        } else {
            listCheck += "LinearAccelleration;0;\n";
        }
        if(chkRotationVector.isChecked()){
            listCheck += "RotationVector;1;\n";
        } else {
            listCheck += "RotationVector;0;\n";
        }
        if(chkAmbientTemperature.isChecked()){
            listCheck += "AmbientTemperature;1;\n";
        } else {
            listCheck += "AmbientTemperature;0;\n";
        }
        if(chkLigth.isChecked()){
            listCheck += "Ligth;1;\n";
        } else {
            listCheck += "Ligth;0;\n";
        }
        if(chkPressure.isChecked()){
            listCheck += "Pressure;1;\n";
        } else {
            listCheck += "Pressure;0;\n";
        }
        if(chkRelativeHumidity.isChecked()){
            listCheck += "RelativeHumidity;1;\n";
        } else {
            listCheck += "RelativeHumidity;0;\n";
        }
        if(chkMagneticField.isChecked()){
            listCheck += "MagneticField;1;\n";
        } else {
            listCheck += "MagneticField;0;\n";
        }
        if(chkProximity.isChecked()){
            listCheck += "Proximity;1;\n";
        } else {
            listCheck += "Proximity;0;\n";
        }
        if(chkOrientation.isChecked()){
            listCheck += "Orientation;1;\n";
        } else {
            listCheck += "Orientation;0;\n";
        }

        try{
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(listCheck.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
