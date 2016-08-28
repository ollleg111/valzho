package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.fragments.IntroFragment;
import com.solodilov.evgen.valzho.fragments.SelectSeasonFragment;

public class MainActivity extends BaseActivity
        implements IntroFragment.OnFragmentInteractionListener, SelectSeasonFragment.OnFragmentSelectionSeason {

    public static final String KEY_SEASON = "season";
    protected FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        initDrawerNav(mToolbar);

        if (savedInstanceState == null) {
            startIntroFragment();
        }
    }



    @Override
    protected void displaySelectedSeason(Seasons seasons) {
        onFragmentSeason(seasons);
    }

    private void startIntroFragment() {
        mFragmentManager
                .beginTransaction()
                .add(R.id.fl_main_container, IntroFragment.newInstance())
                .commit();
    }

    @Override
    public void onFragmentInteraction() {
        mFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main_container, SelectSeasonFragment.newInstance())
                .commit();
    }

    @Override
    public void onFragmentSeason(Seasons season) {
        Intent intent = new Intent(this, SeasonCategoryActivity.class);
        intent.putExtra(KEY_SEASON, season);
        startActivity(intent);
    }
}
