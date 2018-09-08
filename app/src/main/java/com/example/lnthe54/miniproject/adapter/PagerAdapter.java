package com.example.lnthe54.miniproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lnthe54.miniproject.view.fragment.Favourite;
import com.example.lnthe54.miniproject.view.fragment.Newspaper;
import com.example.lnthe54.miniproject.view.fragment.Saved;

/**
 * @author lnthe54 on 9/4/2018
 * @project MiniProject
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tab;

    public PagerAdapter(FragmentManager fm, int tab) {
        super(fm);
        this.tab = tab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                Newspaper newspaper = new Newspaper();
                return newspaper;
            }

            case 1: {
                Saved saved = new Saved();
                return saved;
            }

            case 2: {
                Favourite favourite = new Favourite();
                return favourite;
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tab;
    }
}