package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.SelectSeason;
import com.solodilov.evgen.valzho.fragments.IntroFragment;
import com.solodilov.evgen.valzho.fragments.SelectSeasonFragment;

public class MainActivity extends AppCompatActivity
        implements IntroFragment.OnFragmentInteractionListener, SelectSeasonFragment.OnFragmentSelectionSeason {
    public static final String KEY_SEASON = "season";
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            startIntroFragment();
        }
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
    public void onFragmentSeason(SelectSeason season) {
        Intent intent = new Intent(this, SeasonCategoryActivity.class);
        intent.putExtra(KEY_SEASON, season);
        startActivity(intent);
    }
}
