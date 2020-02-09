package com.mifta.project.id.dicodingproyekakhir.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIES,
            DatabaseContract.MoviesColumns._ID,
            DatabaseContract.MoviesColumns.TITLE,
            DatabaseContract.MoviesColumns.DATE,
            DatabaseContract.MoviesColumns.PHOTO,
            DatabaseContract.MoviesColumns.RATING,
            DatabaseContract.MoviesColumns.OVERVIEW,
            DatabaseContract.MoviesColumns.COUNTRY
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TV_SHOW,
            DatabaseContract.TvShowColumns._ID,
            DatabaseContract.TvShowColumns.TITLE,
            DatabaseContract.TvShowColumns.OVERVIEW,
            DatabaseContract.TvShowColumns.PHOTO
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV_SHOW);
        onCreate(db);
    }
}
