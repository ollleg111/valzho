package com.solodilov.evgen.valzho.api;

import java.io.Serializable;
import java.util.List;

public class Model implements Serializable {
    private Long mId;
    private String mModelName;
    private String mArraySize;
    private String mDescription;
    private List<String> mPhotoURL;

    public Model() {
    }

    public Model(Long id, String modelName, String arraySizes, String description, List<String> photos) {
        mId = id;
        mModelName = modelName;
        mArraySize = arraySizes;
        mDescription = description;
        mPhotoURL = photos;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getmModelName() {
        return mModelName;
    }

    public void setmModelName(String mModelName) {
        this.mModelName = mModelName;
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

    public void setmArraySize(String mArraySize) {
        this.mArraySize = mArraySize;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public List<String> getmPhotoURL() {
        return mPhotoURL;
    }

    public void setmPhotoURL(List<String> mPhotoURL) {
        this.mPhotoURL = mPhotoURL;
    }
}
