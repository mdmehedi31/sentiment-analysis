package com.sentimant.repository;

import com.sentimant.entity.SentimentFeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentimentRepository extends JpaRepository<SentimentFeedEntity, Long> {
}
