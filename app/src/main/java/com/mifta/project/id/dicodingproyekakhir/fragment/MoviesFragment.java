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
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.activity.ReminderActivity;
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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchMovie(menu);
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

    private void searchMovie(Menu menu) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        final MoviesViewModel moviesViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel.class);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_movie_hint));
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
                    moviesViewModel.searchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals("")) {
                        moviesViewModel.searchMovie(newText);
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