package com.example.lnthe54.miniproject.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.adapter.NewspaperAdapter;
import com.example.lnthe54.miniproject.config.Config;
import com.example.lnthe54.miniproject.db.NewsData;
import com.example.lnthe54.miniproject.model.News;
import com.example.lnthe54.miniproject.model.NewsAsync;
import com.example.lnthe54.miniproject.presenter.NewspaperPresenter;
import com.example.lnthe54.miniproject.view.activity.WebActivity;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Newspaper extends Fragment
        implements NewsAsync.XMLParserCallBack, NewspaperAdapter.onCallBack,
        NewspaperPresenter.Newspaper, SearchView.OnQueryTextListener {
    private TextView tvNotification;
    private RecyclerView rvNewspaper;
    private ArrayList<News> listNews = new ArrayList<>();
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private NewspaperPresenter newspaperPresenter;
    private String keySearch;
    private NewsData newsData;
    private NewsAsync async;
    private String link;
    private Toast toast;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newspaper, parent, false);
        View layout_toast = inflater.inflate(R.layout.toast, (ViewGroup) view.findViewById(R.id.layout_toast));
        toast = new Toast(getContext().getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout_toast);
        newspaperPresenter = new NewspaperPresenter(this);
        setHasOptionsMenu(true);
        initViews();
        return view;
    }

    private void initViews() {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvNewspaper = view.findViewById(R.id.rv_newspaper);
        newspaperPresenter.showData();
        newsData = new NewsData(getContext());
        newsData.open();
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
        keySearch = Uri.encode(query);
        newspaperPresenter.searchViews(keySearch);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onParseResult(ArrayList<News> list) {
        listNews.removeAll(listNews);
        listNews.addAll(list);
        newspaperAdapter.notifyDataSetChanged();
    }

    @Override
    public void initDialog(Context context, int style) {
        alertDialog = new SpotsDialog(getContext(), R.style.Custom);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void dismissDialog() {
        alertDialog.dismiss();
    }

    @Override
    public void itemClick(int position) {
        newspaperPresenter.goWebView(position);
    }

    @Override
    public void itemLongClick(int position) {
        newspaperPresenter.addData(position);
    }

    @Override
    public void searchNews(String keySearch) {
        link = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=(" + (keySearch) + ")";
        async = new NewsAsync(getContext(), this);
        async.execute(link);
    }

    @Override
    public void goWebView(int position) {
        String link = listNews.get(position).getLink();
        Intent openWeb = new Intent(getContext(), WebActivity.class);
        openWeb.putExtra(Config.KEY_LINK, link);
        startActivity(openWeb);
    }

    @Override
    public void showData() {
        rvNewspaper.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvNewspaper.setHasFixedSize(true);
        if (listNews != null) {
            tvNotification.setVisibility(View.INVISIBLE);
            rvNewspaper.setVisibility(View.VISIBLE);
        }
        newspaperAdapter = new NewspaperAdapter(this, listNews);
        rvNewspaper.setAdapter(newspaperAdapter);
    }

    @Override
    public void addData(int position) {
        News news = listNews.get(position);
        String title = news.getTitle();
        String desc = news.getDesc();
        String link = news.getLink();
        String img = news.getImage();
        String pubDate = news.getPubDate();

        newsData.addNews(title, desc, link, img, pubDate);

        toast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsData.close();
    }
}