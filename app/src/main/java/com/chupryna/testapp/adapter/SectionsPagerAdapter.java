package com.chupryna.testapp.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.chupryna.testapp.fragment.PlaceholderFragment;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter
{
    private List<Fragment> listFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    public int getPositionByPage(int page) {
        for (int i = 0; i < listFragment.size(); i++) {
            Bundle args = listFragment.get(i).getArguments();
            if (args != null && args.getInt(PlaceholderFragment.ARG_PAGE_NUMBER) == page)
                return i;
        }
        return -1;
    }

    public void addFragment(Fragment fragment) {
        listFragment.add(fragment);
    }

    public void removeFragment(int position) {
        listFragment.remove(position);
    }
}