package com.solodilov.evgen.valzho.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.MyRVAdapter;
import com.solodilov.evgen.valzho.api.Model;
import com.solodilov.evgen.valzho.models.FirebaseRepository;
import com.solodilov.evgen.valzho.models.ModelRepository;
import com.solodilov.evgen.valzho.models.ObserverRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonCategoryFragment extends Fragment implements ObserverRepository {
    private static final String ARG_SECTION_SEASON = "section_season";
    private static final String LOG = SeasonCategoryFragment.class.getCanonicalName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.master_layout)
    RelativeLayout mLayoutMaster;
    @BindView(R.id.progress_wait_load)
    ProgressBar mProgressBar;

    private Seasons mSeason;
    private ModelRepository mModelRepository;
    private MyRVAdapter mMyRVAdapter;

    public static SeasonCategoryFragment newInstance(Seasons season) {
        SeasonCategoryFragment fragment = new SeasonCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_SEASON, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_season_category, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mSeason = (Seasons) getArguments().getSerializable(ARG_SECTION_SEASON);
        int resource = setBackgroundOfResource(mSeason);
        if (resource > 0) {
            view.setBackgroundResource(resource);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModelRepository = new FirebaseRepository();
        mMyRVAdapter = new MyRVAdapter(null, getContext());
        mProgressBar.setVisibility(View.VISIBLE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        mRecyclerView.setAdapter(mMyRVAdapter);
        if (mModelRepository != null)
            mModelRepository.loadModelList(mSeason);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mModelRepository != null)
            mModelRepository.registerObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        mModelRepository.removeObserver();
        super.onStop();
    }

    @Override
    public void update(List<Model> list) {
        if (!list.isEmpty()) {
            mMyRVAdapter.swapAdapter(list);
        } else {
            mLayoutMaster.removeView(mRecyclerView);
            TextView tempTextView = createTempTextView();
            mLayoutMaster.addView(tempTextView);
        }
        mProgressBar.setVisibility(View.GONE);
    }

    private TextView createTempTextView() {
        TextView textView = new TextView(getActivity());
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(etLayoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        textView.setText(getString(R.string.models_are_not_ready));
        return textView;
    }

    private int setBackgroundOfResource(Seasons mSeason) {
        switch (mSeason) {
            case WINTER:
                return R.drawable.title_winter;
            case SPRING:
                return R.drawable.title_spring;
            case SUMMER:
                return R.drawable.title_summer;
            case AUTUMN:
                return R.drawable.title_autumn;
            case ALL:
                return -1;
        }
        return 0;
    }

}

