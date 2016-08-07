package com.solodilov.evgen.valzho.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.api.Model;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {

    public interface OnShowCardModel {
        void onShowCardModel(Model model);
    }


    private static final String LOG_ = MyRVAdapter.class.getCanonicalName();
    private List<Model> mModelList;
    OnShowCardModel mOnShowCardModel;

    public MyRVAdapter(List<Model> modelList, Context context) {
        mOnShowCardModel = (OnShowCardModel) context;
        mModelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_mini, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mModelList != null) {
            final Model model = mModelList.get(position);
            holder.mTvModelName.setText(model.getmModelName());
            holder.mTvAvailableSize.setText(model.getmArraySize());

            if (model.getmPhotoURL() != null) {
                Picasso.with(holder.mTitleImage.getContext())
                        .load(model.getmPhotoURL().get(0))
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .fit()
                        .centerInside()
                        .into(holder.mTitleImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                holder.showImages(true);
                                Log.d(LOG_, "1111111");
                            }

                            @Override
                            public void onError() {
                                Picasso.with(holder.mTitleImage.getContext())
                                        .load(model.getmPhotoURL().get(0))
                                        .fit()
                                        .centerInside()
                                        .error(R.drawable.no_photo)
                                        .into(holder.mTitleImage, new com.squareup.picasso.Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        holder.showImages(true);
                                                        Log.d(LOG_, "1111111");
                                                    }

                                                    @Override
                                                    public void onError() {
                                                        holder.showImages(true);
                                                        Log.d(LOG_, "2222222");
                                                    }
                                                }
                                        );
                            }
                        });
            }
        }
        holder.showImages(true);
    }

    @Override
    public int getItemCount() {
        if (mModelList == null) {
            return 0;
        }
        return mModelList.size();
    }

    public void swapAdapter(List<Model> list) {
        mModelList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_model_name)
        TextView mTvModelName;
        @BindView(R.id.tv_available_sizes)
        TextView mTvAvailableSize;
        @BindView(R.id.title_image_progress)
        ProgressBar mProgressBar;
        @BindView(R.id.title_image)
        ImageView mTitleImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cv)
        public void onClick() {
            if (mOnShowCardModel != null)
                mOnShowCardModel.onShowCardModel(mModelList.get(getAdapterPosition()));
            Log.d(LOG_, this.toString() + " position " + this.getAdapterPosition());
        }

        public void showImages(boolean b) {
            if (b) {
                mProgressBar.setVisibility(View.GONE);
                mTitleImage.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mTitleImage.setVisibility(View.GONE);
            }
        }
    }
}