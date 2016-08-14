package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

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
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            startIntroFragment();
        }
    }

    private void startIntroFragment() {
        getSupportActionBar().hide();
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
        getSupportActionBar().show();
    }

    @Override
    public void onFragmentSeason(Seasons season) {
        Intent intent = new Intent(this, SeasonCategoryActivity.class);
        intent.putExtra(KEY_SEASON, season);
        startActivity(intent);
    }
}
