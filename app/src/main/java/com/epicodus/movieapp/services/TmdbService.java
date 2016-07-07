package com.epicodus.movieapp.services;

import android.util.Log;

import com.epicodus.movieapp.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TmdbService {

    public static void findMovie(String userInput, Callback callback){

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY, userInput);
        urlBuilder.addQueryParameter(Constants.API_QUERY_PARAMETER, Constants.KEY);


        //http://api.themoviedb.org/3/search/movie?query=potter&api_key=7b11a2c4c3202cd75486d2874bd92625

        String url = urlBuilder.build().toString();
        Log.v("url:", url);

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }


}
