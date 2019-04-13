package com.example.moviedb.movies;

import io.reactivex.Observable;

public interface Repository {

    Observable<Result> getResultData();

    Observable<String> getCountryData();
}
