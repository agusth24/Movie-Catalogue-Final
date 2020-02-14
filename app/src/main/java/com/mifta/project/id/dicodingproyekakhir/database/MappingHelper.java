package com.mifta.project.id.dicodingproyekakhir.database;

import android.database.Cursor;

import com.mifta.project.id.dicodingproyekakhir.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.COUNTRY;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.DATE;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.PHOTO;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.RATING;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns.TITLE;
import static com.mifta.project.id.dicodingproyekakhir.database.DatabaseContract.TableColumns._ID;

public class MappingHelper {
    public static ArrayList<MoviesItems> mapCursorToArrayList(Cursor itemCursor) {
        ArrayList<MoviesItems> itemList = new ArrayList<>();

        while (itemCursor.moveToNext()) {
            int id = itemCursor.getInt(itemCursor.getColumnIndexOrThrow(_ID));
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
}
