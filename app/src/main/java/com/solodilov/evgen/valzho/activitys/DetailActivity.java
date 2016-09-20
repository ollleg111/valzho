package com.solodilov.evgen.valzho.activitys;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.ModelFragmentPageAdapter;
import com.solodilov.evgen.valzho.api.Model;
import com.solodilov.evgen.valzho.fragments.FullCardModelFragment;
import com.solodilov.evgen.valzho.models.FireBaseRepository;
import com.solodilov.evgen.valzho.models.ModelRepository;
import com.solodilov.evgen.valzho.models.ObserverRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity
        implements ObserverRepository, FullCardModelFragment.OnRefreshAppBar {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_content_fragment_container)
    ViewPager mViewPagerContent;
    @BindView(R.id.progress_wait_data)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Model mModel;
    private PagerAdapter mPagerAdapter;
    private ModelRepository mModelRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        mModel = (Model) getIntent().getSerializableExtra(BaseActivity.INTENT_NAME_MODEL);
        mModelRepository = new FireBaseRepository();
        Seasons seasons = (Seasons) getIntent().getSerializableExtra(MainActivity.KEY_SEASON);
        mModelRepository.loadModelList(seasons);
    }

    @Override
    protected void onStart() {
        if (mModelRepository != null)
            mModelRepository.registerObserver(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        mModelRepository.removeObserver(this);
        super.onStop();
    }

    @Override
    public void update(List<Model> list) {
        if (!list.isEmpty()) {
            mPagerAdapter = new ModelFragmentPageAdapter(getSupportFragmentManager(), list);
            mViewPagerContent.setAdapter(mPagerAdapter);
            mProgressBar.setVisibility(View.GONE);
            mViewPagerContent.setVisibility(View.VISIBLE);
            int index = getIndexModel(mModel, list);
            if (index != -1)
                mViewPagerContent.setCurrentItem(index);
        } else {
            //Dialog про отсутствие данных
        }
    }

    private int getIndexModel(Model model, List<Model> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getmModelName().equals(model.getmModelName())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onRefreshImageTop(Model m) {
        mCollapsingToolbarLayout.setTitle(mModel.getmModelName());
    }
}
