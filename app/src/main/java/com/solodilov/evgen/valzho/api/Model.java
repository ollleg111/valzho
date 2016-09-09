package com.solodilov.evgen.valzho.api;

import java.io.Serializable;
import java.util.List;

public class Model implements Serializable {
    private String mModelName;
    private String mArraySize;
    private String mDescription;
    private List<String> mPhotoURL;

    Model() {
    }

    public Model(String modelName, String arraySizes, String description, List<String> photos) {
        mModelName = modelName;
        mArraySize = arraySizes;
        mDescription = description;
        mPhotoURL = photos;
    }

    public String getmModelName() {
        return mModelName;
    }

    public String getmArraySize() {
        StringBuilder resp = new StringBuilder();
        resp.append("Доступные размеры: ");
        if (mArraySize != null) {
            resp.append(mArraySize);
        } else {
            resp.append("неизвестны");
        }
        return resp + ".";
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<String> getmPhotoURL() {
        return mPhotoURL;
    }
}
