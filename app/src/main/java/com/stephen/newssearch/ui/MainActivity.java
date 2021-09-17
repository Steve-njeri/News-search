package com.stephen.newssearch.ui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ValueEventListener mSearchedSourceReferenceListener;
    private DatabaseReference mSearchedSourceReference;

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;


    @BindView(R.id.findSearchNewsButton) Button mFindSearchNewsButton;
    @BindView(R.id.sourceEditText) EditText mSourceEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.savedNewsButton) Button mSavedNewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedSourceReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_SOURCE);

        mSearchedSourceReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sourceSnapshot : snapshot.getChildren()) {
                    String source = sourceSnapshot.getValue().toString();
                    Log.d("sources updated", "source: " + source);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //update UI here if error occurred.
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();

        mFindSearchNewsButton.setOnClickListener(this);
        mSavedNewsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == mFindSearchNewsButton) {
            String source = mSourceEditText.getText().toString();

            saveSourceToFirebase(source);

//            if(!(source).equals("")) {
//                addToSharedPreferences(source);
//            }
            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
            intent.putExtra("source", source);
            startActivity(intent);
        }

        if (v == mSavedNewsButton) {
            Intent intent = new Intent(MainActivity.this, SavedNewsListActivity.class);
            startActivity(intent);
        }

    }

    public void saveSourceToFirebase(String source) {
        mSearchedSourceReference.push().setValue(source);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedSourceReference.removeEventListener(mSearchedSourceReferenceListener);
    }
}