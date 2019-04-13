package com.example.moviedb.http;

import com.example.moviedb.http.apimodel.TopMoviesRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<TopMoviesRated> getTopMoviesRated(@Query("api_key") String api_key,
                                                 @Query("page") Integer page);
}
