package fr.altoine.instatrain.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * TransportsPagerAdapter - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */

/*
 * Using FragmentPagerAdapter because there's a fix number of static Fragment that does not contain a lot of heavy data
 * See: https://stackoverflow.com/a/18748107
 */
public class TransportsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mPagesList;
    private List<String> mTitlesList;


    public TransportsPagerAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
        super(manager);
        mPagesList = fragments;
        mTitlesList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mPagesList.get(position);
    }

    @Override
    public int getCount() {
        return mPagesList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = mTitlesList.get(position);
        return (title != null) ? title : super.getPageTitle(position);
    }
}
