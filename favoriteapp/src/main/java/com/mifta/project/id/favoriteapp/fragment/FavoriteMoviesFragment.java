package com.mifta.project.id.favoriteapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.favoriteapp.R;
import com.mifta.project.id.favoriteapp.activity.MoviesDetailActivity;
import com.mifta.project.id.favoriteapp.adapter.ListMovieAdapter;
import com.mifta.project.id.favoriteapp.model.MoviesItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.CONTENT_URI_MOVIE;
import static com.mifta.project.id.favoriteapp.database.MappingHelper.mapCursorToArrayList;


interface LoadMovieCallback {
    void preExecute();

    void postExecute(Cursor cursor);
}

public class FavoriteMoviesFragment extends Fragment implements LoadMovieCallback {
    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";
    private RecyclerView rvMovie;
    private ListMovieAdapter adapter;

    public FavoriteMoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movies);
        adapter = new ListMovieAdapter(getContext());
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver favMovieObserver = new DataObserver(handler, getContext());
        if (getActivity() != null) {
            getActivity().getContentResolver().registerContentObserver(CONTENT_URI_MOVIE, true, favMovieObserver);
        }
        rvMovie.setAdapter(adapter);
        adapter.setOnItemClickCallBack(new ListMovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }
        });

        if (savedInstanceState == null) {
            new LoadMovieAsync(getContext(), this).execute();
        } else {
            ArrayList<MoviesItems> moviesItems = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (moviesItems != null) {
                adapter.setData(moviesItems);
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, adapter.getMoviesItem());
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent intent = new Intent(getContext(), MoviesDetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + movie.getId());
        intent.setData(uri);
        intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync(getContext(), this).execute();
    }

    @Override
    public void preExecute() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<MoviesItems> moviesItems = mapCursorToArrayList(cursor);
        if (moviesItems.size() > 0) {
            adapter.setData(moviesItems);
        } else {
            Toast.makeText(getActivity(), "Data tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        }
    }
}
