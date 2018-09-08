package com.example.lnthe54.miniproject.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.config.Config;
import com.example.lnthe54.miniproject.presenter.WebViewPresenter;

/**
 * @author lnthe54 on 9/8/2018
 * @project MiniProject
 */
public class WebActivity extends AppCompatActivity implements WebViewPresenter.WebView {
    private WebView webNews;
    private ProgressDialog progressDialog;
    private WebViewPresenter webView;
    private String link;
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = new WebViewPresenter(this);
        webView.getDataIntent();
        initViews();
    }

    @Override
    public void getDataIntent() {
        Intent getIntent = getIntent();
        link = getIntent.getStringExtra(Config.KEY_LINK);
    }

    private void initViews() {
        initDialog();
        webNews = findViewById(R.id.web_news);
        webView.loadData();
    }

    @Override
    public void loadData() {
        webNews.getSettings().setSupportZoom(true);
        webNews.loadUrl(link);
        webNews.setWebViewClient(webViewClient);
        progressDialog.show();
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Config.PROGRESS_TEXT);
        progressDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        if (webNews.canGoBack()) {
            webNews.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
