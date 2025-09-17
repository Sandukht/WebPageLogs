package com.example.webpagelogs.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WordStatisticRepository extends JpaRepository<WordStatistic, Long> {
    Optional<WordStatistic> findByUrlAndWord(String url, String word);
}
