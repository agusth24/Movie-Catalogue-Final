package com.mifta.project.id.favoriteapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mifta.project.id.favoriteapp.R;
import com.mifta.project.id.favoriteapp.model.MoviesItems;

public class MoviesDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    private ProgressBar progressBar;
    private MoviesItems movie = new MoviesItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvDate = findViewById(R.id.tv_date);
        TextView tvRating = findViewById(R.id.tv_rating);
        TextView tvCountry = findViewById(R.id.tv_country);
        TextView tvOverview = findViewById(R.id.tv_overview);
        ImageView img = findViewById(R.id.img_detail);
        TextView tvxMovie = findViewById(R.id.tvx_movieinfo);
        TextView tvxRating = findViewById(R.id.tvx_rating);
        TextView tvxCountry = findViewById(R.id.tvx_country);
        TextView tvxOverview = findViewById(R.id.tvx_overview);
        progressBar = findViewById(R.id.progressBar);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        showLoading(true);
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvDate.setText(movie.getDate());
            tvRating.setText(movie.getRating());
            tvCountry.setText(movie.getCountry());
            tvOverview.setText(movie.getOverview());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(img);
            showLoading(false);
        }

        tvxMovie.setText(getResources().getString(R.string.movie_info));
        tvxRating.setText(getResources().getString(R.string.rating));
        tvxCountry.setText(getResources().getString(R.string.country));
        tvxOverview.setText(getResources().getString(R.string.overview));
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
