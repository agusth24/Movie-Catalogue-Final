package com.mifta.project.id.dicodingproyekakhir.activity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mifta.project.id.dicodingproyekakhir.R;
import com.mifta.project.id.dicodingproyekakhir.database.TvShowHelper;
import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import static android.provider.BaseColumns._ID;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.CONTENT_URI_TV;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.PHOTO;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.TITLE;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    private ProgressBar progressBar;
    private MoviesItems movie = new MoviesItems();
    private TvShowHelper tvShowHelper;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        TextView tvTitle = findViewById(R.id.tv_titleTv);
        ImageView img = findViewById(R.id.img_detailtv);
        TextView tvOverview = findViewById(R.id.tv_overviewTv);
        progressBar = findViewById(R.id.progressBar);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvShowHelper = TvShowHelper.getInstance(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        tvShowHelper.open();
        if (tvShowHelper.isExist(movie.getId())) {
            menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_favorite_on);
        }
        tvShowHelper.close();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_favorite) {
            tvShowHelper.open();
            if (!tvShowHelper.isExist(this.movie.getId())) {
                item.setIcon(R.drawable.ic_favorite_on);
                addToFavorite();
            } else {
                item.setIcon(R.drawable.ic_favorite_off);
                removeFromFavorite();
            }
            tvShowHelper.close();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movie.getId());
        contentValues.put(TITLE, movie.getTitle());
        contentValues.put(OVERVIEW, movie.getOverview());
        contentValues.put(PHOTO, movie.getPhoto());

        getContentResolver().insert(CONTENT_URI_TV, contentValues);
        Toast.makeText(this, getResources().getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        uri = Uri.parse(CONTENT_URI_TV + "/" + movie.getId());
        getContentResolver().delete(uri, null, null);
        Toast.makeText(this, getResources().getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
    }
}
