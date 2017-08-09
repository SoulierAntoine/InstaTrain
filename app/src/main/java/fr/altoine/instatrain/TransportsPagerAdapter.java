package fr.altoine.instatrain;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soulierantoine on 03/08/2017.
 */

public class TransportsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mPagesList = new ArrayList<>();
    private List<String> mTitlesList = new ArrayList<>();


    public TransportsPagerAdapter(FragmentManager manager) { super(manager); }

    @Override
    public Fragment getItem(int position) {
        return mPagesList.get(position);
    }

    @Override
    public int getCount() {
        return mPagesList.size();
    }

    void addFragment(Fragment fragment, String title) {
        mPagesList.add(fragment);
        mTitlesList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = mTitlesList.get(position);
        return (title != null) ? title : super.getPageTitle(position);
    }
}
