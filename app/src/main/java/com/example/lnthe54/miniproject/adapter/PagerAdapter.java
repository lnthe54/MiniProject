package com.example.lnthe54.miniproject.adapter;

import android.support.annotation.Nullable;
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

    private static final String NEWSPAPER = "TIN TỨC";
    private static final String SAVED = "ĐÃ LƯU";
    private static final String FAVOURITE = "YÊU THÍCH";

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return Newspaper.getInstance();
            }

            case 1: {
                return Saved.getInstance();
            }

            case 2: {
                return Favourite.getInstance();
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return NEWSPAPER;
            }
            case 1: {
                return SAVED;
            }
            case 2: {
                return FAVOURITE;
            }
        }
        return super.getPageTitle(position);
    }
}