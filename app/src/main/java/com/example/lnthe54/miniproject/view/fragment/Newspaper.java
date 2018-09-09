package com.example.lnthe54.miniproject.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Newspaper extends Fragment
        implements NewsAsync.XMLParserCallBack, NewspaperAdapter.onCallBack, NewspaperPresenter.Newspaper {
    private TextView tvNotification;
    private RecyclerView rvNewspaper;
    private ArrayList<News> listNews = new ArrayList<>();
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private ProgressDialog progressDialog;
    private NewspaperPresenter newspaperPresenter;

    private NewsData newsData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newspaper, parent, false);
        newspaperPresenter = new NewspaperPresenter(this);

        String link = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=(ho%CC%80a%20minzy)";
        NewsAsync async = new NewsAsync(this);
        async.execute(link);

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

    private void initDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(Config.PROGRESS_TEXT);
        progressDialog.setCancelable(false);
    }

    @Override
    public void onParseResult(ArrayList<News> list) {
        listNews.addAll(list);
        newspaperAdapter.notifyDataSetChanged();
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

        Toast.makeText(getContext(), "Add successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsData.close();
    }
}