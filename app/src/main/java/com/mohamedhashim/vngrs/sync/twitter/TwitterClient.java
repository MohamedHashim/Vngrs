package com.mohamedhashim.vngrs.sync.twitter;

import com.mohamedhashim.vngrs.models.Tweet;

import java.util.List;

import rx.Observable;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public interface TwitterClient {

    Observable<List<Tweet>> search(String query);
}
