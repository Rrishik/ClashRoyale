package com.example.ramen.clashroyaleupdates.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.ramen.clashroyaleupdates.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Util {

    private String TAG = getClass().getSimpleName();

    public static void openInWebView(final WebView mWebPage, final String link) {
        WebSettings webSettings = mWebPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebPage.setWebViewClient(new WebViewClient());
        //Log.d(TAG, "openInWebView: ");
        mWebPage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebPage.loadUrl(link);
            }
        }, 500);
    }

    public static void openInBrowser(String link, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(browserIntent);
    }

    public static void loadImageByPicasso(Context context, ImageView imageView, String imageUrl) {

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .noFade()
                .into(imageView);
    }

    private static Date convertToDate(String crDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        try {
            return sdf.parse(crDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertToString(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    public static String reformatDate(String crDate) {
        return convertToString(convertToDate(crDate));
    }

}
