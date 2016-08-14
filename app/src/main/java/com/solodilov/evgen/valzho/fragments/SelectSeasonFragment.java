package com.solodilov.evgen.valzho.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.solodilov.evgen.valzho.R;
import com.solodilov.evgen.valzho.Seasons;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectSeasonFragment extends Fragment {
    private OnFragmentSelectionSeason mListener;

    public static SelectSeasonFragment newInstance() {
        SelectSeasonFragment fragment = new SelectSeasonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_season, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    public void onButtonPressed(Seasons season) {
        if (mListener != null) {
            mListener.onFragmentSeason(season);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentSelectionSeason) {
            mListener = (OnFragmentSelectionSeason) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.button_winter, R.id.button_spring, R.id.button_autumn, R.id.button_summer})
    public void clickSeason(View view) {
        Seasons selectSeason = null;
        switch (view.getId()) {
            case R.id.button_winter:
                selectSeason = Seasons.WINTER;
                break;
            case R.id.button_summer:
                selectSeason = Seasons.SUMMER;
                break;
            case R.id.button_spring:
                selectSeason = Seasons.SPRING;
                break;
            case R.id.button_autumn:
                selectSeason = Seasons.AUTUMN;
                break;
            default:
                break;
        }
        Toast.makeText(getContext(), selectSeason.toString(), Toast.LENGTH_SHORT).show();
        onButtonPressed(selectSeason);
    }

    public interface OnFragmentSelectionSeason {
        void onFragmentSeason(Seasons season);
    }
}
