package com.mifta.project.id.dicodingproyekakhir.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.mifta.project.id.dicodingproyekakhir";
    public static final String TABLE_MOVIES = "Movies";
    public static final String TABLE_TV_SHOW = "TVShow";
    private static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class TableColumns implements BaseColumns {
        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIES)
                .build();
        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV_SHOW)
                .build();
        public static String TITLE = "title";
        public static String DATE = "date";
        public static String PHOTO = "photo";
        public static String RATING = "rating";
        public static String OVERVIEW = "overview";
        public static String COUNTRY = "country";
    }
}
