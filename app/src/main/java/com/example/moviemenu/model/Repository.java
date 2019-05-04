package com.example.moviemenu.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.moviemenu.model.entity.Movie;
import com.example.moviemenu.model.entity.MovieList;
import com.example.moviemenu.model.room.DaoMovieAccess;
import com.example.moviemenu.model.room.MovieDatabase;
import com.example.moviemenu.model.utils.ApiCallInterface;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Repository {

    private ApiCallInterface apiCallInterface;

    public DaoMovieAccess daoMovie;

    public MovieDatabase movieDB;

    public boolean refreshDB;

    SharedPreferences sharedPreferences;

    public final static String LAST_CACHED_TIMESTAMP = "LAST_CACHED_TIMESTAMP";


    public Repository(ApiCallInterface apiCallInterface, MovieDatabase roomDatabase) {
        this.apiCallInterface = apiCallInterface;
        this.movieDB = roomDatabase;
        daoMovie = movieDB.daoMovieAccess();
    }

    public Observable<MovieList> fetchMovies(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        if (shouldRefreshData()) {
            return fetchFromRestApi();
        } else {
            return fetchFromCache();
        }
    }

    private boolean shouldRefreshData() {
        long lastCached = sharedPreferences.getLong(LAST_CACHED_TIMESTAMP, -1);
        long currentTimestamp = System.currentTimeMillis();
        long diff = (currentTimestamp - lastCached) / (60 * 1000);

        if (diff >= 0 && diff > 10)
            return true;
        else
            return false;
    }

    public Observable<MovieList> fetchFromRestApi() {
        refreshDB = true;
        return apiCallInterface.getMovies();
    }

    private Observable<MovieList> fetchFromCache() {
        refreshDB = false;
        return getCache();
    }

    private Observable<MovieList> getCache() {


        Observable<MovieList> observable = Observable.create(emitter -> {
            try {
                MovieList movieList = new MovieList();
                List<Movie> movies = daoMovie.getCachedMovie();
                movieList.setData(movies);

                emitter.onNext(movieList);

                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        return observable;
    }

    public void insertCache(MovieList movieList) {
        if (refreshDB) {
            sharedPreferences
                    .edit()
                    .putLong(LAST_CACHED_TIMESTAMP, System.currentTimeMillis())
                    .apply();

            Completable.fromAction(() -> {
                daoMovie.deleteMovies();
            }).subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            Completable.fromAction(() -> {
                                for (Movie item : movieList.getData()) {
                                    Movie movieItem = new Movie();
                                    movieItem.setGenre(item.getGenre());
                                    movieItem.setPoster(item.getPoster());
                                    movieItem.setTitle(item.getTitle());
                                    movieItem.setYear(item.getYear());
                                    daoMovie.insertMovie(movieItem);
                                }
                            }).subscribeOn(Schedulers.io())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            Log.e("ON_SUBSCRIBE", "Subscribe");
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.e("ON_COMPLETE", "Complete");
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e("ON_SUBSCRIBE", "Error");
                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }
}