package com.djw.dagger2.ui.zhihu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.djw.dagger2.R;
import com.djw.dagger2.adapter.ThemInfoAdapter;
import com.djw.dagger2.base.BaseActivity;
import com.djw.dagger2.data.zhihu.DaypaperThemInfoData;
import com.djw.dagger2.ui.zhihu.contracts.ThemInfoContracts;
import com.djw.dagger2.ui.zhihu.presenter.ThemInfoPresenter;

public class ThemInfoActivity extends BaseActivity<ThemInfoPresenter> implements ThemInfoContracts.View {

    private ThemInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_info);
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tl_them);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getIntent().getExtras().getString("title"));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_them_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ThemInfoAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getList(String.valueOf(getIntent().getExtras().getInt("id")));
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showList(DaypaperThemInfoData daypaperThemInfoData) {
        adapter.notifyListChange(daypaperThemInfoData.getStories());
    }
}
