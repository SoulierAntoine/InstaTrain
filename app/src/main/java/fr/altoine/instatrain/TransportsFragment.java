package fr.altoine.instatrain;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by soulierantoine on 03/08/2017.
 */

public class TransportsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public TransportsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transports, container, false);

        if (getArguments() != null) {
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        }

        return rootView;
    }
}
