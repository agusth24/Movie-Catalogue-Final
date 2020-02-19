package com.mifta.project.id.dicodingproyekakhir.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.ReminderActivity;
import com.mifta.project.id.dicodingproyekakhir.activity.TvShowDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.CardViewTvShowAdapter;
import com.mifta.project.id.dicodingproyekakhir.database.TvShowHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

public class FavoriteTvShowFragment extends Fragment {
    private static CardViewTvShowAdapter cardViewTvShowAdapter;
    private RecyclerView rvTvShow;
    private TvShowHelper tvShowHelper;
    private ArrayList<MoviesItems> listMovies;

    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShow = view.findViewById(R.id.rv_tvshow);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        listMovies = new ArrayList<>();
        cardViewTvShowAdapter = new CardViewTvShowAdapter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        } else if (item.getItemId() == R.id.notification) {
            Intent intent = new Intent(getActivity(), ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        tvShowHelper.open();
        listMovies.clear();
        listMovies.addAll(tvShowHelper.getAllTv());
        cardViewTvShowAdapter.setData(listMovies);
        cardViewTvShowAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTvShow.setLayoutManager(layoutManager);
        rvTvShow.setAdapter(cardViewTvShowAdapter);
        cardViewTvShowAdapter.setOnItemClickCallback(new CardViewTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesItems data) {
                showSelectedMovie(data);
            }
        });
        tvShowHelper.close();
    }

    private void showSelectedMovie(MoviesItems movie) {
        Intent moveWithObjectActivity = new Intent(getContext(), TvShowDetailActivity.class);
        moveWithObjectActivity.putExtra(TvShowDetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectActivity);
    }
}
