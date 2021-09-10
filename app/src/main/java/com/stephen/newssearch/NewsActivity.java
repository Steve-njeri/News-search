package com.stephen.newssearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.newsTextView) TextView mNewsTextView;
    @BindView(R.id.listView) ListView mListView;
    private String[] news = new String[] {
            "Anti-Abortion Whistleblowing Site Gets New Home With Provider Known for Hosting Extremists - The Daily Beast",
            "Biden promised ISIS-K will 'pay.' Having no US troops in Afghanistan makes that harder - CNN",
            "Religious exemptions to vaccine mandates could test 'sincerely held beliefs' - NBC News",
            "Afghanistan: 'Everyone got it wrong' on Taliban takeover - armed forces chief - BBC News",
            "Five sailors declared dead in Navy helicopter crash off Southern California - USA TODAY",
            "New York City was never built to withstand a deluge like the one Ida delivered. It showed. - CNN",
            "Child Covid-19 Cases Rise in States Where Schools Opened Earliest - The Wall Street Journal",
            "Gavin Newsom bets fear can fend off voters' fury at his misrule in CA recall election - New York Post",
            "On Grand Isle, a Fragile Spot Off Louisiana, Vast Damage - The New York Times",
            "Identities released of 3 people found dead after Millcreek standoff - KUTV 2News"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, news);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String news = ((TextView)view).getText().toString();
                Log.v("NewsActivity", "In the onItemClickListener!");
                Toast.makeText(NewsActivity.this, news, Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();
        String news = intent.getStringExtra("news");
        mNewsTextView.setText("Here are all the latest news: " + news);
        Log.d("NewsActivity", "In the onCreate method!");

        Api client = NewsClient.getClient();
        Call<NewsSearchResponse> call = client.getNews(news, "news");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(NewsActivity.this, MainActivity.class);
        startActivity(intent);

    }
}