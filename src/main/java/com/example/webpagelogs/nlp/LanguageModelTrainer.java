package com.example.webpagelogs.nlp;

import com.example.webpagelogs.fetcher.WebFetcher;
import opennlp.tools.langdetect.LanguageDetectorME;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LanguageModelTrainer {

    public List<Map<String,Object>> processUrl(String url) {
        try {
            String text = WebFetcher.fetchTextFromUrl(url);

            LanguageDetectorME detector = LanguageDetector.getDetector();

            String[] words = text.split("\\W+");
            Map<String,Integer> counts = new HashMap<>();

            for (String w : words) {
                w = w.toLowerCase();
                if (w.isEmpty() || w.matches("\\d+")) continue;
                counts.put(w, counts.getOrDefault(w, 0) + 1);
            }

            List<Map.Entry<String,Integer>> top10 = counts.entrySet().stream()
                    .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                    .limit(10)
                    .collect(Collectors.toList());

            List<Map<String,Object>> result = new ArrayList<>();
            for (Map.Entry<String,Integer> entry : top10) {
                String word = entry.getKey();
                int count = entry.getValue();
                String lang = detector.predictLanguage(word).getLang();

                Map<String,Object> row = new HashMap<>();
                row.put("word", word);
                row.put("count", count);
                row.put("lang", lang);
                result.add(row);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error processing URL: " + e.getMessage(), e);
        }
    }
}
