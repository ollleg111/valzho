package com.solodilov.evgen.valzho.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.solodilov.evgen.valzho.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyViewPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private List<String> mListPhoto;

    public MyViewPagerAdapter(Context context, List<String> listPhoto) {
        mListPhoto = listPhoto;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mListPhoto == null || mListPhoto.isEmpty() ? 1 : mListPhoto.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.custom_pager_image_view, container, false);
        final Holder holder = new Holder(rootView);
        if (mListPhoto != null && mListPhoto.size() > 0) {
            holder.mProgressBar.setVisibility(View.VISIBLE);
            Picasso.with(holder.mImageView.getContext())
                    .load(mListPhoto.get(position))
                    .error(R.drawable.no_photo)
                    .into(holder.mImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.mProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            holder.mProgressBar.setVisibility(View.GONE);
                        }
                    });
        }
        container.addView(rootView);
        return rootView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public void swapAdapter(List<String> uriList) {
        mListPhoto = uriList;
        notifyDataSetChanged();
    }

    class Holder {
        @BindView(R.id.image_view_photo)
        ImageView mImageView;
        @BindView(R.id.image_view_photo_progress)
        ProgressBar mProgressBar;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}