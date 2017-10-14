package fr.altoine.instatrain;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import fr.altoine.instatrain.adapters.TransportChoiceAdapter;
import fr.altoine.instatrain.adapters.TransportsPagerAdapter;
import fr.altoine.instatrain.listeners.NoConnectionListener;
import fr.altoine.instatrain.listeners.RetryActionListener;


public class MainActivity extends AppCompatActivity implements
        NoConnectionListener,
        View.OnClickListener {



    // Constants ----------------------------------------------------------------------------------

    private final String TAG = MainActivity.class.getSimpleName();



    // UI Components ------------------------------------------------------------------------------

    private TransportsPagerAdapter mTransportsPagerAdapter;
    private ViewPager mViewPager;
    // private FrameLayout mNoConnectionLayout;
    private ImageView mNoConnectionImage;
    private TextView mNoConnectionText;
    private Button mRetryAction;


    // Miscellaneous ------------------------------------------------------------------------------

    private RetryActionListener retryActionListener;
    private int mCurrentTab;


    // Activity lifecycle -------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // mNoConnectionLayout = (FrameLayout) findViewById(R.id.fl_no_connection);
        mNoConnectionImage = (ImageView) findViewById(R.id.iv_no_connection);
        mNoConnectionText = (TextView) findViewById(R.id.tv_no_connection);
        mRetryAction = (Button) findViewById(R.id.btn_retry);
        mRetryAction.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each sections of the activity
        mTransportsPagerAdapter = setUpPagerAdapter();

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTransportsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Set up the FloatingActionButton for other than ResponseTraffic tabs
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                switch (mCurrentTab) {
                    case 1:
                        showCustomView("metro");
                        break;
                    case 2:
                        showCustomView("RER");
                        break;
                    case 3:
                        showCustomView("tramway");
                        break;
                    default:
                        break;
//                    TrainScheduleDialogFragment f = new TrainScheduleDialogFragment();
//                    f.show(getFragmentManager(), "dialog");
                }
            }
        });

        // The FloatingActionButton is to add routes only, not for ResponseTraffic tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCurrentTab = tab.getPosition();

                if (tab.getPosition() == 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private TransportsPagerAdapter setUpPagerAdapter() {
        TrafficFragment trafficFragment = new TrafficFragment();
        setRetryActionListener(trafficFragment);

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        // TODO: temporary magic numbers
        titles.add(0, "Traffic");
        titles.add(1, "Metro");
        titles.add(2, "Rer");
        titles.add(3, "Tramway");

        fragments.add(trafficFragment);
        fragments.add(TransportsFragment.newInstance(1));
        fragments.add(TransportsFragment.newInstance(2));
        fragments.add(TransportsFragment.newInstance(3));

        return new TransportsPagerAdapter(getFragmentManager(), fragments, titles);
    }

    // App logic ----------------------------------------------------------------------------------

    @Override
    public void showNoConnection() {
        if (mNoConnectionText.getVisibility() == View.INVISIBLE)
            mNoConnectionText.setVisibility(View.VISIBLE);

        if (mNoConnectionImage.getVisibility() == View.INVISIBLE)
            mNoConnectionImage.setVisibility(View.VISIBLE);

        if (mRetryAction.getVisibility() == View.INVISIBLE)
            mRetryAction.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnection() {
        if (mNoConnectionText.getVisibility() == View.VISIBLE)
            mNoConnectionText.setVisibility(View.INVISIBLE);

        if (mNoConnectionImage.getVisibility() == View.VISIBLE)
            mNoConnectionImage.setVisibility(View.INVISIBLE);

        if (mRetryAction.getVisibility() == View.VISIBLE)
            mRetryAction.setVisibility(View.INVISIBLE);

    }

    private void setRetryActionListener(RetryActionListener listener) {
        this.retryActionListener = listener;
    }


    // Handle user's click ------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // TODO: implementation of 'retryAction' shouldn't be class specific.
        // Considering it'd work with every class. Maybe get rid of the RetryActionListener.
        // Then make fragments class member so they can be accessed from this onClick method.
        // Then just change the string corresponding to the name of the method accordingly.
        switch (id) {
            case R.id.btn_retry:
                Method[] methods = TrafficFragment.class.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals("loadTraffic")) {
                        retryActionListener.retryAction("loadTraffic");
                        break;
                    }
                }
                break;
        }
    }



    // Menu ---------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                // TODO: launch settings fragment
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    private EditText passwordInput;

    // TODO: tmp
    public void showCustomView(String transportType) {
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title(getString(R.string.next_transport, transportType))
                        .customView(R.layout.fragment_train_schedule_dialog, true)
                        .positiveText(R.string.connect)
                        .negativeText(android.R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                Toast.makeText(MainActivity.this, "Password: " + passwordInput.getText().toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Foo", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();

        View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        Spinner lineChoice = dialog.getCustomView().findViewById(R.id.sp_line);
        Spinner stationChoice = dialog.getCustomView().findViewById(R.id.sp_station);
        Spinner destinationChoice = dialog.getCustomView().findViewById(R.id.sp_destination);
        TransportChoiceAdapter transportChoiceAdapter = new TransportChoiceAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item);

        lineChoice.setAdapter(transportChoiceAdapter);

        /* passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    passwordInput.setInputType(
                            !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                    passwordInput.setTransformationMethod(
                            !isChecked ? PasswordTransformationMethod.getInstance() : null);
            }
        });

        int widgetColor = ThemeSingleton.get().widgetColor;
        MDTintHelper.setTint(
                checkbox, widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

        MDTintHelper.setTint(
                passwordInput,
                widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor); */

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }
}
