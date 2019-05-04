
package com.example.moviemenu.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "year")
    @SerializedName("year")
    @Expose
    private String year;
    @ColumnInfo(name = "genre")
    @SerializedName("genre")
    @Expose
    private String genre;
    @ColumnInfo(name = "poster")
    @SerializedName("poster")
    @Expose
    private String poster;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

}
