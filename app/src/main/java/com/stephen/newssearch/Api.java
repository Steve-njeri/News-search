package com.stephen.newssearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("search")
    Call<NewsSearchResponse> getNews(
            @Query("news") String news,
            @Query("term") String term
    );
}
