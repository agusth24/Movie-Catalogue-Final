package com.mifta.project.id.dicodingproyekakhir.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.activity.MoviesDetailActivity;
import com.mifta.project.id.dicodingproyekakhir.activity.ReminderActivity;
import com.mifta.project.id.dicodingproyekakhir.adapter.ListMovieAdapter;
import com.mifta.project.id.dicodingproyekakhir.database.MappingHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.CONTENT_URI_MOVIE;

public class FavoriteMoviesFragment extends Fragment {
    private RecyclerView rvMovie;
    private ListMovieAdapter adapter;
    private ArrayList<MoviesItems> listMovies;

    public FavoriteMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
