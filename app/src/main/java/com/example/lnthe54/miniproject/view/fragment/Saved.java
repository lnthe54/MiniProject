package com.example.lnthe54.miniproject.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.adapter.NewspaperAdapter;
import com.example.lnthe54.miniproject.model.News;
import com.example.lnthe54.miniproject.presenter.SavedPresenter;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Saved extends Fragment implements NewspaperAdapter.onCallBack, SavedPresenter.View {
    private TextView tvNotification;
    private RecyclerView rvSaved;
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private ArrayList<News> listNews = new ArrayList<>();
    private SavedPresenter savedPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved, parent, false);

        savedPresenter = new SavedPresenter(this);
        initViews();
        return view;
    }

    private void initViews() {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvSaved = view.findViewById(R.id.rv_saved);
        rvSaved.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvSaved.setHasFixedSize(true);

        newspaperAdapter = new NewspaperAdapter(this, listNews);
        rvSaved.setAdapter(newspaperAdapter);
    }

    @Override
    public void itemClick(int position) {

    }

    @Override
    public void itemLongClick(int position) {
        savedPresenter.showDialog(position);
    }

    @Override
    public void showDialog(int position) {
        Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_saved);

        dialog.show();
    }
}
