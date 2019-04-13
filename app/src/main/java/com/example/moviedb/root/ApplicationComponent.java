package com.example.moviedb.root;

import com.example.moviedb.MainActivity;
import com.example.moviedb.http.MovieExtraInfoApiModule;
import com.example.moviedb.http.MovieTitleApiModule;
import com.example.moviedb.movies.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        MoviesModule.class,
        MovieTitleApiModule.class,
        MovieExtraInfoApiModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
}
