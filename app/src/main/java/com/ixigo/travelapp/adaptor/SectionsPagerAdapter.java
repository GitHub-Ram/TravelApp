package com.ixigo.travelapp.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ixigo.travelapp.activity.TripActivity;
import com.ixigo.travelapp.fragment.CurrentTripFragment;

/**
 * Created by F49558B on 4/8/2017.
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return new CurrentTripFragment();
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return mNumOfTabs;
    }
}