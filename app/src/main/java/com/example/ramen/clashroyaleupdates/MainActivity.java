package com.example.ramen.clashroyaleupdates;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.ramen.clashroyaleupdates.adapter.AdapterData;
import com.example.ramen.clashroyaleupdates.adapter.NewsAdapter;
import com.example.ramen.clashroyaleupdates.helper.PageParser;
import com.example.ramen.clashroyaleupdates.helper.Util;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.RecyclerViewClickListener {

    private String TAG = getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private TextView mErrorMsg;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebView mWebPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_news);
        mErrorMsg = (TextView) findViewById(R.id.tv_error_msg);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        mWebPage = (WebView) findViewById(R.id.wv_page);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NewsAdapter(MainActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);
        loadData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void showNews() {
        mErrorMsg.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    private void loadData() {

        final String url = "https://clashroyale.com/blog/news";

        PageParser p = new PageParser(MainActivity.this, url, new PageParser.PageParseListener() {
            @Override
            public void onResponse(List<AdapterData> dataList) {
                mAdapter.setData(dataList);
                mSwipeRefreshLayout.setRefreshing(false);
                showNews();
            }

            @Override
            public void onError(String error) {
                showError();
                mAdapter.clear();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_refresh) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(String link) {
        //Util.openInBrowser(link, MainActivity.this);
        mWebPage.setVisibility(View.VISIBLE);
        Util.openInWebView(mWebPage, link);
    }
}
