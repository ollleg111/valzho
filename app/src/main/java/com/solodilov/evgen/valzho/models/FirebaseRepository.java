package com.solodilov.evgen.valzho.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solodilov.evgen.valzho.SelectSeason;
import com.solodilov.evgen.valzho.api.Model;

import java.util.LinkedList;
import java.util.List;

public class FirebaseRepository implements ModelRepository {
    private static final String LOG = FirebaseRepository.class.getCanonicalName();
    private FirebaseAuth mAuth;
    private List<Model> modelList = new LinkedList<>();
    private ObserverRepository mObserverRepository;

    @Override
    public void loadModelList(SelectSeason season) {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        if(season==SelectSeason.ALL){
            for(SelectSeason s : SelectSeason.values()){
                if(s!=SelectSeason.ALL)
                getDataSeasone(s,mReference);
            }
        }
        getDataSeasone(season, mReference);

    }

    private void getDataSeasone(SelectSeason season, DatabaseReference mReference) {
        DatabaseReference mReferenceChild = mReference.child(season.name());
        mReferenceChild.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Model m = snapshot.getValue(Model.class);
                        modelList.add(m);
                        Log.d(LOG, m.toString());
                    }
                    notifyObserver();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void registerObserver(ObserverRepository o) {
        mObserverRepository = o;
    }

    @Override
    public void removeObserver(ObserverRepository o) {
        mObserverRepository = null;
    }

    @Override
    synchronized public void notifyObserver() {

        mObserverRepository.update(modelList);
    }
}
