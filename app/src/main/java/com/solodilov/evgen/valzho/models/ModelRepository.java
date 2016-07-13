package com.solodilov.evgen.valzho.models;

import com.solodilov.evgen.valzho.SelectSeason;

public interface ModelRepository {
    void loadModelList(SelectSeason season);

    void registerObserver(ObserverRepository o);

    void removeObserver();

    void notifyObserver();
}
