package com.mifta.project.id.dicodingproyekakhir.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.ListMovieAdapter;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesViewModel;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private static ListMovieAdapter listMovieAdapter;
    private RecyclerView rvMovies;
    private ArrayList<MoviesItems> list = new ArrayList<>();
    private ProgressBar progressBar;


    public MoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progressBar);
        showRecyclerList();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void showRecyclerList() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovieAdapter = new ListMovieAdapter(list);
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);


        MoviesViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        moviesViewModel.setMovies();
        showLoading(true);

        listMovieAdapter.setOnItemClickCallBack(new ListMovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }

        });

        if (getActivity() != null) {
            moviesViewModel.getMovies().observe(getActivity(), new Observer<ArrayList<MoviesItems>>() {
                @Override
                public void onChanged(ArrayList<MoviesItems> moviesItems) {
                    if (moviesItems != null) {
                        listMovieAdapter.setData(moviesItems);
                        showLoading(false);
                    }

                }

            });
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), MoviesDetailActivity.class);
        moveWithObjectActivity.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }

}