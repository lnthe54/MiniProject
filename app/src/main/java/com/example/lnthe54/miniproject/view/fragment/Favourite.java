package com.example.lnthe54.miniproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.miniproject.R;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Favourite extends Fragment {
    private TextView tvNotification;
    private RecyclerView rvFavourite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, parent, false);

        tvNotification = view.findViewById(R.id.tv_notification);
        rvFavourite = view.findViewById(R.id.rv_favourite);
        return view;

    }
}