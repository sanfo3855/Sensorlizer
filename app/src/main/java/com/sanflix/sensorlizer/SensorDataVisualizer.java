package com.sanflix.sensorlizer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class SensorDataVisualizer extends Fragment implements SensorEventListener {
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

    public TextView tvAccellerometerY;
    public CheckBox chkAccelerometer;
    public TextView tvAmbientTemperature;
    public CheckBox chkAmbientTemperature;
    public TextView tvGravityY;
    public CheckBox chkGravity;
    public TextView tvGyroscopeY;
    public CheckBox chkGyroscope;
    public TextView tvLigth;
    public CheckBox chkLigth;
    public TextView tvLinearAccellerationY;
    public CheckBox chkLinearAccelleration;
    public TextView tvMagneticFieldY;
    public CheckBox chkMagneticField;
    public TextView tvPressure;
    public CheckBox chkPressure;
    public TextView tvProximity;
    public CheckBox chkProximity;
    public TextView tvRelativeHumidity;
    public CheckBox chkRelativeHumidity;
    public TextView tvRotationVectorX;
    public CheckBox chkRotationVector;
    public TextView tvOrientationPitch;
    public CheckBox chkOrientation;

    public GraphView rtGraphAccellerometer;
    public GraphView rtGraphGravity;
    public GraphView rtGraphGyroscope;
    public GraphView rtGraphLinearAccelleration;
    public GraphView rtGraphRotationVector;
    public GraphView rtGraphAmbientTemperature;
    public GraphView rtGraphLight;
    public GraphView rtGraphPressure;
    public GraphView rtGraphRelativeHumidity;
    public GraphView rtGraphMagneticField;
    public GraphView rtGraphProximity;
    public GraphView rtGraphOrientation;

    public LineGraphSeries<DataPoint> seriesAccX;
    public LineGraphSeries<DataPoint> seriesAccY;
    public LineGraphSeries<DataPoint> seriesAccZ;
    public int lastAcc;
    public LineGraphSeries<DataPoint> seriesGravX;
    public LineGraphSeries<DataPoint> seriesGravY;
    public LineGraphSeries<DataPoint> seriesGravZ;
    public int lastGrav;
    public LineGraphSeries<DataPoint> seriesGyrX;
    public LineGraphSeries<DataPoint> seriesGyrY;
    public LineGraphSeries<DataPoint> seriesGyrZ;
    public int lastGyr;
    public LineGraphSeries<DataPoint> seriesLinAccX;
    public LineGraphSeries<DataPoint> seriesLinAccY;
    public LineGraphSeries<DataPoint> seriesLinAccZ;
    public int lastLinAcc;
    public LineGraphSeries<DataPoint> seriesRotVecX;
    public LineGraphSeries<DataPoint> seriesRotVecY;
    public LineGraphSeries<DataPoint> seriesRotVecZ;
    public int lastRotVec;
    public LineGraphSeries<DataPoint> seriesAmbTemp;
    public int lastAmbTemp;
    public LineGraphSeries<DataPoint> seriesLig;
    public int lastLig;
    public LineGraphSeries<DataPoint> seriesPre;
    public int lastPre;
    public LineGraphSeries<DataPoint> seriesRelHum;
    public int lastRelHum;
    public LineGraphSeries<DataPoint> seriesMagFldX;
    public LineGraphSeries<DataPoint> seriesMagFldY;
    public LineGraphSeries<DataPoint> seriesMagFldZ;
    public int lastMagFld;
    public LineGraphSeries<DataPoint> seriesProx;
    public int lastProx;
    public LineGraphSeries<DataPoint> seriesOriX;
    public LineGraphSeries<DataPoint> seriesOriY;
    public LineGraphSeries<DataPoint> seriesOriZ;
    public int lastOri;

    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    private String apice2 = "<sup><small>2</small></sup>";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity a = this.getActivity();
        mSensorManager = (SensorManager) a.getSystemService(Context.SENSOR_SERVICE);

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

        SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : SensorList) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mSensorManager.registerListener(this, mAccellerometer, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                mSensorManager.registerListener(this, mLinearAccelleration, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                mSensorManager.registerListener(this, mLigth, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                mSensorManager.registerListener(this, mRelativeHumidity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_UI);
            }
        }

        seriesAccX = new LineGraphSeries<DataPoint>();
        seriesAccX.setColor(Color.GREEN);
        seriesAccX.setTitle("X");
        seriesAccY = new LineGraphSeries<DataPoint>();
        seriesAccY.setColor(Color.WHITE);
        seriesAccY.setTitle("Y");
        seriesAccZ = new LineGraphSeries<DataPoint>();
        seriesAccZ.setColor(Color.YELLOW);
        seriesAccZ.setTitle("Z");
        lastAcc = 0;

        seriesGravX = new LineGraphSeries<DataPoint>();
        seriesGravX.setColor(Color.GREEN);
        seriesGravX.setTitle("X");
        seriesGravY = new LineGraphSeries<DataPoint>();
        seriesGravY.setColor(Color.WHITE);
        seriesGravY.setTitle("Y");
        seriesGravZ = new LineGraphSeries<DataPoint>();
        seriesGravZ.setColor(Color.YELLOW);
        seriesGravZ.setTitle("Z");
        lastGrav = 0;

        seriesGyrX = new LineGraphSeries<DataPoint>();
        seriesGyrX.setColor(Color.GREEN);
        seriesGyrX.setTitle("X");
        seriesGyrY = new LineGraphSeries<DataPoint>();
        seriesGyrY.setColor(Color.WHITE);
        seriesGyrY.setTitle("Y");
        seriesGyrZ = new LineGraphSeries<DataPoint>();
        seriesGyrZ.setColor(Color.YELLOW);
        seriesGyrZ.setTitle("Z");
        lastGyr = 0;

        seriesLinAccX = new LineGraphSeries<DataPoint>();
        seriesLinAccX.setColor(Color.GREEN);
        seriesLinAccX.setTitle("X");
        seriesLinAccY = new LineGraphSeries<DataPoint>();
        seriesLinAccY.setColor(Color.WHITE);
        seriesLinAccY.setTitle("Y");
        seriesLinAccZ = new LineGraphSeries<DataPoint>();
        seriesLinAccZ.setColor(Color.YELLOW);
        seriesLinAccZ.setTitle("Z");
        lastLinAcc = 0;

        seriesRotVecX = new LineGraphSeries<DataPoint>();
        seriesRotVecX.setColor(Color.GREEN);
        seriesRotVecX.setTitle("X");
        seriesRotVecY = new LineGraphSeries<DataPoint>();
        seriesRotVecY.setColor(Color.WHITE);
        seriesRotVecY.setTitle("Y");
        seriesRotVecZ = new LineGraphSeries<DataPoint>();
        seriesRotVecZ.setColor(Color.YELLOW);
        seriesRotVecZ.setTitle("Z");
        lastRotVec = 0;

        seriesAmbTemp = new LineGraphSeries<DataPoint>();
        seriesAmbTemp.setColor(Color.GREEN);
        lastAmbTemp = 0;

        seriesLig = new LineGraphSeries<DataPoint>();
        seriesLig.setColor(Color.WHITE);
        lastLig = 0;

        seriesPre = new LineGraphSeries<DataPoint>();
        seriesPre.setColor(Color.YELLOW);
        lastPre = 0;

        seriesRelHum = new LineGraphSeries<DataPoint>();
        seriesRelHum.setColor(Color.MAGENTA);
        lastRelHum = 0;

        seriesMagFldX = new LineGraphSeries<DataPoint>();
        seriesMagFldX.setColor(Color.GREEN);
        seriesMagFldX.setTitle("X");
        seriesMagFldY = new LineGraphSeries<DataPoint>();
        seriesMagFldY.setColor(Color.WHITE);
        seriesMagFldY.setTitle("Y");
        seriesMagFldZ = new LineGraphSeries<DataPoint>();
        seriesMagFldZ.setColor(Color.YELLOW);
        seriesMagFldZ.setTitle("Z");
        lastMagFld = 0;

        seriesProx = new LineGraphSeries<DataPoint>();
        seriesProx.setColor(Color.BLUE);
        lastProx = 0;

        seriesOriX = new LineGraphSeries<DataPoint>();
        seriesOriX.setColor(Color.GREEN);
        seriesOriX.setTitle("X");
        seriesOriY = new LineGraphSeries<DataPoint>();
        seriesOriY.setColor(Color.WHITE);
        seriesOriY.setTitle("Y");
        seriesOriZ = new LineGraphSeries<DataPoint>();
        seriesOriZ.setColor(Color.YELLOW);
        seriesOriZ.setTitle("Z");
        lastOri = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_sensors_data, container, false);
        final Activity a = this.getActivity();

        tvAccellerometerY = (TextView) v.findViewById(R.id.tvAccellerometerValueY);
        chkAccelerometer = (CheckBox) v.findViewById(R.id.chkAccellerometer);
        tvAmbientTemperature = (TextView) v.findViewById(R.id.tvAmbientTemperatureValue);
        chkAmbientTemperature = (CheckBox) v.findViewById(R.id.chkAmbientTemperature);
        tvGravityY = (TextView) v.findViewById(R.id.tvGravityValueY);
        chkGravity = (CheckBox) v.findViewById(R.id.chkGravity);
        tvGyroscopeY = (TextView) v.findViewById(R.id.tvGyroscopeValueY);
        chkGyroscope = (CheckBox) v.findViewById(R.id.chkGyroscope);
        tvLigth = (TextView) v.findViewById(R.id.tvLigthValue);
        chkLigth = (CheckBox) v.findViewById(R.id.chkLigth);
        tvLinearAccellerationY = (TextView) v.findViewById(R.id.tvLinearAccellerationValueY);
        chkLinearAccelleration = (CheckBox) v.findViewById(R.id.chkLinearAccelleration);
        tvMagneticFieldY = (TextView) v.findViewById(R.id.tvMagneticFieldValueY);
        chkMagneticField = (CheckBox) v.findViewById(R.id.chkMagneticField);
        tvPressure = (TextView) v.findViewById(R.id.tvPressureValue);
        chkPressure = (CheckBox) v.findViewById(R.id.chkPressure);
        tvRelativeHumidity = (TextView) v.findViewById(R.id.tvRelativeHumidityValue);
        chkRelativeHumidity = (CheckBox) v.findViewById(R.id.chkRelativeHumidity);
        tvProximity = (TextView) v.findViewById(R.id.tvProximityValue);
        chkProximity = (CheckBox) v.findViewById(R.id.chkProximity);
        tvRotationVectorX = (TextView) v.findViewById(R.id.tvRotationVectorValueX);
        chkRotationVector = (CheckBox) v.findViewById(R.id.chkRotationVector);
        tvOrientationPitch = (TextView) v.findViewById(R.id.tvOrientationPitch);
        chkOrientation = (CheckBox) v.findViewById(R.id.chkOrientation);


        rtGraphAccellerometer = (GraphView) v.findViewById(R.id.rtGraphAccellerometer);
        rtGraphAccellerometer.addSeries(seriesAccX);
        rtGraphAccellerometer.addSeries(seriesAccY);
        rtGraphAccellerometer.addSeries(seriesAccZ);
        rtGraphAccellerometer.getViewport().setXAxisBoundsManual(true);
        rtGraphAccellerometer.getViewport().setMinX(0);
        rtGraphAccellerometer.getViewport().setMaxX(100);
        rtGraphAccellerometer.getLegendRenderer().setVisible(true);
        rtGraphAccellerometer.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphGravity = (GraphView) v.findViewById(R.id.rtGraphGravity);
        rtGraphGravity.addSeries(seriesGravX);
        rtGraphGravity.addSeries(seriesGravY);
        rtGraphGravity.addSeries(seriesGravZ);
        rtGraphGravity.getViewport().setXAxisBoundsManual(true);
        rtGraphGravity.getViewport().setMinX(0);
        rtGraphGravity.getViewport().setMaxX(100);
        rtGraphGravity.getLegendRenderer().setVisible(true);
        rtGraphGravity.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphGyroscope = (GraphView) v.findViewById(R.id.rtGraphGyroscope);
        rtGraphGyroscope.addSeries(seriesGyrX);
        rtGraphGyroscope.addSeries(seriesGyrY);
        rtGraphGyroscope.addSeries(seriesGyrZ);
        rtGraphGyroscope.getViewport().setXAxisBoundsManual(true);
        rtGraphGyroscope.getViewport().setMinX(0);
        rtGraphGyroscope.getViewport().setMaxX(100);
        rtGraphGyroscope.getLegendRenderer().setVisible(true);
        rtGraphGyroscope.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphLinearAccelleration = (GraphView) v.findViewById(R.id.rtGraphLinearAccelleration);
        rtGraphLinearAccelleration.addSeries(seriesLinAccX);
        rtGraphLinearAccelleration.addSeries(seriesLinAccY);
        rtGraphLinearAccelleration.addSeries(seriesLinAccZ);
        rtGraphLinearAccelleration.getViewport().setXAxisBoundsManual(true);
        rtGraphLinearAccelleration.getViewport().setMinX(0);
        rtGraphLinearAccelleration.getViewport().setMaxX(100);
        rtGraphLinearAccelleration.getLegendRenderer().setVisible(true);
        rtGraphLinearAccelleration.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphRotationVector = (GraphView) v.findViewById(R.id.rtGraphRotationVector);
        rtGraphRotationVector.addSeries(seriesRotVecX);
        rtGraphRotationVector.addSeries(seriesRotVecY);
        rtGraphRotationVector.addSeries(seriesRotVecZ);
        rtGraphRotationVector.getViewport().setXAxisBoundsManual(true);
        rtGraphRotationVector.getViewport().setMinX(0);
        rtGraphRotationVector.getViewport().setMaxX(100);
        rtGraphRotationVector.getLegendRenderer().setVisible(true);
        rtGraphRotationVector.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphAmbientTemperature = (GraphView) v.findViewById(R.id.rtGraphAmbientTemperature);
        rtGraphAmbientTemperature.addSeries(seriesAmbTemp);
        rtGraphAmbientTemperature.getViewport().setXAxisBoundsManual(true);
        rtGraphAmbientTemperature.getViewport().setMinX(0);
        rtGraphAmbientTemperature.getViewport().setMaxX(100);
        rtGraphAmbientTemperature.getLegendRenderer().setVisible(true);
        rtGraphAmbientTemperature.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphLight = (GraphView) v.findViewById(R.id.rtGraphLigth);
        rtGraphLight.addSeries(seriesLig);
        rtGraphLight.getViewport().setXAxisBoundsManual(true);
        rtGraphLight.getViewport().setMinX(0);
        rtGraphLight.getViewport().setMaxX(100);
        rtGraphLight.getLegendRenderer().setVisible(true);
        rtGraphLight.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphPressure = (GraphView) v.findViewById(R.id.rtGraphPressure);
        rtGraphPressure.addSeries(seriesPre);
        rtGraphPressure.getViewport().setXAxisBoundsManual(true);
        rtGraphPressure.getViewport().setMinX(0);
        rtGraphPressure.getViewport().setMaxX(100);
        rtGraphPressure.getLegendRenderer().setVisible(true);
        rtGraphPressure.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphRelativeHumidity = (GraphView) v.findViewById(R.id.rtGraphRelativeHumidity);
        rtGraphRelativeHumidity.addSeries(seriesRelHum);
        rtGraphRelativeHumidity.getViewport().setXAxisBoundsManual(true);
        rtGraphRelativeHumidity.getViewport().setMinX(0);
        rtGraphRelativeHumidity.getViewport().setMaxX(100);
        rtGraphRelativeHumidity.getLegendRenderer().setVisible(true);
        rtGraphRelativeHumidity.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphMagneticField = (GraphView) v.findViewById(R.id.rtGraphMagneticField);
        rtGraphMagneticField.addSeries(seriesMagFldX);
        rtGraphMagneticField.addSeries(seriesMagFldY);
        rtGraphMagneticField.addSeries(seriesMagFldZ);
        rtGraphMagneticField.getViewport().setXAxisBoundsManual(true);
        rtGraphMagneticField.getViewport().setMinX(0);
        rtGraphMagneticField.getViewport().setMaxX(100);
        rtGraphMagneticField.getLegendRenderer().setVisible(true);
        rtGraphMagneticField.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphProximity = (GraphView) v.findViewById(R.id.rtGraphProximity);
        rtGraphProximity.addSeries(seriesProx);
        rtGraphProximity.getViewport().setXAxisBoundsManual(true);
        rtGraphProximity.getViewport().setMinX(0);
        rtGraphProximity.getViewport().setMaxX(100);
        rtGraphProximity.getLegendRenderer().setVisible(true);
        rtGraphProximity.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        rtGraphOrientation = (GraphView) v.findViewById(R.id.rtGraphOrientation);
        rtGraphOrientation.addSeries(seriesOriX);
        rtGraphOrientation.addSeries(seriesOriY);
        rtGraphOrientation.addSeries(seriesOriZ);
        rtGraphOrientation.getViewport().setXAxisBoundsManual(true);
        rtGraphOrientation.getViewport().setMinX(0);
        rtGraphOrientation.getViewport().setMaxX(100);
        rtGraphOrientation.getLegendRenderer().setVisible(true);
        rtGraphOrientation.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        ((TextView) v.findViewById(R.id.tvMotionSensors)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ((TableLayout) a.findViewById(R.id.motionSensorGroup)).getVisibility() == View.VISIBLE){
                    ((TableLayout) a.findViewById(R.id.motionSensorGroup)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.tvMotionSensors))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                } else if( ((TableLayout) a.findViewById(R.id.motionSensorGroup)).getVisibility() == View.GONE){
                    ((TableLayout) a.findViewById(R.id.motionSensorGroup)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.tvMotionSensors))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.tvEnviromentalSensor)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if( ((TableLayout) a.findViewById(R.id.enviromentalSensorGroup)).getVisibility() == View.VISIBLE){
                    ((TableLayout) a.findViewById(R.id.enviromentalSensorGroup)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.tvEnviromentalSensor))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                } else if( ((TableLayout) a.findViewById(R.id.enviromentalSensorGroup)).getVisibility() == View.GONE){
                    ((TableLayout) a.findViewById(R.id.enviromentalSensorGroup)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.tvEnviromentalSensor))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.tvPositionSensor)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if( ((TableLayout) a.findViewById(R.id.positionSensorGroup)).getVisibility() == View.VISIBLE){
                    ((TableLayout) a.findViewById(R.id.positionSensorGroup)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.tvPositionSensor))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                } else if( ((TableLayout) a.findViewById(R.id.positionSensorGroup)).getVisibility() == View.GONE){
                    ((TableLayout) a.findViewById(R.id.positionSensorGroup)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.tvPositionSensor))
                            .setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                }
            }
        });

        File file = new File(a.getFilesDir(), "savedSensor.csv");
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
            Log.i("savedSensorFound", "Checking Saved Sensor");
        } catch(Exception e){
            //e.printStackTrace();
            Log.i("savedSensorNotFound","     Checking available Sensor");
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
                }
                chkOrientation.setChecked(true);
                updateSavedSensor(null);
            }
        }

        OnClickListener checkListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSavedSensor(null);
                Toast.makeText(getActivity(),"Updated sensor to log", Toast.LENGTH_SHORT).show();
            }
        };

        chkAccelerometer.setOnClickListener(checkListener);
        chkAmbientTemperature.setOnClickListener(checkListener);
        chkGravity.setOnClickListener(checkListener);
        chkGyroscope.setOnClickListener(checkListener);
        chkLinearAccelleration.setOnClickListener(checkListener);
        chkLigth.setOnClickListener(checkListener);
        chkMagneticField.setOnClickListener(checkListener);
        chkPressure.setOnClickListener(checkListener);
        chkRelativeHumidity.setOnClickListener(checkListener);
        chkProximity.setOnClickListener(checkListener);
        chkRotationVector.setOnClickListener(checkListener);
        chkOrientation.setOnClickListener(checkListener);
        return v;
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
                rtGraphAccellerometer.setVisibility(View.VISIBLE);
                lastAcc++;
                seriesAccX.appendData(new DataPoint(lastAcc,event.values[0]),true,100);
                seriesAccY.appendData(new DataPoint(lastAcc,event.values[1]),true,100);
                seriesAccZ.appendData(new DataPoint(lastAcc,event.values[2]),true,100);
                tvAccellerometerY.setText(Html.fromHtml(
                                "X: " + event.values[0] + " m/s"+apice2 + "<br>" +
                                "Y: " + event.values[1] + " m/s"+apice2 + "<br>" +
                                "Z: " + event.values[2] + " m/s"+apice2 ));
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                rtGraphAmbientTemperature.setVisibility(View.VISIBLE);
                lastAmbTemp++;
                seriesAmbTemp.appendData(new DataPoint(lastAmbTemp,event.values[0]),true,100);
                tvAmbientTemperature.setText(
                        "Temp: " + event.values[0] + " °C" );
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                rtGraphGravity.setVisibility(View.VISIBLE);
                lastGrav++;
                seriesGravX.appendData(new DataPoint(lastGrav,event.values[0]),true,100);
                seriesGravY.appendData(new DataPoint(lastGrav,event.values[1]),true,100);
                seriesGravZ.appendData(new DataPoint(lastGrav,event.values[2]),true,100);
                tvGravityY.setText(Html.fromHtml(
                        "X: " + event.values[0] + " m/s" + apice2 + "<br>" +
                        "Y: " + event.values[1] + " m/s" + apice2 + "<br>" +
                        "Z: " + event.values[2] + " m/s" + apice2 ));
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                rtGraphGyroscope.setVisibility(View.VISIBLE);
                lastGyr++;
                seriesGyrX.appendData(new DataPoint(lastGyr,event.values[0]),true,100);
                seriesGyrY.appendData(new DataPoint(lastGyr,event.values[1]),true,100);
                seriesGyrZ.appendData(new DataPoint(lastGyr,event.values[2]),true,100);
                tvGyroscopeY.setText(Html.fromHtml(
                        "X: " + event.values[0] + " rad/s" + "<br>" +
                        "Y: " + event.values[1] + " rad/s" + "<br>" +
                        "Z: " + event.values[2] + " rad/s" ));
            } else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                rtGraphLinearAccelleration.setVisibility(View.VISIBLE);
                lastLinAcc++;
                seriesLinAccX.appendData(new DataPoint(lastLinAcc,event.values[0]),true,100);
                seriesLinAccY.appendData(new DataPoint(lastLinAcc,event.values[1]),true,100);
                seriesLinAccZ.appendData(new DataPoint(lastLinAcc,event.values[2]),true,100);
                tvLinearAccellerationY.setText(Html.fromHtml(
                        "X: " + event.values[0] + " m/s" + apice2 + "<br>" +
                        "Y: " + event.values[1] + " m/s" + apice2 + "<br>" +
                        "Z: " + event.values[2] + " m/s" + apice2 ));
            } else if(sensor.getType() == Sensor.TYPE_LIGHT){
                rtGraphLight.setVisibility(View.VISIBLE);
                lastLig++;
                seriesLig.appendData(new DataPoint(lastLinAcc,event.values[0]),true,100);
                tvLigth.setText(
                        "Value: " + event.values[0] + " lx" );
            } else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                rtGraphMagneticField.setVisibility(View.VISIBLE);
                lastMagFld++;
                seriesMagFldX.appendData(new DataPoint(lastMagFld,event.values[0]),true,100);
                seriesMagFldY.appendData(new DataPoint(lastMagFld,event.values[1]),true,100);
                seriesMagFldZ.appendData(new DataPoint(lastMagFld,event.values[2]),true,100);
                System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.length);
                updateOrientationAngles();
                tvMagneticFieldY.setText(Html.fromHtml(
                        "X: " + event.values[0] + " μT" + "<br>" +
                        "Y: " + event.values[1] + " μT" + "<br>" +
                        "Z: " + event.values[2] + " μT" ));
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE){
                rtGraphPressure.setVisibility(View.VISIBLE);
                lastPre++;
                seriesPre.appendData(new DataPoint(lastPre,event.values[0]),true,100);
                tvPressure.setText(
                        "Value:" + event.values[0] + " hPa");
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                rtGraphRelativeHumidity.setVisibility(View.VISIBLE);
                lastRelHum++;
                seriesRelHum.appendData(new DataPoint(lastRelHum,event.values[0]),true,100);
                tvRelativeHumidity.setText(
                        "Value: " + event.values[0] + "%");
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                rtGraphProximity.setVisibility(View.VISIBLE);
                lastProx++;
                seriesProx.appendData(new DataPoint(lastProx,event.values[0]),true,100);
                tvProximity.setText(
                        "Value: " + event.values[0] + " cm");
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                rtGraphRotationVector.setVisibility(View.VISIBLE);
                lastRotVec++;
                seriesRotVecX.appendData(new DataPoint(lastRotVec,event.values[0]),true,100);
                seriesRotVecY.appendData(new DataPoint(lastRotVec,event.values[1]),true,100);
                seriesRotVecZ.appendData(new DataPoint(lastRotVec,event.values[2]),true,100);
                tvRotationVectorX.setText(Html.fromHtml(
                        "X: " + event.values[0] + "<br>" +
                        "Y: " + event.values[1] + "<br>" +
                        "Z: " + event.values[2] + "<br>" +
                        "Scalar Cpt:" + "<br>" + event.values[3] ));
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : SensorList) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mSensorManager.registerListener(this, mAccellerometer, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                mSensorManager.registerListener(this, mAmbientTemperature, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                mSensorManager.registerListener(this, mLinearAccelleration, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                mSensorManager.registerListener(this, mLigth, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                mSensorManager.registerListener(this, mRelativeHumidity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_PROXIMITY){
                mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_UI);
            } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_UI);
            }
        }
        View v = getView();
        ((TextView) v.findViewById(R.id.tvMotionSensors)).performClick();
    }

    @Override
    public void onPause() {
        super.onPause();


        mSensorManager.unregisterListener(this);
    }

    public void updateOrientationAngles() {
        mSensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);
        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
        rtGraphOrientation.setVisibility(View.VISIBLE);
        lastOri++;
        seriesOriX.appendData(new DataPoint(lastOri,mOrientationAngles[0]),true,100);
        seriesOriY.appendData(new DataPoint(lastOri,mOrientationAngles[1]),true,100);
        seriesOriZ.appendData(new DataPoint(lastOri,mOrientationAngles[2]),true,100);
        tvOrientationPitch.setText(Html.fromHtml(
                "Azimuth: " + mOrientationAngles[0] + " °" + "<br>" +
                "Pitch: " + mOrientationAngles[1] + " °" + "<br>" +
                "Roll: " + mOrientationAngles[2] + " °" ));
    }

    public void updateSavedSensor(View v){
        Activity a = this.getActivity();
        File file = new File(a.getFilesDir(), "savedSensor.csv");
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
