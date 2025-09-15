package com.example.webpagelogs.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;

public class WebFetcher {

    public static String fetchTextFromUrl(String inputUrl) throws IOException {
        String url = normalizeUrl(inputUrl);

        Document doc = Jsoup.connect(url)
                .timeout(10000)
                .get();

        // Remove scripts, styles, noscripts
        doc.select("script, style, noscript").remove();
        return doc.body().text();
    }

    private static String normalizeUrl(String inputUrl) throws MalformedURLException {
        try {
            // Prepend https:// if missing
            if (!inputUrl.matches("^(http|https)://.*$")) {
                inputUrl = "https://" + inputUrl;
            }

            // Validate URL
            URL validated = new URL(inputUrl);

            // Normalize and ensure trailing slash
            String normalized = validated.toURI().normalize().toString();
            if (!normalized.endsWith("/")) {
                normalized += "/";
            }

            System.out.println("DEBUG: Using URL = " + normalized); // For debugging
            return normalized;

        } catch (URISyntaxException e) {
            // Convert to MalformedURLException so caller only has to handle one type
            throw new MalformedURLException("Invalid URL syntax: " + inputUrl);
        }
    }
}
