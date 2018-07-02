package com.mohamedhashim.vngrs.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.mohamedhashim.vngrs.R;
import com.mohamedhashim.vngrs.models.Tweet;
import com.mohamedhashim.vngrs.ui.activities.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    public TweetAdapter(List<Tweet> tweets, Context context) {
        this.tweets = tweets;
        this.context = context;
    }

    public void setTweets(List<Tweet> newTweets) {
        tweets = newTweets;
    }

    public void clear() {
        tweets = new ArrayList<Tweet>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Tweet tweet = tweets.get(position);
        holder.title.setText(tweet.getUser().getUserName());
        holder.message.setText(tweet.getBody());
        Glide.with(context)
                .load(tweet.getUser().getImageUrl())
                .into(holder.userImage);

        TextCrawler textCrawler = new TextCrawler();
        LinkPreviewCallback linkPreviewCallback = new LinkPreviewCallback() {
            @Override
            public void onPre() {
                holder.loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPos(SourceContent sourceContent, boolean b) {
                if (!sourceContent.getImages().isEmpty() && sourceContent.getImages().size() > 6) {
                    holder.loading.setVisibility(View.GONE);
                    Glide.with(context).load(sourceContent.getImages().get(5))
                            .into(holder.messageImage);
                }
            }
        };
        if (tweet.getBody().length() < 255 && tweet.getBody().contains("https://")) {
            textCrawler.makePreview(linkPreviewCallback, Uri.parse(tweet.getBody()).toString());
            Log.d("log_tweet", Uri.parse(tweet.getBody()).toString());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tweet tweet = tweets.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("userObject", tweet.getUser());
                intent.putExtra("tweetObjct", tweet);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.message)
        TextView message;
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.messageImage)
        ImageView messageImage;
        @BindView(R.id.loading)
        ProgressBar loading;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
