package com.example.webpagelogs.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordStatisticRepository extends JpaRepository<WordStatistic, Long> {

    // Find a word for a specific URL
    Optional<WordStatistic> findByUrlAndWord(String url, String word);
}
