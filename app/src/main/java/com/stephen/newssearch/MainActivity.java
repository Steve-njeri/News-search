package com.stephen.newssearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mFindSearchNewsButton;
    private EditText mNewsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsEditText = (EditText) findViewById(R.id.newsEditText);
        mFindSearchNewsButton = (Button)findViewById(R.id.findSearchNewsButton);
        mFindSearchNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String news = mNewsEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });
    }
}