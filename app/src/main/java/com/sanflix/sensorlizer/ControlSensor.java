package com.sanflix.sensorlizer;

import java.util.ArrayList;

/**
 * Created by sanfe on 10/27/2017.
 */

public class ControlSensor {
    protected ArrayList<String> list = new ArrayList<>();

    private static ControlSensor control = null;

    private ControlSensor(){};

    public static synchronized ControlSensor getInstance(){
        if(control == null){
            control = new ControlSensor();
        }
        return control;
    }
    public ArrayList<String> getList(){
        return list;
    }

    public void addSensor(String sensor){
        list.add(sensor);
    }

    public void removeSensor(String sensor){
        list.remove(sensor);
    }
}
