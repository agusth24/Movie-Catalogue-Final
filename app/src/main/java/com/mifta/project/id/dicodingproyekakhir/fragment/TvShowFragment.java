package com.mifta.project.id.dicodingproyekakhir.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.ReminderActivity;
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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchTvShow(menu);
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

    private void searchTvShow(Menu menu) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final TvShowViewModel tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_tv_hint));
            searchView.setIconifiedByDefault(false);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.setQuery(query, false);
                    searchView.setIconified(false);
                    searchView.clearFocus();
                    tvShowViewModel.searchTvShow(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals("")) {
                        tvShowViewModel.searchTvShow(newText);
                    }
                    return true;
                }
            });

            MenuItem searchItem = menu.findItem(R.id.search);
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    showRecyclerList();
                    return true;
                }
            });
        }
    }
}