package com.example.ramen.clashroyaleupdates;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramen.clashroyaleupdates.adapter.NewsAdapter;
import com.example.ramen.clashroyaleupdates.helper.VolleyUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private TextView mErrorMsg;
    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_news);
        mErrorMsg = (TextView) findViewById(R.id.tv_error_msg);
        mLoading = (ProgressBar) findViewById(R.id.pb_loading);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    private void showLoading() {
        mErrorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);

    }

    private void showNews() {
        mLoading.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void showError() {
        mLoading.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        mAdapter.setmNewsData(null);
        showLoading();

        String url = "https://clashroyale.com/blog/news";

        VolleyUtils.sendVolleyStringRequest(MainActivity.this, url, new VolleyUtils.VolleyRequestListener() {
            @Override
            public void onResponse(String response) {
                parsePage(response);
                showNews();
            }

            @Override
            public void onError(String error) {
                showError();
                mAdapter.setmNewsData(null);
            }
        });
    }

    private void parsePage(String page) {
        List<String> newsList = new ArrayList<String>();
        Document doc = Jsoup.parse(page);
        Elements divs = doc.getElementsByClass("home-news-primary-item-holder");
        for (Element div : divs) {
            Elements aTags = div.getElementsByTag("a");
            String dataLabels = aTags.attr("data-label");
            String news = dataLabels.substring(dataLabels.lastIndexOf("Image") + 8);
            Log.d(TAG, news);
            newsList.add(news);
        }
        String[] newsArr = new String[newsList.size()];
        newsList.toArray(newsArr);
        mAdapter.setmNewsData(newsArr);
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
            Context context = MainActivity.this;
            String toastText = "Refresh";
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
