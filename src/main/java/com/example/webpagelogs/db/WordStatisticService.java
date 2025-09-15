package com.example.webpagelogs.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordStatisticService {

    @Autowired
    private WordStatisticRepository repository;

    public void processWord(String url, String word, int count, String lang) {
        WordStatistic ws = repository.findByUrlAndWord(url, word).orElse(null);
        if (ws != null) {
            ws.setCount(count);
            ws.setLang(lang);
            repository.save(ws); //Hibernate will update
        } else {
            ws = new WordStatistic(url, word, count, lang);
            repository.save(ws); //Hibernate will insert
        }
    }
}


