package fr.altoine.instatrain;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by soulierantoine on 03/08/2017.
 */

public class TransportsFragment extends Fragment {
    private static final String ARG_PAGE = "title";
    private int mPageNumber;

    public static TransportsFragment newInstance(int pageNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        TransportsFragment fragment = new TransportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TransportsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mPageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transports, container, false);

        if (getArguments() != null) {
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_PAGE)));
        }

        return rootView;
    }
}
