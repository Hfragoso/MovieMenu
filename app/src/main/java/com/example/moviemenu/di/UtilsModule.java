package com.example.moviemenu.di;

import android.arch.lifecycle.ViewModelProvider;

import com.example.moviemenu.model.Repository;
import com.example.moviemenu.model.room.MovieDatabase;
import com.example.moviemenu.model.utils.ApiCallInterface;
import com.example.moviemenu.model.utils.EndPoints;
import com.example.moviemenu.model.utils.ViewModelFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        })
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    Repository getRepository(ApiCallInterface apiCallInterface, MovieDatabase movieDatabase) {
        return new Repository(apiCallInterface, movieDatabase);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository myRepository) {
        return new ViewModelFactory(myRepository);
    }
}
