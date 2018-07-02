package com.mohamedhashim.vngrs.sync.twitter;


import com.mohamedhashim.vngrs.sync.twitter.remote.TweetsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public interface TwitterRestService {
    @GET("search/tweets.json")
    Observable<TweetsResponse> search(@Query("q") String query);
}
