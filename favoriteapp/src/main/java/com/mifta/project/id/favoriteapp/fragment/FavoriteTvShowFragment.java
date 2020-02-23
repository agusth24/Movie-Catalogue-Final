package com.mifta.project.id.favoriteapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
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
import com.mifta.project.id.favoriteapp.activity.TvShowDetailActivity;
import com.mifta.project.id.favoriteapp.adapter.CardViewTvShowAdapter;
import com.mifta.project.id.favoriteapp.model.MoviesItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.CONTENT_URI_MOVIE;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.CONTENT_URI_TV;
import static com.mifta.project.id.favoriteapp.database.MappingHelper.mapCursorTvToArrayList;

interface LoadTvCallback {
    void preExecute();

    void postExecute(Cursor cursor);
}

public class FavoriteTvShowFragment extends Fragment implements LoadTvCallback {
    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";
    private CardViewTvShowAdapter adapter;
    private RecyclerView rvTvShow;

    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShow = view.findViewById(R.id.rv_tvshow);
        adapter = new CardViewTvShowAdapter(getContext());

        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        FavoriteTvShowFragment.DataObserver favTvObserver = new FavoriteTvShowFragment.DataObserver(handler, getContext());

        if (getActivity() != null) {
            getActivity().getContentResolver().registerContentObserver(CONTENT_URI_MOVIE, true, favTvObserver);
        }

        rvTvShow.setAdapter(adapter);
        adapter.setOnItemClickCallback(new CardViewTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }
        });

        if (savedInstanceState == null) {
            new FavoriteTvShowFragment.LoadTvAsync(getContext(), this).execute();
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
        Intent moveWithObjectActivity = new Intent(getContext(), TvShowDetailActivity.class);
        moveWithObjectActivity.putExtra(TvShowDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        new FavoriteTvShowFragment.LoadTvAsync(getContext(), this).execute();
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
        ArrayList<MoviesItems> moviesItems = mapCursorTvToArrayList(cursor);
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
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvCallback> weakCallback;

        private LoadTvAsync(Context context, LoadTvCallback callback) {
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
            return context.getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }
    }
}
