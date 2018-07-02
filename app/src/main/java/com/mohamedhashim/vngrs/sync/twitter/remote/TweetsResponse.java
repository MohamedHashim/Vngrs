package com.mohamedhashim.vngrs.sync.twitter.remote;

import com.google.gson.annotations.SerializedName;
import com.mohamedhashim.vngrs.models.Tweet;

import java.util.List;

public class TweetsResponse {
    @SerializedName("statuses") public List<Tweet> tweets;
}
