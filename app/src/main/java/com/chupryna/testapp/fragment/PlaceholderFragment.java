package com.chupryna.testapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chupryna.testapp.MainActivity;
import com.chupryna.testapp.R;

import java.util.Objects;

public class PlaceholderFragment extends Fragment {

    public static final String ARG_PAGE_NUMBER = "page_number";
    private ImageButton mPlusBtn;
    private ImageButton mMinusBtn;
    private ImageButton mCreateNotificationBtn;

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int position = args != null ? args.getInt(ARG_PAGE_NUMBER) : -1;

        View rootView = inflater.inflate(R.layout.fragment, container, false);
        TextView numberOfPageTV = rootView.findViewById(R.id.number_of_page_tv);
        numberOfPageTV.setText(String.valueOf(position+1));
        if (position > 0)
            rootView.findViewById(R.id.container_minus).setVisibility(View.VISIBLE);

        initView(rootView);
        initListeners();

        return rootView;
    }

    private void initView(View root) {
        mPlusBtn = root.findViewById(R.id.plus_btn);
        mMinusBtn = root.findViewById(R.id.minus_btn);
        mCreateNotificationBtn = root.findViewById(R.id.create_notification_btn);
    }

    private void initListeners() {
        mPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition();
                if (position != -1)
                    ((MainActivity) Objects.requireNonNull(getActivity())).addFragment();
            }
        });

        mMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition();
                if (position != -1)
                    ((MainActivity) Objects.requireNonNull(getActivity())).removeFragment(position);
            }
        });

        mCreateNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition();
                if (position != -1)
                    ((MainActivity) Objects.requireNonNull(getActivity())).showNotification(position);
            }
        });
    }

    private int getPosition() {
        return getArguments() != null ? getArguments().getInt(ARG_PAGE_NUMBER) : -1;
    }
}