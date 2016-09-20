package com.solodilov.evgen.valzho.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.adapters.MyViewPagerAdapter;
import com.solodilov.evgen.valzho.api.Model;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullCardModelFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_SECTION_MODEL = "model";

    private Model mModel;
    OnRefreshAppBar mRefreshAppBar;
    @BindView(R.id.vp_collection_photo)
    ViewPager mViewPager;
    @BindView(R.id.tv_big_model_name)
    TextView mTv;
    @BindView(R.id.tv_big_description)
    TextView mTvDescription;
    @BindView(R.id.tv_big_array_size)
    TextView mTvArraySize;
    @BindView(R.id.arrow_back)
    ImageButton mArrowBack;
    @BindView(R.id.arrow_forward)
    ImageButton mArrowForward;

    public static FullCardModelFragment newInstance(Model model) {
        FullCardModelFragment fragment = new FullCardModelFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mModel = (Model) getArguments().getSerializable(ARG_SECTION_MODEL);
        View rootView = inflater.inflate(R.layout.fragment_full_card_model, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getContext(), mModel != null ? mModel.getmPhotoURL() : null);
        mViewPager.setAdapter(pagerAdapter);
        if (pagerAdapter.getCount() > 1) {
            activateArrow();
        }
        if (mModel != null) {
            initViews();
        }
    }

    private void initViews() {
        mTv.setText(mModel.getmModelName());
        mTvDescription.setText(mModel.getmDescription());
        mTvArraySize.setText(mModel.getmArraySize());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Activity activity = getActivity();
        if (activity != null && activity instanceof OnRefreshAppBar)
            mRefreshAppBar = (OnRefreshAppBar) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        //  Log.d("qqqqqqQQQqqqqq",mModel.getmModelName());
        mRefreshAppBar.onRefreshImageTop(mModel);
    }

    @Override
    public void onStop() {
        if (mRefreshAppBar != null)
            mRefreshAppBar = null;
        super.onStop();
    }

    private void activateArrow() {
        mArrowForward.setOnClickListener(this);
        mArrowBack.setOnClickListener(this);
        arrowCheck();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_back:
                mViewPager.setCurrentItem(getItem(-1), true);
                break;
            case R.id.arrow_forward:
                mViewPager.setCurrentItem(getItem(+1), true);
                break;
            default:
        }
        arrowCheck();
    }

    private void arrowCheck() {
        int i = mViewPager.getCurrentItem();
        if (i == 0) {
            mArrowBack.setVisibility(View.GONE);
            mArrowForward.setVisibility(View.VISIBLE);
        } else if (i == mViewPager.getAdapter().getCount() - 1) {
            mArrowForward.setVisibility(View.GONE);
            mArrowBack.setVisibility(View.VISIBLE);
        } else {
            mArrowForward.setVisibility(View.VISIBLE);
            mArrowBack.setVisibility(View.VISIBLE);
        }
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    public interface OnRefreshAppBar {
        void onRefreshImageTop(Model m);
    }
}
