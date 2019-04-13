package com.example.moviedb.movies;

import com.example.moviedb.http.MoviesApiService;
import com.example.moviedb.http.MoviesExtraInfoApiService;
import com.example.moviedb.http.apimodel.OmdbApi;
import com.example.moviedb.http.apimodel.Result;
import com.example.moviedb.http.apimodel.TopMoviesRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApiService extraInfoApiService;

    private List<String> countries;
    private List<Result> results;

    private long lastTimestamp;
    private static final long CACHE_LIFETIME = 20 * 1000; // 20 seconds

    public MoviesRepository(MoviesApiService mService, MoviesExtraInfoApiService eService){
        this.moviesApiService = mService;
        this.extraInfoApiService = eService;
        this.lastTimestamp = System.currentTimeMillis();

        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMoviesRated> topMoviesRatedObservable = moviesApiService.getTopMoviesRated(1)
                .concatWith(moviesApiService.getTopMoviesRated(2))
                .concatWith(moviesApiService.getTopMoviesRated(3));

        return topMoviesRatedObservable
                .concatMap(new Function<TopMoviesRated, Observable<? extends Result>>() {
                    @Override
                    public Observable<Result> apply(TopMoviesRated topMoviesRated) {
                        return Observable.fromIterable(topMoviesRated.getResults());
                    }
                }).doOnNext(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) {
                        results.add(result);
                    }
                });
    }

    @Override
    public Observable<Result> getResultFromCache() {
        Observable<Result> res;

        if(isUpdated()){
            res = Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            res = Observable.empty();
        }

        return res;
    }

    @Override
    public Observable<Result> getResultData() {
        return null;
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        return getResultFromNetwork().concatMap(new Function<Result, Observable<OmdbApi>>() {
            @Override
            public Observable<OmdbApi> apply(Result result) {
                return extraInfoApiService.getExtraInfoMovie(result.getTitle());
            }
        }).concatMap(new Function<OmdbApi, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(OmdbApi omdbApi) {
                return Observable.just(omdbApi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String country) {
                countries.add(country);
            }
        });
    }

    @Override
    public Observable<String> getCountryFromCache() {
        Observable<String> res;

        if(isUpdated()){
            res = Observable.fromIterable(countries);
        } else {
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            res = Observable.empty();
        }

        return res;
    }

    @Override
    public Observable<String> getCountryData() {
        return null;
    }

    private boolean isUpdated(){
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }
}
