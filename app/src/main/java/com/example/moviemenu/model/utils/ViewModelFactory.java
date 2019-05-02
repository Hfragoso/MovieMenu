package com.example.moviemenu.model.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.moviemenu.model.Repository;
import com.example.moviemenu.viewModel.MovieDataViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDataViewModel.class)) {
            return (T) new MovieDataViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown class name");
        }
    }
}
