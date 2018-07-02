package com.mohamedhashim.vngrs.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mohamedhashim.vngrs.R;
import com.mohamedhashim.vngrs.VngrsApplication;
import com.mohamedhashim.vngrs.models.Tweet;
import com.mohamedhashim.vngrs.ui.adapters.TweetAdapter;
import com.mohamedhashim.vngrs.ui.presenters.MainPresenter;
import com.mohamedhashim.vngrs.ui.utils.SimpleDividerItemDecoration;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainPresenter>
        implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.searchButton)
    ImageButton searchButton;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.searchQuery)
    EditText searchQuery;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private TweetAdapter tweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        VngrsApplication.getAppComponent(this).inject(this);

        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        swipeRefresh.setOnRefreshListener(this);

        tweetAdapter = new TweetAdapter(new ArrayList<Tweet>(), this);

        recyclerView.setAdapter(tweetAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));
        searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    saveSearchQuery(searchQuery.getText().toString());
                    hideSoftKeyboard();
                    getPresenter().search(searchQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });
        if (Hawk.isBuilt()) {
            Log.d("query", Hawk.get("query").toString());
            searchQuery.setText(Hawk.get("query").toString());
            getPresenter().search(Hawk.get("query").toString());
        }
    }

    @Override
    public void onRefresh() {
        hideSoftKeyboard();
        errorMessage.setVisibility(View.GONE);
        getPresenter().refresh();
    }

    @OnEditorAction(R.id.searchQuery)
    boolean onKeyboardAction() {
        hideSoftKeyboard();
        errorMessage.setVisibility(View.GONE);
        getPresenter().search(searchQuery.getText().toString());
        return true;
    }

    @OnClick(R.id.searchButton)
    public void onSearchButtonClicked() {
        hideSoftKeyboard();
        errorMessage.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(true);
        getPresenter().search(searchQuery.getText().toString());
        saveSearchQuery(searchQuery.getText().toString());
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void updateTweets(List<Tweet> newTweets) {
        swipeRefresh.setRefreshing(false);
        tweetAdapter.setTweets(newTweets);
        tweetAdapter.notifyDataSetChanged();
    }

    public void onNetworkError(Throwable throwable) {
        swipeRefresh.setRefreshing(false);
        tweetAdapter.clear();
        tweetAdapter.notifyDataSetChanged();
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void saveSearchQuery(String query) {
        Hawk.init(getApplicationContext()).build();
        Hawk.put("query", query);
    }
}
