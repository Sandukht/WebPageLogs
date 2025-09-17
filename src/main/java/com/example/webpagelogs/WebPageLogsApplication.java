package com.example.webpagelogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootApplication
public class WebPageLogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPageLogsApplication.class, args);
    }

    // Used by LanguageModelTrainer to load stopwords
    public static Set<String> loadStopwords(String resourcePath, Locale locale) {
        Set<String> stopwords = new HashSet<>();
        try (InputStream is = WebPageLogsApplication.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stopwords.add(line.trim().toLowerCase(locale));
            }
        } catch (Exception e) {
            System.err.println("Error loading stopwords from " + resourcePath + ": " + e.getMessage());
        }
        return stopwords;
    }
}
