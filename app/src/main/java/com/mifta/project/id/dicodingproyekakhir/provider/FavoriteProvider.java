package com.mifta.project.id.dicodingproyekakhir.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.mifta.project.id.dicodingproyekakhir.database.MoviesHelper;
import com.mifta.project.id.dicodingproyekakhir.database.TvShowHelper;

import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.AUTHORITY;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TABLE_MOVIES;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TABLE_TV_SHOW;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.CONTENT_URI_MOVIE;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.CONTENT_URI_TV;

public class FavoriteProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIES, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIES + "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_TV_SHOW, TV);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_TV_SHOW + "/#",
                TV_ID);
    }

    private MoviesHelper moviesHelper;
    private TvShowHelper tvShowHelper;

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        moviesHelper = MoviesHelper.getInstance(getContext());
        moviesHelper.open();

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = moviesHelper.query();
                break;
            case MOVIE_ID:
                cursor = moviesHelper.queryById(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvShowHelper.query();
                break;
            case TV_ID:
                cursor = tvShowHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uri_;
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = moviesHelper.insertProvider(values);
                uri_ = Uri.parse(CONTENT_URI_MOVIE + "/" + added);
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case TV:
                added = tvShowHelper.insertProvider(values);
                uri_ = Uri.parse(CONTENT_URI_TV + "/" + added);
                getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
                break;
            default:
                throw new SQLException("FailedAdded " + uri);
        }

        return uri_;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int drop;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                drop = moviesHelper.deleteProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_MOVIE, null);
                break;
            case TV_ID:
                drop = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
                break;
            default:
                drop = 0;
                break;
        }
        return drop;
    }

}
