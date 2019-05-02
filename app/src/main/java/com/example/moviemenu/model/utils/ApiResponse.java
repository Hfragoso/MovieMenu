package com.example.moviemenu.model.utils;

import com.example.moviemenu.model.entity.MovieList;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.example.moviemenu.model.utils.Status.ERROR;
import static com.example.moviemenu.model.utils.Status.LOADING;
import static com.example.moviemenu.model.utils.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final MovieList data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable MovieList data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull MovieList data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }
}
