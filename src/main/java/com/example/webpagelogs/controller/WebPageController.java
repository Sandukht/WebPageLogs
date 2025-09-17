package com.example.webpagelogs.controller;

import com.example.webpagelogs.db.WordStatisticService;
import com.example.webpagelogs.nlp.LanguageModelTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")  // allow React
@RestController
@RequestMapping("/api")
public class WebPageController {

    @Autowired
    private LanguageModelTrainer trainer;

    @Autowired
    private WordStatisticService wordService;

    @PostMapping("/analyse")
    public List<Map<String, Object>> analyzet(@RequestParam String url) {
        List<Map<String, Object>> topWords = trainer.processUrl(url);

        // Save/update in DB
        for (Map<String, Object> row : topWords) {
            String word = (String) row.get("word");
            int count = (Integer) row.get("count");
            String lang = (String) row.get("lang");

            wordService.saveOrUpdate(url, word, count, lang);
        }

        return topWords; // ✅ return JSON directly
    }
}
