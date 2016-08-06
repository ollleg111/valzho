package com.solodilov.evgen.valzho.connections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class TestInternet {
    private Context mContext;

    public TestInternet(Context context) {
        mContext = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
