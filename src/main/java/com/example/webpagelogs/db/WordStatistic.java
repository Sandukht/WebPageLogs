package com.example.webpagelogs.db;

import jakarta.persistence.*;

@Entity
@Table(name = "word_statistics")
public class WordStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String word;
    private int count;
    private String language;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
