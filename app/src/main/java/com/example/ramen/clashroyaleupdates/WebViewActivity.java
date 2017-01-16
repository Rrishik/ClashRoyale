package com.example.ramen.clashroyaleupdates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;

import com.example.ramen.clashroyaleupdates.helper.Util;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebPage;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page);
        mWebPage = (WebView) findViewById(R.id.wv_page);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        if (toolbar != null){
//            setSupportActionBar(toolbar);
//        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        link = extras.getString("link");
        Util.openInWebView(mWebPage, link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.open_in_browser) {
            Util.openInBrowser(link, WebViewActivity.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
