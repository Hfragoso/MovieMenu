package com.example.moviemenu.model;

import com.example.moviemenu.model.entity.MovieList;
import com.example.moviemenu.model.utils.ApiCallInterface;

import io.reactivex.Observable;


public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<MovieList> fetchMovies() {
        return apiCallInterface.getMovies();
    }
}
