package com.solodilov.evgen.valzho.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.api.Model;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullCardModelFragment extends Fragment{

    private static final String ARG_SECTION_MODEL = "model";
    private Model mModel;
    OnRefreshAppBar mRefreshAppBar;
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
        Log.d("onResume","onResume: "+mModel.getmModelName());
  //      mRefreshAppBar.onRefreshImageTop();
    }

    @Override
    public void onStop() {
        if (mRefreshAppBar != null)
            mRefreshAppBar = null;
        super.onStop();
    }

    public interface OnRefreshAppBar {
        void onRefreshImageTop();
        void onRefreshImageTop(int position);
    }
}
