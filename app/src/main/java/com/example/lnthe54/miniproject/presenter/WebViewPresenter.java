package com.example.lnthe54.miniproject.presenter;

/**
 * @author lnthe54 on 9/8/2018
 * @project MiniProject
 */
public class WebViewPresenter {
    private WebView webView;

    public WebViewPresenter(WebView webView) {
        this.webView = webView;
    }

    public void getDataIntent() {
        webView.getDataIntent();
    }

    public void loadData() {
        webView.loadData();
    }

    public interface WebView {
        void getDataIntent();

        void loadData();
    }
}
