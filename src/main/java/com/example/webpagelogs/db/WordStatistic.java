package com.example.webpagelogs.db;

import jakarta.persistence.*;

@Entity
@Table(name = "word_statistics", uniqueConstraints = {
@UniqueConstraint(columnNames = {"url", "word"})
})
public class WordStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String word;
    private int count;
    private String lang;

    public WordStatistic(String url, String word, int count, String lang) {
        this.url = url;
        this.word = word;
        this.count = count;
        this.lang = lang;   
    }
    public WordStatistic() {
    }
   
    public Long getId() {
        return id;
    }       
    public void setId(Long id) {
        this.id = id;
    }       
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
}