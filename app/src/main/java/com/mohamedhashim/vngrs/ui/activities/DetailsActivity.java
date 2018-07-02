package com.mohamedhashim.vngrs.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mohamedhashim.vngrs.BuildConfig;
import com.mohamedhashim.vngrs.R;
import com.mohamedhashim.vngrs.models.Tweet;
import com.mohamedhashim.vngrs.models.TwitterUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Hashim on 7/1/2018.
 */
public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        ButterKnife.setDebug(BuildConfig.DEBUG);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TwitterUser tweetUser = getIntent().getParcelableExtra("userObject");
            Tweet tweet = getIntent().getParcelableExtra("tweetObjct");
            Log.d("details", tweet.getBody() + "\n" + tweetUser.getUserName());
            userName.setText(tweetUser.getUserName());
            message.setText(tweet.getBody());
            Glide.with(getApplicationContext())
                    .load(tweetUser.getImageUrl())
                    .into(userImage);
            if (tweet.getBody().contains("https://")) {
                String url;
                Matcher matcher = urlPattern.matcher(tweet.getBody());
                while (matcher.find()) {
                    int matchStart = matcher.start(1);
                    int matchEnd = matcher.end();
                    url = tweet.getBody().substring(matchStart, matchEnd);
                    initWebView(url);
                }
            } else
                webView.setVisibility(View.GONE);
        }
    }

    private void initWebView(String url) {
        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webView.loadUrl(url);
    }
}
