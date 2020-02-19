package com.mifta.project.id.dicodingproyekakhir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TABLE_TV_SHOW;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.PHOTO;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.TITLE;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns._ID;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TV_SHOW;
    private static DatabaseHelper dataBaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MoviesItems> getAllTv() {
        ArrayList<MoviesItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MoviesItems movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new MoviesItems();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null, _ID + " = ?", new String[]{id}, null, null, null, null);
    }

    public Cursor query() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " ASC", null);
    }

    public long insert(MoviesItems movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(PHOTO, movie.getPhoto());
        return database.insert(DATABASE_TABLE, null, args);
    }


    public int delete(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public boolean isExist(int id) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }
        cursor.close();
        return exist;
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
