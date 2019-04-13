package com.example.moviedb.movies;

import com.example.moviedb.http.MoviesApiService;
import com.example.moviedb.http.MoviesExtraInfoApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter provideMoviesPresenter(MoviesMVP.Model moviesModel){
        return new MoviesPresenter(moviesModel);
    }

    @Provides
    public MoviesMVP.Model provideMovieModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideMovieRepository(MoviesApiService moviesApiService, MoviesExtraInfoApiService extraInfoApiService){
        return new MoviesRepository(moviesApiService, extraInfoApiService);
    }
}
