package com.solodilov.evgen.valzho.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.activitys.SeasonCategoryActivity;
import com.solodilov.evgen.valzho.fragments.SeasonCategoryFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private final SeasonCategoryActivity seasonCategoryActivity;

    public SectionsPagerAdapter(SeasonCategoryActivity seasonCategoryActivity, FragmentManager fm) {
        super(fm);
        this.seasonCategoryActivity = seasonCategoryActivity;
    }

    @Override
    public Fragment getItem(int position) {
        Seasons[] season = Seasons.values();
        return SeasonCategoryFragment.newInstance(season[position]);
    }

    @Override
    public int getCount() {
        return Seasons.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return seasonCategoryActivity.getBaseContext().getString(R.string.text_winter);
            case 1:
                return seasonCategoryActivity.getBaseContext().getString(R.string.text_spring);
            case 2:
                return seasonCategoryActivity.getBaseContext().getString(R.string.text_summer);
            case 3:
                return seasonCategoryActivity.getBaseContext().getString(R.string.text_autumn);
            case 4:
                return seasonCategoryActivity.getBaseContext().getString(R.string.text_all);
        }
        return null;
    }
}
