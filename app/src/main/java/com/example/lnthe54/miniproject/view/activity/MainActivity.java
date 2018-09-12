package com.example.lnthe54.miniproject.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.lnthe54.miniproject.R;
import com.example.lnthe54.miniproject.adapter.PagerAdapter;
import com.example.lnthe54.miniproject.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView {

    private static final String TITLE_TOOLBAR = "Tin Tức";
    private static final String NEWSPAPER = "TIN TỨC";
    private static final String SAVED = "ĐÃ LƯU";
    private static final String FAVOURITE = "YÊU THÍCH";

    private String[] permissions = {Manifest.permission.INTERNET};
    private Toolbar toolbar;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        if (!checkPermissions()) {
            return;
        }

        initViews();
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String check : permissions) {
                int status = checkSelfPermission(check);
                if (status == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(permissions, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkPermissions()) {
            initViews();
        } else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(TITLE_TOOLBAR);

        tabLayout = findViewById(R.id.tab_layout);
        mainPresenter.addTabLayout();

        viewPager = findViewById(R.id.pager);
        mainPresenter.addPagerAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_introduce: {
                mainPresenter.showIntroduce();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(NEWSPAPER));
        tabLayout.addTab(tabLayout.newTab().setText(SAVED));
        tabLayout.addTab(tabLayout.newTab().setText(FAVOURITE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void addPagerAdapter() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void showIntroduce() {
        final Dialog dialogIntroduce = new Dialog(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialogIntroduce.setContentView(R.layout.dialog_introduce);
        ImageButton imgBtnClose = dialogIntroduce.findViewById(R.id.btn_close);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIntroduce.cancel();
            }
        });
        setFinishOnTouchOutside(false);
        dialogIntroduce.show();
    }
}