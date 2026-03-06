package com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.model.Question;

@Entity
@Table(name = "quiz_sessions")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName;

    @Enumerated(EnumType.STRING)
    private Question.Category category;

    private int score;
    private int totalQuestions;
    private LocalDateTime completedAt;

    // ===== CONSTRUCTEUR =====
    public QuizSession() {}

    // ===== GETTERS =====
    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Question.Category getCategory() {
        return category;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public double getPercentage() {
        if (totalQuestions == 0) return 0;
        return (double) score / totalQuestions * 100;
    }

    // ===== SETTERS =====
    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setCategory(Question.Category category) {
        this.category = category;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}