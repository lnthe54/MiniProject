package com.example.lnthe54.miniproject.presenter;

/**
 * @author lnthe54 on 9/8/2018
 * @project MiniProject
 */
public class SavedPresenter {
    private View view;

    public SavedPresenter(View view) {
        this.view = view;
    }

    public void setAdapter() {
        view.setAdapter();
    }

    public void updateList() {
        view.update();
    }

    public void showData() {
        view.showData();
    }

    public void goWebView(int position) {
        view.goWebView(position);
    }

    public void showDialog(int position) {
        view.showDialog(position);
    }

    public interface View {

        void setAdapter();

        void update();

        void showData();

        void goWebView(int position);

        void showDialog(int position);

    }
}
