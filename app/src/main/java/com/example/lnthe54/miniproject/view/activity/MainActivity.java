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

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView, ViewPager.OnPageChangeListener {

    private static final String TITLE_TOOLBAR = "Tin Tức";

    private String[] permissions = {Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
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
    public void addPagerAdapter() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}