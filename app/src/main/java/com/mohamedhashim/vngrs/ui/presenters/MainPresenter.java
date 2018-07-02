package com.mohamedhashim.vngrs.ui.presenters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mohamedhashim.vngrs.VngrsApplication;
import com.mohamedhashim.vngrs.models.Tweet;
import com.mohamedhashim.vngrs.sync.twitter.TwitterClient;
import com.mohamedhashim.vngrs.ui.activities.MainActivity;
import com.mohamedhashim.vngrs.ui.utils.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action2;
import rx.functions.Func0;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public class MainPresenter extends RxPresenter<MainActivity> {
    private static final int REQUEST_TWEETS = 1;

    public static final String SEARCH_QUERY_KEY = MainPresenter.class.getName() + "#searchQuery";

    @Inject TwitterClient twitterClient;
    @Inject @Named(Constants.SUBSCRIBE_ON) Scheduler subscribeOn;
    @Inject @Named(Constants.OBSERVE_ON) Scheduler observeOn;

    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        VngrsApplication.getAppComponent(VngrsApplication.instance).inject(this);

        if(savedState != null) {
            searchQuery = savedState.getString(SEARCH_QUERY_KEY);
        }

        restartableLatestCache(REQUEST_TWEETS,
                new Func0<Observable<List<Tweet>>>() {
                    @Override
                    public Observable<List<Tweet>> call() {
                        return twitterClient.search(searchQuery)
                                .observeOn(observeOn)
                                .subscribeOn(subscribeOn);
                    }
                },
                new Action2<MainActivity, List<Tweet>>() {
                    @Override
                    public void call(MainActivity activity, List<Tweet> tweets) {
                        activity.updateTweets(tweets);
                        Log.d("log",tweets.toString());
                    }
                },
                new Action2<MainActivity, Throwable>() {
                    @Override
                    public void call(MainActivity activity, Throwable throwable) {
                        activity.onNetworkError(throwable);
                    }
                });
    }

    @Override
    public void onSave(@NonNull Bundle state) {
        super.onSave(state);
        state.putString(SEARCH_QUERY_KEY, searchQuery);
    }

    public void search(String query) {
        this.searchQuery = query;
        start(REQUEST_TWEETS);
    }

    public void refresh() {
        start(REQUEST_TWEETS);
    }
}
