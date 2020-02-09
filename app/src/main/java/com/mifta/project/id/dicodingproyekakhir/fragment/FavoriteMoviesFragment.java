package com.mifta.project.id.dicodingproyekakhir.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.ListMovieAdapter;
import com.mifta.project.id.dicodingproyekakhir.database.MoviesHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment {


    private RecyclerView rvMovie;
    private MoviesHelper movieHelper;

    private ListMovieAdapter adapter;
    private ArrayList<MoviesItems> listMovies;


    public FavoriteMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movies);

        movieHelper = MoviesHelper.getInstance(getContext());
        listMovies = new ArrayList<>();
        adapter = new ListMovieAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        movieHelper.open();
        listMovies.clear();
        listMovies.addAll(movieHelper.getAllMovies());
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
        movieHelper.close();
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), MoviesDetailActivity.class);
        moveWithObjectActivity.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }

}
