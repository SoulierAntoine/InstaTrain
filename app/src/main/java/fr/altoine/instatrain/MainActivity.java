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

import fr.altoine.instatrain.models.retrofit.Traffic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TransportsPagerAdapter mTransportsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each sections of the activity
        mTransportsPagerAdapter = setUpPagerAdapter();

        // Set up the ViewPager with the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTransportsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.addOnTabSelectedListener(new OnTransportsChangeListener(mViewPager));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api-ratp.pierre-grimaud.fr/v3/")
                .build();

        IMetroService metroService = retrofit.create(IMetroService.class);
        Call<Traffic> foo = metroService.getTraffic();

        foo.enqueue(new Callback<Traffic>() {
            @Override
            public void onResponse(Call<Traffic> call, Response<Traffic> response) {
                if (response.isSuccessful()) {
                    Traffic bar = response.body();
                } else {
                    Log.v("ASOU", "Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<Traffic> call, Throwable t) {
                // TODO: there's probably no internet if it arrives here
                Log.d("ASOU", t.getMessage());
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    fab.hide();
                else
                    fab.show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private TransportsPagerAdapter setUpPagerAdapter() {
        TransportsPagerAdapter adapter = new TransportsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrafficFragment(), "Traffic");
        adapter.addFragment(new TransportsFragment(), "Metro");
//        adapter.addFragment(TransportsFragment.newInstance(1), "Metros");
        adapter.addFragment(new TransportsFragment(), "Rer");
        adapter.addFragment(new TransportsFragment(), "Tramway");

        return adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO: launch settings fragment
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
