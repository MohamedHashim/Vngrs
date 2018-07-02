package com.mohamedhashim.vngrs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */

public class Tweet implements Parcelable {
    @SerializedName("user") TwitterUser user;
    @SerializedName("text") String body;

    protected Tweet(Parcel in) {
        body = in.readString();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    /**
     *
     * @return The TwitterUser of the user who sent the tweet
     */
    public TwitterUser getUser() {
        return user;
    }

    /**
     *
     * @return The body of tweet
     */
    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (user != null ? !user.equals(tweet.user) : tweet.user != null) return false;
        return body != null ? body.equals(tweet.body) : tweet.body == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "user=" + user +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
    }
}
