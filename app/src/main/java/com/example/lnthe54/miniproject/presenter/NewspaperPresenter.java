package com.example.lnthe54.miniproject.presenter;

/**
 * @author lnthe54 on 9/8/2018
 * @project MiniProject
 */
public class NewspaperPresenter {
    private Newspaper news;

    public NewspaperPresenter(Newspaper news) {
        this.news = news;
    }

    public void searchViews(String keySearch) {
        news.searchNews(keySearch);
    }

    public void goWebView(int position) {
        news.goWebView(position);
    }

    public void showData() {
        news.showData();
    }

    public void addData(int position) {
        news.addData(position);
    }

    public interface Newspaper {

        void searchNews(String keySearch);

        void goWebView(int position);

        void showData();

        void addData(int position);
    }
}
