package com.example.moviemenu.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.moviemenu.model.room.DaoMovieAccess;
import com.example.moviemenu.model.room.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    MovieDatabase movieDatabase;

    public RoomModule(Context context) {
        movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, "movies_db").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    MovieDatabase provideMovieRoomDB() {
        return movieDatabase;
    }

    @Singleton
    @Provides
    DaoMovieAccess provideDaoMovieAccess() {
        return movieDatabase.daoMovieAccess();
    }
}
