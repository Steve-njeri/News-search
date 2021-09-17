package com.stephen.newssearch.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stephen.newssearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findSearchNewsButton) Button mFindSearchNewsButton;
    @BindView(R.id.sourceEditText) EditText mSourceEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindSearchNewsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == mFindSearchNewsButton) {
            String source = mSourceEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
            intent.putExtra("source", source);
            startActivity(intent);
        }

    }
}