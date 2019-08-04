package com.chupryna.testapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chupryna.testapp.adapter.SectionsPagerAdapter;
import com.chupryna.testapp.fragment.PlaceholderFragment;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Custom channel";
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAdapters();
        initManager();

        addFragment(0);
        int page = getPageFromIntent();
        if (page != -1)
            addFragment(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int page = getPageFromIntent();
        if (page != -1)
            mViewPager.setCurrentItem(mSectionsPagerAdapter.getPositionByPage(page));
    }

    private void initView() {
        mViewPager = findViewById(R.id.container);
    }

    private void initAdapters() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void initManager() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My custom channel for notification");
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private int getPageFromIntent() {
        return getIntent().getIntExtra(PlaceholderFragment.ARG_PAGE_NUMBER, -1);
    }

    public void addFragment() {
        Fragment fragment = mSectionsPagerAdapter.getItem(mSectionsPagerAdapter.getCount()-1);
        assert fragment.getArguments() != null;
        int position = fragment.getArguments().getInt(PlaceholderFragment.ARG_PAGE_NUMBER)+1;

        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(position));
        mSectionsPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
    }

    public void addFragment(int position) {
        mSectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(position));
        mSectionsPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
    }

    public void removeFragment(int page) {
        int position = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(position-1);
        mSectionsPagerAdapter.removeFragment(position);
        mSectionsPagerAdapter.notifyDataSetChanged();

        notificationManager.cancel(page);
    }

    public void showNotification(int page) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(PlaceholderFragment.ARG_PAGE_NUMBER, page);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setColor(Color.BLUE)
                        .setContentTitle("Chat heads active")
                        .setContentText("Notification " + (page+1))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);

        Notification notification = builder.build();
        notificationManager.notify(page, notification);
    }
}