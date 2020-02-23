package com.mifta.project.id.favoriteapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.COUNTRY;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.DATE;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.OVERVIEW;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.PHOTO;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.RATING;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.TableColumns.TITLE;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.getColumnInt;
import static com.mifta.project.id.favoriteapp.database.DatabaseContract.getColumnString;

public class MoviesItems implements Parcelable {
    public static final Creator<MoviesItems> CREATOR = new Creator<MoviesItems>() {
        @Override
        public MoviesItems createFromParcel(Parcel in) {
            return new MoviesItems(in);
        }

        @Override
        public MoviesItems[] newArray(int size) {
            return new MoviesItems[size];
        }
    };
    private int id;
    private String photo;
    private String title;
    private String date;
    private String rating;
    private String overview;
    private String country;

    protected MoviesItems(Parcel in) {
        id = in.readInt();
        photo = in.readString();
        title = in.readString();
        date = in.readString();
        rating = in.readString();
        overview = in.readString();
        country = in.readString();
    }


    public MoviesItems() {
    }

    public MoviesItems(int id, String title, String date, String photo, String rating, String overview, String country) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.photo = photo;
        this.rating = rating;
        this.overview = overview;
        this.country = country;
    }

    public MoviesItems(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.date = getColumnString(cursor, DATE);
        this.photo = getColumnString(cursor, PHOTO);
        this.rating = getColumnString(cursor, RATING);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.country = getColumnString(cursor, COUNTRY);
    }

    public MoviesItems(int id, String title, String photo, String overview) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.overview = overview;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(photo);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(rating);
        dest.writeString(overview);
        dest.writeString(country);
    }
}