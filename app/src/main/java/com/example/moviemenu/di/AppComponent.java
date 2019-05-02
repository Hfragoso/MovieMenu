package com.example.moviemenu.di;

import com.example.moviemenu.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void doInjection(MainActivity mainActivity);
}
