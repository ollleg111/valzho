package com.solodilov.evgen.valzho.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.activitys.SeasonCategoryActivity;
import com.solodilov.evgen.valzho.adapters.MyViewPagerAdapter;
import com.solodilov.evgen.valzho.api.Model;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullCardModelFragment extends DialogFragment {

    private static final String ARG_SECTION_MODEL = "model";
    private Model mModel;
    @BindView(R.id.vp_collection_photo)
    ViewPager mViewPager;
    @BindView(R.id.tv_big_model_name)
    TextView mTv;
    @BindView(R.id.tv_big_description)
    TextView mTvDescription;
    @BindView(R.id.tv_big_array_size)
    TextView mTvArraySize;

    public static FullCardModelFragment newInstance(Model model) {
        FullCardModelFragment fragment = new FullCardModelFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModel = (Model) getArguments().getSerializable(ARG_SECTION_MODEL);
        View rootView = inflater.inflate(R.layout.fragment_full_card_model, container);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTv.setText(mModel.getmModelName());
        mTvDescription.setText(mModel.getmDescription());
        mTvArraySize.setText(mModel.getmArraySize());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setAdapter(new MyViewPagerAdapter(getContext(), mModel.getmPhotoURL()));
    }

    @Override
    public void onDestroyView() {
        SeasonCategoryActivity seasonCategoryActivity = (SeasonCategoryActivity) getActivity();
        seasonCategoryActivity.setVisibleToolBar(true);
        super.onDestroyView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mModel = (Model) savedInstanceState.getSerializable(ARG_SECTION_MODEL);
            MyViewPagerAdapter myPagerAdapter = (MyViewPagerAdapter) mViewPager.getAdapter();
            myPagerAdapter.restoreAdapter(mModel.getmPhotoURL());
           }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_SECTION_MODEL, mModel);
        super.onSaveInstanceState(outState);
    }

}
