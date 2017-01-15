package com.example.ramen.clashroyaleupdates.helper;


import android.content.Context;

import com.example.ramen.clashroyaleupdates.adapter.AdapterData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by risk on 09-01-2017.
 */

public class PageParser {

    private Context mContext;
    private Document mDocument;

    public interface PageParseListener {

        void onResponse(List<AdapterData> dataList);

        void onError(String error);

    }

    public PageParser(Context context, String pageUrl, PageParseListener listener) {

        mContext = context;
        fetchPageContent(pageUrl, listener);

    }

    private void fetchPageContent(String pageUrl, final PageParseListener listener) {

        VolleyUtils.sendVolleyStringRequest(mContext, pageUrl, new VolleyUtils.VolleyRequestListener() {
            @Override
            public void onResponse(String response) {

                mDocument = Jsoup.parse(response);
                if (listener != null)
                    listener.onResponse(getPageData());
            }

            @Override
            public void onError(String error) {

                if (listener != null)
                    listener.onError(error);
            }
        });
    }

    private List<AdapterData> getPageData() {
        // TODO this should only parse from the global mDocument and return the array
        // TODO do not forget null checks

        List<AdapterData> newsList = new ArrayList<>();
        Elements divs = mDocument.getElementsByClass("home-news-primary-item-holder");
        for (Element div : divs) {
            Elements aTags = div.getElementsByTag("a");
            String dataLabels = aTags.attr("data-label");
            Elements srcTags = div.getElementsByTag("source");
            String imgLinks = srcTags.attr("data-srcset");
            String news = dataLabels.substring(dataLabels.lastIndexOf("Image") + 8);
            String date = div.getElementsByClass("home-news-primary-item-date").text();
            AdapterData data = new AdapterData(news, imgLinks, date);
            newsList.add(data);
        }
//        AdapterData[] newsArr = new AdapterData[newsList.size()];
//        newsList.toArray(newsArr);
        return newsList;
    }

}
