package com.sentimant.service;


import com.sentimant.entity.SentimentFeedEntity;
import com.sentimant.enums.SentimentEnum;
import com.sentimant.repository.SentimentRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SentimentService {

    private ChatClient chatClient;
    private SentimentRepository sentimentRepository;

    public SentimentService(ChatClient.Builder builder, SentimentRepository sentimentRepository) {
        this.sentimentRepository = sentimentRepository;
        this.chatClient = builder.build();
    }

    public SentimentFeedEntity save(String content) {

        SentimentFeedEntity sentimentFeedEntity =analysisSentiment(content);
        sentimentFeedEntity= this.sentimentRepository.save(sentimentFeedEntity);
        return sentimentFeedEntity;
    }

    private SentimentFeedEntity analysisSentiment(String content) {

        String prompt=String.format("""
            Analyze the sentiment of the following text and respond with only one word: POSITIVE, NEUTRAL, or NEGATIVE.
            Also provide a sentiment score between -1 and 1 where:
            -1 is most negative
            0 is neutral
            1 is most positive
            
            Format the response as: SENTIMENT_TYPE|SCORE
            
            Text to analyze: %s
            """, content);

        String response = chatClient.prompt(prompt).call().content();

        String[] sentiments = response.split("\\|");

        SentimentFeedEntity sentimentFeedEntity = new SentimentFeedEntity();
        sentimentFeedEntity.setContent(content);
        sentimentFeedEntity.setSentimentScore(Double.parseDouble(sentiments[1]));
        sentimentFeedEntity.setSentiment(SentimentEnum.valueOf(sentiments[0]));
        sentimentFeedEntity.setCreatedAt(LocalDateTime.now());

        return sentimentFeedEntity;
    }

    public List<SentimentFeedEntity> getAllSentiments() {
        return sentimentRepository.findAll();
    }

}
