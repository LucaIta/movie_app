package com.epicodus.movieapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.epicodus.movieapp.R;
import com.epicodus.movieapp.model.Movie;
import com.epicodus.movieapp.services.TmdbService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieInfoActivity extends AppCompatActivity {
    public static final String TAG = MovieInfoActivity.class.getSimpleName();

    @Bind(R.id.movieTitlesListView) ListView mListView;

    public ArrayList<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String userInput = intent.getStringExtra("userInput");

        getMovies(userInput);
    }

    private void getMovies(String userInput) {
        final TmdbService tmdbService = new TmdbService();
        tmdbService.findMovie(userInput, new Callback() {

        @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

        @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        Log.v(TAG, jsonData);
                        mMovies = tmdbService.processResults(response);

                        MovieInfoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String[] movieTitles = new String[mMovies.size()];
                                Log.v(TAG, "1");
                                for (int i = 0; i < movieTitles.length; i++) {
                                    movieTitles[i] = mMovies.get(i).getTitle();
                                }
                                ArrayAdapter adapter = new ArrayAdapter(MovieInfoActivity.this,
                                        android.R.layout.simple_list_item_1, movieTitles);
                                mListView.setAdapter(adapter);

                                for (Movie movie : mMovies) {
                                    Log.d(TAG, "Title: " + movie.getTitle());
                                }
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
