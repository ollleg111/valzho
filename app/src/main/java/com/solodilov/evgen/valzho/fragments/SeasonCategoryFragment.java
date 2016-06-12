package com.solodilov.evgen.valzho.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.SelectSeason;
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

    @Nullable
    @BindView(R.id.section_label)
    TextView textView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SelectSeason mSeason;
    private ModelRepository mModelRepository;
    private MyRVAdapter mMyRVAdapter;

    public SeasonCategoryFragment() {
        mModelRepository = new FirebaseRepository();
    }

    public static SeasonCategoryFragment newInstance(SelectSeason season) {
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
        mSeason = (SelectSeason) getArguments().getSerializable(ARG_SECTION_SEASON);
        textView.setText(mSeason.name());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyRVAdapter = new MyRVAdapter(null,getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                mRecyclerView.setAdapter(mMyRVAdapter);

        mModelRepository.registerObserver(this);
        mModelRepository.loadModelList(mSeason);
    }


    public void onSwapAdapter(List<Model> list) {
        mMyRVAdapter.swapAdapter(list);
    }

    @Override
    public void update(List<Model> list) {
        onSwapAdapter(list);
    }
}

