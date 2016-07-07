package com.epicodus.movieapp.services;

import android.util.Log;

import com.epicodus.movieapp.Constants;
import com.epicodus.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public ArrayList<Movie> processResults(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject tmdbJSON = new JSONObject(jsonData);
                JSONArray resultsJSON = tmdbJSON.getJSONArray("results");
                for (int i = 0; i < resultsJSON.length(); i++) {
                    JSONObject movieJSON = resultsJSON.getJSONObject(i);
                    String title = movieJSON.getString("original_title");

                    Movie movie = new Movie(title);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
