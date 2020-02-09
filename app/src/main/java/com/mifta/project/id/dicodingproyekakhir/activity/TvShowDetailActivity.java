package com.mifta.project.id.dicodingproyekakhir.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.database.TvShowHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    ProgressBar progressBar;
    MoviesItems movie = new MoviesItems();
    private TvShowHelper tvShowHelper;
    int zero = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        TextView tvTitle = findViewById(R.id.tv_titleTv);
        ImageView img = findViewById(R.id.img_detailtv);
        TextView tvOverview = findViewById(R.id.tv_overviewTv);
        progressBar = findViewById(R.id.progressBar);

        showLoading(true);
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(img);
            showLoading(false);
        }

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (tvShowHelper.isExist(movie.getId())) {
            menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_favorite_on);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_favorite) {
            if (!tvShowHelper.isExist(this.movie.getId())) {
                item.setIcon(R.drawable.ic_favorite_on);
                addToFavorite();
            } else {
                item.setIcon(R.drawable.ic_favorite_off);
                removeFromFavorite();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToFavorite() {
        long result = tvShowHelper.insert(this.movie);
        if (result > zero)
            Toast.makeText(this, getResources().getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this, getResources().getString(R.string.fail_favorite), Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        int result = tvShowHelper.delete(movie.getId());
        if (result > zero) {
            Toast.makeText(this, getResources().getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.fail_remove), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }
}
