package com.example.ramen.clashroyaleupdates.helper;


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

    public static AdapterData[] parsePage(String page) {
        List<AdapterData> newsList = new ArrayList<AdapterData>();
        Document doc = Jsoup.parse(page);
        Elements divs = doc.getElementsByClass("home-news-primary-item-holder");
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
        AdapterData[] newsArr = new AdapterData[newsList.size()];
        newsList.toArray(newsArr);
        return newsArr;
    }

}
