package com.stephen.newssearch.network;

import com.stephen.newssearch.models.Article;
import com.stephen.newssearch.models.NewsSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything")
    Call<NewsSearchResponse> getTopHeadlines(
            @Query("q") List<Article> q,
            @Query("ApiKey") String ApiKey
    );
}
