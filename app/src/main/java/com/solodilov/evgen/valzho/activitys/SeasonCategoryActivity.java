package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.MyRVAdapter;
import com.solodilov.evgen.valzho.adapters.SectionsPagerAdapter;
import com.solodilov.evgen.valzho.api.Model;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonCategoryActivity extends BaseActivity implements MyRVAdapter.OnShowCardModel {
    @BindView(R.id.vp_content_fragment_container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Seasons mSeason;
    private final FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        initDrawerNav(mToolbar);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, mFragmentManager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.KEY_SEASON)) {
            mSeason = (Seasons) getIntent().getSerializableExtra(MainActivity.KEY_SEASON);
        } else {
            mSeason = Seasons.ALL;
        }
        mViewPager.setCurrentItem(mSeason.ordinal());
    }

    @Override
    protected void displaySelectedSeason(Seasons seasons) {
        mViewPager.setCurrentItem(seasons.ordinal());
    }

    @Override
    public void onShowCardModel(Model model) {

        Seasons seasons = Seasons.values()[mViewPager.getCurrentItem()];
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(BaseActivity.INTENT_NAME_MODEL, model);
        intent.putExtra(MainActivity.KEY_SEASON, seasons);
        startActivity(intent);
    }
}
