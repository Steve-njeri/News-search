package com.stephen.newssearch.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stephen.newssearch.Constants;
import com.stephen.newssearch.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsService {
    public static void findNews(String source, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.NEWS_SOURCE_QUERY_PARAMETER, source);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.API_KEY)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static ArrayList<Article> processResults(Response response){
        ArrayList<Article> articles= new ArrayList<>();
        try {
            String json = response.body().string();
            if (response.isSuccessful()){
                JSONObject jsonObject = new JSONObject(json);
                JSONArray newsJson = jsonObject.getJSONArray("articles");
                Type collectionType = new TypeToken<List<Article>>() {}.getType();
                Gson gson = new GsonBuilder().create();
                articles = gson.fromJson(newsJson.toString(), collectionType);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
