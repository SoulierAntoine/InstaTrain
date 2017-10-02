package fr.altoine.instatrain;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (mCurrentTab > 0) {

                }
            }
        });

        // The FloatingActionButton is to add routes only, not for ResponseTraffic tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                hideNoConnection();
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
        TransportsPagerAdapter adapter = new TransportsPagerAdapter(getFragmentManager());
        TrafficFragment trafficFragment = new TrafficFragment();
//        TrafficFragment trafficFragment = TrafficFragment.newInstance(0);
        setRetryActionListener(trafficFragment);

        adapter.addFragment(trafficFragment, "Traffic");
        adapter.addFragment(TransportsFragment.newInstance(1), "Metro");
        adapter.addFragment(TransportsFragment.newInstance(2), "Rer");
        adapter.addFragment(TransportsFragment.newInstance(3), "Tramway");
//        adapter.addFragment(new TransportsFragment(), "Metro");
//        adapter.addFragment(new TransportsFragment(), "Rer");
//        adapter.addFragment(new TransportsFragment(), "Tramway");

        return adapter;
    }



    // App logic ----------------------------------------------------------------------------------

    @Override
    public void showNoConnection() {
        mNoConnectionImage.setVisibility(View.VISIBLE);
        mNoConnectionText.setVisibility(View.VISIBLE);
        mRetryAction.setVisibility(View.VISIBLE);
        // mNoConnectionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoConnection() {
        // mNoConnectionLayout.setVisibility(View.INVISIBLE);
        mNoConnectionImage.setVisibility(View.INVISIBLE);
        mNoConnectionText.setVisibility(View.INVISIBLE);
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
}
