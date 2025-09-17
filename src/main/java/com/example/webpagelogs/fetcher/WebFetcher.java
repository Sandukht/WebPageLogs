package com.example.webpagelogs.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebFetcher {

    public static String fetchTextFromUrl(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        return doc.body().text();
    }
}
