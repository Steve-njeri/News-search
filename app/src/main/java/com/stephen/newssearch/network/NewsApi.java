package com.stephen.newssearch.network;

import com.stephen.newssearch.models.NewsSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything")
    Call<NewsSearchResponse> getTopHeadlines(
            @Query("q") String q,
            @Query("ApiKey") String ApiKey
    );
}
