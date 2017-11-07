package com.sanflix.sensorlizer;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SelectImported extends Fragment {

    public Spinner chooseImported;
    public ImageButton refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_select_imported, container, false);
        final Activity a = getActivity();
        chooseImported = (Spinner) v.findViewById(R.id.spinner);
        File file = new File(getActivity().getFilesDir()+File.separator+"imported");
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select a date");
        try{
            String [] listFile = file.list();
            for (String fileItem : listFile){
                spinnerArray.add(fileItem.substring(0,fileItem.length()-13));
            }
        } catch (Exception e ){
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(a, R.layout.spinner_layout, spinnerArray);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        chooseImported.setAdapter(adapter);
        chooseImported.getBackground().setColorFilter(getResources().getColor(R.color.colorLightGrey), PorterDuff.Mode.SRC_ATOP);

        chooseImported.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(!selectedItem.equals("Select a date")) {
                    String filename = selectedItem + " imported.csv";

                    Class fragmentClass = PlotVisualizer.class;
                    Bundle args = new Bundle();
                    args.putString("FileName", filename);
                    args.putString("FileDir", "/imported");
                    Fragment fragment = null;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        fragment.setArguments(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.sub_fragment_selected_imported, fragment).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        refresh = (ImageButton) v.findViewById(R.id.refresh);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getActivity().getFilesDir()+File.separator+"imported");
                List<String> spinnerArray = new ArrayList<>();
                spinnerArray.add("Select a date");
                try{
                    String [] listFile = file.list();
                    for (String fileItem : listFile){
                        spinnerArray.add(fileItem.substring(0,fileItem.length()-13));
                    }
                } catch (Exception e ){
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(a, R.layout.spinner_layout, spinnerArray);
                adapter.setDropDownViewResource(R.layout.spinner_layout);
                chooseImported.setAdapter(adapter);
            }
        });
        return v;
    }
}
