package com.stephen.newssearch;

public class Constants {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "13001a04e0014dcbbfc15b81451eec4c";
    public static final String NEWS_SOURCE_QUERY_PARAMETER = "source";

    //Data persistence _ Shared Preferences
    public static final String PREFERENCES_SOURCE_KEY = "source";

    //Child node name for saving the values for searched Sources in Firebase
    public static final String FIREBASE_CHILD_SEARCHED_SOURCE = "searchedSource";

    //News node key
    public static final String FIREBASE_CHILD_NEWS = "news";

    //"index" key of our Article objects used for referencing objects during querying
    public static final String FIREBASE_QUERY_INDEX = "index";

    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_NEWS = "articles";

}

