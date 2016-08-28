package com.solodilov.evgen.valzho.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.activitys.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroFragment extends Fragment {
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.image_intro)
    ImageView mImageIntro;
    private OnFragmentInteractionListener mListener;
    private MainActivity mActivity;

    public static IntroFragment newInstance() {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        mActivity.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mActivity.getDrawerLayout().closeDrawers();
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        setStartAnimations();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivity.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }


    private void mShowAlertDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle("Error connection!")
                .setMessage("There is no Internet connection")
                .setCancelable(false)
                .setNegativeButton("ОК!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        new MyTask(context).execute();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    private void setStartAnimations() {
        mImageIntro.setScaleX(0.1f);
        mImageIntro.setScaleY(0.1f);
        mImageIntro.animate()
                .scaleX(1)
                .scaleY(1)
                .rotationY(-360)
                .setDuration(1500)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new MyTask(getActivity()).execute();
                    }
                });
    }

    private class MyTask extends AsyncTask<Void, Void, Boolean> {
        private Context context;

        MyTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                onButtonPressed();
            } else {
                mShowAlertDialog(context);
            }
        }
    }
}
