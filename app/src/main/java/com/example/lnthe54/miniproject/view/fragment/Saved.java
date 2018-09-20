package com.example.lnthe54.miniproject.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
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

import static android.support.constraint.Constraints.TAG;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class Saved extends Fragment implements NewspaperAdapter.onCallBack, SavedPresenter.Presenter, SearchView.OnQueryTextListener {
    private static final String MESSAGE_DELETE = "Đã xóa";
    private static final String MESSAGE_ADD = "Đã thêm";

    private TextView tvNotification;
    private RecyclerView rvSaved;
    private NewspaperAdapter newspaperAdapter;
    private View view;
    private ArrayList<News> listNews = new ArrayList<>();
    private SavedPresenter savedPresenter;
    private NewsData newsData;
    private static Saved instance;
    private Snackbar snackbar;

    public static Saved getInstance() {
        if (instance == null) {
            instance = new Saved();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved, parent, false);
        snackbar = Snackbar.make(parent, "", Snackbar.LENGTH_SHORT);
        savedPresenter = new SavedPresenter(this);
        newsData = new NewsData(getContext());
        setHasOptionsMenu(true);
        initViews();
        return view;
    }

    private void initViews() {
        tvNotification = view.findViewById(R.id.tv_notification);
        rvSaved = view.findViewById(R.id.rv_saved);
        rvSaved.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        rvSaved.addItemDecoration(divider);
        rvSaved.setHasFixedSize(true);
        setHasOptionsMenu(true);
        newsData.open();

        savedPresenter.setAdapter();

    }

    public void addData() {
        savedPresenter.setAdapter();
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
    public void itemLongClick(View view, int position) {
        savedPresenter.showPopupMenu(view, position);

    }

    @Override
    public void setAdapter() {
        listNews.clear();
        listNews.addAll(newsData.getNews());
        if (listNews.size() != 0) {
            tvNotification.setVisibility(View.INVISIBLE);
            rvSaved.setVisibility(View.VISIBLE);
        } else {
            tvNotification.setVisibility(View.VISIBLE);
            rvSaved.setVisibility(View.INVISIBLE);
        }

        if (newspaperAdapter == null) {
            newspaperAdapter = new NewspaperAdapter(this, listNews);
            rvSaved.setAdapter(newspaperAdapter);

        } else {
            newspaperAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void goWebView(int position) {
        Intent openWeb = new Intent(getContext(), WebActivity.class);
        String link = listNews.get(position).getLink();
        String path = "file:///" + Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + Config.NAME_FOLDER + "/" + link;
        Log.d(TAG, "path: " + path);
        openWeb.putExtra(Config.KEY_LINK, path);
        startActivity(openWeb);
    }

    @Override
    public void showPopupMenu(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);

        popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tv_favourite: {
                        snackbar.setText(MESSAGE_ADD);
                        snackbar.show();
                        break;
                    }

                    case R.id.tv_delete: {
                        newsData.delNews(listNews.get(position).getId());
                        savedPresenter.setAdapter();
                        snackbar.setText(MESSAGE_DELETE);
                        snackbar.show();
                        break;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsData.close();
    }

}
