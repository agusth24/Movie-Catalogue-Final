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

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    ProgressBar progressBar;
    MoviesItems movie = new MoviesItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        TextView tvTitle = findViewById(R.id.tv_titleTv);
        ImageView img = findViewById(R.id.img_detailtv);
        TextView tvOverview = findViewById(R.id.tv_overviewTv);
        progressBar = findViewById(R.id.progressBar);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        showLoading(true);
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(this)
                    .load(movie.getPhoto())
                    .into(img);
            showLoading(false);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
