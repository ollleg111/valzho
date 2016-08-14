package com.solodilov.evgen.valzho.activitys;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.solodilov.evgen.valzho.BuildConfig;
import com.solodilov.evgen.valzho.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
@BindView(R.id.about_text_version)
    TextView mTextVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        String strVersion = "Version: "+ BuildConfig.VERSION_NAME;
        mTextVersion.setText(strVersion);
    }

}
