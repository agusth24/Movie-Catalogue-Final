package com.mifta.project.id.favoriteapp.database;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.mifta.project.id.favoriteapp.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.COUNTRY;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.DATE;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.PHOTO;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.RATING;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.TITLE;

public class MappingHelper {
    public static ArrayList<MoviesItems> mapCursorToArrayList(Cursor itemCursor) {
        ArrayList<MoviesItems> itemList = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE));
            String date = itemCursor.getString(itemCursor.getColumnIndexOrThrow(DATE));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(PHOTO));
            String rating = itemCursor.getString(itemCursor.getColumnIndexOrThrow(RATING));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW));
            String country = itemCursor.getString(itemCursor.getColumnIndexOrThrow(COUNTRY));

            itemList.add(new MoviesItems(id, title, date, photo, rating, overview, country));
        }
        return itemList;
    }

    public static ArrayList<MoviesItems> mapCursorTvToArrayList(Cursor itemCursor) {
        ArrayList<MoviesItems> itemList = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = itemCursor.getString(itemCursor.getColumnIndexOrThrow(TITLE));
            String photo = itemCursor.getString(itemCursor.getColumnIndexOrThrow(PHOTO));
            String overview = itemCursor.getString(itemCursor.getColumnIndexOrThrow(OVERVIEW));

            itemList.add(new MoviesItems(id, title, photo, overview));
        }
        return itemList;
    }
}
