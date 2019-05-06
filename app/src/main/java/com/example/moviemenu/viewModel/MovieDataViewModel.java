package com.example.moviemenu.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import com.example.moviemenu.model.Repository;
import com.example.moviemenu.model.entity.MovieList;
import com.example.moviemenu.model.utils.ApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDataViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> responseLiveData2 = new MutableLiveData<>();


    public MovieDataViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> getMoviesResponse() {
        return responseLiveData;
    }

    public void getMovies(SharedPreferences sharedPreferences) {
        disposables.add(
                repository.fetchMovies(sharedPreferences)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> responseLiveData.setValue(ApiResponse.loading()))
                        .subscribe(
                                result -> {
                                    responseLiveData.setValue(ApiResponse.success(result));
                                    responseLiveData2.setValue(insertDB(result));
                                },
                                throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                        )
        );
    }

    public Boolean insertDB(MovieList movieList) {
        repository.insertCache(movieList);

        return repository.refreshDB;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
