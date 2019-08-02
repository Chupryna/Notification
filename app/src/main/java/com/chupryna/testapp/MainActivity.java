package com.chupryna.testapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAdapters();
    }

    private void initView() {
        mViewPager = findViewById(R.id.container);
    }

    private void initAdapters() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(0));
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(1));
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }




    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int position = args != null ? args.getInt(ARG_SECTION_NUMBER) : -1;

            View rootView = inflater.inflate(R.layout.fragment, container, false);
            TextView numberOfPageTV = rootView.findViewById(R.id.number_of_page_tv);
            numberOfPageTV.setText(String.valueOf(position));

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> listFragment;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            listFragment = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        void addFragment(Fragment fragment) {
            listFragment.add(fragment);
        }
    }
}
