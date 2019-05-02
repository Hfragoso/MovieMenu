package com.example.moviemenu.model.utils;


import com.example.moviemenu.model.entity.MovieList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCallInterface {

    @GET(EndPoints.GET_MOVIES)
    Observable<MovieList> getMovies();
}
