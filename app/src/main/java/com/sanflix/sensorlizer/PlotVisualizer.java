package com.sanflix.sensorlizer;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;


public class PlotVisualizer extends Fragment {
    public PlotVisualizer() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_plot_visualizer, container, false);

        ((TextView) v.findViewById(R.id.accellerometerTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphAccellerometerCnt)).getVisibility()==View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_ACCELEROMETER, R.id.graphAccellerometer);
                    ((LinearLayout) v.findViewById(R.id.graphAccellerometerCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.accellerometerTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphAccellerometerCnt)).getVisibility()==View.VISIBLE){
                    ((LinearLayout) v.findViewById(R.id.graphAccellerometerCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.accellerometerTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.gravityTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphGravityCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_GRAVITY, R.id.graphGravity);
                    ((LinearLayout) v.findViewById(R.id.graphGravityCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.gravityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphGravityCnt)).getVisibility() == View.VISIBLE){
                    ((LinearLayout) v.findViewById(R.id.graphGravityCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.gravityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.gyroscopeTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphGyroscopeCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_GYROSCOPE, R.id.graphGyroscope);
                    ((LinearLayout) v.findViewById(R.id.graphGyroscopeCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.gyroscopeTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphGyroscopeCnt)).getVisibility() == View.VISIBLE){
                    ((LinearLayout) v.findViewById(R.id.graphGyroscopeCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.gyroscopeTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.linearAccellerationTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                 if(((LinearLayout) v.findViewById(R.id.graphLinearAccellerationCnt)).getVisibility() == View.GONE) {
                     drawGraph(Sensor.STRING_TYPE_LINEAR_ACCELERATION, R.id.graphLinearAccelleration);
                     ((LinearLayout) v.findViewById(R.id.graphLinearAccellerationCnt)).setVisibility(View.VISIBLE);
                     ((TextView) v.findViewById(R.id.linearAccellerationTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                 } else if(((LinearLayout) v.findViewById(R.id.graphLinearAccellerationCnt)).getVisibility() == View.VISIBLE) {
                     ((LinearLayout) v.findViewById(R.id.graphLinearAccellerationCnt)).setVisibility(View.GONE);
                     ((TextView) v.findViewById(R.id.linearAccellerationTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                 }
            }
        });

        ((TextView) v.findViewById(R.id.rotationVectorTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphRotationVectorCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_ROTATION_VECTOR, R.id.graphRotationVector);
                    ((LinearLayout) v.findViewById(R.id.graphRotationVectorCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.rotationVectorTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphRotationVectorCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphRotationVectorCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.rotationVectorTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.AmbientTemperatureTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphAmbientTemperatureCnt)).getVisibility()==View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_AMBIENT_TEMPERATURE, R.id.graphAmbientTemperature);
                    ((LinearLayout) v.findViewById(R.id.graphAmbientTemperatureCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.AmbientTemperatureTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphAmbientTemperatureCnt)).getVisibility()==View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphAmbientTemperatureCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.AmbientTemperatureTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.lightTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphLightCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_LIGHT, R.id.graphLight);
                    ((LinearLayout) v.findViewById(R.id.graphLightCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.lightTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphLightCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphLightCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.lightTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.pressureTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphPressureCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_PRESSURE, R.id.graphPressure);
                    ((LinearLayout) v.findViewById(R.id.graphPressureCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.pressureTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphPressureCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphPressureCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.pressureTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.relativeHumidityTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphRelativeHumidityCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_RELATIVE_HUMIDITY, R.id.graphRelativeHumidity);
                    ((LinearLayout) v.findViewById(R.id.graphRelativeHumidityCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.relativeHumidityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphRelativeHumidityCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphRelativeHumidityCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.relativeHumidityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.magneticFieldTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphMagneticFieldCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_MAGNETIC_FIELD, R.id.graphMagneticField);
                    ((LinearLayout) v.findViewById(R.id.graphMagneticFieldCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.magneticFieldTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphMagneticFieldCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphMagneticFieldCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.magneticFieldTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });

        ((TextView) v.findViewById(R.id.proximityTv)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vv) {
                if(((LinearLayout) v.findViewById(R.id.graphProximityCnt)).getVisibility() == View.GONE) {
                    drawGraph(Sensor.STRING_TYPE_PROXIMITY, R.id.graphProximity);
                    ((LinearLayout) v.findViewById(R.id.graphProximityCnt)).setVisibility(View.VISIBLE);
                    ((TextView) v.findViewById(R.id.proximityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down,0,0,0);
                } else if(((LinearLayout) v.findViewById(R.id.graphProximityCnt)).getVisibility() == View.VISIBLE) {
                    ((LinearLayout) v.findViewById(R.id.graphProximityCnt)).setVisibility(View.GONE);
                    ((TextView) v.findViewById(R.id.proximityTv)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_right,0,0,0);
                }
            }
        });



        return v;
    }

    private void drawGraph(String sensorname,int idGraph){
        Log.i(TAG, "drawGraph: "+sensorname);
        Log.i(TAG, "drawGraph: " + idGraph);
        Activity a = getActivity();
        //Reading data from SavedSensor.csv
        File sensorData = new File(a.getFilesDir(), "SensorData.csv");

        try{
            BufferedReader in;
            GraphView graph = (GraphView) getView().findViewById(idGraph);
            LineGraphSeries<DataPoint> accX = new LineGraphSeries<>();
            accX.setColor(Color.GREEN);
            LineGraphSeries<DataPoint> accY = new LineGraphSeries<>();
            accY.setColor(Color.YELLOW);
            LineGraphSeries<DataPoint> accZ = new LineGraphSeries<>();
            accZ.setColor(Color.WHITE);
            in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(sensorData))));
            String line;
            int i=0;
            try {
                while ((line = in.readLine()) != null) {
                    String[] ln = line.split(",");
                    if(ln[ln.length-1].equals(sensorname)) {
                        i++;
                        if (Double.valueOf(ln[1]) != 0)
                            accX.appendData(new DataPoint(i, Double.valueOf(ln[1])), true, 100);
                        if (Double.valueOf(ln[2]) != 0)
                            accY.appendData(new DataPoint(i, Double.valueOf(ln[2])), true, 100);
                        if (Double.valueOf(ln[3]) != 0)
                            accZ.appendData(new DataPoint(i, Double.valueOf(ln[3])), true, 100);
                    }

                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
            if(i!=0) {
                graph.addSeries(accX);
                graph.addSeries(accY);
                graph.addSeries(accZ);
            } else {
                Toast.makeText(getActivity(),"No data to plot for this sensor", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(getActivity(),"No data registered, at all", Toast.LENGTH_LONG).show();
        }




    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
