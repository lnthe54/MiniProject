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

    public void showData() {
        news.showData();
    }

    public interface Newspaper {
        void showData();
    }
}
