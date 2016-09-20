package com.solodilov.evgen.valzho.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solodilov.evgen.valzho.api.Model;
import com.solodilov.evgen.valzho.fragments.FullCardModelFragment;

import java.util.List;

public class ModelFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Model> mModelList;

    public ModelFragmentPageAdapter(FragmentManager fm, List<Model> list) {
        super(fm);
        mModelList = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (mModelList == null || mModelList.isEmpty()) {
            return FullCardModelFragment.newInstance(null);
        }
        return FullCardModelFragment.newInstance(mModelList.get(position));
    }

    @Override
    public int getCount() {
        if (mModelList == null) return 1;
        return mModelList.size();
    }
}
