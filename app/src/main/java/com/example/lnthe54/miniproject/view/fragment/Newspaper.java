package com.example.lnthe54.miniproject.view.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.adapter.NewspaperAdapter;
import com.example.lnthe54.miniproject.config.Config;
import com.example.lnthe54.miniproject.db.NewsData;
import com.example.lnthe54.miniproject.model.DownloadFile;
import com.example.lnthe54.miniproject.model.News;
import com.example.lnthe54.miniproject.model.NewsAsync;
import com.example.lnthe54.miniproject.presenter.NewspaperPresenter;
import com.example.lnthe54.miniproject.view.activity.WebActivity;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Newspaper extends Fragment
        implements NewsAsync.XMLParserCallBack, NewspaperAdapter.onCallBack,
        NewspaperPresenter.Newspaper, SearchView.OnQueryTextListener, DownloadFile.onCallBack {

    private static final int REQUEST_CODE = 0;
    private static final String MESSAGE = "Đã lưu";
    private static final String TAG = "Newspaper";
    private TextView tvNotification;
    private RecyclerView rvNewspaper;
    private ArrayList<News> listNews = new ArrayList<>();
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private NewspaperPresenter newspaperPresenter;
    private String keySearch;
    private NewsData newsData;
    private NewsAsync async;
    private static Newspaper instance;
    private String link;
    private AlertDialog alertDialog;
    private DownloadFile downloadFile;
    private SearchView searchView;
    private Snackbar snackbar;
    private ProgressBar progressBarDownload;
    private ProgressDialog progressDialog;

    public static Newspaper getInstance() {
        if (instance == null) {
            instance = new Newspaper();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newspaper, parent, false);
        snackbar = Snackbar.make(parent, MESSAGE, Snackbar.LENGTH_SHORT);
        newspaperPresenter = new NewspaperPresenter(this);
        setHasOptionsMenu(true);
        initViews();
        return view;
    }

    private void initViews() {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvNewspaper = view.findViewById(R.id.rv_newspaper);
        progressBarDownload = view.findViewById(R.id.progress_download);
        newspaperPresenter.showData();
        newsData = new NewsData(getContext());
        newsData.open();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_main, menu);
        MenuItem item = menu.findItem(R.id.icon_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_voice: {
                newspaperPresenter.voiceSearch();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String query = matches.get(0);
                keySearch = Uri.encode(query);
                newspaperPresenter.searchViews(keySearch);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        if (listNews.size() != 0) {
            tvNotification.setVisibility(View.INVISIBLE);
            rvNewspaper.setVisibility(View.VISIBLE);
        }
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
    public void itemLongClick(View view, int position) {
        newspaperPresenter.addData(position);
        Saved.getInstance().addData();
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
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        rvNewspaper.addItemDecoration(divider);
        rvNewspaper.setHasFixedSize(true);
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

//        Log.d(TAG, "addData: " + link);
        String path = link.substring(link.lastIndexOf('=') + 1, link.lastIndexOf(".") + 1);
        path = path + "html";
        String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());

//        Log.d(TAG, "file name: " + fileName);
        Log.d(TAG, "path: " + path);
//        String pathFile = Environment.getExternalStorageDirectory()
//                + "/" + Config.NAME_FOLDER + "/" + path;
//
        downloadFile = new DownloadFile(getContext(), this);
        downloadFile.execute(path);

        newsData.addNews(title, desc, fileName, img, pubDate);
    }

    @Override
    public void voiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsData.close();
    }

    @Override
    public void initProgressDialog(Context context) {
        progressBarDownload.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgress(Integer[] progress) {
//        progressDialog.setProgress(progress[0]);
//        progressBarDownload.setProgress(progress[0]);
//        progressBarDownload.setVisibility(View.GONE);
    }

    @Override
    public void dismissProgressDialog() {
        progressBarDownload.setVisibility(View.GONE);
        snackbar.show();
    }
}