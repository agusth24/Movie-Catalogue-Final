package com.mifta.project.id.favoriteapp.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mifta.project.id.favoriteapp.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.MY_TMDB_API_KEY;
    private MutableLiveData<ArrayList<MoviesItems>> listMovies = new MutableLiveData<>();

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MoviesItems> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MoviesItems moviesItems = new MoviesItems();
                        moviesItems.setId(movie.getInt("id"));
                        moviesItems.setTitle(movie.getString("name"));
                        moviesItems.setOverview(movie.getString("overview"));
                        moviesItems.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                        listItems.add(moviesItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }

        });
    }

    public void searchTvShow(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MoviesItems> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MoviesItems moviesItems = new MoviesItems();
                        moviesItems.setId(movie.getInt("id"));
                        moviesItems.setTitle(movie.getString("name"));
                        moviesItems.setOverview(movie.getString("overview"));
                        moviesItems.setPhoto("https://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
                        listItems.add(moviesItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", "ERROR: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "ERROR: " + error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MoviesItems>> getMovies() {
        return listMovies;
    }
}