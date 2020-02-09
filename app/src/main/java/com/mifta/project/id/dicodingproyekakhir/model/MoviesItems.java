package com.mifta.project.id.dicodingproyekakhir.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesItems implements Parcelable {
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