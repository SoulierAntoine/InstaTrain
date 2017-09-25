package fr.altoine.instatrain;

import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Antoine on 24/09/2017.
 */

public class TrainScheduleDialogFragment extends DialogFragment {
    public static TrainScheduleDialogFragment newInstance() {

        Bundle args = new Bundle();

        TrainScheduleDialogFragment fragment = new TrainScheduleDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
