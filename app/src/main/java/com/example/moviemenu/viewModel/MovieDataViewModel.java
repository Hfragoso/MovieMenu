package com.example.moviemenu.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviemenu.model.Repository;
import com.example.moviemenu.model.utils.ApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDataViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public MovieDataViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> getMoviesResponse() {
        return responseLiveData;
    }

    public void getMovies() {
        disposables.add(
                repository.fetchMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> responseLiveData.setValue(ApiResponse.loading()))
                        .subscribe(
                                result -> responseLiveData.setValue(ApiResponse.success(result)),
                                throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                        )
        );
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
