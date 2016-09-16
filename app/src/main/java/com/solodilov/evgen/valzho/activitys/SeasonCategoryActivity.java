package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.MyRVAdapter;
import com.solodilov.evgen.valzho.adapters.SectionsPagerAdapter;
import com.solodilov.evgen.valzho.api.Model;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonCategoryActivity extends BaseActivity implements MyRVAdapter.OnShowCardModel {

    @BindView(R.id.vp_fragment_container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private final FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initDrawerNav(mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, mFragmentManager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Seasons season = (Seasons) getIntent().getSerializableExtra(MainActivity.KEY_SEASON);
        mViewPager.setCurrentItem(season.ordinal());
    }

    @Override
    protected void displaySelectedSeason(Seasons seasons) {
        mViewPager.setCurrentItem(seasons.ordinal());
    }

    public void setVisibleToolBar(boolean visible) {
        mTabLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        mToolbar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onShowCardModel(Model model) {
//        FullCardModelFragment fragment = FullCardModelFragment.newInstance(model);
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction().replace(R.id.fl_main_container);
//        fragment.show(fragmentTransaction, null);
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra()
        startActivity(intent);
    }
}
