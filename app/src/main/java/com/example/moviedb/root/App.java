package com.example.moviedb.root;

import android.app.Application;

import com.example.moviedb.http.MovieExtraInfoApiModule;
import com.example.moviedb.http.MovieTitleApiModule;
import com.example.moviedb.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate(){
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .movieTitleApiModule(new MovieTitleApiModule())
                .movieExtraInfoApiModule(new MovieExtraInfoApiModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
