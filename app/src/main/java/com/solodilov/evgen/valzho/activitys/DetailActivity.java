package com.solodilov.evgen.valzho.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.adapters.ModelFragmentPageAdapter;
import com.solodilov.evgen.valzho.adapters.MyViewPagerAdapter;
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
    @BindView(R.id.vp_collection_photo)
    @Nullable
    ViewPager mViewPagerPhoto;
    @BindView(R.id.progress_wait_data)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Model mModel;
    private ModelRepository mModelRepository;
    private List<Model> mList;

    private ModelFragmentPageAdapter mContentViewPagerAdapter;
    private MyViewPagerAdapter mPhotoViewPagerAdapter;

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
        mPhotoViewPagerAdapter = new MyViewPagerAdapter(this, mModel != null ? mModel.getmPhotoURL() : null);
        mViewPagerPhoto.setAdapter(mPhotoViewPagerAdapter);
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
        mList = list;
        if (!list.isEmpty()) {
            if (mContentViewPagerAdapter == null) {
                mContentViewPagerAdapter = new ModelFragmentPageAdapter(getSupportFragmentManager(), mList);
                mViewPagerContent.setAdapter(mContentViewPagerAdapter);
            }
            mContentViewPagerAdapter.swapAdapter(mList);
            mProgressBar.setVisibility(View.GONE);
            mViewPagerContent.setVisibility(View.VISIBLE);
            int index = getIndexModel(mModel, mList);
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
    public void onRefreshImageTop() {
        Model model = mList.get(mViewPagerContent.getCurrentItem());
        mCollapsingToolbarLayout.setTitle(model.getmModelName());
        mPhotoViewPagerAdapter.swapAdapter(model.getmPhotoURL());
    }
}
