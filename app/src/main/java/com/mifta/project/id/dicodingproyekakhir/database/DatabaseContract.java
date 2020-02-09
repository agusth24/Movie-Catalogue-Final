package com.mifta.project.id.dicodingproyekakhir.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIES = "Movies";
    static String TABLE_TV_SHOW = "TVShow";

    public static final class MoviesColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DATE = "date";
        public static String PHOTO = "photo";
        public static String RATING = "rating";
        public static String OVERVIEW = "overview";
        public static String COUNTRY = "country";
    }

    public static final class TvShowColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String PHOTO = "photo";

    }
}
