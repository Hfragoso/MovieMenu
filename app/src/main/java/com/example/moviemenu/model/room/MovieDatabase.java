package com.example.moviemenu.model.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.moviemenu.model.entity.Movie;

@Database(entities = {Movie.class}, version = 3, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract DaoMovieAccess daoMovieAccess();
}