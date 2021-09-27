package com.stephen.newssearch.ui;

import static com.stephen.newssearch.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.R;
import com.stephen.newssearch.adapters.NewsListAdapter;
import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.models.NewsSearchResponse;
import com.stephen.newssearch.network.NewsApi;
import com.stephen.newssearch.network.NewsClient;
import com.stephen.newssearch.util.OnNewsSelectedListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends AppCompatActivity implements OnNewsSelectedListener {
    private Integer mPosition;
    ArrayList<Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mArticles = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_NEWS));

                if (mPosition != null && mArticles != null){
                    Intent intent = new Intent(this, NewsDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
                    startActivity(intent);
                }
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        if (mPosition != null && mArticles != null){
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
        }
    }

    @Override
    public void onNewsSelected(Integer position, ArrayList<Article> articles) {
        mPosition = position;
        mArticles = articles;

    }
}