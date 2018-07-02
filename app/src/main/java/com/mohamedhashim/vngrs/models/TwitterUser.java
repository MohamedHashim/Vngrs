package com.mohamedhashim.vngrs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public class TwitterUser implements Parcelable {
    @SerializedName("name") String userName;
    @SerializedName("profile_image_url_https") String imageUrl;

    protected TwitterUser(Parcel in) {
        userName = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<TwitterUser> CREATOR = new Creator<TwitterUser>() {
        @Override
        public TwitterUser createFromParcel(Parcel in) {
            return new TwitterUser(in);
        }

        @Override
        public TwitterUser[] newArray(int size) {
            return new TwitterUser[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterUser that = (TwitterUser) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null)
            return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "userName='" + userName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(imageUrl);
    }
}
