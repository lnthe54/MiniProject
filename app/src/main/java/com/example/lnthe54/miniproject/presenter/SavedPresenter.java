package com.example.lnthe54.miniproject.presenter;

import android.view.View;

/**
 * @author lnthe54 on 9/8/2018
 * @project MiniProject
 */
public class SavedPresenter {
    private Presenter presenter;

    public SavedPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setAdapter() {
        presenter.setAdapter();
    }

    public void goWebView(int position) {
        presenter.goWebView(position);
    }

    public void showPopupMenu(View view, int position) {
        presenter.showPopupMenu(view, position);
    }

    public interface Presenter {

        void setAdapter();

        void goWebView(int position);

        void showPopupMenu(View view, int position);

    }
}
