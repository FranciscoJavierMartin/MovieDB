package com.example.moviedb.movies;

import com.example.moviedb.http.MoviesApiService;
import com.example.moviedb.http.MoviesExtraInfoApiService;

import io.reactivex.Observable;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApiService extraInfoApiService;

    public MoviesRepository(MoviesApiService mService, MoviesExtraInfoApiService eService){
        this.moviesApiService = mService;
        this.extraInfoApiService = eService;
    }

    @Override
    public Observable<Result> getResultData() {
        return null;
    }

    @Override
    public Observable<String> getCountryData() {
        return null;
    }
}
