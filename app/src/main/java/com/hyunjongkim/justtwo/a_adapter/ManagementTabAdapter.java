package com.hyunjongkim.justtwo.a_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hyunjongkim.justtwo.manage.ManageAppliedRoom;
import com.hyunjongkim.justtwo.manage.ManageUser;

public class ManagementTabAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public ManagementTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // RETURNING THE CURRENT TABS
        switch (position) {
            case 0:
                ManageAppliedRoom manageAppliedRoom = new ManageAppliedRoom();
                return manageAppliedRoom;
            case 1:
                ManageUser manageUser = new ManageUser();
                return manageUser;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
