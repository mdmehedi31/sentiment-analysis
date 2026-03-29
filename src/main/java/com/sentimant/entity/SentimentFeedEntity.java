package com.sentimant.entity;


import com.sentimant.enums.SentimentEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_sentiment_feed")
public class SentimentFeedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "sentiment_score")
    private Double sentimentScore;

    @Column(name = "created_at")
    private LocalDateTime createTime;

    @Column(name = "sentiment")
    @Enumerated(EnumType.STRING)
    private SentimentEnum sentiment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public SentimentEnum getSentiment() {
        return sentiment;
    }

    public void setSentiment(SentimentEnum sentiment) {
        this.sentiment = sentiment;
    }
}
