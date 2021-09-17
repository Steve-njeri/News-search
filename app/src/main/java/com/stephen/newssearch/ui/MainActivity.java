package com.stephen.newssearch.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.findSearchNewsButton) Button mFindSearchNewsButton;
    @BindView(R.id.sourceEditText) EditText mSourceEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mFindSearchNewsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == mFindSearchNewsButton) {
            String source = mSourceEditText.getText().toString();
            addToSharedPreferences(source);
            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
            intent.putExtra("source", source);
            startActivity(intent);
        }

    }

    private void addToSharedPreferences(String source) {
        mEditor.putString(Constants.PREFERENCES_SOURCE_KEY, source).apply();
    }
}