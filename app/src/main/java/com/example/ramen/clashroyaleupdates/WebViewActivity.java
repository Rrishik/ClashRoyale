package com.example.ramen.clashroyaleupdates;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.ramen.clashroyaleupdates.helper.Constants;
import com.example.ramen.clashroyaleupdates.helper.Util;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private WebView mWebPage;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_page);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Clash Royale");
        }

        mWebPage = (WebView) findViewById(R.id.wv_page);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(TAG, "No data specified for webview");
            return;
        }
        link = extras.getString(Constants.Intent.SELECTED_LINK);
        if (link == null || link.trim().isEmpty()) {
            Log.e(TAG, "No URL specified for webview");
            return;
        }
        Util.openInWebView(mWebPage, link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.open_in_browser:
                Util.openInBrowser(link, WebViewActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
