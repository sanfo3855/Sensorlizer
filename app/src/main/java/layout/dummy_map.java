package layout;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanflix.sensorlizer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class dummy_map extends Fragment {


    public dummy_map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dummy_map, container, false);
        return v;
    }

}
