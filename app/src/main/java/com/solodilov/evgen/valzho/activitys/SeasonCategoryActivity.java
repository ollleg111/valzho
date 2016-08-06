package com.solodilov.evgen.valzho.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.MyRVAdapter;
import com.solodilov.evgen.valzho.adapters.SectionsPagerAdapter;
import com.solodilov.evgen.valzho.api.Model;
import com.solodilov.evgen.valzho.fragments.FullCardModelFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonCategoryActivity extends BaseActivity implements MyRVAdapter.OnShowCardModel {

    @BindView(R.id.vp_fragment_container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, mFragmentManager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Seasons season = (Seasons) getIntent().getSerializableExtra(MainActivity.KEY_SEASON);
        mViewPager.setCurrentItem(season.ordinal());
    }

    public void setVisibleToolBar(boolean visible) {
        mTabLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        mToolbar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onShowCardModel(Model model) {
        FullCardModelFragment fragment = FullCardModelFragment.newInstance(model);
        fragment.show(mFragmentManager,null);
//        mFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_content, FullCardModelFragment.newInstance(model))
//                .addToBackStack("")
//                .commit();
    }
}
