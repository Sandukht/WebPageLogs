package com.example.webpagelogs;

import com.example.webpagelogs.input.Input;
import com.example.webpagelogs.db.WordStatisticService;
import com.example.webpagelogs.fetcher.WebFetcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class WebPageLogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPageLogsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(WordStatisticService wordStatisticService) {
        return args -> {
            String url = Input.getUrlFromUser();

            try {
                String text = WebFetcher.fetchTextFromUrl(url);
                String lang = detectLanguage(text);

                // Load stopwords
                Set<String> stopwordsRu = loadStopwords("/stopwords/stopwords_ru.txt", new Locale("ru"));
                Set<String> stopwordsEn = loadStopwords("/stopwords/stopwords_en.txt", Locale.ENGLISH);
                Set<String> stopwordsHy = loadStopwords("/stopwords/stopwords_hy.txt", new Locale("hy"));

                Set<String> allStopwords = new HashSet<>();
                allStopwords.addAll(stopwordsRu);
                allStopwords.addAll(stopwordsEn);
                allStopwords.addAll(stopwordsHy);

                // Count words
                Map<String, Integer> wordCount = new HashMap<>();
                String[] words = text.split("\\W+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if (word.isEmpty()) continue;
                    if (word.matches("\\d+")) continue; // skip numbers
                    if (allStopwords.contains(word)) continue; // skip stopwords

                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }

                // Get top 10
                List<Map.Entry<String, Integer>> top10 = wordCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(10)
                        .collect(Collectors.toList());

                // Save to DB
                for (Map.Entry<String, Integer> entry : top10) {
                    wordStatisticService.processWord(url, entry.getKey(), entry.getValue(), lang);
                }

                // Print final result
                System.out.println("\n=== Top 10 words for URL: " + url + " ===");
                for (Map.Entry<String, Integer> entry : top10) {
                    System.out.printf("Word: %s, Count: %d, Lang: %s%n",
                            entry.getKey(), entry.getValue(), lang);
                }
                System.out.println("=== Word statistics updated ===");

            } catch (Exception e) {
                System.err.println("Error processing URL: " + e.getMessage());
            }
        };
    }

    private static String detectLanguage(String text) {
        int ruCount = 0, enCount = 0, hyCount = 0;

        for (char ch : text.toCharArray()) {
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) enCount++;
            else if ((ch >= 'а' && ch <= 'я') || (ch >= 'А' && ch <= 'Я')) ruCount++;
            else if ((ch >= 0x0531 && ch <= 0x0556) || (ch >= 0x0561 && ch <= 0x0587)) hyCount++;
        }

        if (ruCount >= enCount && ruCount >= hyCount) return "ru";
        else if (enCount >= ruCount && enCount >= hyCount) return "en";
        else return "hy";
    }

    private static Set<String> loadStopwords(String resourcePath, Locale locale) {
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
