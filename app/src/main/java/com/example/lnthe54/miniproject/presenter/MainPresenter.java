package com.example.lnthe54.miniproject.presenter;

/**
 * @author lnthe54 on 9/5/2018
 * @project MiniProject
 */
public class MainPresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void addTabLayout() {
        mainView.addTabLayout();
    }

    public void addPagerAdapter() {
        mainView.addPagerAdapter();
    }

    public void showIntroduce() {
        mainView.showIntroduce();
    }
    public interface MainView {
        void addTabLayout();

        void addPagerAdapter();

        void showIntroduce();
    }
}
