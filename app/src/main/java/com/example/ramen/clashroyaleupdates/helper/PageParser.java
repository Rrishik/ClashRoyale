package com.example.ramen.clashroyaleupdates.helper;

import android.util.Log;

import com.example.ramen.clashroyaleupdates.MainActivity;
import com.example.ramen.clashroyaleupdates.adapter.NewsAdapter;

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


    public static String[] parsePage_news(String page) {
        List<String> newsList = new ArrayList<String>();
        Document doc = Jsoup.parse(page);
        Elements divs = doc.getElementsByClass("home-news-primary-item-holder");
        for (Element div : divs) {
            Elements aTags = div.getElementsByTag("a");
            String dataLabels = aTags.attr("data-label");
            String news = dataLabels.substring(dataLabels.lastIndexOf("Image") + 8);
            Log.d("PageParser", news);
            newsList.add(news);
        }
        String[] newsArr = new String[newsList.size()];
        newsList.toArray(newsArr);
        return newsArr;
    }

    public static String[] parsePage_image(String page){
        List<String> newsList = new ArrayList<String>();
        Document doc = Jsoup.parse(page);
        Elements divs = doc.getElementsByClass("home-news-primary-item-holder");
        for (Element div : divs) {
            Elements srcTags = div.getElementsByTag("source");
            String imgLinks = srcTags.attr("data-srcset");

            Log.d("PageParser", imgLinks);
            newsList.add(imgLinks);
        }
        String[] imgArr = new String[newsList.size()];
        newsList.toArray(imgArr);
        return imgArr;
    }
}
