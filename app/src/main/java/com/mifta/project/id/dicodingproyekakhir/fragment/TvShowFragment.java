package com.mifta.project.id.dicodingproyekakhir.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.TvShowDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.CardViewTvShowAdapter;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;
import com.mifta.project.id.dicodingproyekakhir.model.TvShowViewModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {

    private RecyclerView rvTvShow;
    private ArrayList<MoviesItems> list = new ArrayList<>();
    private ProgressBar progressBar;
    private static CardViewTvShowAdapter cardViewTvShowAdapter;


    public TvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvTvShow = view.findViewById(R.id.rv_tvshow);
        progressBar = view.findViewById(R.id.progressBar);
        showRecyclerList();
        return view;
    }

    private void showRecyclerList() {
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardViewTvShowAdapter = new CardViewTvShowAdapter(list);
        cardViewTvShowAdapter.notifyDataSetChanged();
        rvTvShow.setAdapter(cardViewTvShowAdapter);


        TvShowViewModel tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        tvShowViewModel.setTvShow();
        showLoading(true);

        cardViewTvShowAdapter.setOnItemClickCallback(new CardViewTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }
        });

        if (getActivity() != null) {
            tvShowViewModel.getMovies().observe(getActivity(), new Observer<ArrayList<MoviesItems>>() {
                @Override
                public void onChanged(ArrayList<MoviesItems> moviesItems) {
                    if (moviesItems != null) {
                        cardViewTvShowAdapter.setData(moviesItems);
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
        Intent moveWithObjectActivity = new Intent(getContext(), TvShowDetailActivity.class);
        moveWithObjectActivity.putExtra(TvShowDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }
}