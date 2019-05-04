package com.example.moviemenu.model.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moviemenu.model.entity.Movie;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DaoMovieAccess {
    @Insert
    Long insertMovie(Movie movie);

    @Query("SELECT * FROM movie")
    List<Movie> getCachedMovie();

    @Query("DELETE FROM movie")
    void deleteMovies();
}
