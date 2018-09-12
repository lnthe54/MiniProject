package com.example.lnthe54.miniproject.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.adapter.NewspaperAdapter;
import com.example.lnthe54.miniproject.config.Config;
import com.example.lnthe54.miniproject.db.NewsData;
import com.example.lnthe54.miniproject.model.News;
import com.example.lnthe54.miniproject.presenter.SavedPresenter;
import com.example.lnthe54.miniproject.view.activity.WebActivity;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Saved extends Fragment implements NewspaperAdapter.onCallBack, SavedPresenter.View, SearchView.OnQueryTextListener {
    private TextView tvNotification;
    private RecyclerView rvSaved;
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private ArrayList<News> listNews = new ArrayList<>();
    private SavedPresenter savedPresenter;
    private NewsData newsData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved, parent, false);

        savedPresenter = new SavedPresenter(this);
        setHasOptionsMenu(true);
        initViews();
        savedPresenter.setAdapter();
        savedPresenter.showData();

        return view;
    }

    private void initViews() {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvSaved = view.findViewById(R.id.rv_saved);
        newsData = new NewsData(getContext());
        setHasOptionsMenu(true);
        newsData.open();
//        savedPresenter.showData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_main, menu);
        MenuItem item = menu.findItem(R.id.icon_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void itemClick(int position) {
        savedPresenter.goWebView(position);
    }

    @Override
    public void itemLongClick(int position) {
        savedPresenter.showDialog(position);
    }

    @Override
    public void setAdapter() {
        listNews = newsData.getNews();
        if (newspaperAdapter == null) {
            newspaperAdapter = new NewspaperAdapter(this, listNews);
            rvSaved.setAdapter(newspaperAdapter);
            newspaperAdapter.notifyDataSetChanged();
        } else {
            newspaperAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void update() {
        listNews.clear();
        listNews.addAll(newsData.getNews());
        if (newspaperAdapter != null) {
            newspaperAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showData() {
        rvSaved.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        rvSaved.addItemDecoration(divider);
        rvSaved.setHasFixedSize(true);

        savedPresenter.updateList();
        savedPresenter.setAdapter();
        if (listNews.size() != 0) {
            tvNotification.setVisibility(View.INVISIBLE);
            rvSaved.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goWebView(int position) {
        Intent openWeb = new Intent(getContext(), WebActivity.class);
        String link = listNews.get(position).getLink();
        openWeb.putExtra(Config.KEY_LINK, link);
        startActivity(openWeb);
    }

    @Override
    public void showDialog(final int position) {
        final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_saved);

        TextView tvDel;
        tvDel = dialog.findViewById(R.id.tv_delete);
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsData.delNews(listNews.get(position).getId());
                savedPresenter.updateList();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsData.close();
    }
}
