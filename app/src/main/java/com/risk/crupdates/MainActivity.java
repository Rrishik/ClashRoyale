package com.risk.crupdates;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.risk.crupdates.adapter.AdapterData;
import com.risk.crupdates.adapter.NewsAdapter;
import com.risk.crupdates.helper.PageParser;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity implements NewsAdapter.RecyclerViewClickListener {

    private String TAG = getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private TextView mErrorMsg;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.risk.crupdates.R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(com.risk.crupdates.R.id.rv_news);
        mErrorMsg = (TextView) findViewById(com.risk.crupdates.R.id.tv_error_msg);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(com.risk.crupdates.R.id.srl_refresh);

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
        mSwipeRefreshLayout.setColorSchemeResources(com.risk.crupdates.R.color.colorAccent, android.R.color.holo_blue_bright,
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
        getMenuInflater().inflate(com.risk.crupdates.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == com.risk.crupdates.R.id.action_refresh) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(String link) {
        //FinestWebView.Builder();
        //Intent intent = new Intent(this, WebViewActivity.class);
        //intent.putExtra(Constants.Intent.SELECTED_LINK, link);
        int[] colors = {BLACK, DKGRAY, LTGRAY};
        //startActivity(intent);
        new FinestWebView.Builder(this)
                .updateTitleFromHtml(true)
                .titleColor(WHITE)
                .toolbarColor(rgb(0, 50, 150))
                .iconDefaultColor(WHITE)
                .iconPressedColor(GRAY)
                .showIconClose(true)
                .showIconMenu(true)
                .showSwipeRefreshLayout(false)
                .swipeRefreshColors(colors)
                .showProgressBar(true)
                .progressBarColor(LTGRAY)
                .showUrl(true)
                .urlColor(rgb(240, 240, 240))
                .backPressToClose(true)
                .menuColorRes(com.risk.crupdates.R.color.grey_percent_95)
                .menuTextSize(30)
                .setCustomAnimations(com.risk.crupdates.R.anim.activity_open_enter, com.risk.crupdates.R.anim.activity_open_exit, com.risk.crupdates.R.anim.activity_close_enter, com.risk.crupdates.R.anim.activity_close_exit)
                .show(link);
    }
}
