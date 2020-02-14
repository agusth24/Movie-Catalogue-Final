package com.mifta.project.id.dicodingproyekakhir.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.ListMovieAdapter;
import com.mifta.project.id.dicodingproyekakhir.database.MappingHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.CONTENT_URI_MOVIE;

public class FavoriteMoviesFragment extends Fragment {
    private RecyclerView rvMovie;
    private ListMovieAdapter adapter;
    private ArrayList<MoviesItems> listMovies;
    private static final String TAG = "FavoriteMovieFragment";
    private FragmentManager fragmentManager;

    public FavoriteMoviesFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FavoriteMoviesFragment favoriteMoviesFragment = new FavoriteMoviesFragment();
        if (favoriteMoviesFragment != null && favoriteMoviesFragment.isAdded() ){
            return null;
        }
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movies);
        listMovies = new ArrayList<>();
        adapter = new ListMovieAdapter(getContext());


    }

    @Override
    public void onStart() {
        super.onStart();

        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        listMovies.clear();
        listMovies.addAll(MappingHelper.mapCursorToArrayList(cursor));
        adapter.setData(listMovies);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setAdapter(adapter);
        adapter.setOnItemClickCallBack(new ListMovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }

        });
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent intent = new Intent(getContext(), MoviesDetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + movie.getId());
        intent.setData(uri);
        intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

}
