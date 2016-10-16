package com.solodilov.evgen.valzho.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.solodilov.evgen.valzho.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DevDetailActivity extends AppCompatActivity {
    @BindView(R.id.dev_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_detail_activity);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.dev_calling)
    public void onCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+380676330644".trim()));
        startActivity(intent);
    }
}