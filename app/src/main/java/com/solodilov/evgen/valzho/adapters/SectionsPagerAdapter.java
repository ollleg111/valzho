package com.solodilov.evgen.valzho.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.SelectSeason;
import com.solodilov.evgen.valzho.activitys.SeasonCategoryActivity;
import com.solodilov.evgen.valzho.fragments.SeasonCategoryFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private SeasonCategoryActivity seasonCategoryActivity;

    public SectionsPagerAdapter(SeasonCategoryActivity seasonCategoryActivity, FragmentManager fm) {
        super(fm);
        this.seasonCategoryActivity = seasonCategoryActivity;
    }

    @Override
    public Fragment getItem(int position) {
        SelectSeason[] season = SelectSeason.values();
        return SeasonCategoryFragment.newInstance(season[position]);
    }

    @Override
    public int getCount() {
        return SelectSeason.values().length;
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
