package fr.altoine.instatrain.listeners;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * OnTransportsChangeListener - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */
public class OnTransportsChangeListener extends TabLayout.ViewPagerOnTabSelectedListener {
    public OnTransportsChangeListener(ViewPager viewPager) { super(viewPager); }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        super.onTabUnselected(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        super.onTabReselected(tab);
    }
}
