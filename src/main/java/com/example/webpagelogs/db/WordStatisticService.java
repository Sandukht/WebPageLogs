package com.example.webpagelogs.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordStatisticService {

    @Autowired
    private WordStatisticRepository repository;

    public WordStatistic saveOrUpdate(String url, String word, int count, String language) {
        Optional<WordStatistic> existingOpt = repository.findByUrlAndWord(url, word);

        WordStatistic stat;
        if (existingOpt.isPresent()) {
            stat = existingOpt.get();
            stat.setCount(stat.getCount() + count); // increment existing count
            stat.setLanguage(language); // update language if needed
        } else {
            stat = new WordStatistic();
            stat.setUrl(url);
            stat.setWord(word);
            stat.setCount(count);
            stat.setLanguage(language);
        }
        return repository.save(stat);
    }
}
