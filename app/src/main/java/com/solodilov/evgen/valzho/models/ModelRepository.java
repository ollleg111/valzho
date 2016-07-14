package com.solodilov.evgen.valzho.models;

import com.solodilov.evgen.valzho.Seasons;

public interface ModelRepository {
    void loadModelList(Seasons season);

    void registerObserver(ObserverRepository o);

    void removeObserver();

    void notifyObserver();
}
