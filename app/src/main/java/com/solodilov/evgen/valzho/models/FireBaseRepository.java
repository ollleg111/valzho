package com.solodilov.evgen.valzho.models;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solodilov.evgen.valzho.Seasons;
import com.solodilov.evgen.valzho.api.Model;

import java.util.LinkedList;
import java.util.List;

public class FireBaseRepository implements ModelRepository {
    private static final String LOG = FireBaseRepository.class.getCanonicalName();
    private FirebaseAuth mAuth;
    private final List<Model> modelList = new LinkedList<>();
    private ObserverRepository mObserverRepositoryList;

    @Override
    public void loadModelList(Seasons season) {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        if (modelList != null && !modelList.isEmpty()) modelList.clear();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        if (season == Seasons.ALL) {
            for (Seasons s : Seasons.values()) {
                if (s != Seasons.ALL)
                    getDataSeason(s, mReference);
            }
        }
        getDataSeason(season, mReference);

    }

    private void getDataSeason(Seasons season, DatabaseReference mReference) {
        DatabaseReference mReferenceChild = mReference.child(season.name());
        mReferenceChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {
                            Model m = snapshot.getValue(Model.class);
                            modelList.add(m);
                        } catch (Exception e) {
                            Log.e(LOG, snapshot.toString(), e);
                        }
                    }
                }
                notifyObserver();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void registerObserver(ObserverRepository o) {
        mObserverRepositoryList = o;
    }

    @Override
    public void removeObserver(@Nullable ObserverRepository o) {
        mObserverRepositoryList = null;
    }

    @Override
    synchronized public void notifyObserver() {
        mObserverRepositoryList.update(modelList);
    }
}