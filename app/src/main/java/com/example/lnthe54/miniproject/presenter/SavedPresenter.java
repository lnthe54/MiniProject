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

    public void showDialog(int position) {
        view.showDialog(position);
    }

    public interface View {
        void showDialog(int position);
    }
}
